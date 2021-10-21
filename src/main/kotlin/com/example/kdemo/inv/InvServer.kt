package com.example.kdemo.inv

import com.example.kdemo.ObservableError
import com.example.kdemo.inv.domain.InvLayout
import com.example.kdemo.inv.repo.ContainerRepo
import com.example.kdemo.inv.repo.InvChangeRepo
import com.example.kdemo.inv.repo.InvLayoutRepo
import com.example.kdemo.inv.repo.InvSummaryRepo
import org.springframework.stereotype.Service
import javax.transaction.Transactional
import kotlin.math.min

@Service
class InvServer(
    val invLayoutRepo: InvLayoutRepo,
    val changeRepo: InvChangeRepo,
    val summaryRepo: InvSummaryRepo,
    val containerRepo: ContainerRepo
) {
    // TODO 库存变更
    
    /**
     * 上架
     */
    @Transactional
    fun putAway(bin: String, container: String, sourceOrder: String) {
        // todo 多业务单拼单出库时怎么办？
        
        val lines = invLayoutRepo.findAllByContainer(container)
        lines.forEach {
            it.bin = bin
            invLayoutRepo.save(it)
            
            val summary = summaryRepo.findByPart(it.part)
            summary.stockQty += it.qty
            summary.processingQty -= it.qty
            summaryRepo.save(summary)
        }
    }
    
    /**
     * 下架
     */
    @Transactional
    fun takeAway(bin: String, container: String, sourceOrder: String) {
        val lines = invLayoutRepo.findAllByContainer(container)
        lines.forEach {
            it.bin = ""
            invLayoutRepo.save(it)
            
            val summary = summaryRepo.findByPart(it.part)
            summary.stockQty -= it.qty
            summary.processingQty += it.qty
            summaryRepo.save(summary)
        }
    }
    
    
    /**
     * 装箱
     */
    @Transactional
    fun bind(bin: String, container: String, lines: List<InvLayout>, sourceOrder: String) {
        // 物料直接绑定库位
        lines.forEach {
            // 库存明细
            it.bin = bin
            invLayoutRepo.save(it)
            
            // 库存汇总
            val summary = summaryRepo.findByPart(it.part)
            
            if (container.isBlank()) {
                // 无容器装箱
                summary.stockQty += it.qty
            } else {
                // 有容器装箱
                summary.processingQty += it.qty
            }
            
            summaryRepo.save(summary)
        }
        
        if (container.isNotBlank())
            containerRepo.occupy(container, sourceOrder)
    }
    
    /**
     * 分拣
     *
     * cl 的 qty 取出为负，放入为正
     */
    @Transactional
    fun pick(bin: String, container: String, changeLines: List<InvLayout>, sourceOrder: String) {
        val oldLines = invLayoutRepo.findAllByBin(bin)
        
        changeLines.forEach { change ->
            // 找同物料的库存
            val partInvLayout = oldLines.filter { it.part == change.part }
            if (partInvLayout.isEmpty()) throw ObservableError("没有 ${change.part} 物料可供分拣")
            
            var left = change.qty
            var idx = 0
            while (left != 0.0 && idx < partInvLayout.size) {
                val l = partInvLayout[idx]
                if (l.qty == 0.0) {
                    idx++
                } else {
                    val m = min(l.qty, left) // 变化量
                    l.qty += m
                    left -= m
                }
            }
            
            if (left > 0) throw ObservableError("${change.part} 分拣数量 ${change.qty} 大于库存明细数")
        }
        
        invLayoutRepo.saveAll(oldLines)
        
        changeLines.forEach { change ->
            val partSummary = summaryRepo.findByPart(change.part)
            if (partSummary.stockQty + change.qty < 0)
                throw ObservableError("${change.part} 分拣数量 ${change.qty} 大于库存汇总")
            
            if (container.isEmpty()) {
                // 无容器装箱
                partSummary.stockQty += change.qty
            } else {
                // 有容器装箱
                partSummary.processingQty += change.qty
            }
            
            summaryRepo.save(partSummary)
        }
    }
    
    /**
     * 移库
     */
    @Transactional
    fun transferBin(fromBin: String, toBin: String) {
        val lines = invLayoutRepo.findAllByContainer(fromBin)
        
        lines.forEach {
            it.bin = toBin
        }
        
        invLayoutRepo.saveAll(lines)
    }
    
    /**
     * TODO 冻结
     */
    fun freeze(bin: String, container: String) {
    
    }
    
    /**
     * TODO 解冻
     */
    fun unfreeze(bin: String, container: String) {
    
    }
}
package com.example.kdemo.service

import com.example.kdemo.ObservableError
import com.example.kdemo.entity.WmsUnit
import com.example.kdemo.repository.WmsUnitRepo
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UnitService(private val unitRepo: WmsUnitRepo) {
    
    /**
     *
     * parent 找 child
     * 深度优先便利
     */
    fun isParent(parent: WmsUnit, child: WmsUnit): Boolean {
        val children = unitRepo.findAllByParent(parent.id)
        if (children.isEmpty()) return false
        
        var res = false
        children.forEach {
            res = if (it.id == child.id) {
                true
            } else {
                res || isParent(it, child)
            }
            
            if (res) return true
        }
        return false
    }
    
    /**
     *
     * child 找 parent
     */
    fun isChild(child: WmsUnit, parent: WmsUnit): Boolean {
        if (child.parent.isBlank()) return false
        
        val p = unitRepo.findByIdOrNull(child.parent) ?: return false
        return if (p.id == parent.id) {
            true
        } else {
            isChild(p, parent)
        }
    }
    
    fun split(qty: Double, large: WmsUnit, small: WmsUnit): Double {
        if (!isChild(small, large)) throw ObservableError("无法分解")
        
        val middle = unitRepo.findByIdOrNull(small.parent)
            ?: throw ObservableError("${small.parent} WmsUnit not found")
        
        return if (middle.id == large.id) {
            qty * small.conversionRate
        } else {
            split(qty, large, middle) * small.conversionRate
        }
    }
    
    fun combine(qty: Double, small: WmsUnit, large: WmsUnit): Double {
        if (!isChild(small, large)) throw ObservableError("无法组合")
        
        val middle = unitRepo.findByIdOrNull(small.parent)
            ?: throw ObservableError("${small.parent} WmsUnit not found")
        
        return if (middle.id == large.id) {
            qty / small.conversionRate
        } else {
            combine(qty, large, middle) / small.conversionRate
        }
    }
    
    fun convertToLarge(qty: Double, u: WmsUnit): Map<WmsUnit, Double> {
        if (u.conversionRate > qty) return mapOf(u to qty)
        val pu = unitRepo.findByIdOrNull(u.parent) ?: return mapOf(u to qty)
        
        val r = qty % u.conversionRate
        val largeMap = convertToLarge((qty - r) / u.conversionRate, pu).toMutableMap()
        largeMap[u] = r
        return largeMap
    }
    
}
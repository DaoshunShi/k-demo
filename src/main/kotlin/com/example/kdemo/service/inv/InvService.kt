package com.example.kdemo.service.inv

import com.example.kdemo.ObservableError
import com.example.kdemo.repository.InvDetailRepo
import com.example.kdemo.repository.InvOrderRepo
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class InvService(
    private val invDetailService: InvDetailService,
    private val invDetailRepo: InvDetailRepo,
    private val invOrderRepo: InvOrderRepo
) {
    
    private val logger = LoggerFactory.getLogger(javaClass)
    
    // 预定
    fun reserve(orderId: Int): Boolean {
        val order = invOrderRepo.findById(orderId).orElse(null) ?: throw ObservableError("找不到订单")
        
        // 找现有的 invDetail
        val invDetail = invDetailRepo.findInvDetailByPart(order.part).orElse(null)
        if (invDetail == null) {
            logger.warn("找不到库存")
            return false
        }
        
        // 更新 invOrder
        if (invDetailService.reserve(invDetail, order.qty)) {
            // 预定成功
            logger.info("预定成功")
            order.reserved = true
            invOrderRepo.save(order)
            return true
        } else {
            // 预定失败
            logger.info("预定失败")
            // TODO 加入等待队列
        }
        
        return false
    }
}
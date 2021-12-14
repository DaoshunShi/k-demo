package com.example.kdemo.service.inv

import com.example.kdemo.entity.InvDetail
import com.example.kdemo.repository.InvDetailRepo
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.persistence.EntityManager
import javax.transaction.Transactional

@Service
class InvDetailService(
    private val invDetailRepo: InvDetailRepo,
    private val entityManager: EntityManager
) {
    private val logger = LoggerFactory.getLogger(javaClass)
    
    @Transactional
    fun create(ev: InvDetail) {
        entityManager.persist(ev)
    }
    
    @Transactional
    fun replace(ev: InvDetail) {
        entityManager.merge(ev)
    }
    
    @Synchronized
    @Transactional
    fun reserve(ev: InvDetail, qty: Double): Boolean {
        val exist = invDetailRepo.findByIdOrNull(ev.id) ?: return false
        return if (exist.qty >= qty) {
            exist.qty -= qty
            invDetailRepo.save(exist)
            true
        } else {
            logger.info("${ev.part} 库存不足，需要：${ev.qty}，现有：$qty")
            false
        }
    }
}
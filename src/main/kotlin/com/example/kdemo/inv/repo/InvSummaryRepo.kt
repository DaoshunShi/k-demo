package com.example.kdemo.inv.repo

import com.example.kdemo.inv.domain.InvSummary
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface InvSummaryRepo : PagingAndSortingRepository<InvSummary, String> {
    
    @Query("update InvSummary e set e.stockQty = :stockQty , e.processingQty = :processingQty, e.freezeQty = :freezeQty")
    @Transactional
    @Modifying
    fun updateQty(stockQty: Double, processingQty: Double, freezeQty: Double)
    
    fun findByPart(part: String): InvSummary
}
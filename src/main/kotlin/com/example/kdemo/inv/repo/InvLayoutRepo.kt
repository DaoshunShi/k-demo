package com.example.kdemo.inv.repo

import com.example.kdemo.inv.domain.InvLayout
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface InvLayoutRepo : PagingAndSortingRepository<InvLayout, String> {
    
    fun findAllByContainer(container: String) : List<InvLayout>
    
    fun findAllByBin(bin: String): List<InvLayout>
    
}
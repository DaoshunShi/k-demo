package com.example.kdemo.repository

import com.example.kdemo.entity.WmsUnit
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface WmsUnitRepo : PagingAndSortingRepository<WmsUnit, String> {
    
    fun findAllByParent(parent: String): List<WmsUnit>
    
}
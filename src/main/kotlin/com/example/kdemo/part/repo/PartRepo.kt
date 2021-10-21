package com.example.kdemo.part.repo

import com.example.kdemo.part.domain.Part
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface PartRepo : PagingAndSortingRepository<Part, String> {
    
    fun findByCode(code: String): Part?
    
    fun findAllByCategory(category: String): List<Part>
}
package com.example.kdemo.part.service

import com.example.kdemo.part.domain.Part
import com.example.kdemo.part.domain.PartCategory
import com.example.kdemo.part.repo.PartCategoryRepo
import com.example.kdemo.part.repo.PartRepo
import org.springframework.stereotype.Service

@Service
class PartService(
    val partRepo: PartRepo,
    val partCategoryRepo: PartCategoryRepo
) {
    fun createPart(part: Part): Part {
        return partRepo.save(part)
    }
    
    fun createCategory(category: PartCategory): PartCategory {
        return partCategoryRepo.save(category)
    }
    
    fun findOrCreatePart(part: Part): Part {
        val exist = partRepo.findByCode(code = part.code)
        if (exist != null) return exist
        
        return createPart(part)
    }
    
    fun findAllByCategory(category: String): List<Part> {
        return partRepo.findAllByCategory(category)
    }
}
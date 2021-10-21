package com.example.kdemo.part.repo

import com.example.kdemo.part.domain.PartCategory
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Service

@Service
interface PartCategoryRepo : PagingAndSortingRepository<PartCategory, String> {
}
package com.example.kdemo.repository

import com.example.kdemo.entity.InvOrder
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface InvOrderRepo : PagingAndSortingRepository<InvOrder, Int> {
}
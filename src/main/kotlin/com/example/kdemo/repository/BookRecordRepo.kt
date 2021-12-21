package com.example.kdemo.repository

import com.example.kdemo.entity.BookRecord
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRecordRepo : PagingAndSortingRepository<BookRecord, Int> {
    
    fun findByBookAndNo(book: Int, no: Int): BookRecord?
}
package com.example.kdemo.repository

import com.example.kdemo.entity.BookBorrowRecord
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface BookBorrowRecordRepo: PagingAndSortingRepository<BookBorrowRecord, Int> {
    
    fun findByBookRecordAndUserAndReturned(bookRecord: Int, user:String, returned: Boolean): BookBorrowRecord?
}
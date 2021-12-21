package com.example.kdemo.repository

import com.example.kdemo.entity.Book
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepo : PagingAndSortingRepository<Book, Int> {
    
    fun findBooksByIsbn(isbn: String): List<Book>?
}
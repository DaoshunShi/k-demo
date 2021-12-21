package com.example.kdemo.repository

import com.example.kdemo.entity.BookCategory
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface BookCategoryRepo : PagingAndSortingRepository<BookCategory, Int> {


}
package com.example.kdemo.service.book

import com.example.kdemo.ObservableError
import com.example.kdemo.entity.Book
import com.example.kdemo.entity.BookBorrowRecord
import com.example.kdemo.entity.BookRecord
import com.example.kdemo.helper.JsonHelper
import com.example.kdemo.repository.BookBorrowRecordRepo
import com.example.kdemo.repository.BookCategoryRepo
import com.example.kdemo.repository.BookRecordRepo
import com.example.kdemo.repository.BookRepo
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.annotation.PostConstruct

@Service
class BookService(
    private val bookRepo: BookRepo,
    private val borrowRecordRepo: BookBorrowRecordRepo,
    private val bookRecordRepo: BookRecordRepo,
    private val bookCategoryRepo: BookCategoryRepo
) {
    
    @PostConstruct
    fun init() {
    
    }
    
    fun inboundBooks(addBookReq: AddBookReq) {
        val bo = bookRepo.findById(addBookReq.bookId)
        if (bo.isEmpty) throw ObservableError("找不到图书[${addBookReq.bookId}]")
        
        val book = bo.get()
        val records = mutableListOf<BookRecord>()
        repeat(addBookReq.counts) {
            records[it] = BookRecord(book = book.id, no = it + 1)
        }
        
        bookRecordRepo.saveAll(records)
    }
    
    fun findOrCreateBook(findBookReq: FindBookReq): List<Book> {
        val books = synchronized(this) { bookRepo.findBooksByIsbn(findBookReq.isbn) }
        
        if (books.isNullOrEmpty()) addBook(findBookReq)
        
        return bookRepo.findBooksByIsbn(findBookReq.isbn)
            ?: throw ObservableError("创建书失败[${JsonHelper.mapper.writeValueAsString(findBookReq)}]")
    }
    
    private fun addBook(findBookReq: FindBookReq) {
        synchronized(this) {
            val book = Book()
            findBookReq.copyTo(book)
            bookRepo.save(book)
        }
    }
    
    @Synchronized
    fun borrow(borrowReq: BorrowReq) {
        val borrowRecord = BookBorrowRecord()
        borrowRecord.user = borrowReq.user
        borrowRecord.borrowTime = borrowReq.borrowTime
        borrowRecord.originalReturnTime = borrowReq.borrowTime.plus(30, ChronoUnit.DAYS)
        
        val bookRecord = bookRecordRepo.findByBookAndNo(book = borrowReq.bookId, no = borrowReq.no)
            ?: throw ObservableError("找不到指定书${borrowReq.bookId} ${borrowReq.no}")
        bookRecord.borrowed = true
        bookRecord.borrowedBy = borrowReq.user
        
        // 加锁
        borrowRecordRepo.save(borrowRecord)
        bookRecordRepo.save(bookRecord)
        // 解锁
    }
    
    fun calcPenalty(req: ReturnBookReq): Double {
        val record = borrowRecordRepo.findByBookRecordAndUserAndReturned(req.bookRecordId, req.user, false)
            ?: throw ObservableError("找不到要借阅记录")
        
        val returnTime = Instant.now()
        val borrowTime = Duration.between(record.borrowTime, returnTime)
        
        record.actualReturnTime = returnTime
        record.returned = true
        record.penalty =
            if (borrowTime.get(ChronoUnit.DAYS) <= 30) 0.0 else (borrowTime.get(ChronoUnit.DAYS) - 30) * 0.1
        
        borrowRecordRepo.save(record)
        
        return record.penalty
    }
}

data class FindBookReq(
    var isbn: String,
    var category: String,
    var name: String,
    var author: String,
    var publisher: String,
    var publishTime: String,
    var version: String,
    var size: String,
    var pageNum: Int
) {
    fun copyTo(book: Book) {
        book.isbn = isbn
        book.category = category
        book.name = name
        book.author = author
        book.publisher = publisher
        book.publishTime = publishTime
        book.version = version
        book.size = size
        book.pageNum = pageNum
    }
}

data class AddBookReq(
    val bookId: Int,
    val counts: Int
)

data class BorrowReq(
    val bookId: Int,
    val no: Int,
    val user: String,
    val borrowTime: Instant
)

data class ReturnBookReq(
    val bookRecordId: Int,
    val user: String
)
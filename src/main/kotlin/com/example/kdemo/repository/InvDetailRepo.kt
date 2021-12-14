package com.example.kdemo.repository

import com.example.kdemo.entity.InvDetail
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface InvDetailRepo : PagingAndSortingRepository<InvDetail, Int> {

    fun findInvDetailByPart(part: String): Optional<InvDetail>
}
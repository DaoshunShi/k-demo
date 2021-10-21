package com.example.kdemo.inv.repo

import com.example.kdemo.inv.domain.InvChange
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface InvChangeRepo: PagingAndSortingRepository< InvChange, String> {
}

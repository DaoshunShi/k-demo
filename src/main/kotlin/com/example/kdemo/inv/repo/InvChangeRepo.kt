package com.example.kdemo.inv.repo

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface InvChangeRepo: PagingAndSortingRepository< InvChangeRepo, String> {
}
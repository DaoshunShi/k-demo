package com.example.kdemo.inv.repo

import com.example.kdemo.inv.domain.Container
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface ContainerRepo : PagingAndSortingRepository<Container, String> {
    
    @Query("update Container e set e.occupied = true, e.occupiedReason = :occupiedReason where e._id = :containerId")
    @Transactional
    @Modifying
    fun occupy(containerId: String, occupiedReason: String)
    
    @Query("update Container e set e.occupied = false , e.occupiedReason = '' where e._id = :containerId")
    @Transactional
    @Modifying
    fun free(containerId: String)
    
    // TODO
    
    
}
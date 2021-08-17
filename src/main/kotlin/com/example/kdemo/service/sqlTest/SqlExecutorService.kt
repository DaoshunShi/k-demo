package com.example.kdemo.service.sqlTest

import com.example.kdemo.entity.WmsOrderLog
import org.hibernate.SQLQuery
import org.hibernate.query.internal.NativeQueryImpl
import org.hibernate.transform.Transformers
import org.slf4j.LoggerFactory
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Service
import javax.persistence.EntityManager
import javax.transaction.Transactional


@Service
class SqlExecutorService(private val entityManager: EntityManager) {
    
    private val logger = LoggerFactory.getLogger(javaClass)
    
    // todo 找一个 .setResultTransformer() 的替换方案
    @Query
    fun queryBySql(sql: String): MutableList<out Any>? {
        val query = entityManager.createNativeQuery(sql)
            .unwrap(NativeQueryImpl::class.java)
            .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
        return query.resultList
    }
    
    @Query
    fun queryEntityBySql(sql: String): MutableList<out Any>? {
        val query = entityManager.createNativeQuery(sql)
            .unwrap(SQLQuery::class.java)
            .setResultTransformer(Transformers.aliasToBean(WmsOrderLog::class.java))
        return query.resultList
    }
    
    @Transactional
    @Modifying
    @Query
    fun executeSql(sql: String): Int {
        val query = entityManager.createNativeQuery(sql)
        return query.executeUpdate()
    }
    
}
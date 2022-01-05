package com.example.kdemo

import com.example.kdemo.config.DemoConfig
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.gridfs.GridFSBucket
import com.mongodb.client.gridfs.GridFSBuckets
import org.bson.Document
import org.litote.kmongo.KMongo
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy
import kotlin.reflect.KClass

@Service
class MongoManager(private val demoConfig: DemoConfig) {
    
    private val logger = LoggerFactory.getLogger(javaClass)
    
    private var client: MongoClient? = null
    private var database: MongoDatabase? = null
    private var gridFSFileBucket: GridFSBucket? = null
    
    @PostConstruct
    fun init() {
        val connUrl = demoConfig.mongo
        val databaseName = connUrl.substringAfterLast("/")
        
        val client = KMongo.createClient(connUrl)
        this.client = client
        
        val database = client.getDatabase(databaseName)
        this.database = database
        
        gridFSFileBucket = GridFSBuckets.create(database, "files")
    }
    
    @PreDestroy
    fun destroyMongoDB() {
        client?.close()
    }
    
    fun getDatabase(): MongoDatabase {
        return database ?: throw RuntimeException("无 Mongo 数据库")
    }
    
    fun <T : Any> getCollectionByClass(name: String, tc: KClass<T>): MongoCollection<T> {
        return getDatabase().getCollection(name, tc.java)
    }
    
    fun getCollectionByName(name: String): MongoCollection<Document> {
        return getDatabase().getCollection(name)
    }
    
}


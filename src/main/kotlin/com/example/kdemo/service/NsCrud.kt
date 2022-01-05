package com.example.kdemo.service

import com.example.kdemo.MongoManager
import com.example.kdemo.NsEntityValue
import com.example.kdemo.entity.NsEntityByMap
import com.mongodb.client.FindIterable
import com.mongodb.client.model.Projections
import com.mongodb.client.model.Sorts
import org.bson.Document
import org.bson.conversions.Bson
import org.springframework.stereotype.Service

@Service
class NsCrud(private val mongoManager: MongoManager) {
    
    fun createOne(ev: NsEntityValue, entityName: String) {
        mongoManager.getCollectionByName(entityName).insertOne(Document(ev))
    }
    
    private inline fun <reified T : NsEntityByMap> createOne(ev: T) {
        createOne(ev.map, ev::class.simpleName!!)
    }
    
    fun createMany(evList: List<NsEntityValue>, entityName: String) {
        if (evList.isEmpty()) return
        
        mongoManager.getCollectionByName(entityName).insertMany(evList.map { Document(it) })
    }
    
    fun findOne(filter: Bson?, entityName: String, o: NsFindOptions = NsFindOptions()): NsEntityValue? {
        val c = toCursor(filter, entityName, o)
        val ev = c.first() ?: return null
        // TODO return convertor.jsonToEntityValue(ev, )
        return null
    }
    
    fun findOneInto() {}
    
    fun findOneById() {}
    
    fun findOneByIdInto() {}
    
    fun findMany() {}
    
    fun findManyInto() {}
    
    fun updateOne() {}
    
    fun updateOneById() {}
    
    fun updateMany() {}
    
    fun deleteOne() {}
    
    fun deleteMany() {}
    
    fun count() {}
    
    private fun toCursor(filter: Bson?, entityName: String, o: NsFindOptions): FindIterable<Document> {
        val c = mongoManager.getCollectionByName(entityName).find(filter ?: Document())
        if (!o.projection.isNullOrEmpty()) c.projection(Projections.include(o.projection))
        if (!o.sort.isNullOrEmpty()) c.sort(toSort(o))
        if (o.limit > 0) c.limit(o.limit)
        if (o.skip > 0) c.skip(o.skip)
        return c
    }
    
    private fun toSort(o: NsFindOptions): Bson {
        return if (o.sort.isNullOrEmpty()) {
            Document()
        } else {
            Sorts.orderBy(o.sort.map {
                when {
                    it.startsWith("-") -> Sorts.descending(it.substring(1))
                    it.startsWith("+") -> Sorts.ascending(it.substring(1))
                    else -> Sorts.ascending(it)
                }
            })
        }
    }
    
}


enum class NsQueryType {
    Compound, General, All
}

enum class NsQueryOperator {
    Eq, Ne, Gt, Gte, Lt, Lte, In, Contain, ContainIgnoreCase, Start, End
}

data class NsComplexQuery(
    val type: NsQueryType = NsQueryType.General,
    val or: Boolean? = null,
    val not: Boolean? = null,
    val items: List<NsComplexQuery>? = null,
    val operator: NsQueryOperator? = null,
    val field1: String? = null,
    val field2: String? = null,
    var value: Any? = null
) {
    
    override fun toString(): String {
        return when (type) {
            NsQueryType.All -> "(ALL)"
            NsQueryType.General -> {
                val desc =
                    (if (not == true) "NOT " else "") + (field1 ?: "") + " " + (operator ?: "") + " " + (field2 ?: value
                    ?: "")
                // if (subQuery != null) {
                //     desc += " SubQuery(${subQuery.selectColumn} ${subQuery.table} ${subQuery.where})"
                // }
                "(${desc})"
            }
            NsQueryType.Compound -> {
                var desc = if (items.isNullOrEmpty()) {
                    ""
                } else {
                    items.joinToString(if (or == true) " OR " else " AND ")
                }
                if (not == true) {
                    desc = "NOT $desc"
                }
                "(${desc})"
            }
        }
    }
}


class NsFindOptions(
    val projection: List<String>? = null, val sort: List<String>? = null, val limit: Int = 0, val skip: Int = 0
)
package com.example.kdemo.remote

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RdsCoreRemoteService {
    @GET("ping")
    fun ping(): Call<RdsCorePingRes>
    
    @POST("setOrder")
    fun setOrder(@Body req: RdsCoreOrderReq): Call<String>
    
    @POST("addBlocks")
    fun addBlock(@Body req: RdsCoreBlocksReq): Call<String>
    
    @POST("redoFailedOrder ")
    fun redoFailedOrder(@Body req: RdsCoreRedoFailedOrderReq): Call<String>
    
    @POST("manualFinished")
    fun manualFinished(@Body req: RdsCoreManualFinishedReq): Call<String>
    
    @GET("orderDetails/{id}")
    fun orderDetail(@Path("id") id: String): Call<RdsCoreOrderDetail>
    
    @POST("markComplete")
    fun markComplete(@Body req: MarkComplete): Call<String>
    
}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class RdsCorePingRes(
    val create_on: String? = null,
    val product: String = "",
    val version: String = ""
) {
    fun toPingResult(): PingResult {
        return PingResult(product = product, version = version, success = true)
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class RdsCoreOrderReq(
    val id: String = "",
    val priority: Long = 1,
    val deadline: String = "",
    val vehicle: String?,
    val group: String?,
    val candidates: List<String> = emptyList(),
    val keyRoute: List<String> = emptyList(),
    val blocks: List<RdsCoreBlock> = emptyList(),
    val complete: Boolean = false
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class RdsCoreBlocksReq(
    val id: String = "",
    var blocks: List<RdsCoreBlock> = emptyList()
)

data class RdsCoreBlock(
    val blockId: String = "",
    val location: String = "",
    val binTask: String? = null,
    val goodsId: String? = null
)


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class RdsCoreRedoFailedOrderReq(
    val vehicles: List<String> = emptyList()
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class RdsCoreManualFinishedReq(
    val vehicles: List<String> = emptyList()
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class RdsCoreOrderDetail(
    val id: String = "",
    val vehicle: String = "",
    val createTime: String = "",
    val terminateTime: String = "",
    val state: String = "",
    val blocks: List<RdsCoreBlockDetail> = emptyList(),
    val complete: Boolean = false,
    val msg: String = ""
)

data class RdsCoreBlockDetail(
    val blockId: String = "",
    val state: String = "",
    val result: Map<String, Any> = emptyMap()
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class MarkComplete(
    val id: String = ""
)


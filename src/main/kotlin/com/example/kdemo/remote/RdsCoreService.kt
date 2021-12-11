package com.example.kdemo.remote

import com.example.kdemo.remote.*
import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import okhttp3.ResponseBody
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import retrofit2.Response
import java.time.Instant
import javax.annotation.PostConstruct

@Service
class RdsCoreService(
    private val rdsCoreManager: RdsCoreManager,
) {
    
    private val logger = LoggerFactory.getLogger(javaClass)
    
    @Volatile
    var mockCfg: TaskMockCfg = TaskMockCfg()
    
    @PostConstruct
    fun init() {
        // TODO
        mockCfg.mockDispatch = true
    }
    
    fun ping(): PingResult {
        // mock
        if (mockCfg.mockPing) {
            logger.info("Mock Ping")
            return PingResult(createdOn = Instant.now(), product = "mock", success = true)
        }
        
        // RDS Core
        val pingRes = rdsCoreManager.getService().ping().execute().body()
        if (pingRes != null) return pingRes.toPingResult()
        return PingResult()
    }
    
    fun setOrder(rdsCoreOrderId: String, intendVehicle: String?, group: String?): Response<String> {
        // mock
        if (mockCfg.mockDispatch) {
            Thread.sleep(mockCfg.mockCost)
            logger.info("Mock 创建订单")
            return Response.success("Mock 创建订单成功")
        }
        
        // RDS Core
        val req = RdsCoreOrderReq(id = rdsCoreOrderId, vehicle = intendVehicle, group = group)
        return rdsCoreManager.getService().setOrder(req).execute()
    }
    
    fun markComplete(rdsCoreOrderId: String): Response<String> {
        // mock
        if (mockCfg.mockDispatch) {
            Thread.sleep(mockCfg.mockCost)
            logger.info("Mock 封口订单")
            return Response.success("Mock 封口订单成功")
        }
        
        // RDS Core
        val req = MarkComplete(id = rdsCoreOrderId)
        return rdsCoreManager.getService().markComplete(req).execute()
    }
    
    fun addBlock(
        rdsCoreOrderId: String, blockId: String, bin: String,
        binTask: String? = null, goodsId: String? = null, operation: String? = null, isLoad: Boolean = true
    ): Response<String> {
        // mock
        if (mockCfg.mockDispatch) {
            Thread.sleep(mockCfg.mockCost)
            logger.info("Mock 追加动作块")
            if (isLoad && mockCfg.mockAddLoadBlockFail) {
                return Response.error(400, ResponseBody.create(null, "Mock 追加动作块失败"))
            } else if (!isLoad && mockCfg.mockAddUnloadBlockFail) {
                return Response.error(400, ResponseBody.create(null, "Mock 追加动作块失败"))
            }
            return Response.success("Mock 追加动作块成功")
        }
        
        // RDS Core
        val block = RdsCoreBlock(blockId = blockId, location = bin, binTask = binTask, goodsId = goodsId)
        val req = RdsCoreBlocksReq(
            id = rdsCoreOrderId,
            blocks = listOf(block)
        )
        return rdsCoreManager.getService().addBlock(req).execute()
    }
    
    fun orderDetail(rdsCoreOrderId: String, blockId: String, isLoad: Boolean = true): Response<RdsCoreOrderDetail> {
        // mock
        if (mockCfg.mockDispatch) {
            Thread.sleep(mockCfg.mockCost)
            logger.info("Mock 查询动作块执行情况")
            if (isLoad && mockCfg.mockLoadFail) {
                return Response.success(
                    200, RdsCoreOrderDetail(
                        id = rdsCoreOrderId,
                        vehicle = "mockVehicle",
                        state = "CREATED",
                        blocks = listOf(RdsCoreBlockDetail(blockId = blockId, state = "STOPPED")),
                        complete = false,
                        msg = "Mock 查询动作块执行失败"
                    )
                )
            } else if (!isLoad && mockCfg.mockUnloadFail) {
                return Response.success(
                    200, RdsCoreOrderDetail(
                        id = rdsCoreOrderId,
                        vehicle = "mockVehicle",
                        state = "CREATED",
                        blocks = listOf(RdsCoreBlockDetail(blockId = blockId, state = "STOPPED")),
                        complete = false,
                        msg = "Mock 查询动作块执行失败"
                    )
                )
            }
            return Response.success(
                RdsCoreOrderDetail(
                    id = rdsCoreOrderId,
                    vehicle = "mockVehicle",
                    state = "CREATED",
                    blocks = listOf(RdsCoreBlockDetail(blockId = blockId, state = "FINISHED")),
                    complete = false,
                    msg = "Mock 查询动作块执行成功"
                )
            )
        }
        
        // RDS Core
        return rdsCoreManager.getService().orderDetail(rdsCoreOrderId).execute()
    }
    
    fun redoFailedOrder(vehicle: String): Response<String> {
        // mock
        if (mockCfg.mockDispatch) {
            Thread.sleep(mockCfg.mockCost)
            logger.info("Mock redo")
            return Response.success("Mock redo成功")
        }
        
        // RDS Core
        return rdsCoreManager.getService().redoFailedOrder(RdsCoreRedoFailedOrderReq(vehicles = listOf(vehicle)))
            .execute()
    }
    
    fun manualFinishBlock(vehicle: String): Response<String> {
        // mock
        if (mockCfg.mockDispatch) {
            Thread.sleep(mockCfg.mockCost)
            logger.info("Mock 人工完成")
            return Response.success("Mock 人工完成成功")
        }
        
        // RDS Core
        return rdsCoreManager.getService().manualFinished(RdsCoreManualFinishedReq(vehicles = listOf(vehicle)))
            .execute()
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class PingResult(
    val createdOn: Instant? = null,
    val product: String = "",
    val version: String = "",
    val success: Boolean = false
)

data class TaskMockCfg(
    var mockPing: Boolean = false,
    var mockDispatch: Boolean = false,
    var mockCreateOrderFail: Boolean = false,
    var mockAddLoadBlockFail: Boolean = false,
    var mockLoadFail: Boolean = false,
    var mockAddUnloadBlockFail: Boolean = false,
    var mockUnloadFail: Boolean = false,
    var mockCompleteOrderFail: Boolean = false,
    var mockCost: Long = 3000L
)
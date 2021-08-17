package com.example.kdemo.service.sqlTest

import com.example.kdemo.helper.JsonHelper
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/sql")
class SqlExecutorController(private val sqlExecutorService: SqlExecutorService) {
    
    private val logger = LoggerFactory.getLogger(javaClass)
    
    @GetMapping("query1")
    @ResponseBody
    fun query1(): String {
        val sql = "select * from wms_order_log limit 10;"
        val res = sqlExecutorService.queryBySql(sql)
        // logger.info(JsonHelper.mapper.writeValueAsString(res))
        return JsonHelper.mapper.writeValueAsString(res)
    }
    
    @GetMapping("query2")
    @ResponseBody
    fun query2(): String {
        val sql = "select * from wms_order_log limit 10;"
        val res = sqlExecutorService.queryEntityBySql(sql)
        // logger.info(JsonHelper.mapper.writeValueAsString(res))
        return JsonHelper.mapper.writeValueAsString(res)
    }
    
    
    @GetMapping("execute1")
    @ResponseBody
    fun execute1(): Int {
        val sql = "update wms_order_log l set l.content = \"1\" where l.id = \"610D30BB52E4B811E1239A51\";"
        val res = sqlExecutorService.executeSql(sql)
        logger.info("execute1 executed, res = $res")
        return res
    }
    
}
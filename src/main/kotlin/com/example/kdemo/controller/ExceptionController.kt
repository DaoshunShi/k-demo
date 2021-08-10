package com.example.kdemo.controller

import com.example.kdemo.ObservableError
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("exception")
class ExceptionController {
    
    val logger = LoggerFactory.getLogger(ExceptionController::class.java)
    
    @GetMapping
    @ResponseBody
    fun getObservableException(): String {
        throw ObservableError("123")
        // return "exception res "
    }
    
    // 如果打开 Controller 中的 @ExceptionHandler，则改异常不会被 @ControllerAdvice 捕获
    // @ExceptionHandler(ObservableError::class)
    // fun handler(e: ObservableError): String {
    //     val errMsg = "${e.message} catch observable error"
    //     logger.error(errMsg)
    //     return errMsg
    // }
}

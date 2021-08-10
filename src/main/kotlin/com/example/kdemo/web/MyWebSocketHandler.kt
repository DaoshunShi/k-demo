package com.example.kdemo.web

import com.example.kdemo.helper.JsonHelper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.CopyOnWriteArrayList
import javax.annotation.PreDestroy

@Component
class MyWebSocketHandler : TextWebSocketHandler() {
    
    private val logger = LoggerFactory.getLogger(javaClass)
    
    private val sessions: MutableList<WebSocketSession> = CopyOnWriteArrayList()
    
    @PreDestroy
    fun disponse() {
        sessions.forEach { it.close() }
    }
    
    fun sendAll(eventName: String, message: Any?) {
        val textMsg = TextMessage(JsonHelper.mapper.writeValueAsString(WebSocketTextMessage(eventName, message)))
        sessions.forEach { it.sendMessage(textMsg) }
    }
    
    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        logger.debug("web socket message $message")
    }
    
    override fun afterConnectionEstablished(session: WebSocketSession) {
        sessions += session
    }
    
    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessions -= session
    }
}

data class WebSocketTextMessage(
    val eventName: String,
    val message: Any?
)
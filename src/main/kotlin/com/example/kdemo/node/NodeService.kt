package com.example.kdemo.node

import org.hibernate.internal.util.collections.Stack
import org.hibernate.internal.util.collections.StandardStack
import org.springframework.stereotype.Service

@Service
class NodeService {
    
    private val head = DemoNode()

    
    private fun pushLeft(node: DemoNode) {
        head.left = node
    }
    
    private fun pushRight(node: DemoNode) {
        head.right = node
    }
    
}

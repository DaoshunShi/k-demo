package com.example.kdemo.opc.client

import org.eclipse.milo.opcua.sdk.client.OpcUaClient
import org.eclipse.milo.opcua.sdk.client.model.nodes.objects.ServerTypeNode
import org.eclipse.milo.opcua.stack.core.Identifiers
import org.slf4j.LoggerFactory
import java.util.*
import java.util.concurrent.CompletableFuture


class ReadNodeExample : ClientExample {
    private val logger = LoggerFactory.getLogger(javaClass)
    
    @Throws(Exception::class)
    override fun run(client: OpcUaClient, future: CompletableFuture<OpcUaClient>) {
        // synchronous connect
        client.connect().get()
        
        // Get a typed reference to the Server object: ServerNode
        val serverNode = client.addressSpace.getObjectNode(
            Identifiers.Server,
            Identifiers.ServerType
        ) as ServerTypeNode
        
        // Read properties of the Server object...
        val serverArray = serverNode.serverArray
        val namespaceArray = serverNode.namespaceArray
        logger.info("ServerArray={}", Arrays.toString(serverArray))
        logger.info("NamespaceArray={}", Arrays.toString(namespaceArray))
        
        // Read the value of attribute the ServerStatus variable component
        val serverStatus = serverNode.serverStatus
        logger.info("ServerStatus={}", serverStatus)
        
        // Get a typed reference to the ServerStatus variable
        // component and read value attributes individually
        val serverStatusNode = serverNode.serverStatusNode
        val buildInfo = serverStatusNode.buildInfo
        val startTime = serverStatusNode.startTime
        val currentTime = serverStatusNode.currentTime
        val state = serverStatusNode.state
        logger.info("ServerStatus.BuildInfo={}", buildInfo)
        logger.info("ServerStatus.StartTime={}", startTime)
        logger.info("ServerStatus.CurrentTime={}", currentTime)
        logger.info("ServerStatus.State={}", state)
        future.complete(client)
    }
    
    fun main(args: Array<String>) {
        val example = ReadNodeExample()
        ClientExampleRunner(example).run()
        
    }
}

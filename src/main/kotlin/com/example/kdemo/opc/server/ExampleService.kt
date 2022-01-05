// package com.example.kdemo.opc.server
//
// import org.bouncycastle.jce.provider.BouncyCastleProvider
// import org.eclipse.milo.opcua.sdk.server.OpcUaServer
// import org.eclipse.milo.opcua.sdk.server.api.config.OpcUaServerConfig
// import org.eclipse.milo.opcua.sdk.server.api.config.OpcUaServerConfig.USER_TOKEN_POLICY_ANONYMOUS
// import org.eclipse.milo.opcua.sdk.server.api.config.OpcUaServerConfig.USER_TOKEN_POLICY_USERNAME
// import org.eclipse.milo.opcua.sdk.server.api.config.OpcUaServerConfig.USER_TOKEN_POLICY_X509
// import org.eclipse.milo.opcua.sdk.server.identity.CompositeValidator
// import org.eclipse.milo.opcua.sdk.server.identity.UsernameIdentityValidator
// import org.eclipse.milo.opcua.sdk.server.identity.X509IdentityValidator
// import org.eclipse.milo.opcua.sdk.server.util.HostnameUtil
// import org.eclipse.milo.opcua.stack.core.StatusCodes
// import org.eclipse.milo.opcua.stack.core.UaRuntimeException
// import org.eclipse.milo.opcua.stack.core.security.DefaultCertificateManager
// import org.eclipse.milo.opcua.stack.core.security.DefaultTrustListManager
// import org.eclipse.milo.opcua.stack.core.security.SecurityPolicy
// import org.eclipse.milo.opcua.stack.core.transport.TransportProfile
// import org.eclipse.milo.opcua.stack.core.types.builtin.DateTime
// import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText
// import org.eclipse.milo.opcua.stack.core.types.enumerated.MessageSecurityMode
// import org.eclipse.milo.opcua.stack.core.types.structured.BuildInfo
// import org.eclipse.milo.opcua.stack.core.util.CertificateUtil
// import org.eclipse.milo.opcua.stack.core.util.NonceUtil
// import org.eclipse.milo.opcua.stack.core.util.SelfSignedCertificateGenerator
// import org.eclipse.milo.opcua.stack.core.util.SelfSignedHttpsCertificateBuilder
// import org.eclipse.milo.opcua.stack.server.EndpointConfiguration
// import org.eclipse.milo.opcua.stack.server.security.DefaultServerCertificateValidator
// import org.slf4j.LoggerFactory
// import java.nio.file.Files
// import java.nio.file.Paths
// import java.security.KeyPair
// import java.security.Security
// import java.security.cert.X509Certificate
// import java.util.concurrent.CompletableFuture
// import java.util.concurrent.ExecutionException
// import java.util.concurrent.TimeUnit
// import java.util.concurrent.TimeoutException
//
// /*
// * Copyright (c) 2021 the Eclipse Milo Authors
// *
// * This program and the accompanying materials are made
// * available under the terms of the Eclipse Public License 2.0
// * which is available at https://www.eclipse.org/legal/epl-2.0/
// *
// * SPDX-License-Identifier: EPL-2.0
// */
// package org.eclipse.milo.examples.server
// import org.bouncycastle.jce.provider.BouncyCastleProvider
// import org.eclipse.milo.opcua.sdk.server.OpcUaServer
// import org.eclipse.milo.opcua.sdk.server.api.config.OpcUaServerConfig
// import org.eclipse.milo.opcua.sdk.server.identity.CompositeValidator
// import org.eclipse.milo.opcua.sdk.server.identity.UsernameIdentityValidator
// import org.eclipse.milo.opcua.sdk.server.identity.X509IdentityValidator
// import org.eclipse.milo.opcua.sdk.server.util.HostnameUtil
// import org.eclipse.milo.opcua.stack.core.StatusCodes
// import org.eclipse.milo.opcua.stack.core.UaRuntimeException
// import org.eclipse.milo.opcua.stack.core.security.DefaultCertificateManager
// import org.eclipse.milo.opcua.stack.core.security.DefaultTrustListManager
// import org.eclipse.milo.opcua.stack.core.security.SecurityPolicy
// import org.eclipse.milo.opcua.stack.core.transport.TransportProfile
// import org.eclipse.milo.opcua.stack.core.types.builtin.DateTime
// import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText
// import org.eclipse.milo.opcua.stack.core.types.enumerated.MessageSecurityMode
// import org.eclipse.milo.opcua.stack.core.types.structured.BuildInfo
// import org.eclipse.milo.opcua.stack.core.util.CertificateUtil
// import org.eclipse.milo.opcua.stack.core.util.NonceUtil
// import org.eclipse.milo.opcua.stack.core.util.SelfSignedCertificateGenerator
// import org.eclipse.milo.opcua.stack.core.util.SelfSignedHttpsCertificateBuilder
// import org.eclipse.milo.opcua.stack.server.EndpointConfiguration
// import org.eclipse.milo.opcua.stack.server.security.DefaultServerCertificateValidator
// import org.eclipse.milo.opcua.sdk.server.api.config.OpcUaServerConfig.USER_TOKEN_POLICY_ANONYMOUS
// import org.eclipse.milo.opcua.sdk.server.api.config.OpcUaServerConfig.USER_TOKEN_POLICY_USERNAME
// import org.eclipse.milo.opcua.sdk.server.api.config.OpcUaServerConfig.USER_TOKEN_POLICY_X509
//
// class ExampleServer {
//     companion object {
//         private const val TCP_BIND_PORT = 12686
//         private const val HTTPS_BIND_PORT = 8443
//         @Throws(Exception::class)
//         @JvmStatic
//         fun main(args: Array<String>) {
//             val server = ExampleServer()
//             server.startup().get()
//             val future = CompletableFuture<Void?>()
//             Runtime.getRuntime().addShutdownHook(Thread { future.complete(null) })
//             future.get()
//         }
//
//         private fun buildTcpEndpoint(base: EndpointConfiguration.Builder): EndpointConfiguration {
//             return base.copy()
//                 .setTransportProfile(TransportProfile.TCP_UASC_UABINARY)
//                 .setBindPort(TCP_BIND_PORT)
//                 .build()
//         }
//
//         private fun buildHttpsEndpoint(base: EndpointConfiguration.Builder): EndpointConfiguration {
//             return base.copy()
//                 .setTransportProfile(TransportProfile.HTTPS_UABINARY)
//                 .setBindPort(HTTPS_BIND_PORT)
//                 .build()
//         }
//
//         init {
//             // Required for SecurityPolicy.Aes256_Sha256_RsaPss
//             Security.addProvider(BouncyCastleProvider())
//             try {
//                 NonceUtil.blockUntilSecureRandomSeeded(10, TimeUnit.SECONDS)
//             } catch (e: ExecutionException) {
//                 e.printStackTrace()
//                 System.exit(-1)
//             } catch (e: InterruptedException) {
//                 e.printStackTrace()
//                 System.exit(-1)
//             } catch (e: TimeoutException) {
//                 e.printStackTrace()
//                 System.exit(-1)
//             }
//         }
//     }
//
//     private val server: OpcUaServer
//     private val exampleNamespace: ExampleNamespace
//     private fun createEndpointConfigurations(certificate: X509Certificate): Set<EndpointConfiguration> {
//         val endpointConfigurations: MutableSet<EndpointConfiguration> = LinkedHashSet<EndpointConfiguration>()
//         val bindAddresses: MutableList<String> = com.google.common.collect.Lists.newArrayList<String>()
//         bindAddresses.add("0.0.0.0")
//         val hostnames: MutableSet<String> = LinkedHashSet()
//         hostnames.add(HostnameUtil.getHostname())
//         hostnames.addAll(HostnameUtil.getHostnames("0.0.0.0"))
//         for (bindAddress in bindAddresses) {
//             for (hostname in hostnames) {
//                 val builder: EndpointConfiguration.Builder = EndpointConfiguration.newBuilder()
//                     .setBindAddress(bindAddress)
//                     .setHostname(hostname)
//                     .setPath("/milo")
//                     .setCertificate(certificate)
//                     .addTokenPolicies(
//                         USER_TOKEN_POLICY_ANONYMOUS,
//                         USER_TOKEN_POLICY_USERNAME,
//                         USER_TOKEN_POLICY_X509
//                     )
//                 val noSecurityBuilder: EndpointConfiguration.Builder = builder.copy()
//                     .setSecurityPolicy(SecurityPolicy.None)
//                     .setSecurityMode(MessageSecurityMode.None)
//                 endpointConfigurations.add(buildTcpEndpoint(noSecurityBuilder))
//                 endpointConfigurations.add(buildHttpsEndpoint(noSecurityBuilder))
//
//                 // TCP Basic256Sha256 / SignAndEncrypt
//                 endpointConfigurations.add(
//                     buildTcpEndpoint(
//                         builder.copy()
//                             .setSecurityPolicy(SecurityPolicy.Basic256Sha256)
//                             .setSecurityMode(MessageSecurityMode.SignAndEncrypt)
//                     )
//                 )
//
//                 // HTTPS Basic256Sha256 / Sign (SignAndEncrypt not allowed for HTTPS)
//                 endpointConfigurations.add(
//                     buildHttpsEndpoint(
//                         builder.copy()
//                             .setSecurityPolicy(SecurityPolicy.Basic256Sha256)
//                             .setSecurityMode(MessageSecurityMode.Sign)
//                     )
//                 )
//
//                 /*
//                  * It's good practice to provide a discovery-specific endpoint with no security.
//                  * It's required practice if all regular endpoints have security configured.
//                  *
//                  * Usage of the  "/discovery" suffix is defined by OPC UA Part 6:
//                  *
//                  * Each OPC UA Server Application implements the Discovery Service Set. If the OPC UA Server requires a
//                  * different address for this Endpoint it shall create the address by appending the path "/discovery" to
//                  * its base address.
//                  */
//                 val discoveryBuilder: EndpointConfiguration.Builder = builder.copy()
//                     .setPath("/milo/discovery")
//                     .setSecurityPolicy(SecurityPolicy.None)
//                     .setSecurityMode(MessageSecurityMode.None)
//                 endpointConfigurations.add(buildTcpEndpoint(discoveryBuilder))
//                 endpointConfigurations.add(buildHttpsEndpoint(discoveryBuilder))
//             }
//         }
//         return endpointConfigurations
//     }
//
//     fun getServer(): OpcUaServer {
//         return server
//     }
//
//     fun startup(): CompletableFuture<OpcUaServer> {
//         return server.startup()
//     }
//
//     fun shutdown(): CompletableFuture<OpcUaServer> {
//         exampleNamespace.shutdown()
//         return server.shutdown()
//     }
//
//     init {
//         val securityTempDir = Paths.get(System.getProperty("java.io.tmpdir"), "server", "security")
//         Files.createDirectories(securityTempDir)
//         if (!Files.exists(securityTempDir)) {
//             throw Exception("unable to create security temp dir: $securityTempDir")
//         }
//         val pkiDir = securityTempDir.resolve("pki").toFile()
//         LoggerFactory.getLogger(javaClass)
//             .info("security dir: {}", securityTempDir.toAbsolutePath())
//         LoggerFactory.getLogger(javaClass)
//             .info("security pki dir: {}", pkiDir.absolutePath)
//         val loader: KeyStoreLoader = KeyStoreLoader().load(securityTempDir)
//         val certificateManager = DefaultCertificateManager(
//             loader.getServerKeyPair(),
//             loader.getServerCertificateChain()
//         )
//         val trustListManager = DefaultTrustListManager(pkiDir)
//         val certificateValidator = DefaultServerCertificateValidator(trustListManager)
//         val httpsKeyPair: KeyPair = SelfSignedCertificateGenerator.generateRsaKeyPair(2048)
//         val httpsCertificateBuilder = SelfSignedHttpsCertificateBuilder(httpsKeyPair)
//         httpsCertificateBuilder.setCommonName(HostnameUtil.getHostname())
//         HostnameUtil.getHostnames("0.0.0.0").forEach(httpsCertificateBuilder::addDnsName)
//         val httpsCertificate: X509Certificate = httpsCertificateBuilder.build()
//         val identityValidator = UsernameIdentityValidator(
//             true
//         ) { authChallenge ->
//             val username: String = authChallenge.getUsername()
//             val password: String = authChallenge.getPassword()
//             val userOk = "user" == username && "password1" == password
//             val adminOk = "admin" == username && "password2" == password
//             userOk || adminOk
//         }
//         val x509IdentityValidator = X509IdentityValidator { c -> true }
//
//         // If you need to use multiple certificates you'll have to be smarter than this.
//         val certificate: X509Certificate = certificateManager.getCertificates()
//             .stream()
//             .findFirst()
//             .orElseThrow { UaRuntimeException(StatusCodes.Bad_ConfigurationError, "no certificate found") }
//
//         // The configured application URI must match the one in the certificate(s)
//         val applicationUri: String = CertificateUtil
//             .getSanUri(certificate)
//             .orElseThrow {
//                 UaRuntimeException(
//                     StatusCodes.Bad_ConfigurationError,
//                     "certificate is missing the application URI"
//                 )
//             }
//         val endpointConfigurations: Set<EndpointConfiguration> = createEndpointConfigurations(certificate)
//         val serverConfig: OpcUaServerConfig = OpcUaServerConfig.builder()
//             .setApplicationUri(applicationUri)
//             .setApplicationName(LocalizedText.english("Eclipse Milo OPC UA Example Server"))
//             .setEndpoints(endpointConfigurations)
//             .setBuildInfo(
//                 BuildInfo(
//                     "urn:eclipse:milo:example-server",
//                     "eclipse",
//                     "eclipse milo example server",
//                     OpcUaServer.SDK_VERSION,
//                     "", DateTime.now()
//                 )
//             )
//             .setCertificateManager(certificateManager)
//             .setTrustListManager(trustListManager)
//             .setCertificateValidator(certificateValidator)
//             .setHttpsKeyPair(httpsKeyPair)
//             .setHttpsCertificate(httpsCertificate)
//             .setIdentityValidator(CompositeValidator(identityValidator, x509IdentityValidator))
//             .setProductUri("urn:eclipse:milo:example-server")
//             .build()
//         server = OpcUaServer(serverConfig)
//         exampleNamespace = ExampleNamespace(server)
//         exampleNamespace.startup()
//     }
// }
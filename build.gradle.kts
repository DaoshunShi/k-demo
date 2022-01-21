import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.20"
    kotlin("plugin.spring") version "1.5.20"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
    implementation.get().exclude("org.springframework.boot", "spring-boot-starter-tomcat")
    implementation.get().exclude("org.springframework.boot", "spring-boot-starter-logging")
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    maven {
        setUrl("https://maven.aliyun.com/repository/public/")
    }
    maven {
        setUrl("https://maven.aliyun.com/repository/central")
    }
    maven {
        setUrl("https://maven.aliyun.com/repository/gradle-plugin")
    }
    maven {
        setUrl("https://maven.aliyun.com/repository/public")
    }
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-quartz")
    
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.12.4")
    
    implementation("mysql:mysql-connector-java")
    
    implementation("com.squareup.retrofit2:retrofit:2.7.2")
    implementation("com.squareup.retrofit2:converter-jackson:2.7.2")
    implementation("com.squareup.retrofit2:converter-jaxb:2.7.2")
    implementation("com.squareup.retrofit2:converter-scalars:2.7.2")
    
    implementation("com.squareup.okhttp3:logging-interceptor:3.12.1")
    
    implementation("org.mongodb:bson:4.3.0")
    
    implementation("org.apache.commons:commons-lang3:3.9")
    implementation("commons-io:commons-io:2.6")
    implementation("commons-codec:commons-codec:1.13")
    
    implementation("org.apache.velocity:velocity:1.7")
    
    implementation("org.apache.logging.log4j:log4j-core:2.16.0")
    implementation("org.apache.logging.log4j:log4j-api:2.16.0")
    implementation("org.apache.logging.log4j:log4j-web:2.16.0")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.16.0")
    implementation("org.slf4j:jul-to-slf4j:1.7.32")
    implementation("org.apache.pdfbox:pdfbox:2.0.22")
    
    implementation("org.mvel:mvel2:2.4.7.Final")
    
    implementation("org.litote.kmongo:kmongo:4.3.0")
    
    implementation("org.eclipse.milo:sdk-client:0.6.3")
    implementation("org.eclipse.milo:sdk-server:0.6.3")
    implementation("org.eclipse.milo:dictionary-manager:0.6.3")
    implementation("org.eclipse.milo:stack-client:0.6.3")
    implementation("org.eclipse.milo:stack-core:0.6.3")
    // implementation("com.uchuhimo:konf-core:0.22.1")
    
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

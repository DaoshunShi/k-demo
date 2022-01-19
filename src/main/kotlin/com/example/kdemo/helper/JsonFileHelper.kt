package com.example.kdemo.helper

import com.fasterxml.jackson.core.type.TypeReference
import org.apache.commons.io.FileUtils
import java.io.File
import java.nio.charset.StandardCharsets

object JsonFileHelper {
    
    fun <T> readJsonFromFile(file: File, valueTypeRef: TypeReference<T>): T? {
        return if (file.exists()) {
            val areasStr = FileUtils.readFileToString(file, StandardCharsets.UTF_8)
            if (!areasStr.isNullOrBlank())
                JsonHelper.mapper.readValue(areasStr, valueTypeRef)
            else null
        } else {
            null
        }
    }
    
    fun writeJsonToFile(file: File, obj: Any, pretty: Boolean = false) {
        val str = if (pretty) JsonHelper.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj)
        else JsonHelper.mapper.writeValueAsString(obj)
        FileUtils.writeStringToFile(file, str, StandardCharsets.UTF_8)
    }
    
    fun <T> readYamlFromFile(file: File, valueTypeRef: TypeReference<T>): T? {
        return if (file.exists()) {
            val areasStr = FileUtils.readFileToString(file, StandardCharsets.UTF_8)
            if (!areasStr.isNullOrBlank())
                JsonHelper.yamlMapper.readValue(areasStr, valueTypeRef)
            else null
        } else {
            null
        }
    }
    
    fun writeYamlToFile(file: File, obj: Any) {
        val str = JsonHelper.yamlMapper.writeValueAsString(obj)
        FileUtils.writeStringToFile(file, str, StandardCharsets.UTF_8)
    }
    
}
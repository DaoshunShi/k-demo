package com.example.kdemo

import com.example.kdemo.helper.JsonFileHelper
import com.example.kdemo.helper.JsonHelper
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import org.apache.commons.collections.MapUtils
import org.apache.commons.io.FileUtils
import java.io.*
import java.util.regex.Pattern

var c = 0

fun scan(rootDir: File, fileList: MutableList<File>): MutableList<File> {
    for (file in rootDir.listFiles()) {
        if (file.isFile) {
            println(file.absoluteFile)
            fileList += file
        } else {
            scan(file, fileList)
        }
    }
    return fileList
}

fun scanFile(file: File, map: MutableMap<String, String>): MutableList<String> {
    val lines = file.readLines().toMutableList()
    
    for (i in lines.indices) {
        val line = lines[i]
        
        if (line.contains("(")) {
            lines[i] = line.replace("(", "(\n    private val dict: DictManager,")
            break
        }
    }
    
    // for (line in lines) {
    //     println(line)
    // }
    return lines
}

fun scanFile2(file: File, map: MutableMap<String, String>): MutableList<String> {
    val lines = file.readLines().toMutableList()
    
    // val patternStr = "\"((?=.*[\\u4e00-\\u9fa5])[\\u4e00-\\u9fa5_a-zA-Z0-9\\[\\]\\{\\}\\=\\\$,.\\s\\:，。]+)\""
    val patternStr = "\"(?=.*[\\u4e00-\\u9fa5])[\\u4e00-\\u9fa5_a-zA-Z0-9\\[\\]\\{\\}\\=\\\$,.\\s\\:，。]+\""
    val pattern = Pattern.compile(patternStr)
    
    for (i in lines.indices) {
        val line = lines[i]
        val mr = pattern.matcher(line.substringBefore("//"))
        while (mr.find()) {
            // val str = mr.group(1)
            val str = mr.group()
            println(str)
            if (map.contains(str)) {
                val s = "dict.lo(\"${map[str]}\")"
                lines[i] = line.replace(str, s)
            } else {
                map[str] = "key${c}"
                val s = "dict.lo(\"key${c++}\")"
                lines[i] = lines[i].replace(str, s)
            }
        }
    }
    
    // for (line in lines) {
    //     println(line)
    // }
    return lines
}


fun scanAndReplace(file: File, map: MutableMap<String, I18nMapping>): MutableList<String> {
    val lines = file.readLines().toMutableList()
    
    val patternStr = "dict.lo\\((\\\"key\\d{1,3}\\\")\\)"
    val pattern = Pattern.compile(patternStr)
    
    for (i in lines.indices) {
        val line = lines[i]
        val mr = pattern.matcher(line.substringBefore("//"))
        while (mr.find()) {
            val t = mr.group(1)
            val str = t.substring(1, t.length - 1)
            // val str = mr.group()
            println("str = $str")
            if (map.contains(str)) {
                val mapping = map[str] ?: continue
                var s = "\"" + mapping.en + "\""
                if (mapping.params.size > 0) s = s + ", " + mapping.params.joinToString(", ")
                lines[i] = line.replace(t, s)
            } else {
                println("ERROR: map doesn't contain key: $str")
            }
        }
    }
    
    // for (line in lines) {
    //     println(line)
    // }
    return lines
}

fun scanFileWithStream(file: File) {
    BufferedReader(FileReader(file)).use {
        var line = it.readLine()
        while (line != null) {
            // println(line)
            line = it.readLine()
        }
    }
}

fun writeBackKt(file: File, lines: List<String>) {
    BufferedWriter(FileWriter(file)).use {
        for (i in lines.indices) {
            it.write(lines[i].toString())
            
            if (i != lines.size - 1) {
                it.write("\n")
            }
            
            // if (i == 1) {
            //     it.write("import com.shadow.core.DictManager")
            //     it.write("\n")
            // }
        }
    }
}

fun writeBackKt2(file: File, lines: List<String>) {
    BufferedWriter(FileWriter(file)).use {
        for (i in lines.indices) {
            it.write(lines[i].toString())
            
            if (i != lines.size - 1) {
                it.write("\n")
            }
        }
    }
}

fun genTmpYaml(file: File, map: Map<String, String>) {
    JsonFileHelper.writeYamlToFile(file, map)
}



fun kt2tmpYaml() {
    val map: MutableMap<String, String> = mutableMapOf()
    
    val rootDir = FileUtils.getFile("D:\\Project\\Seer\\mwms-server\\src\\main\\kotlin\\com\\shadow\\")
    val files = scan(rootDir, mutableListOf())
    
    for (file in files) {
        // val file =
        //     FileUtils.getFile("D:\\Project\\Seer\\mwms-server\\src\\main\\kotlin\\com\\shadow\\falcon\\FalconController.kt")
        // scanFile(file)
        val lines = scanFile2(file, map)
        
        writeBackKt(file, lines)
    }
    
    val tmpFile = FileUtils.getFile("D:\\Project\\Seer\\mwms-server\\src\\main\\resources\\tmp.yaml")
    val map2 = MapUtils.invertMap(map) as Map<String, String>
    map2.values.map { it.substring(1, it.length - 1) }
    println(JsonHelper.mapper.writeValueAsString(map2))
    genTmpYaml(tmpFile, map2)
}

fun addDictManager() {
    val map: MutableMap<String, String> = mutableMapOf()
    
    val rootDir = FileUtils.getFile("D:\\Project\\Seer\\mwms-server\\src\\main\\kotlin\\com\\shadow\\falcon")
    val files = scan(rootDir, mutableListOf())
    
    for (file in files) {
        // val file =
        //     FileUtils.getFile("D:\\Project\\Seer\\mwms-server\\src\\main\\kotlin\\com\\shadow\\falcon\\bp\\bc\\BindBinContainerBp.kt")
        
        val lines = scanFile(file, map)
        writeBackKt(file, lines)
    }
    
    println("")
}

fun combineYamls() {
    val y1 = FileUtils.getFile("D:\\Project\\Seer\\mwms-server\\src\\main\\resources\\tmp.yaml")
    val keyToCn: Map<String, String> = JsonFileHelper.readYamlFromFile(y1, jacksonTypeRef()) ?: emptyMap()
    
    val y2 = FileUtils.getFile("D:\\Project\\Seer\\mwms-server\\src\\main\\resources\\tmp2.yaml")
    val keyToEn: Map<String, String> = JsonFileHelper.readYamlFromFile(y2, jacksonTypeRef()) ?: emptyMap()
    
    val cnToEn: MutableMap<String, I18nMapping> = mutableMapOf()
    for (e in keyToEn) {
        println(e.key)
        val cn = keyToCn[e.key] ?: continue
        // TODO 中文转英文
        // cnToEn[cn] = I18nMapping(cn = cn, en = e.value)
        // Key 转 英文
        cnToEn[e.key] = I18nMapping(cn = cn, en = e.value)
    }
    
    val y3 = FileUtils.getFile("D:\\Project\\Seer\\mwms-server\\src\\main\\resources\\tmp3.yaml")
    JsonFileHelper.writeYamlToFile(y3, cnToEn)
    
    // val tMap: Map<String, I18nMapping> = JsonFileHelper.readYamlFromFile(y3, jacksonTypeRef()) ?: emptyMap()
    // println()
}

class I18nMapping(
    var cn: String,
    var en: String,
    var params: MutableList<String> = mutableListOf()
)

fun yaml2Kt() {
    
    val tmpFile = FileUtils.getFile("D:\\Project\\Seer\\mwms-server\\src\\main\\resources\\tmp3.yaml")
    val map: MutableMap<String, I18nMapping> =
        JsonFileHelper.readYamlFromFile(tmpFile, jacksonTypeRef()) ?: mutableMapOf<String, I18nMapping>()
    
    val rootDir = FileUtils.getFile("D:\\Project\\Seer\\mwms-server\\src\\main\\kotlin\\com\\shadow\\falcon")
    val files = scan(rootDir, mutableListOf())
    
    for (file in files) {
        // val file =
        //     FileUtils.getFile("D:\\Project\\Seer\\mwms-server\\src\\main\\kotlin\\com\\shadow\\falcon\\FalconController.kt")
        val lines = scanAndReplace(file, map)
        
        writeBackKt2(file, lines)
    }
}

fun tmpToDictYaml() {
    
    val tmpFile = FileUtils.getFile("D:\\Project\\Seer\\mwms-server\\src\\main\\resources\\tmp3.yaml")
    val map: MutableMap<String, I18nMapping> =
        JsonFileHelper.readYamlFromFile(tmpFile, jacksonTypeRef()) ?: mutableMapOf<String, I18nMapping>()
    
    val dictMap = map.values.associate { it.en to it.cn }
    
    val dictFile = FileUtils.getFile("D:\\Project\\Seer\\mwms-server\\src\\main\\resources\\tmp4.yaml")
    JsonFileHelper.writeYamlToFile(dictFile, dictMap)
}

fun lo(vararg args: Any?): String {
    val text = "新分配机器人 rdsCoreOrderId={} 请求={} 机器人={}"
    return String.format(text, *args)
}

fun test() {
    println(lo("asdfzxc1234", I18nMapping("cn", "en"), 3))
}

fun main() {
    println("Shadow Dict util start")
    
    // kt2tmpYaml()
    // ------------------------------------------
    
    // addDictManager()
    // ------------------------------------------
    
    // combineYamls()
    
    // -----------------------------------------
    
    yaml2Kt()
    
    // ---------------------------------------
    
    tmpToDictYaml()
    // --------------------------
    
    // test()
    
    println("Shadow Dict util end")
}

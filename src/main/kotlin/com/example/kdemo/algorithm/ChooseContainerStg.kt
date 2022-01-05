package com.example.kdemo.algorithm

import kotlin.math.ceil

class ChooseContainerStg {
}

fun main() {
    val tq = 100.0
    val cl = prepareContainersSortedDesc()
    
    // cl 最小的都比 tq 大    >>  cl.last()
    // cl 最大的比 tq 小     >>  cl[x] + cl[y] + cl[z]
    // cl.first() < tq < cl.last()  >>  cl[i]
    
    if (cl.last().qty >= tq) {
        val res = cl.last()
    } else if (cl.first().qty == tq) {
        val res = cl.first()
    } else if (cl.first().qty >= tq) {
        val t = cl.indexOfLast { it.qty >= tq }
        val res = cl[t]
    } else {
        // cl[x] + cl[y] + cl[z] + ...
        
        // TODO check if enough
        
        
        for (i in cl.indices) {
            var rest = tq
            while (rest > 0) {
            
            }
        }
        
    }
    
}

// fun chooseByPackage(cl: List<Container>, tq: Double) {
//
//     var v = Array(size = 5, init = { IntArray(ceil(tq).toInt()) })
//
//     for (i in cl.indices) {
//         for (j in 0 until ceil(tq).toInt()) {
//
//         }
//     }
// }

fun choose(cl: List<Container>, tq: Double) {
    val minContainerNum = cl.size
    val res: List<List<String>> = mutableListOf()
    var v = Array(size = 5, init = { IntArray(ceil(tq).toInt()) })
    
    for (i in 0 until cl.size)
        v[i][0] = 0
    for (j in 0 until ceil(tq).toInt())
        v[0][j] = 0
    
    for (i in 1 until cl.size) {
        // var sum = cl[i].qty
        // val tl = mutableListOf<String>(cl[i]._id)
        for (j in 1 until ceil(tq).toInt()) {
            // if (i == j) continue
            
            if () {
            
            } else {
            
            }
            
        }
    }
    
}


fun prepareContainersSortedDesc(): List<Container> {
    return listOf(
        Container("LX001", 200.0),
        Container("LX002", 100.0),
        Container("LX003", 70.0),
        Container("LX004", 60.0),
        Container("LX005", 50.0),
        Container("LX006", 50.0),
        Container("LX007", 40.0),
        Container("LX008", 10.0),
        Container("LX009", 10.0),
        Container("LX010", 10.0),
        Container("LX011", 10.0),
        Container("LX012", 10.0),
        Container("LX013", 10.0)
    ).sortedByDescending { it.qty }
}

class Container(
    var _id: String,
    var qty: Double,
    var weight: Int = 1
)
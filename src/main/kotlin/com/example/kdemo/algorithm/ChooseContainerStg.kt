package com.example.kdemo.algorithm

import com.example.kdemo.ObservableError
import com.example.kdemo.helper.JsonHelper


fun main() {
    
    // cl 最小的都比 tq 大    >>  cl.last()
    // cl 最大的比 tq 小     >>  cl[x] + cl[y] + cl[z]
    // cl.first() < tq < cl.last()  >>  cl[i]
    
    // if (cl.last().qty >= tq) {
    //     val res = cl.last()
    // } else if (cl.first().qty == tq) {
    //     val res = cl.first()
    // } else if (cl.first().qty >= tq) {
    //     val t = cl.indexOfLast { it.qty >= tq }
    //     val res = cl[t]
    // } else {
    //     // cl[x] + cl[y] + cl[z] + ...
    // }
    
    // val tq = 162.0
    val tq = 65.0
    val cl = prepareContainersSortedDesc()
    
    val r = greedyChoose(cl, tq)
    println("----------------------")
    println(JsonHelper.mapper.writeValueAsString(r))
    
}

fun prepareContainersSortedDesc(): List<Container> {
    return listOf(
        // Container("LX001", 200.0),
        // Container("LX002", 100.0),
        // Container("LX003", 70.0),
        // Container("LX004", 62.0),
        // Container("LX005", 50.0),
        // Container("LX006", 50.0),
        // Container("LX007", 40.0),
        // Container("LX008", 10.0),
        // Container("LX009", 10.0),
        // Container("LX010", 10.0),
        // Container("LX011", 10.0),
        // Container("LX012", 10.0),
        Container("LX013", 30.0),
        Container("LX004", 20.0),
        Container("LX015", 18.0),
        Container("LX016", 17.0),
        Container("LX017", 10.0),
        Container("LX018", 10.0)
    ).sortedByDescending { it.qty }
}

/**
 * 不带权重模式的
 * 前置条件：qty 降序排序 cl
 *
 * for  i : 0 -> n
 *      for j : 0 -> n
 *          if 加上这个 >= tq
 *              判断是否是最优解？
 *                  1、容器数量 < 当前最优解容器数
 *                  2、剩余量 < 当前最优解剩余量
 *              如果是的话，替换最优解
 *          else
 *              直接加上这个（降序排序，越往后需要的数量越多，所以优先加前面的）
 *
 * ------------------------------------------------------------
 *
 * weight 优先模式的：weight 优先模式下，尽可能少的出箱子
 * 前置条件：weight 排序
 *
 * for  i : 0 -> n
 *      for j : 0 -> n
 *          if 加上这个 >= tq
 *              加上是加权值是否最优解？
 *              如果是的话，替换最优解
 *          else
 *              直接加上这个
 *
 * ------------------------------------------------------------
 *
 * 混合权重和数量的
 *
 * 得加钱
 *
 * ------------------------------------------------------------
 */

/**
 * 无法精确匹配，如：
 * 现有 70、62、50、50、40，要出：162
 * 现有 30、20、18、17，要出：65
 */
fun greedyChoose(cl: List<Container>, tq: Double): List<Container> {
    var bestResolution: MutableList<Container> = cl as MutableList<Container>
    var bestRest = bestResolution.sumOf { it.qty } - tq
    
    if (bestRest < 0) throw ObservableError("库存不够")
    
    if (cl.first().qty >= tq) return listOf(cl.last { it.qty >= tq })
    
    for (i in cl.indices) {
        val currentList = mutableListOf(cl[i])
        for (j in i + 1 until cl.size) {
            
            val t = currentList.sumOf { it.qty } + cl[j].qty
            if (t >= tq) {
                if (currentList.size + 1 <= bestResolution.size && t - tq < bestRest) {
                    bestRest = t - tq
                    bestResolution = (currentList.map { it }.toMutableList() + cl[j]) as MutableList<Container>
                    println(JsonHelper.mapper.writeValueAsString(bestResolution))
                    if (t == tq) return bestResolution  // 就是最优解，可以返回了
                }
            } else if (currentList.size > bestResolution.size) {
                break
            } else {
                currentList += cl[j]
            }
        }
    }
    
    return bestResolution
}

class Container(
    var _id: String,
    var qty: Double
)

/**
 * TODO 动态规划
 *
 * 第 N 个箱子一定是在 N-1，N-2 个箱子的基础上出的容器数最少的
 *
 */
fun dynamicChoose(cl: List<Container>, tq: Double): List<Container> {
    
    
    return emptyList()
}
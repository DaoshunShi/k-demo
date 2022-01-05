package com.example.kdemo.algorithm

import kotlin.math.max

/**
 * 背包问题
 *
 * 输入：物品集合 U = {u1,u2,u3,...,un}，体积分别为 s1,s2,s3,...,sn，价值分别为 v1,v2,v3,...,vn，容量为 C 的背包。
 * 输出；背的最大总价值
 */
fun main() {
    val n = 5
    val c = 8
    
    val ul = listOf(1, 2, 3, 4, 5)
    val sl = listOf(2, 2, 6, 5, 4)
    val vl = listOf(6, 3, 5, 4, 6)
    
    var v = Array(size = 5, init = { IntArray(c) })
    for (i in 0 until n)
        v[i][0] = 0
    for (j in 0 until n)
        v[0][j] = 0
    for (i in 1 until n)
        for (j in 1 until c) {
            v[i][j] = v[i - 1][j]
            if (sl[i - 1] < j) {
                v[i][j] = max(v[i][j], v[i - 1][j - sl[i - 1]] + vl[i - 1])
            }
        }
    
    val result = v
}
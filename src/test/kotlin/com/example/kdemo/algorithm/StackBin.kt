package com.example.kdemo.algorithm


class StackBin(
    var binId: String = "",
    var isEmpty: Boolean = true,
    var column: Int = 1,
    var layer: Int = 1
) : Comparable<StackBin> {
    override fun compareTo(other: StackBin): Int {
        return when {
            column > other.column -> 1
            column < other.column -> -1
            layer > other.layer -> 1
            layer < other.layer -> -1
            else -> 0
        }
    }
}

fun initStackBinList(): List<StackBin> {
    return listOf(
        StackBin("bin-3-3", true, 3, 3),
        StackBin("bin-2-1", true, 2, 1),
        StackBin("bin-2-2", true, 2, 2),
        StackBin("bin-1-3", true, 1, 3),
        StackBin("bin-2-3", true, 2, 3),
        StackBin("bin-3-1", true, 3, 1),
        StackBin("bin-3-2", true, 3, 2),
        StackBin("bin-1-2", true, 1, 2),
        StackBin("bin-1-1", true, 1, 1),
    )
}

val customComparator: Comparator<StackBin> = Comparator { o1, o2 ->
    when {
        o1.column > o2.column -> return@Comparator 1
        o1.column < o2.column -> return@Comparator -1
        o1.layer > o2.layer -> return@Comparator 1
        o1.layer < o2.layer -> return@Comparator -1
        else -> return@Comparator 0
    }
}

fun sortBinList(list: List<StackBin>): List<StackBin> {
    return list.sortedWith(customComparator)
}

fun availableToLoad(targetBin: String, binList: List<StackBin>): Boolean {
    binList.reversed().forEach { bin ->
        if (bin.binId == targetBin) return !bin.isEmpty
        else {
            if (!bin.isEmpty) return false
        }
    }
    
    return false
}

fun availableToUnload(targetBin: String, binList: List<StackBin>): Boolean {
    binList.reversed().forEach { bin ->
        if (bin.binId == targetBin) return bin.isEmpty
        else {
            if (!bin.isEmpty) return false
        }
    }
    
    return false
}

fun preferLoad(binList: List<StackBin>): StackBin? {
    binList.reversed().forEach { bin ->
        if (!bin.isEmpty) return bin
    }
    
    return null
}

fun preferUnload(binList: List<StackBin>): StackBin? {
    var lastBin: StackBin? = null
    binList.reversed().forEach { bin ->
        if (!bin.isEmpty) return lastBin
        lastBin = bin
    }
    
    return null
}

fun main() {
    var binList = initStackBinList()
    
    // val t1 = sortBinList(binList)
    val t2 = binList.sorted()
    val targetBin = "bin-2-2"
}

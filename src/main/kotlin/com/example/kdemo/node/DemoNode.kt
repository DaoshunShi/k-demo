package com.example.kdemo.node

import com.example.kdemo.helper.IdHelper

class DemoNode(
    var _id: String = IdHelper.objectIdStr(),
    var name: String = "",
    var parent: DemoNode? = null,
    var left: DemoNode? = null,
    var right: DemoNode? = null,
    var isRoot: Boolean = false
)

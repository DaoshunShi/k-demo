package com.example.kdemo.concurrency

import org.springframework.stereotype.Service
import java.util.concurrent.CopyOnWriteArraySet

@Service
class SyncMapService {
    var set = CopyOnWriteArraySet<String>()
}


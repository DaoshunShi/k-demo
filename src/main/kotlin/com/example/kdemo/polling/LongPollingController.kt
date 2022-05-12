// package com.example.kdemo.polling
//
// import org.slf4j.LoggerFactory
// import org.springframework.stereotype.Controller
// import org.springframework.web.bind.annotation.PostMapping
// import org.springframework.web.bind.annotation.RequestBody
// import org.springframework.web.bind.annotation.RequestMapping
// import org.springframework.web.bind.annotation.RestController
//
// @RestController
// @RequestMapping("/long-polling")
// class LongPollingController {
//     private val logger = LoggerFactory.getLogger(javaClass)
//
//     @PostMapping("/index")
//     fun index() {
//         println("LongPollingController.index")
//     }
//
//     @PostMapping("/publish-msg")
//     fun publishMsg(@RequestBody req: String) {
//         println("publish msg $req")
//         AsyncLongPollingServlet().message = req
//     }
//
// }
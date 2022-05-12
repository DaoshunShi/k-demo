// package com.example.kdemo.polling
//
// import org.slf4j.LoggerFactory
// import java.io.IOException
// import java.io.PrintWriter
// import java.util.concurrent.TimeUnit
// import javax.annotation.PostConstruct
// import javax.servlet.AsyncEvent
// import javax.servlet.AsyncListener
// import javax.servlet.annotation.WebServlet
// import javax.servlet.http.HttpServlet
// import javax.servlet.http.HttpServletRequest
// import javax.servlet.http.HttpServletResponse
//
//
// // TODO 跑不起来
// // https://mp.weixin.qq.com/s/sv_ko4kEM7nV7wo8EZAELQ
// @WebServlet(urlPatterns = ["/asyncLongPollingServlet"], asyncSupported = true)
// class AsyncLongPollingServlet : HttpServlet() {
//
//     private val logger = LoggerFactory.getLogger(javaClass)
//
//     var message: String? = "welcome"
//
//     override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
//         // AsyncContext
//         val asyncContext = req.startAsync()
//
//         // timeout
//         val sleepTime = 4700
//
//         // add listener
//         asyncContext.addListener(CustomAsyncListener())
//
//         // asyncContext.start
//         asyncContext.start(Runnable {
//             //【5.1】hold住连接直到超时了
//             //【5.1】hold住连接直到超时了
//             var curSleepTime: Long = 0
//             var timeout = false
//             while (message == null) {
//                 curSleepTime += 100
//                 try {
//                     TimeUnit.MILLISECONDS.sleep(200)
//                 } catch (e: InterruptedException) {
//                     e.printStackTrace()
//                 }
//                 if (curSleepTime >= sleepTime) {
//                     timeout = true
//                     break
//                 }
//             }
//             if (timeout) { //超时了，直接返回即可.
//                 asyncContext.complete()
//                 return@Runnable
//             }
//
//             //【5.2】获取ServletResponse，返回信息
//
//             //【5.2】获取ServletResponse，返回信息
//             var pw: PrintWriter? = null
//             try {
//                 pw = asyncContext.response.writer
//                 if (message == null) {
//                     pw.write("")
//                 } else {
//                     pw.write(message)
//                 }
//                 pw.flush()
//
//                 // 【5.3】必须明确通知AsyncContext已经完成
//                 asyncContext.complete()
//                 message = null
//             } catch (e: IOException) {
//                 e.printStackTrace()
//             }
//         })
//     }
// }
//
// class CustomAsyncListener : AsyncListener {
//     override fun onComplete(event: AsyncEvent?) {
//         println("CustomAsyncListener.onComplete")
//     }
//
//     override fun onTimeout(event: AsyncEvent?) {
//         println("CustomAsyncListener.onTimeout")
//     }
//
//     override fun onError(event: AsyncEvent?) {
//         println("CustomAsyncListener.onError")
//     }
//
//     override fun onStartAsync(event: AsyncEvent?) {
//         println("CustomAsyncListener.onStartAsync")
//     }
//
// }
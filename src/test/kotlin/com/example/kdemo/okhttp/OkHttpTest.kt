package com.example.kdemo.okhttp

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class OkHttpTest {

}

fun getAUrl(url: String) : String{
    val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request).execute().use { response -> return response.body()!!.string() }
   
}

fun main(args: Array<String>) {
    val response = getAUrl("https://raw.github.com/square/okhttp/master/README.md")
    println(response)
}



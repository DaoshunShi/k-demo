package com.example.kdemo.remote

import com.example.kdemo.ObservableError
import com.example.kdemo.helper.JsonHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Service
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.converter.jaxb.JaxbConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.annotation.PostConstruct


@Service
class RdsCoreManager() {
    
    @Volatile
    private var service: RdsCoreRemoteService? = null
    
    @PostConstruct
    fun initRemoteService() {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        
        val client = OkHttpClient.Builder().addInterceptor(logging).build()
        
        // TODO
        val urlRoot = StringUtils.firstNonBlank("http://localhost")
        
        val retrofit = Retrofit.Builder().baseUrl(urlRoot).addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(JaxbConverterFactory.create())
            .addConverterFactory(JacksonConverterFactory.create(JsonHelper.mapper)).client(client).build()
        
        service = retrofit.create(RdsCoreRemoteService::class.java)
    }
    
    fun getService(): RdsCoreRemoteService {
        if (service == null) initRemoteService()
        return service ?: throw ObservableError("未初始化 RDS-CORE 客户端")
    }
}
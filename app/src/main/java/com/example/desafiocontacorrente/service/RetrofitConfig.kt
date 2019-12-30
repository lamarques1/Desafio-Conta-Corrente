package com.example.desafiocontacorrente.service

import com.example.desafiocontacorrente.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConfig {
    val BASE_URL = "https://www.yourgps.com.br/api/"
    private var retrofit: Retrofit? = null

    private val gson = GsonBuilder()
        .create()

    fun getClient(): Retrofit{
        if (retrofit == null){
            val interceptor = HttpLoggingInterceptor()

            if (BuildConfig.DEBUG){
                interceptor.level = HttpLoggingInterceptor.Level.BODY
            }else{
                interceptor.level = HttpLoggingInterceptor.Level.NONE
            }

            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return this.retrofit!!
    }
}
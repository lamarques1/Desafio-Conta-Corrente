package com.example.desafiocontacorrente.service

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConfig {
    val BASE_URL = "https://www.yourgps.com.br/api/"
    private var retrofit: Retrofit? = null

    private val gson = GsonBuilder()
        .create()

    fun getClient(): Retrofit{
        if (retrofit == null){
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return this.retrofit!!
    }
}
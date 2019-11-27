package com.example.desafiocontacorrente.service

import com.example.desafiocontacorrente.model.Status
import com.example.desafiocontacorrente.model.User
import retrofit2.Call
import retrofit2.http.*

interface RetrofitEndpoint {
    @FormUrlEncoded
    @POST("./check-login")
    fun checkLogin(@Field("email") email: String,
                   @Field("password") password: String)
        :Call<Status>

    @FormUrlEncoded
    @POST("./get-user")
    fun getUser(@Field("email") email: String) : Call<User>
}
package com.example.desafiocontacorrente.service

import com.example.desafiocontacorrente.model.Banking
import com.example.desafiocontacorrente.model.Status
import com.example.desafiocontacorrente.model.User
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RetrofitEndpoint {
    @FormUrlEncoded
    @POST("./check-login")
    fun checkLogin(@Field("email") email: String,
                   @Field("password") password: String)
            :Call<Status>

    @FormUrlEncoded
    @POST("./get-user")
    fun getUser(@Field("email") email: String)
            : Call<User>

    @FormUrlEncoded
    @POST("./get-bank-statement")
    fun getBankStatement(@Field("id_user") userId: String)
            : Call<List<Banking>>

    @FormUrlEncoded
    @POST("./transfer")
    fun transfer(@Field("id_user_from") userFromId: String,
                 @Field("id_user_to") userToId: String,
                 @Field("value") value: String)
            : Call<Status>

}
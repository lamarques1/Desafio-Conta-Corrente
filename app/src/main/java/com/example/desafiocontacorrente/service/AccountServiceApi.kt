package com.example.desafiocontacorrente.service

import com.example.desafiocontacorrente.model.Banking
import com.example.desafiocontacorrente.model.Status
import com.example.desafiocontacorrente.model.User
import retrofit2.http.POST

interface AccountServiceApi {
    interface AccountServiceCallback<T> {
        fun onLoaded(result: T)
        fun onError(errorId: Int)
    }

    @POST("./check-login")
    fun validateUser(email: String,
                     password: String,
                     callback: AccountServiceCallback<Status>)

    @POST("./get-user")
    fun getUser(email: String,
                callback: AccountServiceCallback<User>)

    @POST("./get-bank-statement")
    fun getBankStatement(userId: String,
                         callback: AccountServiceCallback<List<Banking>>)

    @POST("./transfer")
    fun transfer(userFromId: String,
                 userToId: String,
                 value: String,
                 callback: AccountServiceCallback<Status>)
}
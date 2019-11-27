package com.example.desafiocontacorrente.service

import com.example.desafiocontacorrente.model.Status
import com.example.desafiocontacorrente.model.User
import retrofit2.http.POST

interface AccountServiceApi {
    interface AccountServiceCallback<T> {
        fun onLoaded(result: T)
        fun onError(errorId: Int)
    }

    @POST("./check-login")
    fun validateUser(email: String, password: String, callback: AccountServiceCallback<Status>)

    @POST("./get-user")
    fun getUser(email: String, callback: AccountServiceCallback<User>)
}
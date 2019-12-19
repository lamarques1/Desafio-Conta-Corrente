package com.example.desafiocontacorrente.service

import com.example.desafiocontacorrente.R
import com.example.desafiocontacorrente.model.Banking
import com.example.desafiocontacorrente.model.Status
import com.example.desafiocontacorrente.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountServiceImpl : AccountServiceApi{
    private val mRetrofit: RetrofitEndpoint
        get() = RetrofitConfig().getClient().create(RetrofitEndpoint::class.java)

    override fun validateUser(
        email: String,
        password: String,
        callback: AccountServiceApi.AccountServiceCallback<Status>
    ) {
        val callLogin = mRetrofit.checkLogin(email, password)
        callLogin.enqueue(object : Callback<Status>{
            override fun onResponse(call: Call<Status>, response: Response<Status>) {
                if (response.code() == 200){
                    val result = response.body()!!
                    callback.onLoaded(result)
                }else{
                    callback.onError(R.string.error_search)
                }
            }

            override fun onFailure(call: Call<Status>, t: Throwable) {
                callback.onError(R.string.error_search)
            }
        })
    }

    override fun getUser(email: String, callback: AccountServiceApi.AccountServiceCallback<User>) {
        val callUser = mRetrofit.getUser(email)
        callUser.enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.code() == 200){
                    val result: User
                    try {
                        result = response.body()!!
                        callback.onLoaded(result)
                    }catch (e: Exception){
                        callback.onError(R.string.error_user_not_found)
                    }

                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                callback.onError(R.string.error_search)
            }
        })
    }

    override fun getBankStatement(
        userId: String,
        callback: AccountServiceApi.AccountServiceCallback<List<Banking>>
    ) {
        val callBankStatement = mRetrofit.getBankStatement(userId)
        callBankStatement.enqueue(object : Callback<List<Banking>>{
            override fun onResponse(call: Call<List<Banking>>, response: Response<List<Banking>>) {
                if (response.code() == 200){
                    val result: List<Banking>
                    try {
                        result = response.body()!!
                        callback.onLoaded(result)
                    }catch (e: Exception){
                        callback.onError(R.string.error_search)
                    }
                }
            }

            override fun onFailure(call: Call<List<Banking>>, t: Throwable) {
                callback.onError(R.string.error_search)
            }
        })
    }

    override fun transfer(
        userFromId: String,
        userToId: String,
        value: String,
        callback: AccountServiceApi.AccountServiceCallback<Status>
    ) {
        val callTransfer = mRetrofit.transfer(userFromId, userToId, value)
        callTransfer.enqueue(object : Callback<Status>{
            override fun onResponse(call: Call<Status>, response: Response<Status>) {
                if (response.code() == 200){
                    val result: Status
                    try {
                        result = response.body()!!
                        callback.onLoaded(result)
                    }catch (e: Exception){
                        callback.onError(R.string.error_make_transfer)
                    }
                }
            }

            override fun onFailure(call: Call<Status>, t: Throwable) {
                callback.onError(R.string.error_make_transfer)
            }
        })
    }
}
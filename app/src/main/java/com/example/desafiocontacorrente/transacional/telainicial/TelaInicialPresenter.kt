package com.example.desafiocontacorrente.transacional.telainicial

import com.example.desafiocontacorrente.model.User
import com.example.desafiocontacorrente.service.AccountServiceApi
import com.example.desafiocontacorrente.service.AccountServiceImpl

class TelaInicialPresenter(val view: TelaInicialContract.View) : TelaInicialContract.Presenter {

    override fun getUserInfo(email: String) {
        val webClient = AccountServiceImpl()

        webClient.getUser(email, object : AccountServiceApi.AccountServiceCallback<User>{
            override fun onLoaded(result: User) {
                view.bindUserInfo(result)
            }

            override fun onError(errorId: Int) {
                view.displayErrorMessage(errorId)
            }

        })

    }
}
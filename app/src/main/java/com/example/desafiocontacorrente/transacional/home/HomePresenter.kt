package com.example.desafiocontacorrente.transacional.home

import com.example.desafiocontacorrente.model.User
import com.example.desafiocontacorrente.service.AccountServiceApi
import com.example.desafiocontacorrente.service.AccountServiceImpl
import com.example.desafiocontacorrente.utils.SharedPrefUtil

class HomePresenter(val view: HomeContract.View) : HomeContract.Presenter {

    override fun getUserInfo() {
        val user = SharedPrefUtil().getUser(view.getContext())
        val webClient = AccountServiceImpl()

        webClient.getUser(user.email, object : AccountServiceApi.AccountServiceCallback<User>{
            override fun onLoaded(result: User) {
                view.bindUserInfo(result)
            }

            override fun onError(errorId: Int) {
                view.displayErrorMessage(errorId)
            }

        })

    }
}
package com.example.desafiocontacorrente.transacional

import com.example.desafiocontacorrente.model.User
import com.example.desafiocontacorrente.service.AccountServiceApi
import com.example.desafiocontacorrente.service.AccountServiceImpl
import com.example.desafiocontacorrente.transacional.home.HomeFragment
import com.example.desafiocontacorrente.utils.SharedPrefUtil

class MainPresenter(val view: MainContract.View): MainContract.Presenter {

    override fun getUserInfo(email: String) {
        val webClient = AccountServiceImpl()

        webClient.getUser(email, object : AccountServiceApi.AccountServiceCallback<User>{
            override fun onLoaded(result: User) {
                SharedPrefUtil().setUser(view.getContext(), result)

                view.changeFragment(HomeFragment())
            }

            override fun onError(errorId: Int) {

            }

        })

    }
}
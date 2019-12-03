package com.example.desafiocontacorrente.transacional.home

import com.example.desafiocontacorrente.R
import com.example.desafiocontacorrente.model.User
import com.example.desafiocontacorrente.service.AccountServiceApi
import com.example.desafiocontacorrente.service.AccountServiceImpl
import com.example.desafiocontacorrente.utils.Connection
import com.example.desafiocontacorrente.utils.SharedPrefUtil

class HomePresenter(val view: HomeContract.View) : HomeContract.Presenter {

    /**
     * Request user info from service
     * Before all, validate if user has internet connection
     */
    override fun getUserInfo() {
        val user = SharedPrefUtil().getUser(view.getContext())
        val webClient = AccountServiceImpl()

        if(!Connection().isConnected(view.getContext())){
            view.displayErrorMessage(R.string.error_no_connection)

        }else{
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
}
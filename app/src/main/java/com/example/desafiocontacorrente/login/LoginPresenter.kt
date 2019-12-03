package com.example.desafiocontacorrente.login

import com.example.desafiocontacorrente.R
import com.example.desafiocontacorrente.model.Status
import com.example.desafiocontacorrente.model.User
import com.example.desafiocontacorrente.service.AccountServiceApi
import com.example.desafiocontacorrente.service.AccountServiceImpl
import com.example.desafiocontacorrente.utils.Connection

class LoginPresenter(val view: LoginContract.View) : LoginContract.Presenter {

    override fun authUser(email: String, password: String) {
        val webClient = AccountServiceImpl()

        if (email.isBlank() || password.isBlank()){
            view.displayErrorMessage(R.string.error_invalid_field)

        }else if(!Connection().isConnected(view.getContext())){
            view.displayErrorMessage(R.string.error_no_connection)

        }else{
            webClient.validateUser(email, password, object : AccountServiceApi.AccountServiceCallback<Status>{
                override fun onLoaded(result: Status) {
                    if (result.status){
                        view.authUser()
                    }else{
                        view.displayErrorMessage(R.string.error_user_not_found)
                    }
                }

                override fun onError(errorId: Int) {
                    view.displayErrorMessage(errorId)
                }
            })
        }

    }
}
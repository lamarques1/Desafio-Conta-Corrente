package com.example.desafiocontacorrente.transactional

import com.example.desafiocontacorrente.R
import com.example.desafiocontacorrente.model.User
import com.example.desafiocontacorrente.service.AccountServiceApi
import com.example.desafiocontacorrente.service.AccountServiceImpl
import com.example.desafiocontacorrente.transactional.home.HomeFragment
import com.example.desafiocontacorrente.utils.Connection
import com.example.desafiocontacorrente.utils.SharedPrefUtil

class MainPresenter(val view: MainContract.View): MainContract.Presenter {

    /**
     * Get user information to display on view
     * Steps: Check connection, call getUser from service
     * @param email - used on getUser request
     */
    override fun getUserInfo(email: String) {
        val webClient = AccountServiceImpl()
        if(!Connection().isConnected(view.getContext())){
            view.displayErrorMessage(R.string.error_no_connection)
        }else{
            webClient.getUser(email, object : AccountServiceApi.AccountServiceCallback<User>{
                override fun onLoaded(result: User) {
                    SharedPrefUtil().setUser(view.getContext(), result)
                    view.bindNavHeader(result)
                    view.changeFragment(HomeFragment())
                }
                override fun onError(errorId: Int) {

                }
            })
        }

    }
}
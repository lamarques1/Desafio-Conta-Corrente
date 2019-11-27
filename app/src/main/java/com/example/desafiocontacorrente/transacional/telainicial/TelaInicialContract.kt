package com.example.desafiocontacorrente.transacional.telainicial

import android.view.View
import com.example.desafiocontacorrente.model.User

interface TelaInicialContract{
    interface View{
        fun setPresenter()
        fun bindViews(view: android.view.View)
        fun bindUserInfo(user: User)
        fun displayErrorMessage(errorId: Int)
        fun initListeners()
    }
    interface Presenter{
        fun getUserInfo(email: String)
    }
}
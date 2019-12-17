package com.example.desafiocontacorrente.login

import android.content.Context

interface LoginContract {
    interface View {
        fun setPresenter()
        fun getContext(): Context?
        fun initViews()
        fun initListeners()
        fun displayErrorMessage(errorId: Int)
        fun authUser()
    }
    interface Presenter{
        fun authUser(email: String, password: String)
    }
}
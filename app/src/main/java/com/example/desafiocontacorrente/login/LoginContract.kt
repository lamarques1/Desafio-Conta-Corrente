package com.example.desafiocontacorrente.login

interface LoginContract {
    interface View {
        fun setPresenter()
        fun initViews()
        fun initListeners()
        fun displayErrorMessage(errorId: Int)
        fun authUser()
    }
    interface Presenter{
        fun authUser(email : String, password: String)
    }
}
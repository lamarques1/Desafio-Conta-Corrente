package com.example.desafiocontacorrente.transactional.home

import android.content.Context
import com.example.desafiocontacorrente.model.User

interface HomeContract{
    interface View{
        fun setPresenter()
        fun getContext(): Context?
        fun initViews(view: android.view.View)
        fun bindUserInfo(user: User)
        fun displayErrorMessage(errorId: Int)
        fun initListeners()
    }
    interface Presenter{
        fun getUserInfo()
    }
}
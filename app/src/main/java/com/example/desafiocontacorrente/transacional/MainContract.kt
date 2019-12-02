package com.example.desafiocontacorrente.transacional

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.desafiocontacorrente.model.User

interface MainContract {
    interface View{
        fun setPresenter()
        fun getContext(): Context
        fun initViews()
        fun initListeners()
        fun bindNavHeader(user: User)
        fun changeFragment(fragment: Fragment)
        fun setTitulo(titulo: String)
        fun setProgress(visible: Boolean)
    }
    interface Presenter{
        fun getUserInfo(email: String)
    }
}
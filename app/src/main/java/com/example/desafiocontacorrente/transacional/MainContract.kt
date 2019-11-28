package com.example.desafiocontacorrente.transacional

import android.content.Context
import androidx.fragment.app.Fragment

interface MainContract {
    interface View{
        fun setPresenter()
        fun getContext(): Context
        fun bindViews()
        fun changeFragment(fragment: Fragment)
        fun setTitulo(titulo: String)
    }
    interface Presenter{
        fun getUserInfo(email: String)
    }
}
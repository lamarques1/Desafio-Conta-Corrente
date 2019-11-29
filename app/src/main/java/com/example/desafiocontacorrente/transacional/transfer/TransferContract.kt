package com.example.desafiocontacorrente.transacional.transfer

import android.content.Context
import com.example.desafiocontacorrente.model.User


interface TransferContract {
    interface View{
        fun setPresenter()
        fun initViews(view: android.view.View)
        fun getContext() : Context
        fun initListeners()
        fun displayDialog(nameFrom: String, nameTo: String, value: String)
        fun displayErrorMessage()
        fun onRefresh()
    }
    interface Presenter{
        fun makeTransfer(emailTo: String, value: Int)
        fun getUserData(email: String): User?
    }


}
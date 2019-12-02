package com.example.desafiocontacorrente.transacional.transfer

import android.content.Context


interface TransferContract {
    interface View{
        fun setPresenter()
        fun initViews(view: android.view.View)
        fun getContext() : Context
        fun initListeners()
        fun displayDialog(nameFrom: String, nameTo: String, value: String)
        fun displayErrorMessage(errorId: Int)
    }
    interface Presenter{
        fun confirmData(emailTo: String, value: String)
        fun makeTransfer(value: String)
    }


}
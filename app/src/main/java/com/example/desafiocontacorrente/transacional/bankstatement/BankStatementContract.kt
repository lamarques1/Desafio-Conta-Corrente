package com.example.desafiocontacorrente.transacional.bankstatement

import android.content.Context
import com.example.desafiocontacorrente.model.Banking


interface BankStatementContract {
    interface View{
        fun setPresenter()
        fun getContext(): Context
        fun initViews(view: android.view.View)
        fun bindList(bankStatement: List<Banking>)
        fun bindEmptyState()
        fun displayErrorMessage(errorId: Int)
    }
    interface Presenter{
        fun getBankStatement()
    }
}
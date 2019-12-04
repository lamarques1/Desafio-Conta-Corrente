package com.example.desafiocontacorrente.transactional.bankstatement

import android.content.Context
import com.example.desafiocontacorrente.model.Banking


interface BankStatementContract {
    interface View{
        fun setPresenter()
        fun getContext(): Context
        fun initViews(view: android.view.View)
        fun initListeners()
        fun bindList(bankStatement: List<Banking>)
        fun bindEmptyState()
        fun displayErrorMessage(errorId: Int)
    }
    interface Presenter{
        fun getBankStatement()
    }
}
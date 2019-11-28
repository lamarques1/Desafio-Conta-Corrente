package com.example.desafiocontacorrente.transacional.bankstatement

import com.example.desafiocontacorrente.model.Banking
import com.example.desafiocontacorrente.service.AccountServiceApi
import com.example.desafiocontacorrente.service.AccountServiceImpl
import com.example.desafiocontacorrente.utils.SharedPrefUtil

class BankStatementPresenter(val view: BankStatementContract.View): BankStatementContract.Presenter {

    override fun getBankStatement() {
        val user = SharedPrefUtil().getUser(view.getContext())
        val webClient = AccountServiceImpl()

        webClient.getBankStatement(user.id, object : AccountServiceApi.AccountServiceCallback<List<Banking>>{
            override fun onLoaded(result: List<Banking>) {
                if (result.isEmpty()){
                    view.bindEmptyState()
                }else{
                    view.bindList(result)
                }
            }

            override fun onError(errorId: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }
}
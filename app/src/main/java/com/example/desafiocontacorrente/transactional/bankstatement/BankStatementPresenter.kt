package com.example.desafiocontacorrente.transactional.bankstatement

import com.example.desafiocontacorrente.R
import com.example.desafiocontacorrente.model.Banking
import com.example.desafiocontacorrente.service.AccountServiceApi
import com.example.desafiocontacorrente.service.AccountServiceImpl
import com.example.desafiocontacorrente.utils.Connection
import com.example.desafiocontacorrente.utils.SharedPrefUtil

class BankStatementPresenter(val view: BankStatementContract.View): BankStatementContract.Presenter {

    /**
     * Request bank statements from service
     * Before all, validate if user has internet connection
     */
    override fun getBankStatement() {
        val user = SharedPrefUtil().getUser(view.getContext())
        val webClient = AccountServiceImpl()

        if(!Connection().isConnected(view.getContext())){
            view.displayErrorMessage(R.string.error_no_connection)
        }else {
            webClient.getBankStatement(
                user.id,
                object : AccountServiceApi.AccountServiceCallback<List<Banking>> {

                override fun onLoaded(result: List<Banking>) {
                    if (result.isEmpty()) {
                        view.bindEmptyState()
                    } else {
                        view.bindList(result)
                    }
                }

                override fun onError(errorId: Int) {
                    view.displayErrorMessage(errorId)
                }

            })
        }
    }
}
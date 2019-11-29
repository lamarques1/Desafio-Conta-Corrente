package com.example.desafiocontacorrente.transacional.transfer

import com.example.desafiocontacorrente.model.Status
import com.example.desafiocontacorrente.model.User
import com.example.desafiocontacorrente.service.AccountServiceApi
import com.example.desafiocontacorrente.service.AccountServiceImpl
import com.example.desafiocontacorrente.utils.SharedPrefUtil

class TransferPresenter(val view: TransferContract.View): TransferContract.Presenter {

    /**
     * Make a new transfer from current user account
     * Steps: Update current user data (Balance), get destination user data (Id),
     * verify if transfer value < user balance, and call transfer service
     */
    override fun makeTransfer(emailTo: String, value: Int) {

        var userFrom = SharedPrefUtil().getUser(view.getContext()) // Get current user
        userFrom = getUserData(userFrom.email)!! // Update current user

        val userTo = getUserData(emailTo) // Get destination user

        // Verify if current user has enought money to transfer
        if (value < userFrom.balance.toInt()){
            val webClient = AccountServiceImpl()
            // Call service
            webClient.transfer(userFrom.id, userTo?.id!!, value.toString(), object : AccountServiceApi.AccountServiceCallback<Status>{
                override fun onLoaded(result: Status) {
                    view.displayDialog(userFrom.name, userTo.name, value.toString())
                }

                override fun onError(errorId: Int) {
                    view.displayErrorMessage()
                }

            })
        }
    }

    override fun getUserData(email: String): User? {

        var user: User? = null
        val webClient = AccountServiceImpl()

        webClient.getUser(email, object : AccountServiceApi.AccountServiceCallback<User>{
            override fun onLoaded(result: User) {
                user = result
            }

            override fun onError(errorId: Int) {
                view.displayErrorMessage()
            }
        }).apply { return user }
    }
}
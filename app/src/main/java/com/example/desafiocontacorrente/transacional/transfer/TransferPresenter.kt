package com.example.desafiocontacorrente.transacional.transfer

import com.example.desafiocontacorrente.R
import com.example.desafiocontacorrente.model.Status
import com.example.desafiocontacorrente.model.User
import com.example.desafiocontacorrente.service.AccountServiceApi
import com.example.desafiocontacorrente.service.AccountServiceImpl
import com.example.desafiocontacorrente.transacional.home.HomeFragment
import com.example.desafiocontacorrente.utils.Connection
import com.example.desafiocontacorrente.utils.SharedPrefUtil

class TransferPresenter(val view: TransferContract.View): TransferContract.Presenter {

    private var userFrom = SharedPrefUtil().getUser(view.getContext()) // Get current user
    private var userTo : User? = null


    /**
     * Confirm transfer data and request destination user info
     * Validate if user has internet connection
     */
    override fun confirmData(emailTo: String, value: String) {

        val webClient = AccountServiceImpl()
        if (emailTo.isBlank() || value.isBlank()){
            view.displayErrorMessage(R.string.error_invalid_field)
        }else{
            if(!Connection().isConnected(view.getContext())){
                view.displayErrorMessage(R.string.error_no_connection)
            }else{
                webClient.getUser(userFrom.email, object : AccountServiceApi.AccountServiceCallback<User>{
                    override fun onLoaded(result: User) {
                        userFrom = result
                    }

                    override fun onError(errorId: Int) {
                        view.displayErrorMessage(R.string.error_update_data)
                    }
                })
            }

            if(!Connection().isConnected(view.getContext())){
                view.displayErrorMessage(R.string.error_no_connection)
            }else{
                webClient.getUser(emailTo, object : AccountServiceApi.AccountServiceCallback<User> {
                    override fun onLoaded(result: User) {
                        userTo = result
                        makeTransfer(value)
                    }

                    override fun onError(errorId: Int) {
                        view.displayErrorMessage(R.string.error_invalid_email)
                    }
                })
            }
        }
    }

    /**
     * Verify if user has enought money to transfer and call the service
     */
    override fun makeTransfer(value: String) {
        if(userFrom.balance.toInt() > value.toInt()){
            val webClient = AccountServiceImpl()

            if(!Connection().isConnected(view.getContext())){
                view.displayErrorMessage(R.string.error_no_connection)
            }else{
                webClient.transfer(userFrom.id,
                    userTo?.id!!,
                    value,
                    object : AccountServiceApi.AccountServiceCallback<Status>{
                        override fun onLoaded(result: Status) {
                            if(result.status){
                                view.displayDialog(userFrom.name, userTo?.name!!, value)
                            }else{
                                view.displayErrorMessage(R.string.error_make_transfer)
                            }
                        }

                        override fun onError(errorId: Int) {
                            view.displayErrorMessage(R.string.error_make_transfer)
                        }
                    })
            }
        }else{
            view.displayErrorMessage(R.string.error_insufficient_balance)
        }
    }

}
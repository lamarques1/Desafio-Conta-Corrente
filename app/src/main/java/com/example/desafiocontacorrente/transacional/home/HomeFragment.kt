package com.example.desafiocontacorrente.transacional.home


import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.example.desafiocontacorrente.R
import com.example.desafiocontacorrente.login.LoginView
import com.example.desafiocontacorrente.model.User
import com.example.desafiocontacorrente.transacional.bankstatement.BankStatementFragment
import com.example.desafiocontacorrente.transacional.transfer.TransferFragment
import com.example.desafiocontacorrente.utils.BaseFragment

class HomeFragment : BaseFragment(), HomeContract.View {

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var container: LinearLayout
    private lateinit var txtName: TextView
    private lateinit var txtBalance: TextView
    private lateinit var btnBankStatement: Button
    private lateinit var btnTransfer: Button
    private lateinit var btnExit: Button
    private lateinit var btnRefresh: ImageButton

    private lateinit var presenter: HomeContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        setProgress(true)

        initViews(view)
        setTitulo(getString(R.string.title_home))
        initListeners()

        setPresenter()
        presenter.getUserInfo()


        return view
    }

    override fun onResume() {
        super.onResume()
        setTitulo(getString(R.string.title_home))
    }

    override fun setPresenter() {
        presenter = HomePresenter(this)
    }

    override fun getContext(): Context {
        return activity?.applicationContext!!
    }

    override fun initViews(view: View) {
        container = view.findViewById(R.id.homeContainer)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        txtName = view.findViewById(R.id.txtName)
        txtBalance = view.findViewById(R.id.txtSaldo)
        btnBankStatement = view.findViewById(R.id.btnExtrato)
        btnTransfer = view.findViewById(R.id.btnTransferir)
        btnExit = view.findViewById(R.id.btnSair)
        btnRefresh = view.findViewById(R.id.btnRefresh)
    }

    override fun initListeners() {
        swipeRefreshLayout.setOnRefreshListener {
            presenter.getUserInfo()
            swipeRefreshLayout.isRefreshing = false
        }

        btnExit.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle(getString(R.string.dialog_exit_title))
            builder.setMessage(getString(R.string.dialog_exit_body))

            builder.setPositiveButton(getString(R.string.dialog_yes)){ _, _ ->
                val loginIntent = Intent(activity, LoginView::class.java)
                activity?.finish()
                startActivity(loginIntent)
            }
            builder.setNegativeButton(getString(R.string.dialog_no)){ dialog, _ ->
                dialog.dismiss()
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        btnBankStatement.setOnClickListener {
            changeFragment(BankStatementFragment())
        }

        btnTransfer.setOnClickListener {
            changeFragment(TransferFragment())
        }

        btnRefresh.setOnClickListener {

            presenter.getUserInfo()
        }
    }

    override fun bindUserInfo(user: User) {
        container.visibility = View.GONE

        txtName.text = user.name
        txtBalance.text = user.balance

        container.visibility = View.VISIBLE
        setProgress(false)
    }

    override fun displayErrorMessage(errorId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}

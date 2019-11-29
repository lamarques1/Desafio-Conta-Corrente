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
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.example.desafiocontacorrente.R
import com.example.desafiocontacorrente.login.LoginView
import com.example.desafiocontacorrente.model.User
import com.example.desafiocontacorrente.transacional.bankstatement.BankStatementFragment
import com.example.desafiocontacorrente.utils.BaseFragment

class HomeFragment : BaseFragment(), HomeContract.View {

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var txtName: TextView
    private lateinit var txtSaldo: TextView
    private lateinit var btnExtrato: Button
    private lateinit var btnTransferir: Button
    private lateinit var btnSair: Button
    private lateinit var btnRefresh: ImageButton

    private lateinit var presenter: HomeContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tela_inicial, container, false)

        initViews(view)
        setTitulo("Minha Conta")
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
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        txtName = view.findViewById(R.id.txtName)
        txtSaldo = view.findViewById(R.id.txtSaldo)
        btnExtrato = view.findViewById(R.id.btnExtrato)
        btnTransferir = view.findViewById(R.id.btnTransferir)
        btnSair = view.findViewById(R.id.btnSair)
        btnRefresh = view.findViewById(R.id.btnRefresh)
    }

    override fun initListeners() {
        swipeRefreshLayout.setOnRefreshListener {
            presenter.getUserInfo()
            swipeRefreshLayout.isRefreshing = false
        }

        btnSair.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Sair da conta")
            builder.setMessage("Você realmente deseja sair da conta?")

            builder.setPositiveButton("SIM"){_, _ ->
                val loginIntent = Intent(activity, LoginView::class.java)
                activity?.finish()
                startActivity(loginIntent)
            }
            builder.setNegativeButton("NÃO"){dialog, _ ->
                dialog.dismiss()
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        btnExtrato.setOnClickListener {
            changeFragment(BankStatementFragment())
        }

        btnRefresh.setOnClickListener {
            presenter.getUserInfo()
        }
    }

    override fun bindUserInfo(user: User) {
        txtName.text = user.name
        txtSaldo.text = user.balance
    }

    override fun displayErrorMessage(errorId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}

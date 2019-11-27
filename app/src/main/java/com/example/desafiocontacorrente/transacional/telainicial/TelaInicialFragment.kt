package com.example.desafiocontacorrente.transacional.telainicial


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import com.example.desafiocontacorrente.R
import com.example.desafiocontacorrente.login.LoginView
import com.example.desafiocontacorrente.model.User
import com.example.desafiocontacorrente.utils.BaseFragment

/**
 * A simple [Fragment] subclass.
 */
class TelaInicialFragment(val email: String) : BaseFragment(), TelaInicialContract.View {

    private lateinit var txtName: TextView
    private lateinit var txtSaldo: TextView
    private lateinit var btnExtrato: Button
    private lateinit var btnTransferir: Button
    private lateinit var btnSair: Button

    private lateinit var presenter: TelaInicialContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tela_inicial, container, false)

        bindViews(view)
        setTitulo("Minha Conta")
        initListeners()

        setPresenter()
        presenter.getUserInfo(email)

        return view
    }

    override fun setPresenter() {
        presenter = TelaInicialPresenter(this)
    }

    override fun bindViews(view: View) {
        txtName = view.findViewById(R.id.txtName)
        txtSaldo = view.findViewById(R.id.txtSaldo)
        btnExtrato = view.findViewById(R.id.btnExtrato)
        btnTransferir = view.findViewById(R.id.btnTransferir)
        btnSair = view.findViewById(R.id.btnSair)
    }

    override fun initListeners() {
        btnSair.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Sair da conta")
            builder.setMessage("Você realmente deseja sair da conta?")

            builder.setPositiveButton("SIM"){dialog, which ->
                val loginIntent = Intent(activity, LoginView::class.java)
                activity?.finish()
                startActivity(loginIntent)
            }
            builder.setNegativeButton("NÃO"){dialog, which ->
                dialog.dismiss()
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
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

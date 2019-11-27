package com.example.desafiocontacorrente.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.desafiocontacorrente.R
import com.example.desafiocontacorrente.transacional.MainLogadoActivity
import com.example.desafiocontacorrente.utils.BaseActivity

class LoginView : BaseActivity(), LoginContract.View {

//    private lateinit var toolbar: Toolbar
    private lateinit var presenter: LoginContract.Presenter

    private lateinit var btnLogin: Button
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_view)

        setPresenter()
        initViews()
        initListeners()
        //TODO: Remover referencias ao toolbar (xml tbm)
        supportActionBar?.hide()
        /*toolbar.title = getString(R.string.title_acesso_a_conta)
        setSupportActionBar(toolbar)*/
    }

    override fun setPresenter() {
        presenter = LoginPresenter(this)
    }

    override fun initViews() {
//        toolbar = findViewById(R.id.toolbar)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
    }

    override fun initListeners() {
        btnLogin.setOnClickListener {
            //TODO: receber dados do editText
            presenter.authUser("lamarques.oliveira@evosystems.com.br", "123456")
        }
    }

    override fun authUser() {
        val mainLogadoIntent = Intent(this, MainLogadoActivity::class.java)
        mainLogadoIntent.putExtra("email", "lamarques.oliveira@evosystems.com.br")
        startActivity(mainLogadoIntent)
    }

    override fun displayErrorMessage(errorId: Int) {
        Toast.makeText(this, errorId, Toast.LENGTH_SHORT).show()
    }
}

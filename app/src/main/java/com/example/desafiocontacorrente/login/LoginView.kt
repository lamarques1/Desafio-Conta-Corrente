package com.example.desafiocontacorrente.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.desafiocontacorrente.R
import com.example.desafiocontacorrente.transacional.MainActivity
import com.example.desafiocontacorrente.utils.BaseActivity

class LoginView : BaseActivity(), LoginContract.View {

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
        supportActionBar?.hide()
    }

    override fun setPresenter() {
        presenter = LoginPresenter(this)
    }

    override fun initViews() {
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
    }

    override fun initListeners() {
        btnLogin.setOnClickListener {
            presenter.authUser(etEmail.text.toString().trim(), etPassword.text.toString().trim())
        }
    }

    override fun authUser() {
        val homeIntent = Intent(this, MainActivity::class.java)
        homeIntent.putExtra("email", etEmail.text.toString().trim())
        startActivity(homeIntent)
    }

    override fun displayErrorMessage(errorId: Int) {
        Toast.makeText(this, errorId, Toast.LENGTH_SHORT).show()
    }
}

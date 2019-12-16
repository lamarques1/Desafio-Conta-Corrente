package com.example.desafiocontacorrente.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.desafiocontacorrente.R
import com.example.desafiocontacorrente.transactional.MainActivity
import com.example.desafiocontacorrente.utils.BaseActivity
import java.util.*

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

    override fun getContext(): Context{
        return applicationContext
    }

    override fun initViews() {
        etEmail = findViewById(R.id.etEmailLogin)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
    }

    /**
     * When button tapped, call presenter to authenticate login.
     */
    override fun initListeners() {
        btnLogin.setOnClickListener {
            presenter.authUser(etEmail.text.toString().trim(), etPassword.text.toString().trim())
        }

        etEmail.setOnFocusChangeListener { v, _ ->
            if (!v.hasFocus()) {
                val imm  = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
            }
        }

        etPassword.setOnFocusChangeListener { v, _ ->
            if (!v.hasFocus()) {
                val imm  = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
            }
        }
    }

    /**
     * Start transactional activity with user email
     */
    override fun authUser() {
        val homeIntent = Intent(this, MainActivity::class.java)
        homeIntent.putExtra("email", etEmail.text.toString())
        homeIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        homeIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(homeIntent)
    }

    override fun displayErrorMessage(errorId: Int) {
        Toast.makeText(this, errorId, Toast.LENGTH_SHORT).show()
    }
}

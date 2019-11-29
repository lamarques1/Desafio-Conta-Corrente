package com.example.desafiocontacorrente.transacional

import android.content.Context
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.desafiocontacorrente.R
import com.example.desafiocontacorrente.transacional.home.HomeFragment
import com.example.desafiocontacorrente.transacional.home.HomePresenter
import com.example.desafiocontacorrente.utils.BaseActivity
import com.example.desafiocontacorrente.utils.SharedPrefUtil


open class MainActivity : BaseActivity(), MainContract.View {

    private lateinit var email : String
    private lateinit var toolbar: Toolbar
    private lateinit var fragmentContainer: FrameLayout
    private lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_logado)

        email = intent.getStringExtra("email")!!

        bindViews()
        setPresenter()
        presenter.getUserInfo(email)

        toolbar.title = ""
        setSupportActionBar(toolbar)
    }

    override fun bindViews() {
        toolbar = findViewById(R.id.toolbar)
        fragmentContainer = findViewById(R.id.conteudo)
    }

    override fun setPresenter() {
        presenter = MainPresenter(this)
    }

    override fun getContext(): Context {
        return applicationContext
    }

    //TODO: Realizar controle para telas de transferencia, utilizar when
    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.findFragmentById(R.id.conteudo) !is HomeFragment){
            changeFragment(HomeFragment())
        }else{
            finish()
        }
    }

    override fun changeFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.add(fragmentContainer.id, fragment)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun setTitulo(titulo: String){
        toolbar.title = titulo
    }

}

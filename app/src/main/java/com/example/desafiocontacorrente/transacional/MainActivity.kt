package com.example.desafiocontacorrente.transacional

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.desafiocontacorrente.R
import com.example.desafiocontacorrente.transacional.home.HomeFragment
import com.example.desafiocontacorrente.utils.BaseActivity


open class MainActivity : BaseActivity(), MainContract.View {

    private lateinit var email : String
    private lateinit var toolbar: Toolbar
    private lateinit var fragmentContainer: FrameLayout
    private lateinit var progressBar: ProgressBar

    private lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        email = intent.getStringExtra("email")!!

        bindViews()
        setPresenter()

        setProgress(true)
        presenter.getUserInfo(email)

        toolbar.title = getString(R.string.title_home)
        setSupportActionBar(toolbar)
    }

    override fun bindViews() {
        toolbar = findViewById(R.id.toolbar)
        fragmentContainer = findViewById(R.id.conteudo)
        progressBar = findViewById(R.id.progressBar)
    }

    override fun setPresenter() {
        presenter = MainPresenter(this)
    }

    override fun getContext(): Context {
        return applicationContext
    }

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

        fragmentTransaction.replace(fragmentContainer.id, fragment)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun setTitulo(titulo: String){
        toolbar.title = titulo
    }

    override fun setProgress(visible: Boolean){
        if (visible){
            progressBar.visibility = View.VISIBLE
            fragmentContainer.visibility = View.GONE
        }else{
            progressBar.visibility = View.GONE
            fragmentContainer.visibility = View.VISIBLE
        }
    }

}

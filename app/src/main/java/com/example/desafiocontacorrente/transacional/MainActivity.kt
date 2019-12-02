package com.example.desafiocontacorrente.transacional

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.desafiocontacorrente.R
import com.example.desafiocontacorrente.transacional.home.HomeFragment
import com.example.desafiocontacorrente.transacional.home.HomePresenter
import com.example.desafiocontacorrente.utils.BaseActivity
import com.example.desafiocontacorrente.utils.SharedPrefUtil
import com.google.android.material.navigation.NavigationView


open class MainActivity : BaseActivity(), MainContract.View {

    private lateinit var email : String
    private lateinit var toolbar: Toolbar
    private lateinit var fragmentContainer: FrameLayout
    private lateinit var presenter: MainContract.Presenter

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_logado)

        email = intent.getStringExtra("email")!!



        bindViews()
        setPresenter()
        presenter.getUserInfo(email)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navHome, R.id.navBankStatement, R.id.navExit
            ), drawerLayout
        )

        toolbar.title = ""
        setSupportActionBar(toolbar)

        val navController = findNavController(R.id.nav_host_fragment)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun bindViews() {
        toolbar = findViewById(R.id.toolbar)
        fragmentContainer = findViewById(R.id.nav_host_fragment)
        drawerLayout = findViewById(R.id.drawerLayout)
        navView = findViewById(R.id.navView)
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
        if (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) !is HomeFragment){
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

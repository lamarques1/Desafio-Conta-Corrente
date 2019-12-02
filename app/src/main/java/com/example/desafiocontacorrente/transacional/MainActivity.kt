package com.example.desafiocontacorrente.transacional

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.example.desafiocontacorrente.R
import com.example.desafiocontacorrente.model.User
import com.example.desafiocontacorrente.transacional.bankstatement.BankStatementFragment
import com.example.desafiocontacorrente.transacional.home.HomeFragment
import com.example.desafiocontacorrente.transacional.transfer.TransferFragment
import com.example.desafiocontacorrente.utils.BaseActivity
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.nav_header.*


open class MainActivity : BaseActivity(), MainContract.View {

    private lateinit var email : String
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var fragmentContainer: FrameLayout
    private lateinit var progressBar: ProgressBar

    private lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        email = intent.getStringExtra("email")!!

        initViews()
        initListeners()
        setPresenter()

        setProgress(true)
        presenter.getUserInfo(email)

        toolbar.title = getString(R.string.title_home)
        setSupportActionBar(toolbar)
    }

    override fun initViews() {
        toolbar = findViewById(R.id.mToolbar)
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView= findViewById(R.id.navView)
        fragmentContainer = findViewById(R.id.conteudo)
        progressBar = findViewById(R.id.progressBar)
    }

    override fun initListeners() {
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        toolbar.setNavigationOnClickListener { view ->
            view.setOnClickListener {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        navigationView.setNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.navBankStatement -> changeFragment(BankStatementFragment())
                R.id.navTransfer -> changeFragment(TransferFragment())
                R.id.navExit -> {
                    val builder = AlertDialog.Builder(this@MainActivity)
                    builder.setTitle(getString(R.string.dialog_exit_title))
                    builder.setMessage(getString(R.string.dialog_exit_body))

                    builder.setPositiveButton(getString(R.string.dialog_yes)){ _, _ ->
                        finish()
                    }
                    builder.setNegativeButton(getString(R.string.dialog_no)){ dialog, _ ->
                        dialog.dismiss()
                    }

                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                }
            }

            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun bindNavHeader(user: User) {
        navHeaderName.text = user.name
        navHeaderEmail.text = user.email

        Glide.with(this)
            .load(user.profile)
            .placeholder(R.drawable.ic_person_24px)
            .into(navHeaderProfile)
    }

    override fun setPresenter() {
        presenter = MainPresenter(this)
    }

    override fun getContext(): Context {
        return applicationContext
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}

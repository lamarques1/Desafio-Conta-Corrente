package com.example.desafiocontacorrente.transactional

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
import com.example.desafiocontacorrente.transactional.bankstatement.BankStatementFragment
import com.example.desafiocontacorrente.transactional.home.HomeFragment
import com.example.desafiocontacorrente.transactional.transfer.TransferFragment
import com.example.desafiocontacorrente.utils.BaseActivity
import com.example.desafiocontacorrente.utils.Connection
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.nav_header.*


open class MainActivity : BaseActivity(), MainContract.View {

    private lateinit var email : String
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle
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
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar , R.string.open_drawer, R.string.close_drawer)
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
                    showExitDialog(this)
                }
            }

            drawerLayout.closeDrawer(GravityCompat.START)
            true
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

    override fun getContext(): Context? = this

    /**
     * Handle back button action
     * Control Drawer visibility and fragment's changes
     */
    override fun onBackPressed() {
        if(!drawerLayout.isDrawerOpen(GravityCompat.START)){
            if (supportFragmentManager.findFragmentById(R.id.conteudo) !is HomeFragment){
                changeFragment(HomeFragment())
            }else{
                showExitDialog(this)
            }
        }else{
            drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    /**
     * Check if already exists the fragment specified, if not do the change
     */
    override fun changeFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager

        val oldFragment = fragmentManager.findFragmentById(R.id.conteudo)

        if (fragment != oldFragment) {
            if(!Connection().isConnected(this)){
                displayErrorMessage(R.string.error_no_connection)

            }else{
                val fragmentTransaction = fragmentManager.beginTransaction().let {
                    it.replace(fragmentContainer.id, fragment)
                    it.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    it.addToBackStack(null)
                }
                fragmentTransaction.commit()
            }
        }
    }

    override fun displayErrorMessage(errorId: Int) {
        Toast.makeText(this, errorId, Toast.LENGTH_SHORT).show()
    }

    /**
     * Control drawer layout visibility
     * @param lock - true: lock drawer layout closed, false: unlock
     */
    override fun lockDrawerLayout(lock: Boolean){
        if (lock) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }else{
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        }
    }

    override fun setTitulo(titulo: String){
        toolbar.title = titulo
    }

    /**
     * Handle loading time. Must start Visible and change to Gone after content's ready to show
     * @param visible
     */
    override fun setProgress(visible: Boolean){
        if (visible){
            progressBar.visibility = View.VISIBLE
            fragmentContainer.visibility = View.GONE
        }else{
            progressBar.visibility = View.GONE
            fragmentContainer.visibility = View.VISIBLE
        }
    }

    override fun getToggle(): ActionBarDrawerToggle{
        return toggle
    }

    /**
     * Handle clicks on toolbar icons
     * If current fragment's HomeFragment: open drawer layout
     * for any other fragment: call onBackPressed() returning to HomeFragment
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{
                val fragmentManager = supportFragmentManager
                if (fragmentManager.findFragmentById(R.id.conteudo) is HomeFragment){
                    drawerLayout.openDrawer(GravityCompat.START)
                }else{
                    onBackPressed()
                }
            }


        }
        return super.onOptionsItemSelected(item)
    }


}

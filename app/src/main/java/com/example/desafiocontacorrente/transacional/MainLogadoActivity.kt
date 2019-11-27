package com.example.desafiocontacorrente.transacional

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.desafiocontacorrente.R
import com.example.desafiocontacorrente.transacional.telainicial.TelaInicialFragment
import com.example.desafiocontacorrente.utils.BaseActivity

class MainLogadoActivity : BaseActivity() {

    companion object{
        lateinit var email : String
    }

    private lateinit var toolbar: Toolbar
    private lateinit var fragmentContainer: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_logado)

        email = intent.getStringExtra("email")!!
        bindViews()

        toolbar.title = ""
        setSupportActionBar(toolbar)

        changeFragment(TelaInicialFragment(email))
    }

    private fun bindViews() {
        toolbar = findViewById(R.id.toolbar)
        fragmentContainer = findViewById(R.id.conteudo)
    }

    fun changeFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val oldFragment = fragmentManager.findFragmentById(fragmentContainer.id)

        fragmentTransaction.add(fragmentContainer.id, fragment)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun setTitulo(titulo: String){
        toolbar.title = titulo
    }
}

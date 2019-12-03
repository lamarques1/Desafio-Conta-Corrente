package com.example.desafiocontacorrente.utils

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import com.example.desafiocontacorrente.R
import com.example.desafiocontacorrente.transacional.MainActivity

open class BaseFragment : Fragment() {
    private var actionBar: ActionBar? = null
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        actionBar = (activity as MainActivity).supportActionBar
        toggle = (activity as MainActivity).getToggle()
        toggle.isDrawerIndicatorEnabled = false
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun showBackButton(show: Boolean){
        if (show){
            toggle.setHomeAsUpIndicator(R.drawable.ic_arrow_back_24px)
        }else{
            toggle.setHomeAsUpIndicator(R.drawable.ic_dehaze_24px)
        }
    }

    fun setTitulo(titulo: String){
        if (activity is MainActivity) {
            (activity as MainActivity).setTitulo(titulo)
        }
    }

    fun setProgress(visible: Boolean){
        if (activity is MainActivity) {
            (activity as MainActivity).setProgress(visible)
        }
    }

    open fun changeFragment(fragment: BaseFragment){
        if (activity is MainActivity) {
            (activity as MainActivity).changeFragment(fragment)
        }
    }

}
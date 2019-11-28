package com.example.desafiocontacorrente.utils

import android.app.ProgressDialog
import androidx.fragment.app.Fragment
import com.example.desafiocontacorrente.R
import com.example.desafiocontacorrente.transacional.MainActivity

open class BaseFragment : Fragment() {
    private lateinit var progressDialog: ProgressDialog

    fun showProgress(show: Boolean) {
        if (show) {
            progressDialog = ProgressDialog.show(context, "", getString(R.string.progress_loading))
        }
        else {
            progressDialog.dismiss()
        }
    }

    fun setTitulo(titulo: String){
        if (activity is MainActivity) {
            (activity as MainActivity).setTitulo(titulo)
        }
    }

    fun changeFragment(fragment: BaseFragment){
        if (activity is MainActivity) {
            (activity as MainActivity).changeFragment(fragment)
        }
    }

}
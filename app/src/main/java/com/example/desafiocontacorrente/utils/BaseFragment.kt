package com.example.desafiocontacorrente.utils

import android.app.ProgressDialog
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.example.desafiocontacorrente.R
import com.example.desafiocontacorrente.transacional.MainLogadoActivity

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
        if (activity is MainLogadoActivity) {
            (activity as MainLogadoActivity).setTitulo(titulo)
        }
    }



    fun hideKeyboard(view : View){
        val imm  = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
package com.example.desafiocontacorrente.utils

import android.app.AlertDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.desafiocontacorrente.R

/**
 * General controls for activities
 */
open class BaseActivity : AppCompatActivity() {

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count != 0) {
            supportFragmentManager.popBackStack()
        }

    }

    open fun showExitDialog(context: Context?){

        val builder = AlertDialog.Builder(context)
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
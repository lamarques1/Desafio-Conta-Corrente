package com.example.desafiocontacorrente.utils

import androidx.appcompat.app.AppCompatActivity

/**
 * General controls for activities
 */
open class BaseActivity : AppCompatActivity() {
    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }

    }
}
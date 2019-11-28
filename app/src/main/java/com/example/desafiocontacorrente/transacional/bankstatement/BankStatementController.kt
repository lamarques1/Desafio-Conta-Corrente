package com.example.desafiocontacorrente.transacional.bankstatement

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import com.example.desafiocontacorrente.R
import com.example.desafiocontacorrente.utils.SharedPrefUtil
import java.text.SimpleDateFormat

open class BankStatementController {
    @SuppressLint("SimpleDateFormat")
    fun formatDate(strDate: String): String{
        val inputFormat = SimpleDateFormat("yyyy-MM-dd")
        val outputFormat = SimpleDateFormat("dd-MM-yy")

        val date = inputFormat.parse(strDate)!!

        return outputFormat.format(date).replace("-", "/")
    }

    fun getBankingType(context: Context, fromId: Int): String{
        val user = SharedPrefUtil().getUser(context)

        return if (user.id == fromId.toString()){
            "Transferência Enviada"
        } else{
            "Transferência Recebida"
        }
    }

    fun getValueIcon(context: Context, fromId: Int): Int{
        val user = SharedPrefUtil().getUser(context)

        return if (user.id == fromId.toString()){
            R.drawable.ic_minus_24px
        }else{
            R.drawable.ic_plus_24px
        }
    }
}
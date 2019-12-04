package com.example.desafiocontacorrente.transactional.bankstatement.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiocontacorrente.R
import com.example.desafiocontacorrente.model.Banking
import com.example.desafiocontacorrente.transactional.bankstatement.BankStatementController
import kotlinx.android.synthetic.main.item_extrato.view.*

class BankStatementAdapter(val context: Context, val bankStatement: List<Banking>):
    RecyclerView.Adapter<BankStatementAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_extrato, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bankStatement.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val banking = bankStatement[position]
        holder.bindView(banking)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindView(banking: Banking){
            itemView.visibility = View.GONE

            val date = itemView.txtDate
            val value = itemView.txtValue
            val type = itemView.txtType
            val valueOperator = itemView.ivValueOperator

            value.text = banking.value.toString()
            date.text = BankStatementController().formatDate(banking.data)
            type.text = BankStatementController().getBankingType(context, banking.id_from)
            valueOperator.setBackgroundResource(BankStatementController().getValueIcon(context, banking.id_from))
            //
            itemView.visibility = View.VISIBLE
        }
    }
}
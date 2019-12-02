package com.example.desafiocontacorrente.transacional.transfer


import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.example.desafiocontacorrente.R
import com.example.desafiocontacorrente.transacional.home.HomeFragment
import com.example.desafiocontacorrente.utils.BaseFragment

/**
 * A simple [Fragment] subclass.
 */
class TransferFragment : BaseFragment(), TransferContract.View {
    override fun onRefresh() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private lateinit var etEmail: EditText
    private lateinit var etValue: EditText
    private lateinit var btnTransfer: Button

    private lateinit var presenter: TransferContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_transfer, container, false)

        initViews(view)
        setPresenter()
        setTitulo(getString(R.string.title_transfer))

        initListeners()

        return view
    }

    override fun setPresenter() {
        presenter = TransferPresenter(this)
    }

    override fun getContext(): Context {
        return activity?.applicationContext!!
    }

    override fun initViews(view: View) {
        etEmail = view.findViewById(R.id.etEmailTransfer)
        etValue = view.findViewById(R.id.etValue)
        btnTransfer = view.findViewById(R.id.btnTransfer)
    }

    override fun initListeners() {
        btnTransfer.setOnClickListener {
            presenter.confirmData(etEmail.text.toString(), etValue.text.toString())
        }
    }

    override fun displayDialog(nameFrom: String, nameTo: String, value: String) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(activity!!.getString(R.string.dialog_transfer_title))
        builder.setMessage("Valor enviado: R$$value,00 \nDe: $nameFrom\nPara: $nameTo")
        builder.setNeutralButton("VOLTAR"){_, _ ->
            run {
                changeFragment(HomeFragment())
            }
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun displayErrorMessage(errorId: Int) {
        Toast.makeText(context, errorId, Toast.LENGTH_SHORT).show()
    }
}
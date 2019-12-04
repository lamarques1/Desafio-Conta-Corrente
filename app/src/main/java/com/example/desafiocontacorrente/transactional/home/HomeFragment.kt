package com.example.desafiocontacorrente.transactional.home


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.desafiocontacorrente.R
import com.example.desafiocontacorrente.model.User
import com.example.desafiocontacorrente.transactional.MainActivity
import com.example.desafiocontacorrente.transactional.bankstatement.BankStatementFragment
import com.example.desafiocontacorrente.transactional.transfer.TransferFragment
import com.example.desafiocontacorrente.utils.BaseActivity
import com.example.desafiocontacorrente.utils.BaseFragment

class HomeFragment : BaseFragment(), HomeContract.View {

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var container: LinearLayout
    private lateinit var txtName: TextView
    private lateinit var txtBalance: TextView
    private lateinit var btnBankStatement: Button
    private lateinit var btnTransfer: Button
    private lateinit var btnExit: Button
    private lateinit var btnRefresh: ImageButton

    private lateinit var presenter: HomeContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        setProgress(true)

        initViews(view)
        setTitulo(getString(R.string.title_home))
        initListeners()

        setPresenter()
        presenter.getUserInfo()

        (activity as MainActivity).lockDrawerLayout(false)
        return view
    }

    override fun onResume() {
        super.onResume()
        setTitulo(getString(R.string.title_home))
        showBackButton(false)
    }

    override fun setPresenter() {
        presenter = HomePresenter(this)
    }

    override fun getContext(): Context {
        return activity?.applicationContext!!
    }

    override fun initViews(view: View) {
        container = view.findViewById(R.id.homeContainer)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        txtName = view.findViewById(R.id.txtName)
        txtBalance = view.findViewById(R.id.txtSaldo)
        btnBankStatement = view.findViewById(R.id.btnExtrato)
        btnTransfer = view.findViewById(R.id.btnTransferir)
        btnExit = view.findViewById(R.id.btnSair)
        btnRefresh = view.findViewById(R.id.btnRefresh)
    }

    override fun initListeners() {
        swipeRefreshLayout.setOnRefreshListener {
            presenter.getUserInfo()
            swipeRefreshLayout.isRefreshing = false
        }

        btnExit.setOnClickListener {
            (activity as BaseActivity).showExitDialog(requireActivity())
        }

        btnBankStatement.setOnClickListener {
            changeFragment(BankStatementFragment())
        }

        btnTransfer.setOnClickListener {
            changeFragment(TransferFragment())
        }

        btnRefresh.setOnClickListener {

            presenter.getUserInfo()
        }
    }

    override fun bindUserInfo(user: User) {
        container.visibility = View.GONE

        txtName.text = user.name
        txtBalance.text = user.balance

        container.visibility = View.VISIBLE
        setProgress(false)
    }

    override fun displayErrorMessage(errorId: Int) {
        Toast.makeText(context, errorId, Toast.LENGTH_SHORT).show()
    }


}

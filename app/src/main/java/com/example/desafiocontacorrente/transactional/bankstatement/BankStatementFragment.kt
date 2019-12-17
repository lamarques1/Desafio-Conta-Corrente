package com.example.desafiocontacorrente.transactional.bankstatement


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.desafiocontacorrente.R
import com.example.desafiocontacorrente.model.Banking
import com.example.desafiocontacorrente.transactional.MainActivity
import com.example.desafiocontacorrente.transactional.bankstatement.adapter.BankStatementAdapter
import com.example.desafiocontacorrente.utils.BaseFragment

/**
 * Load user's bankstatement
 * Create a list containing transfer type, date and the value for each item.
 */
class BankStatementFragment : BaseFragment(), BankStatementContract.View {

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var emptyState: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BankStatementAdapter
    private lateinit var presenter: BankStatementContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bank_statement, container, false)
        setProgress(true)

        initViews(view)
        setPresenter()
        presenter.getBankStatement()

        initListeners()

        (activity as MainActivity).lockDrawerLayout(true)

        return view
    }

    override fun onResume() {
        super.onResume()
        setTitulo(getString(R.string.title_bank_statement))
        showBackButton(true)
    }

    override fun setPresenter() {
        presenter = BankStatementPresenter(this)
    }

    override fun getContext(): Context? {
        return super.getContext()
    }

    override fun initViews(view: View) {
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        emptyState = view.findViewById(R.id.BankingEmptyState)
        recyclerView = view.findViewById(R.id.recyclerView)
    }

    override fun initListeners() {
        swipeRefreshLayout.setOnRefreshListener {
            presenter.getBankStatement()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun bindList(bankStatement: List<Banking>) {
        emptyState.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE

        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = BankStatementAdapter(context, bankStatement)
        recyclerView.adapter = adapter
        setProgress(false)
    }

    override fun bindEmptyState() {
        recyclerView.visibility = View.GONE
        emptyState.visibility = View.VISIBLE
        setProgress(false)
    }

    override fun displayErrorMessage(errorId: Int) {
        Toast.makeText(context, errorId, Toast.LENGTH_SHORT).show()
    }
}

package com.laptrinhdidong.nhom3.quanlichitieu.MainApp.TichLuy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.laptrinhdidong.nhom3.quanlichitieu.BarChart.Nhom3QuocBarChart
import com.laptrinhdidong.nhom3.quanlichitieu.Model.Accumulate
import com.laptrinhdidong.nhom3.quanlichitieu.Model.Database
import com.laptrinhdidong.nhom3.quanlichitieu.OnItemClickListener
import com.laptrinhdidong.nhom3.quanlichitieu.PieChart.Nhom3QuocPieChart
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.Spending.Nhom3BinhSpendingFragment
import com.laptrinhdidong.nhom3.quanlichitieu.Statistical.ReportExpense.Nhom3AnReportExpenseFragment
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3AnTichluyFragmentBinding

class Nhom3AnTichLuyFragment : Fragment(), OnItemClickListener {
    private lateinit var binding: Nhom3AnTichluyFragmentBinding
    private lateinit var viewModel: Nhom3AnTichLuyViewModel
    private lateinit var adapter: Nhom3AnTichLuyAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(Nhom3AnTichLuyViewModel::class.java)
        binding = DataBindingUtil.inflate<Nhom3AnTichluyFragmentBinding>(
            inflater,
            R.layout.nhom3_an_tichluy_fragment,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = Nhom3AnTichLuyAdapter(this)
        binding.recycleviewTichluy.layoutManager = LinearLayoutManager(context)
        if(!viewModel.firstAccess)
        {
            viewModel.getDataInit()
            viewModel.firstAccess=true
        }
        adapter.data = viewModel.getData()
        binding.recycleviewTichluy.adapter = adapter
        binding.btnTrantoadd.setOnClickListener {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<Nhom3AnAddSavingFragment>(R.id.fragment_mainapp)
                addToBackStack(null)
            }
        }
    }

    override fun onItemClick(index: Int) {
    }

    override fun onItemClick(accumulate: Accumulate) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            Database.instance.idAccumulate=accumulate.id
            replace<Nhom3AnDetaiSavingFragment>(R.id.fragment_mainapp)
            addToBackStack(null)
        }
    }
    override fun onClickDelete(index: Int) {
        viewModel.deleteAccumulate(index)
        viewModel.getDataInit()
        adapter.data = viewModel.getData()
        binding.recycleviewTichluy.adapter = adapter
    }

    override fun onClickAddMoney() {

    }
}
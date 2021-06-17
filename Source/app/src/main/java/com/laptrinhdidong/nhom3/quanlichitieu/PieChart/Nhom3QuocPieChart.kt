package com.laptrinhdidong.nhom3.quanlichitieu.PieChart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.laptrinhdidong.nhom3.quanlichitieu.R
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending.Nhom3QuocPieChartViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3QuocActivityTablayoutBinding

class Nhom3QuocPieChart  :Fragment() {

    private lateinit var binding : Nhom3QuocActivityTablayoutBinding
    private lateinit var viewModel: Nhom3AnOuterPieChartViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(Nhom3AnOuterPieChartViewModel::class.java)
        binding = DataBindingUtil.inflate<Nhom3QuocActivityTablayoutBinding>(
            inflater,
            R.layout.nhom3_quoc_activity_tablayout,
            container,
            false
        )
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(!viewModel.firstAccess) {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                add<Nhom3QuocFrgamentDay>(R.id.fragment_piechart)
                addToBackStack(null)
            }
            viewModel.firstAccess=true
        }
        binding.radioDay.setOnClickListener{
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<Nhom3QuocFrgamentDay>(R.id.fragment_piechart)
                addToBackStack(null)
            }
        }

        binding.radioMonth.setOnClickListener{
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<Nhom3QuocFragmentMonth>(R.id.fragment_piechart)
                addToBackStack(null)
            }
        }

        binding.radioYear.setOnClickListener{
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<Nhom3QuocFragmentYear>(R.id.fragment_piechart)
                addToBackStack(null)
            }
        }
    }
}
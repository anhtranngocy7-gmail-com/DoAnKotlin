package com.laptrinhdidong.nhom3.quanlichitieu.BarChart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.*
import androidx.lifecycle.ViewModelProvider
import com.laptrinhdidong.nhom3.quanlichitieu.PieChart.*
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3QuocActivityTablayoutLinechartBinding


class Nhom3QuocBarChart : Fragment() {
    private lateinit var binding : Nhom3QuocActivityTablayoutLinechartBinding
    private lateinit var viewModel: Nhom3AnOuterPieChartViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(Nhom3AnOuterPieChartViewModel::class.java)
        binding = DataBindingUtil.inflate<Nhom3QuocActivityTablayoutLinechartBinding>(
            inflater,
            R.layout.nhom3_quoc_activity_tablayout_linechart,
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
                add<Nhom3QuocFragmentDay_BC>(R.id.fragment_linechart)
                addToBackStack(null)
            }
            viewModel.firstAccess=true
        }
        binding.radioDay.setOnClickListener{
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<Nhom3QuocFragmentDay_BC>(R.id.fragment_linechart)
                addToBackStack(null)
            }
        }

        binding.radioMonth.setOnClickListener{
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<Nhom3QuocFragmentMonth_BC>(R.id.fragment_linechart)
                addToBackStack(null)
            }
        }

        binding.radioYear.setOnClickListener{
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<Nhom3QuocFragmentYear_BC>(R.id.fragment_linechart)
                addToBackStack(null)
            }
        }

    }

}


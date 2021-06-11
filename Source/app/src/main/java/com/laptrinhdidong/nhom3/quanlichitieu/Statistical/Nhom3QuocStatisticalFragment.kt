package com.laptrinhdidong.nhom3.quanlichitieu.Statistical

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.laptrinhdidong.nhom3.quanlichitieu.BarChart.Nhom3QuocBarChart
import com.laptrinhdidong.nhom3.quanlichitieu.PieChart.Nhom3QuocPieChart
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.Statistical.ReportExpense.Nhom3AnReportExpenseFragment
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3AnTichluyFragmentBinding
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3QuocAcitvityStatisticalFragmentBinding

class Nhom3QuocStatisticalFragment : Fragment() {
    private lateinit var binding: Nhom3QuocAcitvityStatisticalFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<Nhom3QuocAcitvityStatisticalFragmentBinding>(
            inflater,
            R.layout.nhom3_quoc_acitvity_statistical_fragment,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnPhantichchitieu.setOnClickListener{
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<Nhom3QuocPieChart>(R.id.frag_container_view)
                addToBackStack(null)
            }
        }
//        binding.btnTinhhinhthuchi.setOnClickListener{
//            parentFragmentManager.commit {
//                setReorderingAllowed(true)
//                replace<Nhom3QuocBarChart>(R.id.frag_container_view)
//                addToBackStack(null)
//            }
//        }
        binding.btnThongkechitieu.setOnClickListener{
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<Nhom3AnReportExpenseFragment>(R.id.frag_container_view)
                addToBackStack(null)
            }
        }
    }
}
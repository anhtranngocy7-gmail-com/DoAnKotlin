package com.laptrinhdidong.nhom3.quanlichitieu.BarChart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending.Nhom3QuocBieudocotAdapter
import com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending.Nhom3QuocBieudocotViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3QuocFragmentMonthBcBinding
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3QuocFragmentYearBcBinding
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3QuocFragmentYearBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Nhom3QuocFragmentYear_BC.newInstance] factory method to
 * create an instance of this fragment.
 */
class Nhom3QuocFragmentYear_BC : Fragment() {


    private lateinit var binding : Nhom3QuocFragmentYearBcBinding
    private lateinit var viewModel: Nhom3QuocBieudocotViewModel
    private lateinit var adapter : Nhom3QuocBieudocotAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(Nhom3QuocBieudocotViewModel::class.java)
        binding = DataBindingUtil.inflate<Nhom3QuocFragmentYearBcBinding>(
            inflater,
            R.layout.nhom3_quoc_fragment_year_bc,
            container,
            false
        )
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = Nhom3QuocBieudocotAdapter()
        binding.recycleviewYearBC.layoutManager = LinearLayoutManager(context)
        adapter.data = viewModel.getData()
        binding.recycleviewYearBC.adapter = adapter
    }

}
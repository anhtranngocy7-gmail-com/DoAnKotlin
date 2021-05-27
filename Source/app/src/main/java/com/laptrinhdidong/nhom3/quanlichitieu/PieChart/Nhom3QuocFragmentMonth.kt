package com.laptrinhdidong.nhom3.quanlichitieu.PieChart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending.Nhom3QuocPieChartAdapter
import com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending.Nhom3QuocPieChartViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3QuocFragmentMonthBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Nhom3QuocFragmentMonth.newInstance] factory method to
 * create an instance of this fragment.
 */
class Nhom3QuocFragmentMonth : Fragment() {
    private lateinit var binding: Nhom3QuocFragmentMonthBinding
    private lateinit var viewModel: Nhom3QuocPieChartViewModel
    private lateinit var adapter : Nhom3QuocPieChartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(Nhom3QuocPieChartViewModel::class.java)
        binding = DataBindingUtil.inflate<Nhom3QuocFragmentMonthBinding>(
            inflater,
            R.layout.nhom3_quoc_fragment_month,
            container,
            false
        )
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = Nhom3QuocPieChartAdapter()
        binding.recycleViewMonth.layoutManager = LinearLayoutManager(context)
        adapter.data = viewModel.getData()
        binding.recycleViewMonth.adapter = adapter

        //Setup PieChart
        val pieEntries = arrayListOf<PieEntry>()
        pieEntries.add(PieEntry(30.0f,"Ăn uống"))
        pieEntries.add(PieEntry(40.0f,"Đi lại"))
        pieEntries.add(PieEntry(35.0f,"Sinh hoạt"))

        //Setup PieChart Animation
        binding.pieChartMonth.animateXY(1000,1000)

        //Setup PieChart Entries Color
        val pieDataSet = PieDataSet(pieEntries,"Biểu đồ chi tiêu")
        pieDataSet.setColors(
            resources.getColor(R.color.stroke_checked),
            resources.getColor(R.color.red),
            resources.getColor(R.color.teal_200)
        )

        //Setup Pie Data Set in PieData
        val pieData = PieData(pieDataSet)

        //Setup Text in PieChart Center
        binding.pieChartMonth.centerText= "Biểu đồ chi tiêu"
        binding.pieChartMonth.setCenterTextColor(resources.getColor(R.color.black))
        binding.pieChartMonth.setCenterTextSize(15f)
        binding.pieChartMonth.setEntryLabelTextSize(8f)

        binding.pieChartMonth.legend.textColor = resources.getColor(R.color.white)

        //Hide Description
        binding.pieChartMonth.description.isEnabled = false


        //this enable the value on each pieEntry
        pieData.setDrawValues(true)

        binding.pieChartMonth.data = pieData
    }





}
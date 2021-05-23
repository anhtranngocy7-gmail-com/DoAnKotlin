package com.laptrinhdidong.nhom3.quanlichitieu

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending.Nhom3QuocBieudoAdapter
import com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending.Nhom3QuocBieudoViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3QuocFrgamentDayBinding
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.laptrinhdidong.nhom3.quanlichitieu.MainApp.TichLuy.Nhom3AnTichLuyAdapter
import com.laptrinhdidong.nhom3.quanlichitieu.MainApp.TichLuy.Nhom3AnTichLuyViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Nhom3QuocFrgamentDay.newInstance] factory method to
 * create an instance of this fragment.
 */
class Nhom3QuocFrgamentDay : Fragment() {
    private lateinit var binding : Nhom3QuocFrgamentDayBinding
    private lateinit var viewModel: Nhom3QuocBieudoViewModel
    private lateinit var adapter : Nhom3QuocBieudoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(Nhom3QuocBieudoViewModel::class.java)
        binding = DataBindingUtil.inflate<Nhom3QuocFrgamentDayBinding>(
           inflater,
           R.layout.nhom3_quoc_frgament_day,
           container,
           false
       )
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = Nhom3QuocBieudoAdapter()
        binding.recycleViewDay.layoutManager = LinearLayoutManager(context)
        adapter.data = viewModel.getData()
        binding.recycleViewDay.adapter = adapter

//        val spendingList: MutableList<PieEntry> = mutableListOf(
//            PieEntry(57F,"Ăn uống"),
//            PieEntry(23F,"Đi lại"),
//            PieEntry(20F,"Sinh hoạt")
//        )
//
//        val pieDataSet = PieDataSet(spendingList,"Biểu đồ chi tiêu")
//        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS)
//        pieDataSet.valueTextColor = Color.WHITE
//        pieDataSet.valueTextSize = 24f
//
//        val pieData  = PieData(pieDataSet)
//        binding.piechart.data = pieData
//        binding.piechart.description.isEnabled=false
//        binding.piechart.centerText = "Biểu đồ chi tiêu"
//        binding.piechart.animate()

        //Setup PieChart
        val pieEntries = arrayListOf<PieEntry>()
        pieEntries.add(PieEntry(30.0f,"Ăn uống"))
        pieEntries.add(PieEntry(40.0f,"Đi lại"))
        pieEntries.add(PieEntry(35.0f,"Sinh hoạt"))

        //Setup PieChart Animation
        binding.pieChart.animateXY(1000,1000)

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
        binding.pieChart.centerText= "Biểu đồ chi tiêu"
        binding.pieChart.setCenterTextColor(resources.getColor(R.color.black))
        binding.pieChart.setCenterTextSize(15f)

        //Hide Description
        binding.pieChart.description.isEnabled = false


        //this enable the value on each pieEntry
        pieData.setDrawValues(true)

        binding.pieChart.data = pieData

    }



}








package com.laptrinhdidong.nhom3.quanlichitieu.BarChart

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending.Nhom3QuocBieudocotAdapter
import com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending.Nhom3QuocBieudocotViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending.Nhom3QuocBieudotronAdapter
import com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending.Nhom3QuocBieudotronViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3QuocFragmentDayBcBinding
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3QuocFrgamentDayBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Nhom3QuocFragmentDay_BC.newInstance] factory method to
 * create an instance of this fragment.
 */
class Nhom3QuocFragmentDay_BC : Fragment() {

    private lateinit var binding : Nhom3QuocFragmentDayBcBinding
    private lateinit var viewModel: Nhom3QuocBieudocotViewModel
    private lateinit var adapter : Nhom3QuocBieudocotAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(Nhom3QuocBieudocotViewModel::class.java)
        binding = DataBindingUtil.inflate<Nhom3QuocFragmentDayBcBinding>(
            inflater,
            R.layout.nhom3_quoc_fragment_day_bc,
            container,
            false
        )
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = Nhom3QuocBieudocotAdapter()
        binding.recycleviewDayBC.layoutManager = LinearLayoutManager(context)
        adapter.data = viewModel.getData()
        binding.recycleviewDayBC.adapter = adapter



        binding.barChartDay.setDrawBarShadow(false)
        binding.barChartDay.description.isEnabled = false
        binding.barChartDay.setDrawGridBackground(true)
        binding.barChartDay.description.isEnabled = false




        val barOne = arrayListOf<BarEntry>()
        barOne.add(BarEntry(1f,20f))
        barOne.add(BarEntry(2f,40f))
        barOne.add(BarEntry(3f,50f))

        val barTwo = arrayListOf<BarEntry>()
        barTwo.add(BarEntry(1f,33f))
        barTwo.add(BarEntry(2f,14f))
        barTwo.add(BarEntry(3f,66f))



        val set1 = BarDataSet(barOne, "barOne")
        set1.setColors(resources.getColor(R.color.stroke_checked))
        val set2 = BarDataSet(barTwo,"barTwo")
        set2.setColors(resources.getColor(R.color.red))

        val data = BarData(set1,set2)
        binding.barChartDay.data = data

        val labels = arrayOf<String>("", "Tháng 4", "Tháng 5", "Tháng 6","")

        val xAxis: XAxis = binding.barChartDay.xAxis
        xAxis.setCenterAxisLabels(true)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(true)
        xAxis.granularity = 1f
        xAxis.textColor = Color.WHITE
        xAxis.textSize = 12f
        xAxis.axisLineColor = Color.WHITE
        xAxis.axisMinimum = 1f
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)

        val leftAxis = binding.barChartDay.axisLeft
        leftAxis.textColor = Color.WHITE
        leftAxis.textSize = 12f
        leftAxis.axisLineColor = Color.WHITE
        leftAxis.setDrawGridLines(true)
        leftAxis.granularity = 2f
        leftAxis.setLabelCount(8,true)
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)

        binding.barChartDay.axisRight.isEnabled = false
        binding.barChartDay.legend.isEnabled = false

        val barSpace : Float = 0f
        val groupSpace: Float = 0.4f
        data.barWidth = 0.3f
        //(barspace + barWith) *2 + groupspace = 1

        xAxis.axisMaximum = labels.size-1.1f
        binding.barChartDay.data = data
        binding.barChartDay.setScaleEnabled(false)
        binding.barChartDay.setVisibleXRangeMaximum(6f)
        binding.barChartDay.groupBars(1f,groupSpace,barSpace)
        binding.barChartDay.invalidate()










    }
}



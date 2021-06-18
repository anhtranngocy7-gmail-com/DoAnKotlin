package com.laptrinhdidong.nhom3.quanlichitieu.BarChart

import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending.Nhom3QuocBarChartAdapter
import com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending.Nhom3QuocBarChartViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3QuocFragmentYearBcBinding
import com.whiteelephant.monthpicker.MonthPickerDialog
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

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


    private lateinit var binding: Nhom3QuocFragmentYearBcBinding
    private lateinit var viewModel: Nhom3QuocBarChartViewModel
    private lateinit var adapter: Nhom3QuocBarChartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(Nhom3QuocBarChartViewModel::class.java)
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
        adapter = Nhom3QuocBarChartAdapter()
        binding.recycleviewYearBC.adapter = adapter
        binding.recycleviewYearBC.layoutManager = LinearLayoutManager(context)
        if(!viewModel.firstAccess)
        {
            isCheckYear(viewModel.fromDate.substring(0,4),viewModel.toDate.substring(0,4))
            viewModel.firstAccess=true
        }else
        {
            BindingDataChart()
            BindingDataRecycleView()
        }

        val tv_from = binding.tvFrom
        val tv_to = binding.tvTo

        tv_from.text = viewModel.fromDate.substring(0,4)

        //Choosen Year from
        tv_from.setOnClickListener {
            val monthPickerDialog: MonthPickerDialog.Builder = MonthPickerDialog.Builder(
                activity!!,
                MonthPickerDialog.OnDateSetListener
                { selectedMonth, selectedYear ->
                    tv_from.text = "" + selectedYear + ""
                    isCheckYear(tv_from.text.toString(), tv_to.text.toString())
                },
                viewModel.year_now,
                viewModel.month_now
            )
            monthPickerDialog.setActivatedMonth(viewModel.month_now)
                .setMinYear(1990)
                .setActivatedYear(viewModel.year_now)
                .setMaxYear(2050)
                .showYearOnly()
                .setTitle("Select Year")
                .build().show()

        }

        //Set năm hiển thị = năm hiện tại
        tv_to.text = viewModel.toDate.substring(0,4)

        //Choosen Year to
        tv_to.setOnClickListener {
            val monthPickerDialog: MonthPickerDialog.Builder = MonthPickerDialog.Builder(
                activity!!,
                MonthPickerDialog.OnDateSetListener
                { selectedMonth, selectedYear ->
                    tv_to.text = "" + selectedYear + ""
                    isCheckYear(tv_from.text.toString(), tv_to.text.toString())
                },
                viewModel.year_now,
                viewModel.month_now
            )
            monthPickerDialog.setActivatedMonth(viewModel.month_now)
                .setMinYear(1990)
                .setActivatedYear(viewModel.year_now)
                .setMaxYear(2050)
                .showYearOnly()
                .setTitle("Select Year")
                .build().show()

        }
    }

    fun isCheckYear(from_day: String, end_day: String) {

        var sdf: SimpleDateFormat = SimpleDateFormat("yyyy")
        var dateStart: Date = sdf.parse(from_day)
        var dateEnd: Date = sdf.parse(end_day)

        try {
            sdf = SimpleDateFormat("yyyy")
            dateStart = sdf.parse(from_day)
            dateEnd = sdf.parse(end_day)
            if (dateStart.compareTo(dateEnd) > 0) {
                sdf = SimpleDateFormat("yyyy")
                dateStart = sdf.parse(end_day)
                dateEnd = sdf.parse(from_day)
            }

        } catch (ex: ParseException) {
            ex.printStackTrace()
        }
        val date_Start = SimpleDateFormat("yyyy").format(dateStart).toString()
        val date_End = SimpleDateFormat("yyyy").format(dateEnd).toString()
        viewModel.fromDate = "$date_Start-01-01"
        viewModel.toDate = "$date_End-12-15"
        viewModel.getData(3)
        BindingDataChart()
        BindingDataRecycleView()
        binding.tvFrom.text=viewModel.fromDate.substring(0,4)
        binding.tvTo.text=viewModel.toDate.substring(0,4)

    }
    fun BindingDataChart() {
        val lineOne = arrayListOf<Entry>()
        val lineTwo = arrayListOf<Entry>()
        var labels = arrayListOf("")
        var i = 1
        viewModel.lstEx.forEach {
            lineOne.add(Entry(i.toFloat(), it.money_collect.toFloat()))
            lineTwo.add(Entry(i.toFloat(), it.money_lost.toFloat()))
            labels.add(i.toString())
            i++
        }
        labels.add("")


        //Setup LineDataSet in LineData
        val set1 = LineDataSet(lineOne, "Thu")
        set1.setColors(resources.getColor(R.color.stroke_checked))
        val set2 = LineDataSet(lineTwo, "Chi")
        set2.setColors(resources.getColor(R.color.red))

        val ilineDataSet = arrayListOf<ILineDataSet>()
        ilineDataSet.add(set1)
        ilineDataSet.add(set2)
        val data = LineData(ilineDataSet)

        //Configure value text size
        data.setValueTextColor(Color.WHITE)
        data.setValueTextSize(12f)


        binding.lineChartYear.data = data
        binding.lineChartYear.invalidate()

        //Array Title xAxis

        //Configuration XAxis
        val xAxis: XAxis = binding.lineChartYear.xAxis
        xAxis.setCenterAxisLabels(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(true)
        xAxis.granularity = 1f
        xAxis.textColor = Color.WHITE
        xAxis.textSize = 12f
        xAxis.axisLineColor = Color.WHITE
        xAxis.axisMinimum = 1f
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)

        //Configuration YAxis
        val leftAxis = binding.lineChartYear.axisLeft
        leftAxis.textColor = Color.WHITE
        leftAxis.textSize = 12f
        leftAxis.axisLineColor = Color.WHITE
        leftAxis.setDrawGridLines(true)
        leftAxis.granularity = 2f
        leftAxis.setLabelCount(8, true)
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)

        //Enabale Legend, description ,backround, Rightasix
        binding.lineChartYear.description.isEnabled = false
        binding.lineChartYear.setDrawGridBackground(false)
        binding.lineChartYear.axisRight.isEnabled = false
        binding.lineChartYear.legend.isEnabled = false

        xAxis.axisMaximum = labels.size - 0f

        binding.lineChartYear.setScaleEnabled(false)
    }

    fun BindingDataRecycleView() {
        binding.recycleviewYearBC.adapter = adapter
        adapter.data = viewModel.lstEx
    }

}

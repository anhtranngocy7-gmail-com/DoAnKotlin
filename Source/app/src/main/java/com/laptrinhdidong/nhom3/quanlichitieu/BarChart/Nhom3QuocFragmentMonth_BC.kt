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
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3QuocFragmentMonthBcBinding
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
 * Use the [Nhom3QuocFragmentMonth_BC.newInstance] factory method to
 * create an instance of this fragment.
 */
class Nhom3QuocFragmentMonth_BC : Fragment() {


    private lateinit var binding: Nhom3QuocFragmentMonthBcBinding
    private lateinit var viewModel: Nhom3QuocBarChartViewModel
    private lateinit var adapter: Nhom3QuocBarChartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(Nhom3QuocBarChartViewModel::class.java)
        binding = DataBindingUtil.inflate<Nhom3QuocFragmentMonthBcBinding>(
            inflater,
            R.layout.nhom3_quoc_fragment_month_bc,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = Nhom3QuocBarChartAdapter()
        binding.recycleviewMonthBC.layoutManager = LinearLayoutManager(context)
        if(!viewModel.firstAccess)
        {
            isCheckMonth(viewModel.fromDate.substring(0,7),viewModel.toDate.substring(0,7))
            viewModel.firstAccess=true
        }else
        {
            BindingDataChart()
            BindingDataRecycleView()
        }
        /*============= Show Month Dialog ==================*/
        //Setup Month Dialog
        val tv_from = binding.tvFrom
        val tv_to = binding.tvTo

        //Set tháng hiển thị = tháng hiện tại
        tv_from.text = viewModel.fromDate.substring(0,7)
        //Choosen Month/Year from
        tv_from.setOnClickListener {
            val monthPickerDialog: MonthPickerDialog.Builder = MonthPickerDialog.Builder(
                activity!!,
                MonthPickerDialog.OnDateSetListener
                { selectedMonth, selectedYear ->
                    tv_from.text = ""+ selectedYear + "-"+ (selectedMonth + 1)
                    isCheckMonth(tv_from.text.toString(), tv_to.text.toString())
                }, viewModel.year_now, viewModel.month_now
            )
            monthPickerDialog.setActivatedMonth(viewModel.month_now)
                .setMinYear(1990)
                .setActivatedYear(viewModel.year_now)
                .setMaxYear(2050)
                .setTitle("Select Month Year")
                .build().show()

        }

        //Set tháng hiển thị = tháng hiện tại
        tv_to.text = viewModel.toDate.substring(0,7)
        //Choosen Month/Year to
        tv_to.setOnClickListener {
            val monthPickerDialog: MonthPickerDialog.Builder = MonthPickerDialog.Builder(
                activity!!,
                MonthPickerDialog.OnDateSetListener
                { selectedMonth, selectedYear ->
                    tv_to.text = ""+ selectedYear + "-"+ (selectedMonth + 1)
                    isCheckMonth(tv_from.text.toString(), tv_to.text.toString())
                }, viewModel.year_now, viewModel.month_now
            )
            monthPickerDialog.setActivatedMonth(viewModel.month_now)
                .setMinYear(1990)
                .setActivatedYear(viewModel.year_now)
                .setMaxYear(2050)
                .setTitle("Select Month Year")
                .build().show()

        }

    }

    fun isCheckMonth(from_day: String, end_day: String) {

        var sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM")
        var dateStart: Date = sdf.parse(from_day)
        var dateEnd: Date = sdf.parse(end_day)

        try {
            sdf = SimpleDateFormat("yyyy-MM")
            dateStart = sdf.parse(from_day)
            dateEnd = sdf.parse(end_day)
            if (dateStart.compareTo(dateEnd) > 0) {
                sdf = SimpleDateFormat("yyyy-MM")
                dateStart = sdf.parse(end_day)
                dateEnd = sdf.parse(from_day)
            }

        } catch (ex: ParseException) {
            ex.printStackTrace()
        }
        val date_Start = SimpleDateFormat("yyyy-MM").format(dateStart).toString()
        val date_End = SimpleDateFormat("yyyy-MM").format(dateEnd).toString()
        viewModel.fromDate = date_Start+"-01"
        viewModel.toDate = date_End+"-15"
        viewModel.getData(2)
        binding.tvFrom.text=viewModel.fromDate.substring(0,7)
        binding.tvTo.text=viewModel.toDate.substring(0,7)
        BindingDataChart()
        BindingDataRecycleView()
        Log.e("START DAY", date_Start)
        Log.e("END DAY", date_End)

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


        binding.lineChartMonth.data = data
        binding.lineChartMonth.invalidate()

        //Array Title xAxis

        //Configuration XAxis
        val xAxis: XAxis = binding.lineChartMonth.xAxis
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
        val leftAxis = binding.lineChartMonth.axisLeft
        leftAxis.textColor = Color.WHITE
        leftAxis.textSize = 12f
        leftAxis.axisLineColor = Color.WHITE
        leftAxis.setDrawGridLines(true)
        leftAxis.granularity = 2f
        leftAxis.setLabelCount(8, true)
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)

        //Enabale Legend, description ,backround, Rightasix
        binding.lineChartMonth.description.isEnabled = false
        binding.lineChartMonth.setDrawGridBackground(false)
        binding.lineChartMonth.axisRight.isEnabled = false
        binding.lineChartMonth.legend.isEnabled = false

        xAxis.axisMaximum = labels.size - 0f

        binding.lineChartMonth.setScaleEnabled(false)
    }
    fun BindingDataRecycleView() {
        binding.recycleviewMonthBC.adapter = adapter
        adapter.data = viewModel.lstEx
    }
}
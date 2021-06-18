package com.laptrinhdidong.nhom3.quanlichitieu.BarChart

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3QuocFragmentDayBcBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


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

    private lateinit var binding: Nhom3QuocFragmentDayBcBinding
    private lateinit var viewModel: Nhom3QuocBarChartViewModel
    private lateinit var adapter: Nhom3QuocBarChartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(Nhom3QuocBarChartViewModel::class.java)
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
        val tv_from = binding.tvFrom
        val tv_to = binding.tvTo
        adapter = Nhom3QuocBarChartAdapter()
        binding.recycleviewDayBC.layoutManager = LinearLayoutManager(context)
        if (!viewModel.firstAccess) {
            isCheckDate(viewModel.fromDate, viewModel.toDate)
            viewModel.firstAccess = true
        } else {
            BindingDataChart()
            BindingDataRecycleView()
        }
        //Date Calendar

        //Set day hiển thị = day hiện tại
        tv_from.text = viewModel.fromDate
        //Choosen Date from
        tv_from.setOnClickListener {
            val datePickerDialog =
                DatePickerDialog(
                    activity!!, R.style.Theme_AppCompat_Light_Dialog,
                    DatePickerDialog.OnDateSetListener
                    { view, year, month, dayOfMonth ->
                        tv_from.text = "" + year + "-" + (month + 1) + "-" + dayOfMonth
                        isCheckDate(tv_from.text.toString(), tv_to.text.toString())
                    }, viewModel.year_now, viewModel.month_now, viewModel.day_now
                )
            datePickerDialog.setTitle("Select Date")
            datePickerDialog.show()
        }

        //Set day hiển thị = day hiện tại
        tv_to.text = viewModel.toDate
        //Choosen Date to
        tv_to.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                activity!!, R.style.Theme_AppCompat_Light_Dialog,
                DatePickerDialog.OnDateSetListener
                { view, year, month, dayOfMonth ->
                    tv_to.text = "" + year + "-" + (month + 1) + "-" + dayOfMonth
                    isCheckDate(tv_from.text.toString(), tv_to.text.toString())
                }, viewModel.year_now, viewModel.month_now, viewModel.day_now
            )
            datePickerDialog.setTitle("Select Date")
            datePickerDialog.show()
        }

        //Setup Line Chart


    }

    fun isCheckDate(from_day: String, end_day: String) {

        var sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        var dateStart: Date = sdf.parse(from_day)
        var dateEnd: Date = sdf.parse(end_day)

        try {
            sdf = SimpleDateFormat("yyyy-MM-dd")
            dateStart = sdf.parse(from_day)
            dateEnd = sdf.parse(end_day)
            if (dateStart.compareTo(dateEnd) > 0) {
                sdf = SimpleDateFormat("yyyy-MM-dd")
                dateStart = sdf.parse(end_day)
                dateEnd = sdf.parse(from_day)
            }

        } catch (ex: ParseException) {
            ex.printStackTrace()
        }
        val date_Start = SimpleDateFormat("yyyy-MM-dd").format(dateStart).toString()
        val date_End = SimpleDateFormat("yyyy-MM-dd").format(dateEnd).toString()
        viewModel.fromDate = date_Start
        viewModel.toDate = date_End
        viewModel.getData(1)
        binding.tvFrom.text = viewModel.fromDate
        binding.tvTo.text = viewModel.toDate
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


        binding.lineChartDay.data = data
        binding.lineChartDay.invalidate()

        //Array Title xAxis

        //Configuration XAxis
        val xAxis: XAxis = binding.lineChartDay.xAxis
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
        val leftAxis = binding.lineChartDay.axisLeft
        leftAxis.textColor = Color.WHITE
        leftAxis.textSize = 12f
        leftAxis.axisLineColor = Color.WHITE
        leftAxis.setDrawGridLines(true)
        leftAxis.granularity = 2f
        leftAxis.setLabelCount(8, true)
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)

        //Enabale Legend, description ,backround, Rightasix
        binding.lineChartDay.description.isEnabled = false
        binding.lineChartDay.setDrawGridBackground(false)
        binding.lineChartDay.axisRight.isEnabled = false
        binding.lineChartDay.legend.isEnabled = false

        xAxis.axisMaximum = labels.size - 0f

        binding.lineChartDay.setScaleEnabled(false)
    }

    fun BindingDataRecycleView() {
        binding.recycleviewDayBC.adapter = adapter
        adapter.data = viewModel.lstEx
    }
}



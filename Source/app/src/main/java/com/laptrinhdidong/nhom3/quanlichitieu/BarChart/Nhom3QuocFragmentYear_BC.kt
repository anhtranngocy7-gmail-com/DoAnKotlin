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
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending.Nhom3QuocBarChartAdapter
import com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending.Nhom3QuocBarChartViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3QuocFragmentYearBcBinding
import com.whiteelephant.monthpicker.MonthPickerDialog
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
        binding.recycleviewYearBC.layoutManager = LinearLayoutManager(context)
        adapter.data = viewModel.getData()
        binding.recycleviewYearBC.adapter = adapter

        /*============= Show Year Dialog ==================*/
        //Setup Month Dialog
        val tv_from = binding.tvFrom
        val tv_to = binding.tvTo
        val today = Calendar.getInstance()
        val year_now = today.get(Calendar.YEAR)
        val month_now = today.get(Calendar.MONTH)
        val day_now = today.get(Calendar.DAY_OF_MONTH)

        //Set năm hiển thị = năm hiện tại
        tv_from.text = ""+ year_now + ""

        //Choosen Year from
        tv_from.setOnClickListener {
            val monthPickerDialog: MonthPickerDialog.Builder = MonthPickerDialog.Builder(
                activity!!,
                MonthPickerDialog.OnDateSetListener
                { selectedMonth, selectedYear -> tv_from.text = "" + selectedYear },
                year_now,
                month_now
            )
            monthPickerDialog.setActivatedMonth(month_now)
                .setMinYear(1990)
                .setActivatedYear(year_now)
                .setMaxYear(2050)
                .showYearOnly()
                .setTitle("Select Year")
                .build().show()

        }

        //Set năm hiển thị = năm hiện tại
        tv_to.text = ""+ year_now + ""

        //Choosen Year to
        tv_to.setOnClickListener {
            val monthPickerDialog: MonthPickerDialog.Builder = MonthPickerDialog.Builder(
                activity!!,
                MonthPickerDialog.OnDateSetListener
                { selectedMonth, selectedYear -> tv_to.text = "" + selectedYear },
                year_now,
                month_now
            )
            monthPickerDialog.setActivatedMonth(month_now)
                .setMinYear(1990)
                .setActivatedYear(year_now)
                .setMaxYear(2050)
                .showYearOnly()
                .setTitle("Select Year")
                .build().show()

        }


        //Setup Line Chart

        val lineOne = arrayListOf<Entry>()
        lineOne.add(Entry(1f, 5f))
        lineOne.add(Entry(2f, 9f))
        lineOne.add(Entry(3f, 4f))
        lineOne.add(Entry(4f, 8f))
        lineOne.add(Entry(5f, 12f))
        lineOne.add(Entry(6f, 2f))

        val lineTwo = arrayListOf<Entry>()
        lineTwo.add(Entry(1f, 6f))
        lineTwo.add(Entry(2f, 10f))
        lineTwo.add(Entry(3f, 7f))
        lineTwo.add(Entry(4f, 15f))
        lineTwo.add(Entry(5f, 13f))
        lineTwo.add(Entry(6f, 3f))

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

       // data.setDrawValues(false)
        data.setValueTextColor(Color.WHITE)
        data.setValueTextSize(12f)

        binding.lineChartYear.data = data
        binding.lineChartYear.invalidate()

        //Array Title xAxis
        val labels = arrayOf<String>("", "4", "5", "6", "7", "8", "9", "")

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

        binding.lineChartYear.setVisibleXRangeMaximum(6f)
    }

}
    fun getDayFrom(){

    }
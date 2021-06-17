package com.laptrinhdidong.nhom3.quanlichitieu.PieChart

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending.Nhom3QuocPieChartAdapter
import com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending.Nhom3QuocPieChartViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.PieChart.Legend.Nhom3QuocLegendPiechartAdapter
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3QuocFragmentYearBinding
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
 * Use the [Nhom3QuocFragmentYear.newInstance] factory method to
 * create an instance of this fragment.
 */

class Nhom3QuocFragmentYear : Fragment() {
    private lateinit var binding: Nhom3QuocFragmentYearBinding
    private lateinit var viewModel: Nhom3QuocPieChartViewModel
    private lateinit var adapter: Nhom3QuocPieChartAdapter
    private lateinit var adapter_legned: Nhom3QuocLegendPiechartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(Nhom3QuocPieChartViewModel::class.java)
        binding = DataBindingUtil.inflate<Nhom3QuocFragmentYearBinding>(
            inflater,
            R.layout.nhom3_quoc_fragment_year,
            container,
            false
        )
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Array Colors
        val arrayColors = mutableListOf<Int>(
            resources.getColor(R.color.red),
            resources.getColor(R.color.yellow),
            resources.getColor(R.color.teal_200),
            resources.getColor(R.color.purple_200),
            resources.getColor(R.color.purple_700),
            resources.getColor(R.color.greeny)
        )

        adapter = Nhom3QuocPieChartAdapter()
        binding.recycleViewYear.layoutManager = LinearLayoutManager(context)
        adapter.data = viewModel.getData()
        binding.recycleViewYear.adapter = adapter

        adapter_legned = Nhom3QuocLegendPiechartAdapter()
        binding.recycleviewLegend.layoutManager = LinearLayoutManager(context)
        adapter_legned.data = viewModel.getData()
        binding.recycleviewLegend.adapter = adapter_legned

        /*============= Show Year Dialog ==================*/
        //Setup Month Dialog
        val tv_from = binding.tvFrom
        val tv_to = binding.tvTo
        val today = Calendar.getInstance()
        val year_now = today.get(Calendar.YEAR)
        val month_now = today.get(Calendar.MONTH)
        val day_now = today.get(Calendar.DAY_OF_MONTH)

        //Set năm hiển thị = năm hiện tại
        tv_from.text = "" + year_now + ""

        //Choosen Year from
        tv_from.setOnClickListener {
            val monthPickerDialog: MonthPickerDialog.Builder = MonthPickerDialog.Builder(activity!!,
                MonthPickerDialog.OnDateSetListener
                { selectedMonth, selectedYear -> tv_from.text = "" + selectedYear + ""
                    isCheckYear(tv_from.text.toString(), tv_to.text.toString())
                },
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
        tv_to.text = "" + year_now + ""

        //Choosen Year to
        tv_to.setOnClickListener {
            val monthPickerDialog: MonthPickerDialog.Builder = MonthPickerDialog.Builder(activity!!,
                MonthPickerDialog.OnDateSetListener
                { selectedMonth, selectedYear -> tv_to.text = "" + selectedYear +""
                    isCheckYear(tv_from.text.toString(), tv_to.text.toString())
                },
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

        isCheckYear(tv_from.text.toString(), tv_to.text.toString())

        //Setup PieChart
        val pieEntries = arrayListOf<PieEntry>()
        pieEntries.add(PieEntry(30.0f))
        pieEntries.add(PieEntry(40.0f))
        pieEntries.add(PieEntry(35.0f))

        //Setup PieChart Animation
        binding.pieChartYear.animateXY(1000, 1000)

        //Setup PieChart Entries Color
        val pieDataSet = PieDataSet(pieEntries, "Biểu đồ chi tiêu")
        pieDataSet.setColors(
            arrayColors[0],
            arrayColors[1],
            arrayColors[2],
            arrayColors[3],
            arrayColors[4],
            arrayColors[5],

            )

        //Setup Pie Data Set in PieData
        val pieData = PieData(pieDataSet)

        //Configure value text size
        pieData.setValueTextSize(15f)

        //Setup Text in PieChart Center
        binding.pieChartYear.setCenterTextColor(resources.getColor(R.color.black))
        binding.pieChartYear.setCenterTextSize(15f)

        binding.pieChartYear.setEntryLabelTextSize(8f)

        binding.pieChartYear.legend.textColor = resources.getColor(R.color.white)

        //Hide Description
        binding.pieChartYear.description.isEnabled = false

        //Setup legend
        val legend = binding.pieChartYear.legend
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        legend.orientation = Legend.LegendOrientation.VERTICAL
        legend.isEnabled = false


        //this enable the value on each pieEntry
        pieData.setDrawValues(true)

        binding.pieChartYear.data = pieData
    }


//    @RequiresApi(Build.VERSION_CODES.O)
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
        Log.e("START DAY", date_Start)
        Log.e("END DAY", date_End)

    }

}
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
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3QuocFragmentMonthBinding
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
 * Use the [Nhom3QuocFragmentMonth.newInstance] factory method to
 * create an instance of this fragment.
 */
class Nhom3QuocFragmentMonth : Fragment() {
    private lateinit var binding: Nhom3QuocFragmentMonthBinding
    private lateinit var viewModel: Nhom3QuocPieChartViewModel
    private lateinit var adapter: Nhom3QuocPieChartAdapter
    private lateinit var adapter_legned: Nhom3QuocLegendPiechartAdapter

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
        Log.e("Trang 2","đã month")
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = Nhom3QuocPieChartAdapter()
        adapter_legned = Nhom3QuocLegendPiechartAdapter()
        binding.recycleViewMonth.layoutManager = LinearLayoutManager(context)
        binding.recycleviewLegend.layoutManager = LinearLayoutManager(context)
        if(!viewModel.firstAccess)
        {
            viewModel.getExMoney(2)
        }else
        {
            viewModel.firstAccess=true
        }
        BindingDataRecycleView()
        /*============= Show Month Dialog ==================*/
        //Setup Month Dialog
        val tv_from = binding.tvFrom
        val tv_to = binding.tvTo
        val today = Calendar.getInstance()
        val year_now = today.get(Calendar.YEAR)
        val month_now = today.get(Calendar.MONTH)
        val day_now = today.get(Calendar.DAY_OF_MONTH)

        //Set tháng hiển thị = tháng hiện tại
        tv_from.text = "" + (month_now + 1) + "/" + year_now

        //Choosen Month/Year from
        tv_from.setOnClickListener {
            val monthPickerDialog: MonthPickerDialog.Builder = MonthPickerDialog.Builder(
                activity!!,
                MonthPickerDialog.OnDateSetListener
                { selectedMonth, selectedYear ->
                    tv_from.text = "" + (selectedMonth + 1) + "/" + selectedYear
                    isCheckMonth(tv_from.text.toString(), tv_to.text.toString())
                }, year_now, month_now
            )
            monthPickerDialog.setActivatedMonth(month_now)
                .setMinYear(1990)
                .setActivatedYear(year_now)
                .setMaxYear(2050)
                .setTitle("Select Month Year")
                .build().show()

        }

        //Set tháng hiển thị = tháng hiện tại
        tv_to.text = "" + (month_now + 1) + "/" + year_now

        //Choosen Month/Year to
        tv_to.setOnClickListener {
            val monthPickerDialog: MonthPickerDialog.Builder = MonthPickerDialog.Builder(
                activity!!,
                MonthPickerDialog.OnDateSetListener
                { selectedMonth, selectedYear ->
                    tv_to.text = "" + (selectedMonth + 1) + "/" + selectedYear
                    isCheckMonth(tv_from.text.toString(), tv_to.text.toString())
                }, year_now, month_now
            )
            monthPickerDialog.setActivatedMonth(month_now)
                .setMinYear(1990)
                .setActivatedYear(year_now)
                .setMaxYear(2050)
                .setTitle("Select Month Year")
                .build().show()

        }

        isCheckMonth(tv_from.text.toString(), tv_to.text.toString())


        /*============= Show Pie Chart ==================*/
        BindingDataChart()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun isCheckMonth(from_day: String, end_day: String) {

        var sdf: SimpleDateFormat = SimpleDateFormat("MM/yyyy")
        var dateStart: Date = sdf.parse(from_day)
        var dateEnd: Date = sdf.parse(end_day)

        try {
            sdf = SimpleDateFormat("MM/yyyy")
            dateStart = sdf.parse(from_day)
            dateEnd = sdf.parse(end_day)
            if (dateStart.compareTo(dateEnd) > 0) {
                sdf = SimpleDateFormat("MM/yyyy")
                dateStart = sdf.parse(end_day)
                dateEnd = sdf.parse(from_day)
            }

        } catch (ex: ParseException) {
            ex.printStackTrace()
        }
        val date_Start = SimpleDateFormat("yyyy-MM").format(dateStart).toString()
        val date_End = SimpleDateFormat("yyyy-MM").format(dateEnd).toString()
        viewModel.fromDate= "$date_Start-01"
        viewModel.toDate=date_End+"-15"
        viewModel.getExMoney(2)
        Log.e("quoc", date_Start)
        Log.e("binh", date_End)
    }
    fun BindingDataRecycleView()
    {
        binding.recycleViewMonth.adapter = adapter
        binding.recycleviewLegend.adapter = adapter_legned
        adapter.data = viewModel.lstEx
        adapter_legned.data = viewModel.lstEx
    }
    fun BindingDataChart()
    {
        //Array Colors
        val arrayColors = mutableListOf<Int>(
            resources.getColor(R.color.red),
            resources.getColor(R.color.yellow),
            resources.getColor(R.color.teal_200),
            resources.getColor(R.color.purple_200),
            resources.getColor(R.color.purple_700),
            resources.getColor(R.color.greeny)
        )
        //Setup PieChart
        val pieEntries = arrayListOf<PieEntry>()
        pieEntries.add(PieEntry(30.0f))
        pieEntries.add(PieEntry(40.0f))
        pieEntries.add(PieEntry(35.0f))

        //Setup PieChart Animation
        binding.pieChartMonth.animateXY(1000, 1000)

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
        binding.pieChartMonth.setCenterTextColor(resources.getColor(R.color.black))
        binding.pieChartMonth.setCenterTextSize(15f)
        binding.pieChartMonth.setEntryLabelTextSize(8f)
        binding.pieChartMonth.legend.textColor = resources.getColor(R.color.white)

        //Hide Description
        binding.pieChartMonth.description.isEnabled = false

        //Setup legend
        val legend = binding.pieChartMonth.legend
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        legend.orientation = Legend.LegendOrientation.VERTICAL
        legend.isEnabled = false


        //this enable the value on each pieEntry
        pieData.setDrawValues(true)

        binding.pieChartMonth.data = pieData
    }
}


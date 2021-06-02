package com.laptrinhdidong.nhom3.quanlichitieu.PieChart

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending.Nhom3QuocPieChartAdapter
import com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending.Nhom3QuocPieChartViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3QuocFrgamentDayBinding
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.laptrinhdidong.nhom3.quanlichitieu.PieChart.Legend.Nhom3QuocLegendPiechartAdapter
import com.laptrinhdidong.nhom3.quanlichitieu.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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
    private lateinit var viewModel: Nhom3QuocPieChartViewModel
    private lateinit var adapter : Nhom3QuocPieChartAdapter
    private lateinit var adapter_legned : Nhom3QuocLegendPiechartAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(Nhom3QuocPieChartViewModel::class.java)
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
        binding.recycleViewDay.layoutManager = LinearLayoutManager(context)
        adapter.data = viewModel.getData()
        binding.recycleViewDay.adapter = adapter

        adapter_legned = Nhom3QuocLegendPiechartAdapter()
        binding.recycleviewLegend.layoutManager = LinearLayoutManager(context)
        adapter_legned.data = viewModel.getData()
        binding.recycleviewLegend.adapter = adapter_legned

       //Date Calendar
        val tv_from = binding.tvFrom
        val tv_to = binding.tvTo
        val today = Calendar.getInstance()
        val year_now = today.get(Calendar.YEAR)
        val month_now= today.get(Calendar.MONTH)
        val day_now = today.get(Calendar.DAY_OF_MONTH)
    //
    //        val id = Locale("in", "ID")
    //        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy",id)
        //Choosen Date from
        tv_from.setOnClickListener { val datePickerDialog = DatePickerDialog(activity!!,
            DatePickerDialog.OnDateSetListener
        { view, year, month, dayOfMonth -> tv_from.text= ""+dayOfMonth +"/"+(month+1)+"/"+year },year_now,month_now,day_now)
            datePickerDialog.setTitle("Select Date")
            datePickerDialog.show()
        }

        //Choosen Date to
        tv_to.setOnClickListener { val datePickerDialog = DatePickerDialog(activity!!,
            DatePickerDialog.OnDateSetListener
            { view, year, month, dayOfMonth -> tv_to.text= ""+dayOfMonth +"/"+(month+1)+"/"+year  },year_now,month_now,day_now)
            datePickerDialog.setTitle("Select Date")
            datePickerDialog.show()
        }


        //Setup PieChart
        val pieEntries = arrayListOf<PieEntry>()
        pieEntries.add(PieEntry(57.4f))
        pieEntries.add(PieEntry(34.4f))
        pieEntries.add(PieEntry(25.5f))


        //Setup PieChart Animation
        binding.pieChart.animateXY(1000,1000)

        //Setup PieChart Entries Color
        val pieDataSet = PieDataSet(pieEntries,"Biểu đồ chi tiêu")
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
        binding.pieChart.setCenterTextColor(resources.getColor(R.color.black))
        binding.pieChart.setEntryLabelTextSize(12f)
        binding.pieChart.setCenterTextSize(15f)
        binding.pieChart.legend.textColor = resources.getColor(R.color.white)

        //Hide Description
        binding.pieChart.description.isEnabled = false

        //Setup legend
        val legend = binding.pieChart.legend
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        legend.orientation = Legend.LegendOrientation.VERTICAL
        legend.isEnabled = false



        //this enable the value on each pieEntry
        pieData.setDrawValues(true)

        binding.pieChart.data = pieData

    }



}








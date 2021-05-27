package com.laptrinhdidong.nhom3.quanlichitieu.PieChart

import android.app.DatePickerDialog
import android.os.Bundle
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
import com.laptrinhdidong.nhom3.quanlichitieu.R
import java.util.*

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
        adapter = Nhom3QuocPieChartAdapter()
        binding.recycleViewDay.layoutManager = LinearLayoutManager(context)
        adapter.data = viewModel.getData()
        binding.recycleViewDay.adapter = adapter


       //Date Calendar
        val tv_from = binding.tvFrom
        val tv_to = binding.tvTo
        val cal = Calendar.getInstance()
        val year_from = cal.get(Calendar.YEAR)
        val month_from = cal.get(Calendar.MONTH)
        val day_from = cal.get(Calendar.DAY_OF_MONTH)

        tv_from.setOnClickListener { val datePickerDialog = DatePickerDialog(activity!!,
            DatePickerDialog.OnDateSetListener
        { view, year, month, dayOfMonth -> tv_from.text= ""+dayOfMonth +"/ "+(month+1)+"/ "+year  },year_from,month_from,day_from)
        datePickerDialog.show()
        }
        tv_to.setOnClickListener { val datePickerDialog = DatePickerDialog(activity!!,
            DatePickerDialog.OnDateSetListener
            { view, year, month, dayOfMonth -> tv_to.text= ""+dayOfMonth +"/ "+(month+1)+"/ "+year  },year_from,month_from,day_from)
            datePickerDialog.show()
        }







        //Setup PieChart
        val pieEntries = arrayListOf<PieEntry>()
        pieEntries.add(PieEntry(57.2f,"Ăn uống"))
        pieEntries.add(PieEntry(32.8f,"Đi lại"))
        pieEntries.add(PieEntry(12.1f,"Sinh hoạt"))

        //Setup PieChart Animation
        binding.pieChart.animateXY(1000,1000)

        //Setup PieChart Entries Color
        val pieDataSet = PieDataSet(pieEntries,"Biểu đồ chi tiêu")
        pieDataSet.setColors(
            resources.getColor(R.color.stroke_checked),
            resources.getColor(R.color.red),
            resources.getColor(R.color.teal_200),
        )

        //Setup Pie Data Set in PieData
        val pieData = PieData(pieDataSet)

        //Setup Text in PieChart Center
        binding.pieChart.centerText= "Biểu đồ chi tiêu"
        binding.pieChart.setCenterTextColor(resources.getColor(R.color.black))
        binding.pieChart.setCenterTextSize(15f)
        binding.pieChart.setEntryLabelTextSize(12f)

        binding.pieChart.legend.textColor = resources.getColor(R.color.white)

        //Hide Description
        binding.pieChart.description.isEnabled = false

        //Setup legend
        val legend = binding.pieChart.legend
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        legend.orientation = Legend.LegendOrientation.VERTICAL
        legend.isEnabled = true



        //this enable the value on each pieEntry
        pieData.setDrawValues(true)

        binding.pieChart.data = pieData

    }



}








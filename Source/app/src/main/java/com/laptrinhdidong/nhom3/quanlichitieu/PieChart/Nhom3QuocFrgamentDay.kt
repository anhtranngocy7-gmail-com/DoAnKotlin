package com.laptrinhdidong.nhom3.quanlichitieu.PieChart

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.laptrinhdidong.nhom3.quanlichitieu.BarChart.Nhom3QuocBarChart
import com.laptrinhdidong.nhom3.quanlichitieu.PieChart.Legend.Nhom3QuocLegendPiechartAdapter
import com.laptrinhdidong.nhom3.quanlichitieu.R
import java.util.*
import com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending.Nhom3QuocPieChartAdapter
import com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending.Nhom3QuocPieChartViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.MainApp.TichLuy.Nhom3AnTichLuyFragment
import com.laptrinhdidong.nhom3.quanlichitieu.Model.Database
import com.laptrinhdidong.nhom3.quanlichitieu.Spending.Nhom3BinhSpendingFragment
import com.laptrinhdidong.nhom3.quanlichitieu.Statistical.ReportExpense.Nhom3AnReportExpenseFragment
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3QuocFrgamentDayBinding

import kotlinx.android.synthetic.main.nhom3_anh_activity_sign_in.*
import java.text.ParseException
import java.text.SimpleDateFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [Nhom3QuocFrgamentDay.newInstance] factory method to
 * create an instance of this fragment.
 */
//class Nhom3QuocFrgamentDay : Fragment() {
//    private lateinit var binding: Nhom3QuocFrgamentDayBinding
//    private lateinit var viewModel: Nhom3QuocPieChartViewModel
//    private lateinit var adapter: Nhom3QuocPieChartAdapter
//    private lateinit var adapter_legned: Nhom3QuocLegendPiechartAdapter
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
////        viewModel = ViewModelProvider(this).get(Nhom3QuocPieChartViewModel::class.java)
//
//        binding = DataBindingUtil.inflate<Nhom3QuocFrgamentDayBinding>(
//            inflater,
//            R.layout.nhom3_quoc_frgament_day,
//            container,
//            false
//
//        )
//        Log.e("quoc", "day")
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        Log.e("anh", "day")
//    }
//}
class Nhom3QuocFrgamentDay : Fragment() {
    private lateinit var binding: Nhom3QuocFrgamentDayBinding
    private lateinit var viewModel: Nhom3QuocPieChartViewModel
    private lateinit var adapter: Nhom3QuocPieChartAdapter
    private lateinit var adapter_legned: Nhom3QuocLegendPiechartAdapter

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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Date Calendar
        val tv_from : TextView = binding.tvFrom
        val tv_to : TextView  = binding.tvTo
        //Khai b??o binding
        adapter = Nhom3QuocPieChartAdapter()
        binding.recycleViewDay.layoutManager = LinearLayoutManager(context)
        adapter_legned = Nhom3QuocLegendPiechartAdapter()
        binding.recycleviewLegend.layoutManager = LinearLayoutManager(context)
        //Check n???u l???n ?????u truy c???p th?? getData
        if(!viewModel.firstAccess)
        {
            isCheckDate(viewModel.fromDate,viewModel.toDate)
            viewModel.firstAccess=true
        }else
        {
            BindingDataChart()
            BindingDataRecycleView()
        }
        //Set day hi???n th??? = day hi???n t???i
        tv_from.setText(viewModel.fromDate)
        //Set day hi???n th??? = day hi???n t???i
        tv_to.setText(viewModel.toDate)
        //Choosen Date from
        tv_from.setOnClickListener {
            val datePickerDialog =
                DatePickerDialog(
                    activity!!, R.style.Theme_AppCompat_Light_Dialog,
                    DatePickerDialog.OnDateSetListener
                    { view, year, month, dayOfMonth ->
                        tv_from.text = ""+year+"-"+(month + 1)+"-"+dayOfMonth
                        isCheckDate(tv_from.text.toString(),tv_to.text.toString())
                    }, viewModel.year_now, viewModel.month_now, viewModel.day_now
                )
            datePickerDialog.setTitle("Select Date")
            datePickerDialog.show()
            print(tv_from.text.toString())

        }

        //Choosen Date to
        tv_to.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                activity!!, R.style.Theme_AppCompat_Light_Dialog,
                DatePickerDialog.OnDateSetListener
                { view, year, month, dayOfMonth ->
                    tv_to.text = ""+year+"-"+(month + 1)+"-"+dayOfMonth
                    isCheckDate(tv_from.text.toString(),tv_to.text.toString())

                }, viewModel.year_now, viewModel.month_now, viewModel.day_now
            )
            datePickerDialog.setTitle("Select Date")
            datePickerDialog.show()

        }
        binding.btnXemchitiet.setOnClickListener {
            Database.instance.fromDateF=viewModel.fromDate
            Database.instance.toDateF=viewModel.toDate
            Database.instance.stateTransferFragment=true
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<Nhom3AnReportExpenseFragment>(R.id.fragment_mainapp)
                addToBackStack(null)
            }
        }
        /*============= Show Pie Chart ==================*/
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun isCheckDate(from_day : String, end_day : String ) {

        var sdf = SimpleDateFormat("yyyy-MM-dd")
        var dateStart : Date = sdf.parse(from_day)
        var dateEnd : Date  =  sdf.parse(end_day)

        try {
            sdf  = SimpleDateFormat("yyyy-MM-dd")
            dateStart =sdf.parse(from_day)
            dateEnd = sdf.parse(end_day)
            if(dateStart.compareTo(dateEnd) > 0){
                sdf  = SimpleDateFormat("yyyy-MM-dd")
                dateStart =sdf.parse(end_day)
                dateEnd = sdf.parse(from_day)
            }

        }catch (ex : ParseException){
            ex.printStackTrace()
        }
        val date_Start = SimpleDateFormat("yyyy-MM-dd").format(dateStart).toString()
        val date_End = SimpleDateFormat("yyyy-MM-dd").format(dateEnd).toString()
        viewModel.fromDate=date_Start
        viewModel.toDate=date_End
        viewModel.getExMoney(1)
        binding.tvFrom.text=viewModel.fromDate
        //Set day hi???n th??? = day hi???n t???i
        binding.tvTo.text=viewModel.toDate
        BindingDataChart()
        BindingDataRecycleView()
        binding.tvTongChiTieu.text=viewModel.totalMoney.toString()+"vnd"

    }
    fun BindingDataRecycleView()
    {
        binding.recycleViewDay.adapter = adapter
        binding.recycleviewLegend.adapter = adapter_legned
        adapter.data = viewModel.lstEx
        adapter_legned.data = viewModel.lstEx
    }
    fun BindingDataChart()
    {
        val arrayColors = mutableListOf<Int>(
            resources.getColor(R.color.red),
            resources.getColor(R.color.yellow),
            resources.getColor(R.color.teal_200),
            resources.getColor(R.color.purple_200),
            resources.getColor(R.color.purple_700),
            resources.getColor(R.color.greeny)
        )
        val pieEntries = arrayListOf<PieEntry>()
        viewModel.lstEx.forEach {
            pieEntries.add(PieEntry(it.persent))
        }
        //Setup PieChart Animation
        binding.pieChart.animateXY(1000, 1000)
        //Setup PieChart Entries Color
        val pieDataSet = PieDataSet(pieEntries, "Bi???u ????? chi ti??u")
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








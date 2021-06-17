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
        adapter = Nhom3QuocBarChartAdapter()
        binding.recycleviewDayBC.layoutManager = LinearLayoutManager(context)
        adapter.data = viewModel.getData()
        binding.recycleviewDayBC.adapter = adapter

        //Date Calendar
        val tv_from = binding.tvFrom
        val tv_to = binding.tvTo
        val today = Calendar.getInstance()
        val year_now = today.get(Calendar.YEAR)
        val month_now = today.get(Calendar.MONTH)
        val day_now = today.get(Calendar.DAY_OF_MONTH)

        //Set day hiển thị = day hiện tại
        tv_from.text = "" + day_now +"/"+  (month_now +1)+ "/" + year_now
        //Choosen Date from
        tv_from.setOnClickListener {
            val datePickerDialog =
                DatePickerDialog(
                    activity!!, R.style.Theme_AppCompat_Light_Dialog,
                    DatePickerDialog.OnDateSetListener
                    { view, year, month, dayOfMonth ->
                        tv_from.text = "" + dayOfMonth + "/" + (month + 1) + "/" + year
                        isCheckDate(tv_from.text.toString(),tv_to.text.toString())
                    }, year_now, month_now, day_now
                )
            datePickerDialog.setTitle("Select Date")
            datePickerDialog.show()
        }

        //Set day hiển thị = day hiện tại
        tv_to.text = "" + day_now +"/"+ (month_now +1)+ "/" + year_now
        //Choosen Date to
        tv_to.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                activity!!, R.style.Theme_AppCompat_Light_Dialog,
                DatePickerDialog.OnDateSetListener
                { view, year, month, dayOfMonth ->
                    tv_to.text = "" + dayOfMonth + "/" + (month + 1) + "/" + year
                    isCheckDate(tv_from.text.toString(),tv_to.text.toString())
                }, year_now, month_now, day_now
            )
            datePickerDialog.setTitle("Select Date")
            datePickerDialog.show()
        }
        isCheckDate(tv_from.text.toString(),tv_to.text.toString())

        //Setup Line Chart

        val lineOne = arrayListOf<Entry>()
        lineOne.add(Entry(1f, 5f))
        lineOne.add(Entry(2f, 9f))
        lineOne.add(Entry(3f, 4f))
        lineOne.add(Entry(4f, 8f))
        lineOne.add(Entry(5f, 12f))
        lineOne.add(Entry(6f, 22f))
        lineOne.add(Entry(7f, 17f))
        lineOne.add(Entry(8f, 6f))
        lineOne.add(Entry(9f, 3f))
        lineOne.add(Entry(10f, 19f))
        lineOne.add(Entry(11f, 21f))
        lineOne.add(Entry(12f, 13f))


        val lineTwo = arrayListOf<Entry>()
        lineTwo.add(Entry(1f, 6f))
        lineTwo.add(Entry(2f, 10f))
        lineTwo.add(Entry(3f, 7f))
        lineTwo.add(Entry(4f, 15f))
        lineTwo.add(Entry(5f, 13f))
        lineTwo.add(Entry(6f, 3f))
        lineTwo.add(Entry(7f, 24f))
        lineTwo.add(Entry(8f, 16f))
        lineTwo.add(Entry(9f, 15f))
        lineTwo.add(Entry(10f, 4f))
        lineTwo.add(Entry(11f, 17f))
        lineTwo.add(Entry(12f, 9f))



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
        val labels = arrayOf<String>("","1","2","3", "4", "5", "6", "7", "8", "9","10","11","12","")

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
        fun isCheckDate(from_day : String, end_day : String ) {

        var sdf : SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        var dateStart : Date = sdf.parse(from_day)
        var dateEnd : Date  =  sdf.parse(end_day)

        try {
            sdf  = SimpleDateFormat("dd/MM/yyyy")
            dateStart =sdf.parse(from_day)
            dateEnd = sdf.parse(end_day)
            if(dateStart.compareTo(dateEnd) > 0){
                sdf  = SimpleDateFormat("dd/MM/yyyy")
                dateStart =sdf.parse(end_day)
                dateEnd = sdf.parse(from_day)
            }

        }catch (ex : ParseException){
            ex.printStackTrace()
        }
        val date_Start = SimpleDateFormat("yyyy-MM-dd").format(dateStart).toString()
        val date_End = SimpleDateFormat("yyyy-MM-dd").format(dateEnd).toString()
        Log.e("START DAY",date_Start)
        Log.e("END DAY",date_End)

    }

}



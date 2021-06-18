package com.laptrinhdidong.nhom3.quanlichitieu.Statistical.ReportExpense

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.laptrinhdidong.nhom3.quanlichitieu.MainApp.TichLuy.Nhom3AnTichLuyViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.Model.Database
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3AnFragmentReportexpenseBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [Nhom3AnReportExpenseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Nhom3AnReportExpenseFragment : Fragment() {
    private lateinit var binding: Nhom3AnFragmentReportexpenseBinding
    private lateinit var outerList : MutableList<Nhom3AnGroupReportExpense>
    private lateinit var map : MutableMap<Nhom3AnGroupReportExpense,MutableList<Nhom3AnItemReportExpense>>
    private lateinit var adapter: Nhom3AnOuterAdapter
    private lateinit var viewModel: Nhom3AnReportExpenseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(Nhom3AnReportExpenseViewModel::class.java)
        binding = DataBindingUtil.inflate<Nhom3AnFragmentReportexpenseBinding>(
            inflater,
            R.layout.nhom3_an_fragment_reportexpense,
            container,
            false
        )
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = Nhom3AnOuterAdapter(requireContext())
        binding.rcvOuter.layoutManager = LinearLayoutManager(context)
        if(!viewModel.firstAccess)
        {
            if(Database.instance.stateTransferFragment)
            {
                viewModel.fromDate=Database.instance.fromDateF
                viewModel.toDate=Database.instance.toDateF
                Database.instance.stateTransferFragment=false
            }
            isCheckDate(viewModel.fromDate,viewModel.toDate)
            viewModel.firstAccess=true
        }else
        {
            BindingDataRecycleView()
        }
        //Date Calendar
        val tv_from = binding.tvFrom
        val tv_to = binding.tvTo

        //Choosen Date from
        tv_from.setText(viewModel.fromDate)
        //Set day hiển thị = day hiện tại
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
    }
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
        viewModel.getListEx()
        BindingDataRecycleView()
        binding.tvFrom.text=viewModel.fromDate
        binding.tvTo.text=viewModel.toDate

    }
    fun BindingDataRecycleView()
    {
        adapter.lstOut=viewModel.outerList
        adapter.map=viewModel.map
        binding.rcvOuter.adapter=adapter
    }
}
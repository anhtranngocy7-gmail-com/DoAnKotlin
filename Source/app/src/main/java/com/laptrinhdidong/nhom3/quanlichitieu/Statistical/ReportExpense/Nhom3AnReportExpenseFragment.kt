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
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3AnFragmentReportexpenseBinding
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
        if(!viewModel.firstAccess)
        {
            assignData()
            viewModel.firstAccess=true
        }
        adapter = Nhom3AnOuterAdapter(requireContext())
        binding.rcvOuter.layoutManager = LinearLayoutManager(context)
        adapter.lstOut=viewModel.outerList
        adapter.map=viewModel.map
        binding.rcvOuter.adapter=adapter

        //Date Calendar
        val tv_from = binding.tvFrom
        val tv_to = binding.tvTo
        val today = Calendar.getInstance()
        val year_now = today.get(Calendar.YEAR)
        val month_now = today.get(Calendar.MONTH)
        val day_now = today.get(Calendar.DAY_OF_MONTH)

        //Choosen Date from
        tv_from.setOnClickListener {
            val datePickerDialog =
                DatePickerDialog(
                    activity!!, R.style.Theme_AppCompat_Light_Dialog,
                    DatePickerDialog.OnDateSetListener
                    { view, year, month, dayOfMonth ->
                        tv_from.text = "" + dayOfMonth + "/" + (month + 1) + "/" + year
                    }, year_now, month_now, day_now
                )
            datePickerDialog.setTitle("Select Date")
            datePickerDialog.show()
        }

        //Choosen Date to
        tv_to.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                activity!!, R.style.Theme_AppCompat_Light_Dialog,
                DatePickerDialog.OnDateSetListener
                { view, year, month, dayOfMonth ->
                    tv_to.text = "" + dayOfMonth + "/" + (month + 1) + "/" + year
                }, year_now, month_now, day_now
            )
            datePickerDialog.setTitle("Select Date")
            datePickerDialog.show()
        }
    }
    fun assignData()
    {
        viewModel.getListEx()
    }
}
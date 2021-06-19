package com.laptrinhdidong.nhom3.quanlichitieu.MainApp.TongQuan

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import com.laptrinhdidong.nhom3.quanlichitieu.BarChart.Nhom3QuocBarChart
import com.laptrinhdidong.nhom3.quanlichitieu.MainApp.TichLuy.Nhom3AnTichLuyFragment
import com.laptrinhdidong.nhom3.quanlichitieu.Model.Accumulate
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3AnTongquanFragmentBinding
import com.laptrinhdidong.nhom3.quanlichitieu.Nhom3AnOverviewViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.Object.Nhom3AnItemFeature
import com.laptrinhdidong.nhom3.quanlichitieu.OnItemClickListener
import com.laptrinhdidong.nhom3.quanlichitieu.PieChart.Nhom3QuocPieChart
import com.laptrinhdidong.nhom3.quanlichitieu.Spending.Nhom3BinhSpendingFragment
import com.laptrinhdidong.nhom3.quanlichitieu.Statistical.ReportExpense.Nhom3AnReportExpenseFragment
import java.lang.Exception

class Nhom3AnTongQuanFragment : Fragment(), OnItemClickListener {
    private lateinit var binding: Nhom3AnTongquanFragmentBinding
    private lateinit var viewModel: Nhom3AnOverviewViewModel
    private lateinit var adapterNhom3An: Nhom3AnTinhNangAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(Nhom3AnOverviewViewModel::class.java)
        binding = DataBindingUtil.inflate<Nhom3AnTongquanFragmentBinding>(
            inflater,
            R.layout.nhom3_an_tongquan_fragment,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterNhom3An= Nhom3AnTinhNangAdapter(this)
        binding.recycleviewFeature.layoutManager=GridLayoutManager(context,3)
        if(!viewModel.firstAccess)
        {
            viewModel.getData()
            viewModel.firstAccess=true
        }
        binding.viewmodel=viewModel
        binding.recycleviewFeature.adapter=adapterNhom3An
        binding.btnDropMore.setOnClickListener(View.OnClickListener {
            binding.linearMoreinfor.visibility=if(binding.linearMoreinfor.isVisible){View.GONE}else{View.VISIBLE }
        })
        binding.spinnerTime.onItemSelectedListener= object :OnItemSelectedListener
        {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.getExInMoney(position+1)
                binding.verticalProgressbargreen.progress=100- viewModel.lstEiInfo[0].percent
                binding.verticalProgressbarred.progress=100-viewModel.lstEiInfo.get(1).percent
                binding.tvTongthu.text=viewModel.lstEiInfo.get(1).money.toString()
                binding.tvTongchi.text=viewModel.lstEiInfo.get(0).money.toString()
//                binding.viewmodel=viewModel
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
        binding.ivEditpure.setOnClickListener {
            var mDialogView = LayoutInflater.from(context)
                .inflate(R.layout.nhom3_an_custom_addmoney_dialog, null)
            var mBuilder = AlertDialog.Builder(context)
                .setView(mDialogView)
            val addFunction: AlertDialog = mBuilder.create()
            addFunction.show()
            val tvInput = mDialogView.findViewById<EditText>(R.id.addAction)
            val btnaddConfirm = mDialogView.findViewById<Button>(R.id.addConfirm)
            val btnaddCancel = mDialogView.findViewById<Button>(R.id.addCancel)
            btnaddConfirm.setOnClickListener {
                var money=0.toBigDecimal()
                try {
                  money=tvInput.text.toString().toBigDecimal()
                    viewModel.setMoney(money)
                    viewModel.ac.money=money
                    binding.tvTientrongvi.text=viewModel.ac.money.toString()
                    addFunction.dismiss()
                }catch (e : Exception)
                {
                    Toast.makeText(requireContext(),"số tiền rỗng",Toast.LENGTH_SHORT).show()
                }
            }
            btnaddCancel.setOnClickListener {
                addFunction.dismiss()
            }
        }
    }

    override fun onItemClick(index: Int) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            when(index)
            {
                0 ->replace<Nhom3BinhSpendingFragment>(R.id.fragment_mainapp)
                1 ->replace<Nhom3AnTichLuyFragment>(R.id.fragment_mainapp)
                2 ->replace<Nhom3AnReportExpenseFragment>(R.id.fragment_mainapp)
                3 ->replace<Nhom3QuocPieChart>(R.id.fragment_mainapp)
                4 ->replace<Nhom3QuocBarChart>(R.id.fragment_mainapp)
            }
            addToBackStack(null)
        }
    }

    override fun onItemClick(accumulate: Accumulate) {
    }

    override fun onClickDelete(index: Int) {
    }

    override fun onClickAddMoney(index: Int) {
    }
//    private fun getPermission() {
//        ActivityCompat.requestPermissions(
//            context as Activity,
//            arrayOf(Manifest.permission.INTERNET),
//            PackageManager.PERMISSION_GRANTED
//        )
//        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
//        StrictMode.setThreadPolicy(policy)
//    }
}
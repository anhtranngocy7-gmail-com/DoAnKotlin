package com.laptrinhdidong.nhom3.quanlichitieu.Spending

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.SpendingItem
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3BinhActivitySpendingBinding
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout
import java.text.SimpleDateFormat
import java.util.*


class Nhom3BinhSpendingActivity: AppCompatActivity() {
    private lateinit var viewModel: Nhom3BinhSpendingViewModel
    private lateinit var binding: Nhom3BinhActivitySpendingBinding

    private var formatDate:SimpleDateFormat = SimpleDateFormat("EEE, dd/MM/YYYY")
    private var spendingItem : SpendingItem = SpendingItem(0,"",formatDate,true,"")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.nhom3_binh_activity_spending)
        viewModel = ViewModelProvider(this).get(Nhom3BinhSpendingViewModel::class.java)
        binding.spendingItem = spendingItem
        binding.editDateSpending.setOnClickListener{
            val getDate = Calendar.getInstance()
            val dialog =  DatePickerDialog(this,DatePickerDialog.OnDateSetListener{view,mYear,mMonth,mDay->
                val selectDate = Calendar.getInstance()
                val year = selectDate.set(Calendar.YEAR,mYear)
                val month = selectDate.set(Calendar.MONTH,mMonth)
                val day = selectDate.set(Calendar.DAY_OF_MONTH,mDay)
                val date = viewModel.formatDate.format(selectDate.time)
                viewModel.dateDefault = selectDate.time
                binding.calendar.setText(date)
            },getDate.get(Calendar.YEAR),getDate.get(Calendar.MONTH),getDate.get(Calendar.DAY_OF_MONTH))
            dialog.show()
        }

        binding.calendar.setText(viewModel.formatDate.format(viewModel.dateDefault))



    }

}
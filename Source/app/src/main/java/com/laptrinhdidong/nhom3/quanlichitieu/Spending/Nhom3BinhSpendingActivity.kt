package com.laptrinhdidong.nhom3.quanlichitieu.Spending

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3BinhActivitySpendingBinding


class Nhom3BinhSpendingActivity: AppCompatActivity() {
    private lateinit var viewModel: Nhom3BinhSpendingViewModel
    private lateinit var binding: Nhom3BinhActivitySpendingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.nhom3_binh_activity_spending)
        viewModel = ViewModelProvider(this).get(Nhom3BinhSpendingViewModel::class.java)
        binding.calendar.append(viewModel.sdf.format(viewModel.date))
    }
}
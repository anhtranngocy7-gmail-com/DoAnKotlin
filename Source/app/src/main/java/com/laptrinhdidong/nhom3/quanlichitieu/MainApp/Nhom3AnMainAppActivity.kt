package com.laptrinhdidong.nhom3.quanlichitieu.MainApp

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.Manifest
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.laptrinhdidong.nhom3.quanlichitieu.MainApp.TichLuy.Nhom3AnTichLuyFragment
import com.laptrinhdidong.nhom3.quanlichitieu.MainApp.TongQuan.Nhom3AnTongQuanFragment
import com.laptrinhdidong.nhom3.quanlichitieu.PieChart.Nhom3QuocPieChart
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.Spending.Nhom3BinhSpendingFragment
import com.laptrinhdidong.nhom3.quanlichitieu.Statistical.Nhom3QuocStatisticalFragment
import com.laptrinhdidong.nhom3.quanlichitieu.Statistical.ReportExpense.Nhom3AnReportExpenseFragment

import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3AnActivityMainAppBinding

class Nhom3AnMainAppActivity : AppCompatActivity() {
    private lateinit var binding: Nhom3AnActivityMainAppBinding
    private lateinit var viewModel: Nhom3AnMainAppViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPermission()
        binding = DataBindingUtil.setContentView(this, R.layout.nhom3_an_activity_main_app)
        viewModel = ViewModelProvider(this).get(Nhom3AnMainAppViewModel::class.java)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            when (viewModel.optionMenu) {
                0 -> {
                    add<Nhom3AnTongQuanFragment>(R.id.fragment_mainapp)
                }
                1 -> {
                    add<Nhom3AnTichLuyFragment>(R.id.fragment_mainapp)
                }
                2 -> {
                    add<Nhom3BinhSpendingFragment>(R.id.fragment_mainapp)
                }
                3 -> {
                    add<Nhom3QuocPieChart>(R.id.fragment_mainapp)
                }
                4 ->{
                    add<Nhom3AnReportExpenseFragment>(R.id.fragment_mainapp)
                }
            }
        }
        onchangeOptionNav()
    }

    private fun onchangeOptionNav() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item: MenuItem ->
            supportFragmentManager.commit {
                setReorderingAllowed(true)

                when (item.itemId) {
                    R.id.action_home -> {
                        replace<Nhom3AnTongQuanFragment>(R.id.fragment_mainapp)
                        viewModel.optionMenu = 0
                    }
                    R.id.action_accumulate -> {
                        replace<Nhom3AnTichLuyFragment>(R.id.fragment_mainapp)
                        viewModel.optionMenu = 1
                    }
                    R.id.action_spending -> {
                        replace<Nhom3BinhSpendingFragment>(R.id.fragment_mainapp)
                        viewModel.optionMenu = 2
                    }
                    R.id.action_report -> {
                        replace<Nhom3QuocPieChart>(R.id.fragment_mainapp)
                        viewModel.optionMenu = 3
                    }
                    R.id.action_more ->{
                        replace<Nhom3AnReportExpenseFragment>(R.id.fragment_mainapp)
                        viewModel.optionMenu = 4
                    }

                }
                addToBackStack(null)
            }

            return@OnNavigationItemSelectedListener true
        })
        binding.bottomNavigationView.selectedItemId=R.id.action_accumulate
    }
    private fun getPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.INTERNET),
            PackageManager.PERMISSION_GRANTED
        )
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }
}
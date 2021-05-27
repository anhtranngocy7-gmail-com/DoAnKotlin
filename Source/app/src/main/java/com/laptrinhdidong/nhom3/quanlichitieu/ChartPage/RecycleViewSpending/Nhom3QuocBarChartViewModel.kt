package com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending

import androidx.lifecycle.ViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.Object.Nhom3QuocItemBarChart

class Nhom3QuocBarChartViewModel : ViewModel() {
    fun getData() :MutableList<Nhom3QuocItemBarChart>{
        return mutableListOf(
            Nhom3QuocItemBarChart("Tháng 4", 100000,2000000,3000000),
            Nhom3QuocItemBarChart("Tháng 5", 200000,2000000,5000000),
            Nhom3QuocItemBarChart("Tháng 6", 300000,3000000,6000000)
        )
    }
}
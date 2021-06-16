package com.laptrinhdidong.nhom3.quanlichitieu.PieChart.Legend

import androidx.lifecycle.ViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.Object.Nhom3QuocItemLegendPieChart
import com.laptrinhdidong.nhom3.quanlichitieu.Object.Nhom3QuocItemPieChart

class Nhom3QuocLegendViewModel : ViewModel() {
    fun getData() :MutableList<Nhom3QuocItemLegendPieChart> {
        return mutableListOf(
            Nhom3QuocItemLegendPieChart("Ăn uống"),
            Nhom3QuocItemLegendPieChart("Đi lại"),
            Nhom3QuocItemLegendPieChart("Sinh hoạt")
        )
    }
}
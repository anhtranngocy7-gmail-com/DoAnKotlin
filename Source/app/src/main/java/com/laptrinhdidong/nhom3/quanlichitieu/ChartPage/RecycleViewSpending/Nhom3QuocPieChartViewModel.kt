package com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending

import androidx.lifecycle.ViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.Object.Nhom3QuocItemPieChart

class Nhom3QuocPieChartViewModel : ViewModel() {
    fun getData() :MutableList<Nhom3QuocItemPieChart>{
        return mutableListOf(
            Nhom3QuocItemPieChart("Ăn uống", 1000000,57.4),
            Nhom3QuocItemPieChart("Đi lại",500000,34.4),
            Nhom3QuocItemPieChart("Sinh hoạt", 4000000, 25.5),
            Nhom3QuocItemPieChart("Thuê nhà", 4000000, 12.5)

        )
    }
}
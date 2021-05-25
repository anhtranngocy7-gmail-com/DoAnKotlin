package com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending

import androidx.lifecycle.ViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.Object.Nhom3QuocItemBieudocot
import com.laptrinhdidong.nhom3.quanlichitieu.Object.Nhom3QuocItemBieudotron

class Nhom3QuocBieudocotViewModel : ViewModel() {
    fun getData() :MutableList<Nhom3QuocItemBieudocot>{
        return mutableListOf(
            Nhom3QuocItemBieudocot("Tháng 4", 100000,2000000,3000000),
            Nhom3QuocItemBieudocot("Tháng 5", 200000,2000000,5000000),
            Nhom3QuocItemBieudocot("Tháng 6", 300000,3000000,6000000)
        )
    }
}
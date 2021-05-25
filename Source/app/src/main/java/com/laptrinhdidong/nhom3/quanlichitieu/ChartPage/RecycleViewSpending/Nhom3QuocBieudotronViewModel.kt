package com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending

import androidx.lifecycle.ViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.Object.Nhom3QuocItemBieudotron

class Nhom3QuocBieudotronViewModel : ViewModel() {
    fun getData() :MutableList<Nhom3QuocItemBieudotron>{
        return mutableListOf(
            Nhom3QuocItemBieudotron("Ăn uống", 100000,57.4),
            Nhom3QuocItemBieudotron("Đi lại",50000,34.4),
            Nhom3QuocItemBieudotron("Sinh hoạt", 40000, 25.5)
        )
    }
}
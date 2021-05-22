package com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending

import androidx.lifecycle.ViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.Object.Nhom3QuocItemBieudo

class Nhom3QuocBieudoViewModel : ViewModel() {
    fun getData() :MutableList<Nhom3QuocItemBieudo>{
        return mutableListOf(
            Nhom3QuocItemBieudo("Ăn uống", 100000,57.4),
            Nhom3QuocItemBieudo("Đi lại",50000,34.4),
            Nhom3QuocItemBieudo("Sinh hoạt", 40000, 25.5)
        )
    }
}
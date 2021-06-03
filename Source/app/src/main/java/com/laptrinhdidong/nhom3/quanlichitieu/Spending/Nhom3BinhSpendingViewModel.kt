package com.laptrinhdidong.nhom3.quanlichitieu.Spending

import androidx.lifecycle.ViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.Account
import com.laptrinhdidong.nhom3.quanlichitieu.SpendingItem
import java.util.Date
import java.util.Calendar
import java.text.SimpleDateFormat
class Nhom3BinhSpendingViewModel: ViewModel() {

    var calDefault = Calendar.getInstance()
    var dateDefault = calDefault.time
    var formatDate=SimpleDateFormat("EEE, YYYY-MM-dd");
    var spendingItem : SpendingItem = SpendingItem("","","",true,"")

}
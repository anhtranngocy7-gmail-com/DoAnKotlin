package com.laptrinhdidong.nhom3.quanlichitieu.Spending

import androidx.lifecycle.ViewModel
import java.util.Date
import java.util.Calendar
import java.text.SimpleDateFormat
class Nhom3BinhSpendingViewModel: ViewModel() {
    var cal=Calendar.getInstance()
    var date=cal.time
    var sdf=SimpleDateFormat("EEE, dd/MM/YYYY");

}
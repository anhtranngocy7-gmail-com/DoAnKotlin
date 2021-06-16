package com.laptrinhdidong.nhom3.quanlichitieu.Spending

import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.Account
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.SpendingItem
import java.util.Date
import java.util.Calendar
import java.text.SimpleDateFormat
class Nhom3BinhSpendingViewModel: ViewModel() {

    var calDefault = Calendar.getInstance()
    var dateDefault = calDefault.time
    var formatDate=SimpleDateFormat("EEE, YYYY-MM-dd");
    var spendingItem : SpendingItem = SpendingItem("","","",true,"")

    fun validateAddAction(editAddAction: String): Boolean{
        return editAddAction.isNotEmpty()
    }
    fun append(arr: Array<String?>, element: String): Array<String?> {
        val array = arrayOfNulls<String>(arr.size + 1)
        System.arraycopy(arr, 0, array, 0, arr.size)
        array[arr.size] = element
        return array
    }
    var spending_new = arrayOf<String?>("Ăn uống", "Dịch vụ", "Di chuyển",
        "Sức khỏe", "Giải trí", "Mua sắm","Thêm mục khác")
    var spendingRem  = arrayOf<String?>()

}


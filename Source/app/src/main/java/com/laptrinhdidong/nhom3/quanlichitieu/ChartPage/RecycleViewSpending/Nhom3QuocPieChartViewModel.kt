package com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending

import android.util.Log
import androidx.lifecycle.ViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.Model.Accumulate
import com.laptrinhdidong.nhom3.quanlichitieu.Model.Database
import com.laptrinhdidong.nhom3.quanlichitieu.Object.Nhom3QuocItemPieChart
import java.sql.CallableStatement
import java.sql.ResultSet
import java.util.*

class Nhom3QuocPieChartViewModel : ViewModel() {
    val today : Calendar = Calendar.getInstance()
    val year_now = today.get(Calendar.YEAR)
    val month_now = today.get(Calendar.MONTH)
    val day_now = today.get(Calendar.DAY_OF_MONTH)
    var fromDate =""+year_now+"-"+(month_now +1)+"-"+day_now
    var toDate =""+year_now+"-"+(month_now +1)+"-"+day_now

    lateinit var result: ResultSet
    var firstAccess : Boolean = false
    lateinit var callP: CallableStatement
    lateinit var lstEx: MutableList<Nhom3QuocItemPieChart>
    var totalMoney=0.toBigDecimal()
    fun getExMoney(opt: Int) : String{
        if (!Database.instance.stateConnect) {
            //nếu kết nối thất bại thì return cảnh báo
            if (!Database.instance.createConnection())
                return Database.instance.messageFail
        }
        try {
            lstEx = mutableListOf()
            when(opt)
            {
                1 -> callP= Database.instance.connection!!.prepareCall("{call getMoneyExByDate(?,?,?)}")
                2 -> callP= Database.instance.connection!!.prepareCall("{call getMoneyExByMonth(?,?,?)}")
                3 -> callP= Database.instance.connection!!.prepareCall("{call getMoneyExByYear(?,?,?)}")
            }
            Database.instance.idUserApp?.let { callP.setInt(1, it) }
            callP.setString(2,fromDate)
            callP.setString(3,toDate)
            if (callP.execute()) {
                result = callP.resultSet
                Log.e("opt", opt.toString())
                Log.e("from", fromDate)
                Log.e("to", toDate)
                while (result.next() && !result.isAfterLast) {
                    var cateName = result.getString("CateName").toString()
                    var total = result.getBigDecimal("Total")
                    totalMoney+=total
                    var tmpEx = Nhom3QuocItemPieChart(cateName,total,0f)
                    Log.e("A",total.toString())
                    lstEx.add(tmpEx)
                }
            }
            callP.close()
            lstEx.forEach {
                it.persent=(it.spendingMoney.toFloat()/totalMoney.toFloat())*100f
                Log.e("Total", totalMoney.toString())
            }
            return Database.instance.messageDone
        } catch (e: Exception) {
            Database.instance.stateConnect = false
            return Database.instance.messageFail
        }
    }
}
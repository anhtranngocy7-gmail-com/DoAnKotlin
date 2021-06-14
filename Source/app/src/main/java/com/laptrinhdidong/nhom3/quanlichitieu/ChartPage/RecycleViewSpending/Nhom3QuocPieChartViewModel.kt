package com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending

import android.util.Log
import androidx.lifecycle.ViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.Model.Accumulate
import com.laptrinhdidong.nhom3.quanlichitieu.Model.Database
import com.laptrinhdidong.nhom3.quanlichitieu.Object.Nhom3QuocItemPieChart
import java.sql.CallableStatement
import java.sql.ResultSet

class Nhom3QuocPieChartViewModel : ViewModel() {
    lateinit var result: ResultSet
    var fromDate ="2020-1-1"
    var toDate ="2021-12-31"
    var firstAccess : Boolean = false
    lateinit var callP: CallableStatement
    lateinit var lstEx: MutableList<Nhom3QuocItemPieChart>
    fun getExMoney(opt: Int) :MutableList<Nhom3QuocItemPieChart>{
        lstEx = mutableListOf()
        var totalMoney=0.toBigDecimal();
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
            while (result.next() && !result.isAfterLast) {
                var cateName = result.getString("CateName").toString()
                Log.e("A",cateName)
                var total = result.getBigDecimal("Total")
                totalMoney+=total
                var tmpEx = Nhom3QuocItemPieChart(cateName,total,0f)
                lstEx.add(tmpEx)
            }
        }
        callP.close()
        lstEx.forEach {
            it.persent=(it.spendingMoney.toFloat()/totalMoney.toFloat())*100f
            Log.e("Total", totalMoney.toString())
        }
        return lstEx
    }
}
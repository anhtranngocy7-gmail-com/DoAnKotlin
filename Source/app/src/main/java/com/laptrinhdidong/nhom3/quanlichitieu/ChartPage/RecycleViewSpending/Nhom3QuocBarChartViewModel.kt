package com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending

import android.util.Log
import androidx.lifecycle.ViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.Model.Database
import com.laptrinhdidong.nhom3.quanlichitieu.Object.Nhom3QuocItemBarChart
import com.laptrinhdidong.nhom3.quanlichitieu.Object.Nhom3QuocItemPieChart
import java.sql.CallableStatement
import java.sql.ResultSet
import java.util.*

class Nhom3QuocBarChartViewModel : ViewModel() {
    val today = Calendar.getInstance()
    val year_now = today.get(Calendar.YEAR)
    val month_now = today.get(Calendar.MONTH)
    val day_now = today.get(Calendar.DAY_OF_MONTH)

    lateinit var result: ResultSet
    var fromDate =""+year_now+"-"+(month_now +1)+"-"+day_now
    var toDate =""+year_now+"-"+(month_now +1)+"-"+day_now
    var firstAccess : Boolean = false
    lateinit var callP: CallableStatement
    lateinit var lstEx: MutableList<Nhom3QuocItemBarChart>
    fun getData(opt: Int) :MutableList<Nhom3QuocItemBarChart>{
        lstEx = mutableListOf()
        var timeNameColumn="D"
        var totalNameColumn="TotalMoney"
        var kindNameColumn="CateKind"
        var totalMoney=0.toBigDecimal();
        when(opt)
        {
            1 -> callP= Database.instance.connection!!.prepareCall("{call getMoneyEIByDate(?,?,?)}")
            2 -> {
                callP= Database.instance.connection!!.prepareCall("{call getMoneyEIByMonth(?,?,?)}")
                totalNameColumn="Total"
                kindNameColumn="Kind"
            }
            3 -> {
                callP = Database.instance.connection!!.prepareCall("{call getMoneyEIByYear(?,?,?)}")
                timeNameColumn="Y"
            }

        }
        Database.instance.idUserApp?.let { callP.setInt(1, it) }
        callP.setString(2,fromDate)
        callP.setString(3,toDate)
        if (callP.execute()) {
            result = callP.resultSet
            var temp=0
            while (result.next() && !result.isAfterLast) {
                var d = result.getString(timeNameColumn).toString()
                var total = result.getBigDecimal(totalNameColumn)
                var cateKind = result.getBoolean(kindNameColumn)
                if(lstEx.size==0)
                {
                    if(cateKind==false)
                    {
                        lstEx.add(Nhom3QuocItemBarChart(d, 0.toBigDecimal(),total,0.toBigDecimal()))
                        temp=0
                    }else
                    {
                        lstEx.add(Nhom3QuocItemBarChart(d, 0.toBigDecimal(),0.toBigDecimal(),total))
                        lstEx.add(Nhom3QuocItemBarChart("temp",0.toBigDecimal(),0.toBigDecimal(),0.toBigDecimal()))
                        temp=1
                    }
                }else
                {
                    if(cateKind==false)
                    {
                        if(temp==0)
                        {
                            lstEx.add(Nhom3QuocItemBarChart("temp",0.toBigDecimal(),0.toBigDecimal(),0.toBigDecimal()))
                        }
                            lstEx.last().title_bc = d
                            lstEx.last().money_lost = total
                            temp = 0

                    }else
                    {
                        lstEx.last().title_bc=d
                        lstEx.last().money_collect=total
                        lstEx.add(Nhom3QuocItemBarChart("temp",0.toBigDecimal(),0.toBigDecimal(),0.toBigDecimal()))
                        temp=1
                    }
                }
            }
            callP.close()
            try {
                if(lstEx.size!=1) {
                    lstEx.removeLast()
                }
            }catch (e: Exception)
            {
            }
            lstEx.forEach {
                it.total_money=it.money_collect-it.money_lost
                Log.e("An:", it.title_bc)
            }
        }
        return lstEx
    }
}
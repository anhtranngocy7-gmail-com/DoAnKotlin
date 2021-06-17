package com.laptrinhdidong.nhom3.quanlichitieu.Statistical.ReportExpense

import android.util.Log
import androidx.lifecycle.ViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.Model.Database
import java.sql.CallableStatement
import java.sql.ResultSet
import java.util.*

class Nhom3AnReportExpenseViewModel: ViewModel() {
    val today = Calendar.getInstance()
    val year_now = today.get(Calendar.YEAR)
    val month_now = today.get(Calendar.MONTH)
    val day_now = today.get(Calendar.DAY_OF_MONTH)

    var firstAccess : Boolean = false
    lateinit var callP: CallableStatement
    lateinit var outerList : MutableList<Nhom3AnGroupReportExpense>
    lateinit var map : MutableMap<Nhom3AnGroupReportExpense,MutableList<Nhom3AnItemReportExpense>>
    lateinit var result: ResultSet
    var fromDate =""+year_now+"-"+(month_now +1)+"-"+day_now
    var toDate =""+year_now+"-"+(month_now +1)+"-"+day_now
    fun getListEx()
    {
        outerList= mutableListOf()
        map= mutableMapOf()
        callP= Database.instance.connection!!.prepareCall("{call getListExByDate(?,?,?)}")
        Database.instance.idUserApp?.let { callP.setInt(1, it) }
        callP.setString(2,fromDate)
        callP.setString(3,toDate)
        if (callP.execute()) {
            result = callP.resultSet
            while (result.next()&& !result.isAfterLast) {
                var cateName = result.getString("CateName").toString()
                var descript = result.getString("Descript")
                var total = result.getBigDecimal("TotalMoney")
                if(outerList.size==0)
                {
                    outerList.add(Nhom3AnGroupReportExpense(cateName, total))
                    map[outerList.last()] = mutableListOf()
                    map[outerList.last()]?.add(Nhom3AnItemReportExpense(descript,total))
                    outerList.last().total+=total
                }
                else
                {
                    if(map.keys.last().title!=cateName)
                    {
                        outerList.add(Nhom3AnGroupReportExpense(cateName, 0.toBigDecimal()))
                        map[outerList.last()] = mutableListOf()
                    }
                    map[outerList.last()]?.add(Nhom3AnItemReportExpense(descript,total))
                    outerList.last().total+=total
                }
            }
            outerList.forEach {
                Log.e("Outer",it.title)
            }
        }
        callP.close()
    }
}
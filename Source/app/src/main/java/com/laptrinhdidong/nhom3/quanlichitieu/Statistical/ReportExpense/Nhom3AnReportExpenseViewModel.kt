package com.laptrinhdidong.nhom3.quanlichitieu.Statistical.ReportExpense

import android.util.Log
import androidx.lifecycle.ViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.Model.Database
import java.sql.CallableStatement
import java.sql.ResultSet

class Nhom3AnReportExpenseViewModel: ViewModel() {
    var firstAccess : Boolean = false
    lateinit var callP: CallableStatement
    lateinit var outerList : MutableList<Nhom3AnGroupReportExpense>
    lateinit var map : MutableMap<Nhom3AnGroupReportExpense,MutableList<Nhom3AnItemReportExpense>>
    lateinit var result: ResultSet
    var fromDate ="2020-1-1"
    var toDate ="2021-12-31"
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
            while (result.next() && !result.isAfterLast) {
                var cateName = result.getString("CateName").toString()
                var descript = result.getString("Descript")
                var total = result.getBigDecimal("TotalMoney")
                if(outerList.size==0)
                {
                    outerList.add(Nhom3AnGroupReportExpense(cateName, total))
                    map[outerList.last()] = mutableListOf()
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
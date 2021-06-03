package com.laptrinhdidong.nhom3.quanlichitieu.MainApp.TichLuy

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.Model.Account
import com.laptrinhdidong.nhom3.quanlichitieu.Model.Accumulate
import com.laptrinhdidong.nhom3.quanlichitieu.Model.Database
import com.laptrinhdidong.nhom3.quanlichitieu.Object.Nhom3AnItemTichLuy
import java.sql.CallableStatement
import java.sql.ResultSet

class Nhom3AnTichLuyViewModel : ViewModel() {

    fun getData(): MutableList<Accumulate> {
        return lstaccumulate
    }

    lateinit var result: ResultSet
    var lstaccumulate: MutableList<Accumulate> = mutableListOf()
    lateinit var strmoney: String

    init {
        var callP: CallableStatement = Database.instance.connection!!.prepareCall("{call getAccumulate(?)}")
        Database.instance.idUserApp?.let { callP.setInt(1, it) }
        if (callP.execute()) {
            result = callP.resultSet
            while (result.next() && !result.isAfterLast) {
                var id=result.getInt("ID")
                var title = result.getString("Title").toString()
                var targetmoney = result.getBigDecimal("TargetMoney")
                var currentmoney = result.getBigDecimal("CurrentMoney")
                var tmpaccumulate = Accumulate(id,title, targetmoney, currentmoney)
                lstaccumulate.add(tmpaccumulate)
            }
        }
        callP.close()

    }
    //////////////////////
//    var txt = MutableLiveData<String>()
//    var result: ResultSet
//
//    init {
//        txt.value = "Danh mục"
//        result = Database.instance.getData("select * from dbo.DanhMuc")
//    }
//
//    fun getData() {
//        if (Database.instance.connection != null) {
//            if (result.next()) {
//                var str: String = result.getString("TenDM").toString()
//                txt.value = str
//            }
//        } else {
//            txt.value = "null"
//        }
//        txt.postValue(txt.value)
//    }
}
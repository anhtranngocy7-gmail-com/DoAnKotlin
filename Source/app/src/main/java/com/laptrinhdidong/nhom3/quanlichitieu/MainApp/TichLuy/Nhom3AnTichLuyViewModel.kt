package com.laptrinhdidong.nhom3.quanlichitieu.MainApp.TichLuy

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.Model.Database
import com.laptrinhdidong.nhom3.quanlichitieu.Object.Nhom3AnItemTichLuy
import java.sql.ResultSet

class Nhom3AnTichLuyViewModel : ViewModel() {
    var money =  Nhom3DatSavingMoneyCal()
    var time =  Nhom3DatSavingTimeCal()

    fun getData() :MutableList<Nhom3AnItemTichLuy>{

        return mutableListOf(
            Nhom3AnItemTichLuy("Mua nhà",money.predict_desired_toPay(30000000.0, 0.1, 12.0).toLong(),time.predict_desired_toPay(30000000.0, 0.1, 6000000.0).toLong()),
            Nhom3AnItemTichLuy("Mua xe",money.predict_desired_toBuy(30000000.0, 12.0).toLong(),time.predict_desired_toBuy(30000000.0, 12000000.0).toLong())

//            Nhom3AnItemTichLuy("Mua nhà", 30000000,5000000),
//            Nhom3AnItemTichLuy("Mua xe",10000000, 2000000)
        )
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
package com.laptrinhdidong.nhom3.quanlichitieu.MainApp.TichLuy

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.Model.Database
import com.laptrinhdidong.nhom3.quanlichitieu.Object.Nhom3AnItemTichLuy
import java.sql.ResultSet

class Nhom3AnTichLuyViewModel : ViewModel() {

    fun getData() :MutableList<Nhom3AnItemTichLuy>{
        return mutableListOf(
            Nhom3AnItemTichLuy("Mua nhà",100000000,10000000),
            Nhom3AnItemTichLuy("Mua xe",30000000,5000000)
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
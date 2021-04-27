package com.laptrinhdidong.nhom3.quanlichitieu.Test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.Model.Database
import java.sql.ResultSet

class Nhom3AnTestViewModel : ViewModel() {
    var txt = MutableLiveData<String>()
    var result: ResultSet

    init {
        txt.value = "Danh mục"
        result = Database.instance.getData("select * from dbo.DanhMuc")
    }

    fun getData() {
        if (Database.instance.connection != null) {
            if (result.next()) {
                var str: String = result.getString("TenDM").toString()
                txt.value = str
            }
        } else {
            txt.value = "null"
        }
        txt.postValue(txt.value)
    }
}
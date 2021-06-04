package com.laptrinhdidong.nhom3.quanlichitieu

import android.util.Log
import androidx.lifecycle.ViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.Model.Account
import com.laptrinhdidong.nhom3.quanlichitieu.Model.Database
import java.sql.CallableStatement
import java.sql.ResultSet

class Nhom3AnOverviewViewModel: ViewModel() {
     lateinit var result: ResultSet
     lateinit var ac : Account
     lateinit var strmoney:String
    init {
        var callP : CallableStatement = Database.instance.connection!!.prepareCall("{call getAccByID(?)}")
        Database.instance.idUserApp?.let { callP.setInt(1, it) }
        if(callP.execute())
        {
            result=callP.resultSet
            if(result.next())
            {
                var username=result.getString("UserName").toString()
                var accountname=result.getString("FullName").toString()
                var pass=result.getString("Pass").toString()
                var birthday: String? = result.getString("BirthDay")?.toString()
                var money=result.getBigDecimal("CurrentMoney")
                strmoney=money.toString()
                ac= Account(accountname,username,pass,birthday,money)
            }
        }

    }
}
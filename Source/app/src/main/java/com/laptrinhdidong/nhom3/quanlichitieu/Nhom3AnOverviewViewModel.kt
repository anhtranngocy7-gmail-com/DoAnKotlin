package com.laptrinhdidong.nhom3.quanlichitieu

import android.util.Log
import androidx.lifecycle.ViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.Model.Account
import com.laptrinhdidong.nhom3.quanlichitieu.Model.Database
import com.laptrinhdidong.nhom3.quanlichitieu.Model.ExInInfo
import java.math.BigDecimal
import java.sql.CallableStatement
import java.sql.ResultSet

class Nhom3AnOverviewViewModel : ViewModel() {
    var firstAccess=false
    lateinit var result: ResultSet
    var ac: Account= Account("null","null",0.toBigDecimal())

    var lstEiInfo: MutableList<ExInInfo> = mutableListOf(
        ExInInfo(0.toBigDecimal(),0),
        ExInInfo(0.toBigDecimal(),0)
    )
    var spareMoney=0.toBigDecimal()

    var countCompleteAccumulate: String = "Mục tiêu tích lũy hoàn thành: "

    fun getData():String {
        if(!Database.instance.stateConnect)
        {
            //nếu kết nối thất bại thì return cảnh báo
            if(!Database.instance.createConnection())
                return Database.instance.messageFail
        }
        try {
        var callP: CallableStatement =
            Database.instance.connection!!.prepareCall("{call getAccByID(?)}")
        Database.instance.idUserApp?.let { callP.setInt(1, it) }
        if (callP.execute()) {
            result = callP.resultSet
            if (result.next()) {
                var accountname = result.getString("FullName").toString()
                var birthday: String? = result.getString("BirthDay")?.toString()
                var money = result.getBigDecimal("CurrentMoney")
                ac = Account(accountname, birthday, money)
                Database.instance.currentMoney=ac.money
            }
        }
        callP = Database.instance.connection!!.prepareCall("{call countCompleteAccumulate(?)}")
        Database.instance.idUserApp?.let { callP.setInt(1, it) }
        if(callP.execute())
        {
            result=callP.resultSet
            result.next()
            countCompleteAccumulate+=result.getString("C")
            countCompleteAccumulate+="/"
            result.next()
            countCompleteAccumulate+=result.getString("C")
        }
        callP.close()
        getExInMoney(1)
            Database.instance.stateConnect=true
            return Database.instance.messageDone
        }catch (e : Exception)
        {
            Database.instance.stateConnect=false
            return Database.instance.messageFail
        }
    }
    fun getExInMoney(opt: Int) :String{
        if (!Database.instance.stateConnect) {
            //nếu kết nối thất bại thì return cảnh báo
            if (!Database.instance.createConnection())
                return Database.instance.messageFail
        }
        try {
            var callP2: CallableStatement =
                Database.instance.connection!!.prepareCall("{call getMoneyEIByNow(?,?)}")
            Database.instance.idUserApp?.let { callP2.setInt(1, it) }
            callP2.setInt(2, opt)
            if (callP2.execute()) {
                result = callP2.resultSet
                var totalMoney = 0.toBigDecimal()
                var i = 0
                while (result.next()) {
                    if (!result.isAfterLast) {
                        var total = result.getBigDecimal("Total")
                        lstEiInfo[i].money = total
                        totalMoney += total
                    }
                    i++
                }
                lstEiInfo.forEach {
                    it.percent = ((it.money.toFloat() / totalMoney.toFloat()) * 100f).toInt()
                    Log.e("times", it.percent.toString())
                }
                Log.e("A0", lstEiInfo[0].percent.toString())
                Log.e("A1", lstEiInfo[1].percent.toString())
                Log.e("A1", totalMoney.toString())
                spareMoney = lstEiInfo[1].money - lstEiInfo[0].money
            }
            callP2.close()

            Database.instance.stateConnect=true
            return Database.instance.messageDone
        }catch (e : Exception)
        {
            Database.instance.stateConnect=false
            return Database.instance.messageFail
        }

    }
    fun setMoney(money: BigDecimal): String{
        if(!Database.instance.stateConnect)
        {
            //nếu kết nối thất bại thì return cảnh báo
            if(!Database.instance.createConnection())
                return Database.instance.messageFail
        }
        try {
            var callP: CallableStatement =
                Database.instance.connection!!.prepareCall("{call setMoney(?,?)}")
            Database.instance.idUserApp?.let { callP.setInt(1, it) }
            callP.setBigDecimal(2,money)
            callP.execute()
            callP.close()
            Database.instance.currentMoney=money
            Database.instance.stateConnect=true
            return Database.instance.messageDone
        }catch (e : Exception)
        {
            Database.instance.stateConnect=false
            return Database.instance.messageFail
        }
    }
}
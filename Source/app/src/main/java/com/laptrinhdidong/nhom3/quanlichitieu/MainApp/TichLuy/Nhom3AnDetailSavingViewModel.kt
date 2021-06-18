package com.laptrinhdidong.nhom3.quanlichitieu.MainApp.TichLuy

import androidx.lifecycle.ViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.Model.Accumulate
import com.laptrinhdidong.nhom3.quanlichitieu.Model.Database
import java.sql.CallableStatement
import java.sql.ResultSet

class Nhom3AnDetailSavingViewModel : ViewModel() {
    var firstAccess = false
    var tmpaccumulate=Accumulate(1,"null",0.toBigDecimal(),0.toBigDecimal(),false,0f,0.toBigDecimal(),0f)
    lateinit var result: ResultSet
    fun getDataInit(): String{
        if (!Database.instance.stateConnect) {
            //nếu kết nối thất bại thì return cảnh báo
            if (!Database.instance.createConnection())
                return Database.instance.messageFail
        }
        try {
            var callP: CallableStatement =
                Database.instance.connection!!.prepareCall("{call getDetailAccumulate(?)}")
            Database.instance.idAccumulate?.let { callP.setInt(1, it) }
            if (callP.execute()) {
                result = callP.resultSet
                while (result.next() && !result.isAfterLast) {
                    var id = result.getInt("ID")
                    var title = result.getString("Title").toString()
                    var targetmoney = result.getBigDecimal("TargetMoney")
                    var currentmoney = result.getBigDecimal("CurrentMoney")
                    var buyOrPay = result.getBoolean("BuyOrPay")
                    var timeRemain = result.getFloat("TimeAsert")
                    var income = result.getBigDecimal("IncomePerMonth")
                    var percentgrowth = result.getFloat("PercentGrowth")
                    tmpaccumulate = Accumulate(
                        id,
                        title,
                        targetmoney,
                        currentmoney,
                        buyOrPay,
                        timeRemain,
                        income,
                        percentgrowth
                    )
                }
            }
            callP.close()
            Database.instance.stateConnect = true
            return Database.instance.messageDone
        } catch (e: Exception) {
            Database.instance.stateConnect = false
            return Database.instance.messageFail
        }
    }
}
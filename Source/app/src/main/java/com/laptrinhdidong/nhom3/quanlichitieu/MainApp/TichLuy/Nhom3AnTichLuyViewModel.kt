package com.laptrinhdidong.nhom3.quanlichitieu.MainApp.TichLuy

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.Model.Account
import com.laptrinhdidong.nhom3.quanlichitieu.Model.Accumulate
import com.laptrinhdidong.nhom3.quanlichitieu.Model.Database
import com.laptrinhdidong.nhom3.quanlichitieu.Object.Nhom3AnItemTichLuy
import java.math.BigDecimal
import java.sql.CallableStatement
import java.sql.ResultSet

class Nhom3AnTichLuyViewModel : ViewModel() {
    lateinit var result: ResultSet
    var lstaccumulate: MutableList<Accumulate> = mutableListOf()
    lateinit var strmoney: String
    var firstAccess = false
    fun getData(): MutableList<Accumulate> {
        return lstaccumulate
    }

    fun getDataInit(): String {
        if (!Database.instance.stateConnect) {
            //nếu kết nối thất bại thì return cảnh báo
            if (!Database.instance.createConnection())
                return Database.instance.messageFail
        }
        try {
            var callP: CallableStatement =
                Database.instance.connection!!.prepareCall("{call getAccumulate(?)}")
            Database.instance.idUserApp?.let { callP.setInt(1, it) }
            if (callP.execute()) {
                result = callP.resultSet
                lstaccumulate= mutableListOf()
                while (result.next() && !result.isAfterLast) {
                    var id = result.getInt("ID")
                    var title = result.getString("Title").toString()
                    var targetmoney = result.getBigDecimal("TargetMoney")
                    var currentmoney = result.getBigDecimal("CurrentMoney")
                    var buyOrPay = result.getBoolean("BuyOrPay")
                    var timeRemain = result.getFloat("TimeAsert")
                    var income = result.getBigDecimal("IncomePerMonth")
                    var percentgrowth = result.getFloat("PercentGrowth")


                    var tmpaccumulate = Accumulate(
                        id,
                        title,
                        targetmoney,
                        currentmoney,
                        buyOrPay,
                        timeRemain,
                        income,
                        percentgrowth
                    )
                    lstaccumulate.add(tmpaccumulate)
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

    fun deleteAccumulate(id: Int): String {
        if (!Database.instance.stateConnect) {
            //nếu kết nối thất bại thì return cảnh báo
            if (!Database.instance.createConnection())
                return Database.instance.messageFail
        }
        try {
            var callP: CallableStatement =
                Database.instance.connection!!.prepareCall("{call deleteAccumulate(?)}")
            callP.setInt(1, id)
            callP.execute()
            callP.close()
            Database.instance.stateConnect = true
            return Database.instance.messageDone
        } catch (e: Exception) {
            Database.instance.stateConnect = false
            return Database.instance.messageFail
        }
    }
    fun upDateMoney(index :Int,money : BigDecimal) : String
    {
        if (!Database.instance.stateConnect) {
            //nếu kết nối thất bại thì return cảnh báo
            if (!Database.instance.createConnection())
                return Database.instance.messageFail
        }
        try {
            var accumulate = lstaccumulate[index]
            var newTargetMoney = accumulate.targetmoney - (accumulate.currentmoney + money)
            if (newTargetMoney < 0.toBigDecimal()) {
                newTargetMoney = 0.toBigDecimal()
            }
            var savingCal = Nhom3DatSavingTimeCal()
            (savingCal as Nhom3DatSavingTimeCal).Nhom3DatSavingTimeCal(
                newTargetMoney.toDouble(),
                accumulate.percent.toDouble(),
                accumulate.incomPerMonth.toDouble()
            )
            var newTime = 0f
            if (accumulate.buyOrPay) {
                newTime=savingCal.predict_desired_toBuy().toFloat()
            }else
            {
                newTime=savingCal.predict_desired_toPay().toFloat()
            }
            var callP = Database.instance.connection!!.prepareCall("{call updateTimeAsert(?,?,?)}")
            callP.setInt(1,accumulate.id)
            callP.setBigDecimal(2,accumulate.currentmoney+money)
            callP.setFloat(3,newTime)
            callP.execute()
            callP.close()
            Database.instance.stateConnect=true
            return Database.instance.messageDone
        }catch (e : Exception)
        {
            Database.instance.stateConnect=false
            return Database.instance.messageFail
        }
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
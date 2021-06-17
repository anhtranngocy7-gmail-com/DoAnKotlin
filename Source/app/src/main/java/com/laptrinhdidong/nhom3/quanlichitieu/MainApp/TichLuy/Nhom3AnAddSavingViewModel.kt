package com.laptrinhdidong.nhom3.quanlichitieu.MainApp.TichLuy

import android.util.Log
import androidx.lifecycle.ViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.Model.Database
import java.sql.CallableStatement
import java.sql.ResultSet

class Nhom3AnAddSavingViewModel : ViewModel() {
    lateinit var callP: CallableStatement
    lateinit var result: ResultSet
    lateinit var savingCal: Nhom3DatSavingCal
    var nameTarget =""
    var targetMoney=0.toBigDecimal()
    var percent = 0.toDouble()
    var time = 0f
    var incomePerMonth = 0.toBigDecimal()
    fun createAccumulate(optBoP:Boolean, optToM: Boolean) : String
    {
        if(!Database.instance.stateConnect)
        {
            //nếu kết nối thất bại thì return cảnh báo
            if(!Database.instance.createConnection())
                return Database.instance.messageFail
        }
        try {
            if(!optToM)
            {
                savingCal=Nhom3DatSavingTimeCal()
                (savingCal as Nhom3DatSavingTimeCal).Nhom3DatSavingTimeCal(targetMoney.toDouble(),percent.toDouble(),incomePerMonth.toDouble())
                if(optBoP)
                {
                    time=savingCal.predict_desired_toBuy().toFloat()
                    Log.e("raw",savingCal.predict_desired_toBuy().toString())
                }else
                {
                    time=savingCal.predict_desired_toPay().toFloat()
                    Log.e("raw",savingCal.predict_desired_toPay().toString())
                }
            }else
            {
                savingCal=Nhom3DatSavingMoneyCal()
                (savingCal as Nhom3DatSavingMoneyCal).Nhom3DatSavingMoneyCal(targetMoney.toDouble(),percent.toDouble(),time.toDouble())
                if(optBoP)
                {
                    incomePerMonth=savingCal.predict_desired_toBuy().toBigDecimal()
                    Log.e("raw",savingCal.predict_desired_toBuy().toString())
                }else
                {
                    incomePerMonth=savingCal.predict_desired_toPay().toBigDecimal()
                    Log.e("raw",savingCal.predict_desired_toPay().toString())
                }
            }
            Log.e("time",time.toString())
            Log.e("income",incomePerMonth.toString())
            callP = Database.instance.connection!!.prepareCall("{call createAccumulate(?,?,?,?,?,?,?,?)}")
            Database.instance.idUserApp?.let { callP.setInt(1, it) }
            callP.setString(2,nameTarget)
            callP.setBigDecimal(3,targetMoney)
            callP.setBigDecimal(4,0.toBigDecimal())
            callP.setFloat(5,time)
            callP.setBigDecimal(6,incomePerMonth)
            callP.setBoolean(7,optBoP)
            callP.setFloat(8,percent.toFloat())
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
}
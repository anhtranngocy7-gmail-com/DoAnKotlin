package com.laptrinhdidong.nhom3.quanlichitieu.SignIn

import android.util.Log
import androidx.lifecycle.ViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.Account
import com.laptrinhdidong.nhom3.quanlichitieu.Model.Database
import java.lang.Exception
import java.sql.CallableStatement
import java.sql.ResultSet
import java.sql.SQLException


class Nhom3AnhSignInViewModel: ViewModel() {
    var account : Account = Account("", "", "", "")
    lateinit var result: ResultSet
    lateinit var callP: CallableStatement
    fun checkAccount(username: String, pass: String):Byte{
        try {
            callP=Database.instance.connection!!.prepareCall("{call getAccByUserPass(?,?)}")
            callP.setString(1,username)
            callP.setString(2,pass)
            callP.execute()
            result = callP.resultSet
            return if(result.next()) {
                Database.instance.idUserApp=result.getInt("ID")
                callP.close()
                1
            }else {
                callP.close()
                0
            }
        }catch (e: Exception)
        {
            Database.instance.createConnection()
            return 2
        }


    }
    fun checkAccountSocialExit(id: String, name: String):Boolean
    {
        try{
            callP= Database.instance.connection!!.prepareCall("{call getAccByUserPass(?,?)}")
            callP.setString(1,id)
            callP.setString(2,"111111111")
            callP.execute()
            result = callP.resultSet
            if(result.next())
            {
                Database.instance.idUserApp=result.getInt("ID")
                callP.close()
            }else
            {
                callP.close()
                CreateAccount(id,name)
            }
            return true
        }catch (e:Exception)
        {
            Database.instance.createConnection()
            return false
        }
    }
    private fun CreateAccount(id: String, name: String)
    {
        callP = Database.instance.connection!!.prepareCall("{call createAcc(?,?,?)}")
        callP.setString(1,id)
        callP.setString(2,"111111111")
        callP.setString(3,name)
        callP.execute()
        callP.close()
        checkAccountSocialExit(id,name)
    }
}
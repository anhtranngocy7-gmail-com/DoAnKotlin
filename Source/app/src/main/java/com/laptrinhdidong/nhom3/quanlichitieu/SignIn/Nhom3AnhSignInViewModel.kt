package com.laptrinhdidong.nhom3.quanlichitieu.SignIn

import androidx.lifecycle.ViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.Account
import com.laptrinhdidong.nhom3.quanlichitieu.Model.Database
import java.sql.CallableStatement
import java.sql.ResultSet


class Nhom3AnhSignInViewModel: ViewModel() {
    var account : Account = Account("", "", "", "")
    lateinit var result: ResultSet
    lateinit var callP: CallableStatement
    fun checkAccount(username: String, pass: String):Boolean{
        callP = Database.instance.connection!!.prepareCall("{call getAcc(?,?)}")
        callP.setString(1,username)
        callP.setString(2,pass)
        callP.execute()
        result = callP.resultSet
        return if(result.next()) {
            Database.instance.idUserApp=result.getInt("ID")
            callP.close()
            true
        }else {
            false
        }
    }
    fun checkAccountFBExit(idfb: String, name: String)
    {
        callP= Database.instance.connection!!.prepareCall("{call getAccByFB(?)}")
        callP.setString(1,idfb)
        callP.execute()
        result = callP.resultSet
        if(result.next())
        {
            Database.instance.idUserApp=result.getInt("ID")
            callP.close()
            return
        }else
        {
            callP.close()
            CreateAccount(idfb,name)
        }
    }
    private fun CreateAccount(idfb: String, name: String)
    {
        callP = Database.instance.connection!!.prepareCall("{call CreateAcc(?,?,?)}")
        callP.setString(1,idfb)
        callP.setString(2,"111111111")
        callP.setString(3,name)
        callP.execute()
        callP.close()
        checkAccountFBExit(idfb,name)
    }
}
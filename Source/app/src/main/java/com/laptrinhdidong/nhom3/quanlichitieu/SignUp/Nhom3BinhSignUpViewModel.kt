package com.laptrinhdidong.nhom3.quanlichitieu.SignUp

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.Account
import com.laptrinhdidong.nhom3.quanlichitieu.Model.Database
import java.lang.Exception
import java.sql.CallableStatement
import java.sql.ResultSet

class Nhom3BinhSignUpViewModel: ViewModel() {
    var account : Account = Account("","","","")
    lateinit var result: ResultSet
    lateinit var callP: CallableStatement
    fun validateEmail(editemail: String): Boolean{
        var emailPattern = "^[A-Za-z0-9]+[A-Za-z0-9]*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)$"
        Log.e("emailPattern",emailPattern)
        return !(editemail.isEmpty()|| !editemail.matches(Regex(emailPattern)))
    }

    fun validatePassword(editpass: String): Boolean{
        var passwordVal = "(?=.*[0-9])" + "(?=.*[a-z])" + "(?=.*[A-Z])" + "(?=.*[!@#$%^&*()])" + ".{8,}"
        return !(editpass.isEmpty()||!editpass.matches(Regex(passwordVal)))

    }
    fun registerUser(view: View, editpass: String, editemail: String): Boolean {
        return !(!validatePassword(editpass) or !validateEmail(editemail))
    }
    fun checkAccExit():Byte
    {
        try {
            callP= Database.instance.connection!!.prepareCall("{call checkExitByUserName(?)}")
            callP.setString(1,account.email)
            callP.execute()
            result = callP.resultSet
            if(result.next()){
                Log.e("call","success")
                if(result.getInt("C")==1)
                {
                    callP.close()
                    return 0
                }
            }
            Log.e("call","success 2")
            createAcc(account.email,account.password,account.fullname)
            return 1
        }catch (e:Exception)
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
                createAcc(id,"111111111",name)
                checkAccountSocialExit(id,name)
            }
            return true
        }catch (e:Exception)
        {
            Database.instance.createConnection()
            return false
        }
    }
    private fun createAcc(userName:String, pass:String, name:String)
    {
        callP= Database.instance.connection!!.prepareCall("{call createAcc(?,?,?)}")
        callP.setString(1,userName)
        callP.setString(2,pass)
        callP.setString(3,name)
        callP.execute()
        callP.close()
    }
}
package com.laptrinhdidong.nhom3.quanlichitieu.SignUp

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.Account

class Nhom3BinhSignUpViewModel: ViewModel() {
    var account : Account = Account("","","","")
    fun validateEmail(editemail: String): Boolean{
        Log.e("Error Mail","Binh")
        var emailPattern = "[a-zA-z0._-]+@[a-z]+\\.+[a-z]+"
        return !(editemail.isEmpty()|| !editemail.matches(Regex(emailPattern)))
    }

    fun validatePassword(editpass: String): Boolean{
        Log.e("Error pass","Binh")
        var passwordVal = "(?=.*[0-9])" + "(?=.*[a-z])" + "(?=.*[A-Z])" + "(?=.*[!@#$%^&*()])" + ".{8,}"
        return !(editpass.isEmpty()||!editpass.matches(Regex(passwordVal)))

    }

    fun registerUser(view: View, editpass: String, editemail: String): Boolean {
        Log.e("Error pass, mail","Binh")
        return !(!validatePassword(editpass) or !validateEmail(editemail))

    }
}
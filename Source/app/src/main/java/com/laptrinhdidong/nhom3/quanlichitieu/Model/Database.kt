package com.laptrinhdidong.nhom3.quanlichitieu.Model

import android.util.Log
import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

class Database private constructor() {
    val ip: String = "192.168.1.226"
    val port: String = "1433"
    val databaseName: String = "QuanLyChiTieu"
    val classes: String = "net.sourceforge.jtds.jdbc.Driver"
    val username: String = "an321"
    val password: String = "an321"
    val url: String = "jdbc:jtds:sqlserver://$ip:$port/$databaseName"
    var connection: Connection? = null
    lateinit var statement: Statement
    lateinit var result: ResultSet

    init {
        try {
            Class.forName(classes)
            connection = DriverManager.getConnection(url, username, password)
        } catch (e: Exception) {
            Log.d("connect", e.message.toString())
        }

    }

    companion object {
        val instance = Database()

    }

    //Lấy dữ liệu từ database server qua từng câu query sqlStament
    fun getData(sqlStament: String): ResultSet {
        statement = connection?.createStatement()!!
        return statement.executeQuery(sqlStament)
    }

    //Update dữ liệu cho Database, nếu thành công update ở sever thì trả về true
    fun updateData(sqlStament: String): Boolean {
        statement = connection?.createStatement()!!
        return statement.executeUpdate(sqlStament) > 0
    }
}
package com.laptrinhdidong.nhom3.quanlichitieu.Model

import android.util.Log
import com.laptrinhdidong.nhom3.quanlichitieu.Account
import java.lang.Exception
import java.sql.*

class Database private constructor() {
    val ip: String = "192.168.0.108"
    val port: String = "1433"
    val databaseName: String = "KotlinProject"
    val classes: String = "net.sourceforge.jtds.jdbc.Driver"
    val username: String = "an321"
    val password: String = "an321"
    val url: String = "jdbc:jtds:sqlserver://$ip:$port/$databaseName"
    var connection: Connection? = null
    var stateConnect = true
    var idUserApp: Int? = 1
    lateinit var statement: Statement
    lateinit var result: ResultSet

    init {
        createConnection()
    }

    companion object {
        val instance = Database()
    }

    fun createConnection(): Boolean {
        return try {
            Class.forName(classes)
            connection = DriverManager.getConnection(url, username, password)
            Log.e("connect", "success")
            true
        } catch (e: Exception) {
            Log.e("connect", e.message.toString())

            false
        }
    }

    //Lấy dữ liệu từ database server qua từng câu query sqlStament
    fun getData(sqlStament: String): ResultSet {
        statement = connection?.createStatement()!!
        var rsSet = statement.executeQuery(sqlStament)
        statement.close(); //đóng statment sau khi query
        return rsSet
    }

    //Update dữ liệu cho Database, nếu thành công update ở sever thì trả về true
    fun updateData(sqlStament: String): Boolean {
        statement = connection?.createStatement()!!
        return statement.executeUpdate(sqlStament) > 0
    }
}
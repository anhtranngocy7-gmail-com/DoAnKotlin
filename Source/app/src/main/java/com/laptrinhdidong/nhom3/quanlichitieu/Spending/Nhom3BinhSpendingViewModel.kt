package com.laptrinhdidong.nhom3.quanlichitieu.Spending

import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.Account
<<<<<<< HEAD
import com.laptrinhdidong.nhom3.quanlichitieu.Model.Database
=======
>>>>>>> b4044b6bda95259a026df1f696ab9e4b0e333eb1
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.SpendingItem
import java.sql.CallableStatement
import java.sql.ResultSet
import java.util.Date
import java.util.Calendar
import java.text.SimpleDateFormat
class Nhom3BinhSpendingViewModel: ViewModel() {
    lateinit var callP: CallableStatement
    lateinit var result: ResultSet
    val messageFail ="Không có kết nối, kiểm tra lại kết nối mạng!"
    val messageDone ="Ghi nhận thành công"
    lateinit var mapCategory: MutableMap<String, Int>
    var calDefault = Calendar.getInstance()
    var dateDefault = calDefault.time
    var formatDate=SimpleDateFormat("YYYY-MM-dd");
    var spendingItem : SpendingItem = SpendingItem("","","",true,"")
<<<<<<< HEAD
    fun GetCategory() : MutableMap<String, Int>
    {
        mapCategory = mutableMapOf()
        if(!Database.instance.stateConnect)
        {
            if(Database.instance.createConnection())
                return mapCategory
        }
        callP = Database.instance.connection!!.prepareCall("{call getCategory()}")
        callP.execute()
        result = callP.resultSet
        while(result.next()) {
            mapCategory[result.getString("CateName")] = result.getInt("ID")
        }
        callP.close()
        return mapCategory
    }
    fun CreateExOrIn() : String
    {
        //kiểm tra kết nối, nếu mất kết nối thì kết nối lại
        if(!Database.instance.stateConnect)
        {
            //nếu kết nối thất bại thì return cảnh báo
            if(!Database.instance.createConnection())
                return messageFail
        }
        try {
            callP = Database.instance.connection!!.prepareCall("{call createExOrIn(?,?,?,?,?)}")
            callP.setInt(1,Database.instance.idUserApp!!)
            if(spendingItem.mode)
            {
                callP.setInt(2,2)
            }
            callP.setBigDecimal(3,spendingItem.money.toBigDecimal())
            callP.setString(4,spendingItem.description)
            callP.setString(5,spendingItem.date)
            callP.execute()
            callP.close()
            Database.instance.stateConnect=true
            return messageDone
        }catch (e : Exception)
        {
            Database.instance.stateConnect=false
            return messageFail
        }
    }
}
=======

    fun validateAddAction(editAddAction: String): Boolean{
        return editAddAction.isNotEmpty()
    }
    fun append(arr: Array<String?>, element: String): Array<String?> {
        val array = arrayOfNulls<String>(arr.size + 1)
        System.arraycopy(arr, 0, array, 0, arr.size)
        array[arr.size] = element
        return array
    }
    var spending_new = arrayOf<String?>("Ăn uống", "Dịch vụ", "Di chuyển",
        "Sức khỏe", "Giải trí", "Mua sắm","Thêm mục khác")
    var spendingRem  = arrayOf<String?>()

}

>>>>>>> b4044b6bda95259a026df1f696ab9e4b0e333eb1

package com.laptrinhdidong.nhom3.quanlichitieu.Spending

import androidx.lifecycle.ViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.Model.CateInfo

import com.laptrinhdidong.nhom3.quanlichitieu.Model.Database

import com.laptrinhdidong.nhom3.quanlichitieu.SpendingItem
import java.sql.CallableStatement
import java.sql.ResultSet
import java.util.Calendar
import java.text.SimpleDateFormat

class Nhom3BinhSpendingViewModel : ViewModel() {
    lateinit var callP: CallableStatement
    lateinit var result: ResultSet
    var cateRecommend = "null"
    var lstCategory: MutableList<MutableList<CateInfo>> = mutableListOf(
        mutableListOf(),
        mutableListOf()
    )
    var firstAccess = false
    var spending_new = arrayOf<String?>()
    var spendingRem = arrayOf<String?>()
    var calDefault = Calendar.getInstance()
    var dateDefault = calDefault.time
    var formatDate = SimpleDateFormat("YYYY-MM-dd");
    var spendingItem: SpendingItem = SpendingItem("", "", "", false, "")

    fun GetCategory(opt: Boolean): String {
        if (!Database.instance.stateConnect) {
            //nếu kết nối thất bại thì return cảnh báo
            if (!Database.instance.createConnection())
                return Database.instance.messageFail
        }
        try {
            lstCategory[0] = mutableListOf()
            lstCategory[1] = mutableListOf()
            callP = Database.instance.connection!!.prepareCall("{call getCategory(?)}")
            Database.instance.idUserApp?.let { callP.setInt(1, it) }
            callP.execute()
            result = callP.resultSet
            while (result.next()) {
                var kind = result.getBoolean("CateKind")
                var idCate = result.getInt("ID")
                var cateName = result.getString("CateName")
                var idUser = result.getInt("IdUser")
                if (kind == false) {
                    lstCategory[0].add(CateInfo(idCate, cateName, idUser))
                } else {
                    lstCategory[1].add(CateInfo(idCate, cateName, idUser))
                }
            }
            spending_new = arrayOf()
            var temp=0
            if(opt)
            {
                temp=1
            }else
            {
                temp=0
            }
            lstCategory[temp].forEach {
                spending_new = append(spending_new, it.cateName)
            }
            spending_new = append(spending_new, "Thêm mục khác")

            callP.close()
            Database.instance.stateConnect = true
            return Database.instance.messageDone
        } catch (e: Exception) {
            Database.instance.stateConnect = false
            return Database.instance.messageFail
        }
    }

    fun CreateExOrIn(): String {
        //kiểm tra kết nối, nếu mất kết nối thì kết nối lại
        if (!Database.instance.stateConnect) {
            //nếu kết nối thất bại thì return cảnh báo
            if (!Database.instance.createConnection())
                return Database.instance.messageFail
        }
        try {
            callP = Database.instance.connection!!.prepareCall("{call createExOrIn(?,?,?,?,?)}")
            Database.instance.idUserApp?.let { callP.setInt(1, it) }
            var idCateTemp = 0
            if (!spendingItem.mode) {
                lstCategory[0].forEach {
                    if (it.cateName == spendingItem.area) {
                        idCateTemp = it.id
                        return@forEach
                    }
                }
            } else {
                lstCategory[1].forEach {
                    if (it.cateName == spendingItem.area) {
                        idCateTemp = it.id
                        return@forEach
                    }
                }
            }
            callP.setInt(2,idCateTemp)
            callP.setBigDecimal(3, spendingItem.money.toBigDecimal())
            callP.setString(4, spendingItem.description)
            callP.setString(5, spendingItem.date)
            callP.execute()
            callP.close()
            Database.instance.stateConnect = true
            return Database.instance.messageDone
        } catch (e: Exception) {
            Database.instance.stateConnect = false
            return Database.instance.messageFail
        }
    }

    fun CreateCate(cateName: String) : String
    {
        if (!Database.instance.stateConnect) {
            //nếu kết nối thất bại thì return cảnh báo
            if (!Database.instance.createConnection())
                return Database.instance.messageFail
        }
        try{
            callP = Database.instance.connection!!.prepareCall("{call createCate(?,?,?)}")
            Database.instance.idUserApp?.let { callP.setInt(1, it) }
            callP.setString(2,cateName)
            callP.setBoolean(3,spendingItem.mode)
            callP.execute()
            callP.close()
            Database.instance.stateConnect = true
            return Database.instance.messageDone
        } catch (e: Exception) {
            Database.instance.stateConnect = false
            return Database.instance.messageFail
        }
    }
    fun DeleteCate(cateName: String): String {
        if (!Database.instance.stateConnect) {
            //nếu kết nối thất bại thì return cảnh báo
            if (!Database.instance.createConnection())
                return Database.instance.messageFail
        }
        try {
            var idCateTemp=0
            if (!spendingItem.mode) {
                lstCategory[0].forEach {
                    if (it.cateName == cateName) {
                        idCateTemp = it.id
                        return@forEach
                    }
                }
            } else {
                lstCategory[1].forEach {
                    if (it.cateName == cateName) {
                        idCateTemp = it.id
                        return@forEach
                    }
                }
            }
            callP = Database.instance.connection!!.prepareCall("{call deleteCate(?)}")
            callP.setInt(1, idCateTemp)
            callP.execute()
            callP.close()
            Database.instance.stateConnect = true
            return Database.instance.messageDone
        } catch (e: Exception) {
            Database.instance.stateConnect = false
            return Database.instance.messageFail
        }
    }

    fun getRecommend(keyword: String): String {
        if (!Database.instance.stateConnect) {
            //nếu kết nối thất bại thì return cảnh báo
            if (!Database.instance.createConnection())
                return Database.instance.messageFail
        }
        try {
            callP = Database.instance.connection!!.prepareCall("{call getCateByKeyword(?,?)}")
            Database.instance.idUserApp?.let { callP.setInt(1, it) }
            callP.setString(2, keyword)
            callP.execute()
            result = callP.resultSet
            while (result.next()) {
                cateRecommend = result.getString("CateName")
            }
            callP.close()
            Database.instance.stateConnect = true
            return Database.instance.messageDone
        } catch (e: Exception) {
            Database.instance.stateConnect = false
            return Database.instance.messageFail
        }
    }

    fun validateAddAction(editAddAction: String): Boolean {
        return editAddAction.isNotEmpty()
    }

    fun append(arr: Array<String?>, element: String): Array<String?> {
        val array = arrayOfNulls<String>(arr.size + 1)
        System.arraycopy(arr, 0, array, 0, arr.size)
        array[arr.size] = element
        return array
    }

}


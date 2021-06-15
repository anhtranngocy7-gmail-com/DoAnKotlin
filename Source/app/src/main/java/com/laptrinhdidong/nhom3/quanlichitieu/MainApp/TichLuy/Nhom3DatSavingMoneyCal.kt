package com.laptrinhdidong.nhom3.quanlichitieu.MainApp.TichLuy

class Nhom3DatSavingMoneyCal:Nhom3DatSavingCal(){

    override fun Standardize(variable: Double): Long {
        var money_standard: Long = 0


        money_standard = Math.round(variable)
        money_standard = money_standard - money_standard % 1000

        return money_standard

        TODO("Not yet implemented")
    }

    override fun predict_desired_toBuy(target: Double, variable_desired: Double): Double {
        var money_f: Double = 0.0

        money_f = target / variable_desired

        money_f = Standardize(money_f).toDouble()
        return money_f

        TODO("Not yet implemented")
    }

    override fun predict_desired_toPay(target: Double, percent_growth: Double, variable_desired: Double): Double {
        var money_f: Double = 0.0

        if (percent_growth == 0.0){
            money_f = target / variable_desired
        }
        else{
            var temp: Double = Math.pow(1 + percent_growth, variable_desired.toDouble())
            money_f = target / temp

        }

        money_f = Standardize(money_f).toDouble()
        return money_f
        TODO("Not yet implemented")
    }

//    override fun daily_Expense(): Double {
//        TODO("Not yet implemented")
//
//        return 0.0
//    }
//
//    override fun toText(): String {
//        TODO("Not yet implemented")
//
//        return "0"
//    }
}
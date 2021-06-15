package com.laptrinhdidong.nhom3.quanlichitieu.MainApp.TichLuy

class Nhom3DatSavingTimeCal:Nhom3DatSavingCal() {
    override fun Standardize(variable: Double): Long {
        var time_standard_f: Double = 0.0
        var time_standard_i: Long = 0

        time_standard_f = (variable*100.0).toInt() / 100.0
        time_standard_i = time_standard_f.toLong()

        if( time_standard_f >  time_standard_i )  time_standard_i++

        return time_standard_i
        
        TODO("Not yet implemented")
        
    }

    override fun predict_desired_toBuy(target: Double, variable_desired: Double): Double {
        var time_f: Double = 0.0

        time_f = target / variable_desired

        time_f = Standardize(time_f).toDouble()

        return time_f
        TODO("Not yet implemented")
    }

    override fun predict_desired_toPay(target: Double, percent_growth: Double, variable_desired: Double): Double {
        var time_f: Double = 0.0

        time_f = Math.log(target / variable_desired) / Math.log(1 + percent_growth)

        time_f = Standardize(time_f).toDouble()
        return time_f
        TODO("Not yet implemented")

    }
//
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
package com.laptrinhdidong.nhom3.quanlichitieu.MainApp.TichLuy

class Nhom3DatSavingTimeCal:Nhom3DatSavingCal() {

    fun Nhom3DatSavingTimeCal(target: Double, percent_growth: Double, variable_desired: Double){
        this.Target = target
        this.Percent_growth = percent_growth
        this.Money_desired = variable_desired
    }

    override fun Standardize(): Long {
        var time_standard_f: Double = 0.0
        var time_standard_i: Long = 0

        time_standard_f = (this.Time_desired*100.0).toInt() / 100.0
        time_standard_i = time_standard_f.toLong()

        if( time_standard_f >  time_standard_i )  time_standard_i++

        return time_standard_i
        
        TODO("Not yet implemented")
        
    }

    override fun predict_desired_toBuy(): Double {

        this.Time_desired = this.Target / this.Money_desired

        return Standardize().toDouble()
        TODO("Not yet implemented")
    }

    override fun predict_desired_toPay(): Double {

        this.Time_desired = Math.log(this.Target / this.Money_desired) / Math.log(1 + this.Percent_growth)

        return Standardize().toDouble()
        TODO("Not yet implemented")

    }

    override fun daily_Expense(): Double {

        return 0.0
        TODO("Not yet implemented")
    }

    override fun toText(): String {
        var str: String = this.Time_desired.toString()

        if(this.Time_desired == 1.0 && this.Time_desired > 0.0) str = str + " month"
        else str = str + " months"

        return str
        TODO("Not yet implemented")


    }
}
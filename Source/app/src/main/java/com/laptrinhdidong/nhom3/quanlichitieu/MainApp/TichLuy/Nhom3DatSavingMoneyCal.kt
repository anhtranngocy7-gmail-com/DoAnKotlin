package com.laptrinhdidong.nhom3.quanlichitieu.MainApp.TichLuy

class Nhom3DatSavingMoneyCal:Nhom3DatSavingCal(){

    fun Nhom3DatSavingMoneyCal(target: Double, percent_growth: Double, variable_desired: Double){
        this.Target = target
        this.Percent_growth = Percent_growth
        this.Time_desired = variable_desired
    }

    override fun Standardize(): Long {
        var money_standard: Long = 0


        money_standard = Math.round(this.Money_desired)
        money_standard = money_standard - money_standard % 1000

        return money_standard

        TODO("Not yet implemented")
    }

    override fun predict_desired_toBuy(): Double {

        this.Money_desired = this.Target / this.Time_desired

        this.Money_desired = Standardize().toDouble()
        return this.Money_desired

        TODO("Not yet implemented")
    }

    override fun predict_desired_toPay(): Double {

        if (this.Percent_growth == 0.0){
            this.Money_desired= this.Target / this.Time_desired
        }
        else{
            var temp: Double = Math.pow(1 + this.Percent_growth, this.Time_desired)
            this.Money_desired = this.Target / temp

        }

        this.Money_desired = Standardize().toDouble()
        return Money_desired
        TODO("Not yet implemented")
    }

    override fun daily_Expense(): Double {
        return 0.0
        TODO("Not yet implemented")


    }

    override fun toText(): String {
        var str: String = ""
        var temp: Long = this.Money_desired.toLong()
        var count: Int = 0
        var arr = arrayOf<Int>()

        while(temp != 0.toLong()) {
            temp = temp / 1000
            arr.set(count++, arr.get(count))
            arr.set(count,  (temp % 1000).toInt())

            }
        for (i in 0..arr.size){
            when(i){
                1 -> {
                    str = str + arr.get(i).toString() + " billion"
                }
                2 -> {
                    str = str + arr.get(i).toString() + " million"
                }
                3 -> {
                    str = str + arr.get(i).toString() + " thousand"
                }
            }

        }



        return str
        TODO("Not yet implemented")


    }
}
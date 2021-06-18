package com.laptrinhdidong.nhom3.quanlichitieu.MainApp.TichLuy

open abstract class Nhom3DatSavingCal {

    protected var Target: Double = 0.0
    protected var Percent_growth: Double = 0.0
    protected var Time_desired: Double = 0.0
    protected var Money_desired: Double = 0.0


    public abstract fun Standardize(): Long
    public abstract fun predict_desired_toBuy(): Double
    public abstract fun predict_desired_toPay() : Double
    public abstract fun daily_Expense(): Double
    public abstract fun toText(): String
}
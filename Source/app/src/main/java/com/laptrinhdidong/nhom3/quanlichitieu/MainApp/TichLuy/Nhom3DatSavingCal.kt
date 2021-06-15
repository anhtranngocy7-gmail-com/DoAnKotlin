package com.laptrinhdidong.nhom3.quanlichitieu.MainApp.TichLuy

open abstract class Nhom3DatSavingCal {

    public abstract fun Standardize(variable: Double): Long
    public abstract fun predict_desired_toBuy(target: Double, variable_desired: Double): Double
    public abstract fun predict_desired_toPay(target: Double, percent_growth: Double, variable_desired: Double) : Double
//    public abstract fun daily_Expense(): Double
//    public abstract fun toText(): String
}
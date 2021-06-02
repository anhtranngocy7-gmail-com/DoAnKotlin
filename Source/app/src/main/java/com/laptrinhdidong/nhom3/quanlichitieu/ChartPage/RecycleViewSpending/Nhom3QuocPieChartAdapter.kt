package com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending

import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.laptrinhdidong.nhom3.quanlichitieu.Object.Nhom3QuocItemPieChart
import com.laptrinhdidong.nhom3.quanlichitieu.R

class Nhom3QuocPieChartAdapter : RecyclerView.Adapter<Nhom3QuocPieChartAdapter.ViewHolder>() {

    var data: MutableList<Nhom3QuocItemPieChart> = mutableListOf()
    var data_color: MutableList<ColorStateList?> = mutableListOf(
        ColorStateList.valueOf(Color.rgb(239,83,98)),
        ColorStateList.valueOf(Color.rgb(255,255,102)),
        ColorStateList.valueOf(Color.rgb(3,218,197)),
        ColorStateList.valueOf(Color.rgb(187,134,252)),
        ColorStateList.valueOf(Color.rgb(55,0,179)),
        ColorStateList.valueOf(Color.rgb(0,221,0)),

    )
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        var view = layoutInflater.inflate(R.layout.nhom3_quoc_item_piechart,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        var item = this.data[position]
        holder.title.text = item.title
        holder.spending_money.text = item.spendingMoney.toString()+ "đ"
        holder.percent_money.text = "("+item.persent.toString()+ "%"+ ")"
        holder.progress.progress = item.persent.toInt()
        holder.progress.progressTintList = this.data_color[position]

    }

    override fun getItemCount(): Int {
     return data.size
    }

    class ViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title : TextView = itemView.findViewById(R.id.tvTittle)
        val spending_money : TextView = itemView.findViewById(R.id.tvMoney)
        val percent_money : TextView = itemView.findViewById(R.id.tvPercent)
        val progress : ProgressBar = itemView.findViewById(R.id.progressBarCT)
    }
}
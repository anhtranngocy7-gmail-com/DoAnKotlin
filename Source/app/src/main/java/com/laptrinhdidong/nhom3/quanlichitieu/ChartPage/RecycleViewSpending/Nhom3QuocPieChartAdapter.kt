package com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending

import android.content.res.ColorStateList
import android.graphics.Color
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
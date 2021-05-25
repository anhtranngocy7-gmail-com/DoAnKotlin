package com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.laptrinhdidong.nhom3.quanlichitieu.Object.Nhom3QuocItemBieudotron
import com.laptrinhdidong.nhom3.quanlichitieu.R

class Nhom3QuocBieudotronAdapter : RecyclerView.Adapter<Nhom3QuocBieudotronAdapter.ViewHolder>() {

    var data: MutableList<Nhom3QuocItemBieudotron> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        var view = layoutInflater.inflate(R.layout.nhom3_quoc_item_bieudotron,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        var item = this.data[position]
        holder.title.text = item.title
        holder.spending_money.text = item.spendingMoney.toString()+ "đ"
        holder.percent_money.text = item.persent.toString()+ "%"
    }

    override fun getItemCount(): Int {
     return data.size
    }

    class ViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title : TextView = itemView.findViewById(R.id.tvTittle)
        val spending_money : TextView = itemView.findViewById(R.id.tvMoney)
        val percent_money : TextView = itemView.findViewById(R.id.tvPercent)


    }
}
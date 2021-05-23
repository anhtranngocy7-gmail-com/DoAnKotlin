package com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.laptrinhdidong.nhom3.quanlichitieu.Object.Nhom3QuocItemBieudocot
import com.laptrinhdidong.nhom3.quanlichitieu.R

class Nhom3QuocBieudocotAdapter : RecyclerView.Adapter<Nhom3QuocBieudocotAdapter.ViewHolder>() {

    var data: MutableList<Nhom3QuocItemBieudocot> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        var view = layoutInflater.inflate(R.layout.nhom3_quoc_item_bieudocot,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        var item = this.data[position]
        holder.title_BC.text = item.title_bc
        holder.collect_money.text = item.money_collect.toString()
        holder.lost_money.text = item.money_lost.toString()
        holder.total_money.text= item.total_money.toString() + "đ"
    }

    override fun getItemCount(): Int {
     return data.size
    }

    class ViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title_BC : TextView = itemView.findViewById(R.id.tvTittle)
        val collect_money : TextView = itemView.findViewById(R.id.tvMoneyColect)
        val lost_money : TextView = itemView.findViewById(R.id.tvMoneyLost)
        val total_money : TextView = itemView.findViewById(R.id.tvMoneyTotal)


    }
}
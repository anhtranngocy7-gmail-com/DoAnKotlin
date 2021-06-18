package com.laptrinhdidong.nhom3.quanlichitieu.Statistical.ReportExpense

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.laptrinhdidong.nhom3.quanlichitieu.R

class Nhom3AnInnerAdapter : RecyclerView.Adapter<Nhom3AnInnerAdapter.ViewHolder>()  {
    var lstIn: MutableList<Nhom3AnItemReportExpense> = mutableListOf()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        var view = layoutInflater.inflate(R.layout.nhom3_an_itemview_reportexpense,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text=lstIn[position].title
        holder.total.text=lstIn[position].money.toString()+" vnd"
    }

    override fun getItemCount(): Int {
        return lstIn.size
    }
    class ViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title : TextView = itemView.findViewById(R.id.tv_item_re_title)
        val total : TextView = itemView.findViewById(R.id.tv_item_re_money)
    }
}
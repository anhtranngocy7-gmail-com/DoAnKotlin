package com.laptrinhdidong.nhom3.quanlichitieu.Statistical.ReportExpense

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.laptrinhdidong.nhom3.quanlichitieu.R

class Nhom3AnOuterAdapter(context:Context): RecyclerView.Adapter<Nhom3AnOuterAdapter.ViewHolder>()  {
    var context = context
    var lstOut: MutableList<Nhom3AnGroupReportExpense> = mutableListOf()
    var map : MutableMap<Nhom3AnGroupReportExpense,MutableList<Nhom3AnItemReportExpense>> = mutableMapOf()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        var view = layoutInflater.inflate(R.layout.nhom3_an_itemview_group_reportexpense,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text=lstOut[position].title
        holder.total.text=lstOut[position].total.toString()+" vnd"
        holder.icon.setImageResource(R.drawable.iconsavemoney)
        val adapInner : Nhom3AnInnerAdapter = Nhom3AnInnerAdapter()
        holder.rcv.layoutManager=LinearLayoutManager(context)
        adapInner.lstIn=map[lstOut[position]]!!
        holder.rcv.adapter=adapInner
        holder.cb.setOnClickListener(View.OnClickListener {
            holder.rcv.visibility=if(holder.rcv.isVisible){View.GONE}else{View.VISIBLE}
        })

    }

    override fun getItemCount(): Int {
        return lstOut.size
    }
    class ViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cb: CheckBox=itemView.findViewById(R.id.cb_arrow)
        val title : TextView = itemView.findViewById(R.id.tv_group_title)
        val total : TextView = itemView.findViewById(R.id.tv_group_total)
        val icon: ImageView =itemView.findViewById(R.id.imv_typeEx)
        val rcv: RecyclerView = itemView.findViewById(R.id.rcv_inner)
    }
}
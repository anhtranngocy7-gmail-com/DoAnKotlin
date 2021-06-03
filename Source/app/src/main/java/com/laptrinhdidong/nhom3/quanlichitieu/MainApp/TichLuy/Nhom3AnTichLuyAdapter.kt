package com.laptrinhdidong.nhom3.quanlichitieu.MainApp.TichLuy

import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.laptrinhdidong.nhom3.quanlichitieu.Model.Accumulate
import com.laptrinhdidong.nhom3.quanlichitieu.Object.Nhom3AnItemTichLuy
import com.laptrinhdidong.nhom3.quanlichitieu.R

class Nhom3AnTichLuyAdapter : RecyclerView.Adapter<Nhom3AnTichLuyAdapter.ViewHolder>() {
    var data: MutableList<Accumulate> = mutableListOf()
    var data_color: MutableList<ColorStateList?> = mutableListOf(
            ColorStateList.valueOf(Color.rgb(10,0,10)),
            ColorStateList.valueOf(Color.rgb(0,10,10)),
            ColorStateList.valueOf(Color.rgb(10,10,0))
        )
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        var view = layoutInflater.inflate(R.layout.nhom3_an_itemview_tichluy,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = this.data[position]
        holder.title.text=item.title
        holder.target.text=item.targetmoney.toString() +" đ"
        holder.current.text=item.currentmoney.toString()+ "đ"
        holder.progress.progress=((item.currentmoney.toInt()/item.targetmoney.toInt())*100)
        holder.progress.progressTintList= ColorStateList.valueOf(Color.rgb(10,10,10))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    //Create ViewHolder
    class ViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title : TextView = itemView.findViewById(R.id.tv_title)
        val target : TextView = itemView.findViewById(R.id.tv_targetmoney)
        val progress: ProgressBar=itemView.findViewById(R.id.progressBar)
        val current : TextView = itemView.findViewById(R.id.tv_currentmoney)
    }
}
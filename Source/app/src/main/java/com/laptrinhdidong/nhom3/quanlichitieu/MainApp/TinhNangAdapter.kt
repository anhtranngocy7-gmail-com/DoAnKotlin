package com.laptrinhdidong.nhom3.quanlichitieu.MainApp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.laptrinhdidong.nhom3.quanlichitieu.R

class TinhNangAdapter() : RecyclerView.Adapter<TinhNangAdapter.ViewHolder>() {
    var data: List<ItemFeature> = listOf(
        ItemFeature(R.drawable.icon_feature_zoom_money, "Chi tiêu"),
        ItemFeature(R.drawable.icon_feature_bank, "Tích lũy"),
        ItemFeature(R.drawable.icon_feature_report, "Thống kê chi tiêu"),
        ItemFeature(R.drawable.icon_feature_chartpie, "Phân tích chi tiêu"),
        ItemFeature(R.drawable.icon_feature_chartcolumn, "Tình hình thu chi")
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        var view = layoutInflater.inflate(R.layout.itemview_tinhnang,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = data[position]
        holder.image.setImageResource(item.image)
        holder.tvName.text = item.description
    }

    override fun getItemCount(): Int {
        return data.size
    }

    //Create ViewHolder
    class ViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById<ImageView>(R.id.imageViewFeature)
        val tvName: TextView = itemView.findViewById<TextView>(R.id.tvName)
    }
}
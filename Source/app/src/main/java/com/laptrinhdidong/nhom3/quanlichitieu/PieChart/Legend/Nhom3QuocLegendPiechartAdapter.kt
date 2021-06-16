package com.laptrinhdidong.nhom3.quanlichitieu.PieChart.Legend

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending.Nhom3QuocPieChartAdapter
import com.laptrinhdidong.nhom3.quanlichitieu.Object.Nhom3QuocItemLegendPieChart
import com.laptrinhdidong.nhom3.quanlichitieu.Object.Nhom3QuocItemPieChart
import com.laptrinhdidong.nhom3.quanlichitieu.R


class Nhom3QuocLegendPiechartAdapter : RecyclerView.Adapter<Nhom3QuocLegendPiechartAdapter.ViewHolder>() {
    var data: MutableList<Nhom3QuocItemPieChart> = mutableListOf()
    var data_color: MutableList<Int> = mutableListOf(
        R.color.red,
        R.color.yellow,
        R.color.teal_200,
        R.color.purple_200,
        R.color.purple_700,
        R.color.greeny,
    )
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Nhom3QuocLegendPiechartAdapter.ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        var view = layoutInflater.inflate(R.layout.nhom3_quoc_itemlegend_piechart,parent,false)
        return Nhom3QuocLegendPiechartAdapter.ViewHolder(view)

    }

    override fun onBindViewHolder(holder: Nhom3QuocLegendPiechartAdapter.ViewHolder, position: Int) {
        var item = this.data[position]
        holder.title_legend.text = item.title
        holder.image_legend.setBackgroundResource(data_color[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
    class ViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView){
        val image_legend : ImageView = itemView.findViewById(R.id.imgLegend)
        val title_legend : TextView = itemView.findViewById(R.id.tvTitleLegend)
    }
}
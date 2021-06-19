package com.laptrinhdidong.nhom3.quanlichitieu.MainApp.TichLuy

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.laptrinhdidong.nhom3.quanlichitieu.Model.Accumulate
import com.laptrinhdidong.nhom3.quanlichitieu.OnItemClickListener
import com.laptrinhdidong.nhom3.quanlichitieu.R

class Nhom3AnTichLuyAdapter(listener: OnItemClickListener) : RecyclerView.Adapter<Nhom3AnTichLuyAdapter.ViewHolder>() {
    private var listener : OnItemClickListener = listener
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
        holder.target.text=item.targetmoney.toString() +" vnd"
        holder.current.text=item.currentmoney.toString()+ "vnd"
        var percentTmp=((item.currentmoney.toFloat()/item.targetmoney.toFloat())*100f).toInt()
        if(percentTmp>100)
        {
            percentTmp=100
        }
        holder.progress.progress=percentTmp
        holder.progress.progressTintList= ColorStateList.valueOf(Color.rgb(0,239,0))
        holder.viewMain.setOnClickListener {
            listener.onItemClick(item)
        }
        holder.tvDelete.setOnClickListener {
            listener.onClickDelete(item.id)
            holder.linearTmp.visibility=View.GONE
        }
        holder.tvThemTien.setOnClickListener {
            listener.onClickAddMoney(position)
            holder.linearTmp.visibility=View.GONE
        }
        holder.ivTmp.setOnClickListener {
            holder.linearTmp.visibility=if(holder.linearTmp.isVisible){View.GONE}else{View.VISIBLE }
        }

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
        val ivTmp : ImageView = itemView.findViewById(R.id.iv_tmp)
        val linearTmp :View = itemView.findViewById(R.id.linear_tmp)
        val tvDelete : TextView = itemView.findViewById(R.id.tv_delete_accu)
        val tvThemTien :TextView=itemView.findViewById(R.id.tv_themtiem)
        val viewMain=itemView.findViewById<View>(R.id.view_main)
    }
}
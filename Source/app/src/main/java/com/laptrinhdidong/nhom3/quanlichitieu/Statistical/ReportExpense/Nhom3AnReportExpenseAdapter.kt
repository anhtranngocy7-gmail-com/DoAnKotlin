package com.laptrinhdidong.nhom3.quanlichitieu.Statistical.ReportExpense

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import com.laptrinhdidong.nhom3.quanlichitieu.Model.Accumulate
import com.laptrinhdidong.nhom3.quanlichitieu.R

class Nhom3AnReportExpenseAdapter internal constructor(private val context: Context,private val chapTerList: List<Nhom3AnGroupReportExpense>,private val topicsList:HashMap<Nhom3AnGroupReportExpense,List<Nhom3AnItemReportExpense>>): BaseExpandableListAdapter() {
    override fun getGroupCount(): Int {
        Log.e("groupSize",chapTerList.size.toString())
        return chapTerList.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        Log.e("childSize",this.topicsList[this.chapTerList[groupPosition]]!!.size.toString())
        return this.topicsList[this.chapTerList[groupPosition]]!!.size
    }

    override fun getGroup(groupPosition: Int): Any {
        return chapTerList[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return this.topicsList[this.chapTerList[groupPosition]]!![childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var convertView=convertView
        if(convertView==null)
        {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView=inflater.inflate(R.layout.nhom3_an_itemview_group_reportexpense,parent,false)
        }
        val title : TextView = convertView!!.findViewById(R.id.tv_group_title)
        val total : TextView = convertView!!.findViewById(R.id.tv_group_total)
        val icon : ImageView = convertView!!.findViewById(R.id.imv_typeEx)
        icon.setImageResource(R.drawable.eat_and_drink)
        title.text=chapTerList[groupPosition].title
        total.text=chapTerList[groupPosition].total
        Log.e("An",chapTerList[groupPosition].title)
        return convertView
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var convertView=convertView
        if(convertView==null)
        {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView=inflater.inflate(R.layout.nhom3_an_itemview_reportexpense,parent,false)
        }
        val title : TextView = convertView!!.findViewById(R.id.tv_item_re_title)
        val total : TextView = convertView!!.findViewById(R.id.tv_item_re_money)
        title.text= topicsList[groupPosition]!![childPosition].title
        total.text= topicsList[groupPosition]!![childPosition].money
        Log.e("An1",topicsList[groupPosition]!![childPosition].title)
        return convertView
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}
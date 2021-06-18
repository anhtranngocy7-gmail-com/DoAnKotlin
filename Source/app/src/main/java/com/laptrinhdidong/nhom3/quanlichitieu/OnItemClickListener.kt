package com.laptrinhdidong.nhom3.quanlichitieu

import com.laptrinhdidong.nhom3.quanlichitieu.Model.Accumulate
import com.laptrinhdidong.nhom3.quanlichitieu.Object.Nhom3AnItemFeature

interface OnItemClickListener {
        fun onItemClick(index: Int)
        fun onItemClick(accumulate: Accumulate)
        fun onClickDelete(index: Int)
        fun onClickAddMoney()
}
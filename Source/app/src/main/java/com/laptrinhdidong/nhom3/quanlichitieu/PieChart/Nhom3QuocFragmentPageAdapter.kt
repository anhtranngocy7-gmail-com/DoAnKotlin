package com.laptrinhdidong.nhom3.quanlichitieu.PieChart

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class Nhom3QuocFragmentPageAdapter(fm:FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
            if(position == 0){
                return Nhom3QuocFrgamentDay()
            }else if(position == 1){
                return Nhom3QuocFragmentMonth()
            }
            else {
                return Nhom3QuocFragmentYear()
            }

    }

    override fun getPageTitle(position: Int): CharSequence? {
        if (position == 0) {
            return "Ngày"
        } else if (position == 1) {
            return "Tháng"
        } else {
            return "Năm"
        }
    }
}
package com.laptrinhdidong.nhom3.quanlichitieu.PieChart

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class Nhom3QuocFragmentPageAdapter(fm:FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return  when(position){
            0 -> Nhom3QuocFrgamentDay()
            1 -> Nhom3QuocFragmentMonth()
            else ->{
                return Nhom3QuocFragmentYear()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Ngày"
            1-> "Tháng"
            else ->{
                return "Năm"
            }

        }
    }
}
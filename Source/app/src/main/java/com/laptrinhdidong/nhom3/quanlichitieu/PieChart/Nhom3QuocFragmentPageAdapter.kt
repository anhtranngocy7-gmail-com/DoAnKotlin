package com.laptrinhdidong.nhom3.quanlichitieu.PieChart

import android.R
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter

class Nhom3QuocFragmentPageAdapter(fm: FragmentManager, behavior: Int) :
    FragmentStatePagerAdapter(fm, behavior) {
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
//        Log.e("check postion", position.toString())
//            if(position == 0){
//                Log.e("xxx", "page adapter1")
//                return Nhom3QuocFrgamentDay()
//
//            }else if(position == 1){
//                Log.e("xxx", "page adapter2")
//                return Nhom3QuocFragmentMonth()
//            }
//            else{
//                Log.e("xxx", "page adapter3")
//                return Nhom3QuocFragmentYear()
        Log.e("check postion", position.toString())
        return when(position){
            0 -> Nhom3QuocFrgamentDay()

            1 -> Nhom3QuocFragmentMonth()
            else -> {
                return Nhom3QuocFragmentYear()
            }
        }

    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Ngày"
            1 -> "Tháng"
            else ->{
                return "Năm"
            }

        }
    }
}
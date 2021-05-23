package com.laptrinhdidong.nhom3.quanlichitieu.PieChart

import android.os.Bundle

import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.laptrinhdidong.nhom3.quanlichitieu.R


class Nhom3QuocBieudotron : AppCompatActivity(){
    private lateinit var toolbar1: Toolbar
    private lateinit var viewPager1 : ViewPager
    private lateinit var tabLayout1 : TabLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nhom3_quoc_activity_bieudotron_tablayout)

        toolbar1 = findViewById(R.id.toolbar)
        viewPager1 = findViewById(R.id.viewPager)
        tabLayout1 =findViewById(R.id.tabLayout)

        toolbar1.setTitle("Biểu đồ chi tiêu")
        toolbar1.setTitleMargin(20,0,0,0)



       val fragmentAdapter = Nhom3QuocFragmentPageAdapter(supportFragmentManager)

        viewPager1.adapter = fragmentAdapter

        tabLayout1.setupWithViewPager(viewPager1)



    }
}
package com.laptrinhdidong.nhom3.quanlichitieu

import android.os.Bundle

import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout


class Nhom3QuocBieudotron : AppCompatActivity(){
    private lateinit var toolbar1: Toolbar
    private lateinit var viewPager1 : ViewPager
    private lateinit var tabLayout1 : TabLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nhom3_quoc_activity_bieudo)

        toolbar1 = findViewById(R.id.toolbar)
        viewPager1 = findViewById(R.id.viewPager)
        tabLayout1 =findViewById(R.id.tabLayout)

        toolbar1.setTitle("Biểu đồ chi tiêu")



       val fragmentAdapter = Nhom3QuocFragmentPageAdapter(supportFragmentManager)

        viewPager1.adapter = fragmentAdapter

        tabLayout1.setupWithViewPager(viewPager1)



    }
}
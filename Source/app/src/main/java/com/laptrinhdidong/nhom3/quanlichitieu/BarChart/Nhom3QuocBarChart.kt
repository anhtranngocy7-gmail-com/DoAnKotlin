package com.laptrinhdidong.nhom3.quanlichitieu.BarChart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.laptrinhdidong.nhom3.quanlichitieu.R

class Nhom3QuocBarChart : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nhom3_quoc_activity_barchart_tablayout)

        
        toolbar = findViewById(R.id.toolbar)
        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)

        
        val fragmentAdapterBC = Nhom3QuocFragmentPageBCAdapter(supportFragmentManager)

        viewPager.adapter = fragmentAdapterBC

        tabLayout.setupWithViewPager(viewPager)


    }
}
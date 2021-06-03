package com.laptrinhdidong.nhom3.quanlichitieu.PieChart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending.Nhom3QuocPieChartViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3QuocActivityPiechartTablayoutBinding
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3QuocFrgamentDayBinding


class Nhom3QuocPieChart : Fragment(){
    private lateinit var toolbar1: Toolbar
    private lateinit var viewPager1 : ViewPager
    private lateinit var tabLayout1 : TabLayout
    private lateinit var binding: Nhom3QuocActivityPiechartTablayoutBinding
    private  lateinit var fragmentAdapter :Nhom3QuocFragmentPageAdapter

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<Nhom3QuocActivityPiechartTablayoutBinding>(
            inflater,
            R.layout.nhom3_quoc_activity_piechart_tablayout,
            container,
            false
        )

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.title = "Phân tích chi tiêu"
        fragmentAdapter = Nhom3QuocFragmentPageAdapter(childFragmentManager)
        binding.viewPager.adapter = fragmentAdapter

        binding.tabLayout.setupWithViewPager(binding.viewPager)

    }


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.nhom3_quoc_activity_piechart_tablayout)
//
//        toolbar1 = findViewById(R.id.toolbar)
//        viewPager1 = findViewById(R.id.viewPager)
//        tabLayout1 = findViewById(R.id.tabLayout)
//
//        toolbar1.setTitle("Phân tích chi tiêu")
//        toolbar1.setTitleMargin(20, 0, 0, 0)
//
//
//        val fragmentAdapter = Nhom3QuocFragmentPageAdapter(supportFragmentManager)
//
//        viewPager1.adapter = fragmentAdapter
//
//        tabLayout1.setupWithViewPager(viewPager1)
//    }



}
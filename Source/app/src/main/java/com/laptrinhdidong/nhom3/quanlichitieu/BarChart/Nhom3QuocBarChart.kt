package com.laptrinhdidong.nhom3.quanlichitieu.BarChart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.laptrinhdidong.nhom3.quanlichitieu.PieChart.Nhom3QuocFragmentPageAdapter
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3QuocActivityBarchartTablayoutBinding
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3QuocActivityPiechartTablayoutBinding

class Nhom3QuocBarChart : Fragment() {
    private lateinit var toolbar: Toolbar
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    private lateinit var binding: Nhom3QuocActivityBarchartTablayoutBinding
    private  lateinit var fragmentAdapter : Nhom3QuocFragmentPageBCAdapter

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<Nhom3QuocActivityBarchartTablayoutBinding>(
            inflater,
            R.layout.nhom3_quoc_activity_barchart_tablayout,
            container,
            false
        )

        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentAdapter = Nhom3QuocFragmentPageBCAdapter(childFragmentManager)
        binding.viewPager.adapter = fragmentAdapter

        binding.tabLayout.setupWithViewPager(binding.viewPager)

    }
}
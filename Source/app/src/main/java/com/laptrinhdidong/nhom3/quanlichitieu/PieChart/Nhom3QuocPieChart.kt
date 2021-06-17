package com.laptrinhdidong.nhom3.quanlichitieu.PieChart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3QuocActivityPiechartTablayoutBinding


class Nhom3QuocPieChart : Fragment(){

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

        fragmentAdapter = Nhom3QuocFragmentPageAdapter(childFragmentManager)
        binding.viewPager.adapter = fragmentAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }
}
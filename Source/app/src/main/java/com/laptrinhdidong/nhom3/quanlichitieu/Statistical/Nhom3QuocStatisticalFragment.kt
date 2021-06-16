package com.laptrinhdidong.nhom3.quanlichitieu.Statistical

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.laptrinhdidong.nhom3.quanlichitieu.R

class Nhom3QuocStatisticalFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.nhom3_quoc_acitvity_statistical_fragment,container,false)
        return view
    }
}
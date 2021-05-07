package com.laptrinhdidong.nhom3.quanlichitieu.MainApp.TongQuan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.laptrinhdidong.nhom3.quanlichitieu.MainApp.TongQuan.Nhom3AnTinhNangAdapter
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3AnTichluyFragmentBinding
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3AnTongquanFragmentBinding

class Nhom3AnTongQuanFragment : Fragment() {
    private lateinit var binding: Nhom3AnTongquanFragmentBinding
    private lateinit var adapterNhom3An: Nhom3AnTinhNangAdapter
    private lateinit var rcv:RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<Nhom3AnTongquanFragmentBinding>(
            inflater,
            R.layout.nhom3_an_tongquan_fragment,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterNhom3An= Nhom3AnTinhNangAdapter()
        binding.recycleviewFeature.layoutManager=GridLayoutManager(context,3)
        binding.recycleviewFeature.adapter=adapterNhom3An
    }
}
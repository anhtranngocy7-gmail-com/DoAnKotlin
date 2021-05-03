package com.laptrinhdidong.nhom3.quanlichitieu.MainApp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.TongquanFragmentBinding

class TongQuanFragment : Fragment() {
    private lateinit var binding: TongquanFragmentBinding
    private lateinit var adapter: TinhNangAdapter
    private lateinit var rcv:RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.tongquan_fragment, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter= TinhNangAdapter()
        rcv=view.findViewById(R.id.recycleview_feature)
        rcv.layoutManager= GridLayoutManager(context,3)
        rcv.adapter=adapter
    }
}
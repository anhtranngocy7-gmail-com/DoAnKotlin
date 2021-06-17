package com.laptrinhdidong.nhom3.quanlichitieu.MainApp.TichLuy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.laptrinhdidong.nhom3.quanlichitieu.PieChart.Nhom3QuocPieChart
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3AnTichluyFragmentBinding

class Nhom3AnTichLuyFragment: Fragment() {
    private lateinit var binding : Nhom3AnTichluyFragmentBinding
    private lateinit var viewModel: Nhom3AnTichLuyViewModel
    private lateinit var adapter: Nhom3AnTichLuyAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(Nhom3AnTichLuyViewModel::class.java)
        binding = DataBindingUtil.inflate<Nhom3AnTichluyFragmentBinding>(
            inflater,
            R.layout.nhom3_an_tichluy_fragment,
            container,
            false
        )
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = Nhom3AnTichLuyAdapter()
        binding.recycleviewTichluy.layoutManager = LinearLayoutManager(context)
        adapter.data = viewModel.getData()
        binding.recycleviewTichluy.adapter = adapter
        binding.btnTrantoadd.setOnClickListener{
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<Nhom3AnAddSavingFragment>(R.id.fragment_mainapp)
                addToBackStack(null)
            }
        }
    }
}
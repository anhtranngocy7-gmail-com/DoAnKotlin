package com.laptrinhdidong.nhom3.quanlichitieu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending.Nhom3QuocBieudoAdapter
import com.laptrinhdidong.nhom3.quanlichitieu.ChartPage.RecycleViewSpending.Nhom3QuocBieudoViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3QuocFrgamentDayBinding
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.laptrinhdidong.nhom3.quanlichitieu.MainApp.TichLuy.Nhom3AnTichLuyAdapter
import com.laptrinhdidong.nhom3.quanlichitieu.MainApp.TichLuy.Nhom3AnTichLuyViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Nhom3QuocFrgamentDay.newInstance] factory method to
 * create an instance of this fragment.
 */
class Nhom3QuocFrgamentDay : Fragment() {
    private lateinit var binding : Nhom3QuocFrgamentDayBinding
    private lateinit var viewModel: Nhom3QuocBieudoViewModel
    private lateinit var adapter : Nhom3QuocBieudoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(Nhom3QuocBieudoViewModel::class.java)
        binding = DataBindingUtil.inflate<Nhom3QuocFrgamentDayBinding>(
           inflater,
           R.layout.nhom3_quoc_frgament_day,
           container,
           false
       )
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = Nhom3QuocBieudoAdapter()
        binding.recycleViewDay.layoutManager = LinearLayoutManager(context)
        adapter.data = viewModel.getData()
        binding.recycleViewDay.adapter = adapter
    }

}
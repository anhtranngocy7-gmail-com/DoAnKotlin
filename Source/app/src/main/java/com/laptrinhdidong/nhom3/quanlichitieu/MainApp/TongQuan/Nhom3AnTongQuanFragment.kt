package com.laptrinhdidong.nhom3.quanlichitieu.MainApp.TongQuan

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3AnTongquanFragmentBinding
import com.laptrinhdidong.nhom3.quanlichitieu.Nhom3AnOverviewViewModel

class Nhom3AnTongQuanFragment : Fragment() {
    private lateinit var binding: Nhom3AnTongquanFragmentBinding
    private lateinit var viewModel: Nhom3AnOverviewViewModel
    private lateinit var adapterNhom3An: Nhom3AnTinhNangAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(Nhom3AnOverviewViewModel::class.java)
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
        binding.viewmodel=viewModel
        adapterNhom3An= Nhom3AnTinhNangAdapter()
        binding.recycleviewFeature.layoutManager=GridLayoutManager(context,3)
        binding.recycleviewFeature.adapter=adapterNhom3An
        binding.btnDropMore.setOnClickListener(View.OnClickListener {
            binding.linearMoreinfor.visibility=if(binding.linearMoreinfor.isVisible){View.GONE}else{View.VISIBLE }
        })
        binding.spinnerTime.onItemSelectedListener= object :OnItemSelectedListener
        {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.getExInMoney(position+1)
                binding.viewmodel=viewModel
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }
//    private fun getPermission() {
//        ActivityCompat.requestPermissions(
//            context as Activity,
//            arrayOf(Manifest.permission.INTERNET),
//            PackageManager.PERMISSION_GRANTED
//        )
//        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
//        StrictMode.setThreadPolicy(policy)
//    }
}
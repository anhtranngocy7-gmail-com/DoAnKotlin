package com.laptrinhdidong.nhom3.quanlichitieu.MainApp.TichLuy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3AnAddsavingBinding
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3AnDetailAccumulateBinding
import java.lang.Exception

class Nhom3AnDetaiSavingFragment : Fragment() {
    private lateinit var binding: Nhom3AnDetailAccumulateBinding
    private lateinit var viewModel: Nhom3AnDetailSavingViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(Nhom3AnDetailSavingViewModel::class.java)
        binding = DataBindingUtil.inflate<Nhom3AnDetailAccumulateBinding>(
            inflater,
            R.layout.nhom3_an_detail_accumulate,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!viewModel.firstAccess)
        {
            viewModel.getDataInit()
            viewModel.firstAccess=true
        }
        if(viewModel.tmpaccumulate.buyOrPay)
        {
            binding.tvBuyorpay.text="Mua"
        }else
        {
            binding.tvBuyorpay.text="Vay nợ"+" ("+viewModel.tmpaccumulate.percent+"%)"
        }
        binding.edtNameTarget.text=viewModel.tmpaccumulate.title
        binding.edtSotien.text=viewModel.tmpaccumulate.targetmoney.toString()
        binding.edtSoltienhienco.text=viewModel.tmpaccumulate.currentmoney.toString()
        binding.edtSotime.text=viewModel.tmpaccumulate.timeRemain.toString()
        binding.edtTienhangthang.text=viewModel.tmpaccumulate.incomPerMonth.toString()
        binding.ivCanceldetail.setOnClickListener {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<Nhom3AnTichLuyFragment>(R.id.fragment_mainapp)
                addToBackStack(null)
            }
        }
    }
}
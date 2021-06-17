package com.laptrinhdidong.nhom3.quanlichitieu.MainApp.TichLuy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3AnAddsavingBinding
import java.lang.Exception

class Nhom3AnAddSavingFragment : Fragment() {
    private lateinit var binding: Nhom3AnAddsavingBinding
    private lateinit var viewModel: Nhom3AnAddSavingViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(Nhom3AnAddSavingViewModel::class.java)
        binding = DataBindingUtil.inflate<Nhom3AnAddsavingBinding>(
            inflater,
            R.layout.nhom3_an_addsaving,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCfAddsaving.setOnClickListener {
            try {
                viewModel.nameTarget = binding.edtNameTarget.text.toString()
                viewModel.percent = binding.edtSolai.text.toString().toDouble()
                viewModel.targetMoney = binding.edtSotien.text.toString().toBigDecimal()
                if(binding.spinnerTimeorincome.selectedItemPosition == 0) {
                    viewModel.time = binding.edtTime.text.toString().toFloat()
                }else
                {
                    viewModel.incomePerMonth = binding.edtIncome.text.toString().toBigDecimal()
                }
            }catch (e: Exception)
            {
                Toast.makeText(requireContext(),"Chưa đủ input",Toast.LENGTH_SHORT).show()
            }
            viewModel.createAccumulate(
                binding.spinnerBuyorpay.selectedItemPosition == 0,
                binding.spinnerTimeorincome.selectedItemPosition == 0
            )

        }
        myOnClickNoChangeData()
    }

    fun myOnClickNoChangeData() {
        binding.spinnerBuyorpay.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position == 0) {
                        binding.clLai.visibility = View.GONE
                    } else if (position == 1) {
                        binding.clLai.visibility = View.VISIBLE
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }
        binding.spinnerTimeorincome.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position == 0) {
                        binding.clIncome.visibility = View.GONE
                        binding.clTime.visibility = View.VISIBLE
                    } else if (position == 1) {
                        binding.clTime.visibility = View.GONE
                        binding.clIncome.visibility = View.VISIBLE
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }
        binding.ivCancel.setOnClickListener {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<Nhom3AnTichLuyFragment>(R.id.fragment_mainapp)
                addToBackStack(null)
            }
        }
    }
}
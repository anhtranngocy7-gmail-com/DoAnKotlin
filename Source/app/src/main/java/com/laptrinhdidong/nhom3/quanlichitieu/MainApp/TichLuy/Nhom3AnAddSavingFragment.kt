package com.laptrinhdidong.nhom3.quanlichitieu.MainApp.TichLuy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3AnAddsavingBinding
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3AnTichluyFragmentBinding

class Nhom3AnAddSavingFragment : Fragment() {
    private lateinit var binding : Nhom3AnAddsavingBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        binding.spinnerBuyorpay.onItemSelectedListener= object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(position==0)
                {
                    binding.clLai.visibility=View.GONE
                }else if(position==1)
                {
                    binding.clLai.visibility=View.VISIBLE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
        binding.spinnerTimeorincome.onItemSelectedListener=object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(position==0)
                {
                    binding.clIncome.visibility=View.GONE
                    binding.clTime.visibility=View.VISIBLE
                }else if(position==1)
                {
                    binding.clTime.visibility=View.GONE
                    binding.clIncome.visibility=View.VISIBLE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }
}
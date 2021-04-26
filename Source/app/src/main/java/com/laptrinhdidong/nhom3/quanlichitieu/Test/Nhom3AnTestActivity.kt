package com.laptrinhdidong.nhom3.quanlichitieu.Test

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.ActivityNhom3AnTestBinding

class Nhom3AnTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNhom3AnTestBinding
    private lateinit var viewmodel: Nhom3AnTestViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        getPermission()
        setUpViewModelBinding()
    }

    private fun getPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.INTERNET),
            PackageManager.PERMISSION_GRANTED
        )
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }

    private fun setUpViewModelBinding() {
        viewmodel = ViewModelProvider(this).get(Nhom3AnTestViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_nhom3_an_test)
        binding.tvTest.text = viewmodel.txt.value
        binding.btnGetdata.setOnClickListener {
            viewmodel.getData()
            viewmodel.txt.observe(this, { txt ->
                binding.tvTest.text = txt
            })
        }
    }
}
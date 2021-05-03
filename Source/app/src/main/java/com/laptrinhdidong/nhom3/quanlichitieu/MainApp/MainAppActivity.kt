package com.laptrinhdidong.nhom3.quanlichitieu.MainApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.ActivityMainAppBinding

class MainAppActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainAppBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main_app)
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item: MenuItem ->
            supportFragmentManager.commit {
                setReorderingAllowed(true)

                when (item.itemId) {
                    R.id.action_home -> add<TongQuanFragment>(R.id.fragment_mainapp)
                    R.id.action_accumulate -> add<TichLuyFragment>(R.id.fragment_mainapp)
//                    R.id.action_accumulate -> Toast.makeText(this@MainAppActivity,"tichluy",Toast.LENGTH_SHORT).show()

                }
                addToBackStack(null)
            }

            return@OnNavigationItemSelectedListener true
        })

    }
}
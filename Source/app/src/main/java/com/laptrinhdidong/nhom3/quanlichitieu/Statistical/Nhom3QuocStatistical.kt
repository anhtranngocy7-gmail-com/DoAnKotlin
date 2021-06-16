package com.laptrinhdidong.nhom3.quanlichitieu.Statistical

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.laptrinhdidong.nhom3.quanlichitieu.R

class Nhom3QuocStatistical : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nhom3_quoc_activity_statistical)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<Nhom3QuocStatisticalFragment>(R.id.frag_container_view)
        }

    }
}
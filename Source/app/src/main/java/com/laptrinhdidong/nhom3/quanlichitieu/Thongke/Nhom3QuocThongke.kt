package com.laptrinhdidong.nhom3.quanlichitieu.Thongke

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.laptrinhdidong.nhom3.quanlichitieu.R

class Nhom3QuocThongke : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nhom3_quoc_activity_thongke)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<Nhom3QuocThongKeFragment>(R.id.frag_container_view)
        }

    }
}
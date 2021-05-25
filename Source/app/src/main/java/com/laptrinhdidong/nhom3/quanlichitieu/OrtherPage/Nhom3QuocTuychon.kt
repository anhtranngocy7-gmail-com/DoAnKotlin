package com.laptrinhdidong.nhom3.quanlichitieu.OrtherPage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.Thongke.Nhom3QuocThongKeFragment

class Nhom3QuocTuychon: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nhom3_quoc_activity_tuychon)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<Nhom3QuocThongKeFragment>(R.id.frag_container_view)
        }

    }
}
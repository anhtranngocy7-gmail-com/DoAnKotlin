package com.laptrinhdidong.nhom3.quanlichitieu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.laptrinhdidong.nhom3.quanlichitieu.Spending.Nhom3BinhSpendingFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<Nhom3BinhSpendingFragment>(R.id.frag_container_view)
        }

    }
}
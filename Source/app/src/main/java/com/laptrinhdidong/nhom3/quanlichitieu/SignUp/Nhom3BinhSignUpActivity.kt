package com.laptrinhdidong.nhom3.quanlichitieu.SignUp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.laptrinhdidong.nhom3.quanlichitieu.Account
import com.laptrinhdidong.nhom3.quanlichitieu.Nhom3AnhSignInActivity
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3BinhActivitySignUpBinding

class Nhom3BinhSignUpActivity : AppCompatActivity() {
    private var account : Account = Account("", "", "","")
    private lateinit var viewModel: Nhom3BinhSignUpViewModel
    private lateinit var binding: Nhom3BinhActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.nhom3_binh_activity_sign_up)
        viewModel = ViewModelProvider(this).get(Nhom3BinhSignUpViewModel::class.java)
        binding.account = account
        binding.btnConfirm.setOnClickListener {
                Log.e("Test button", "Binh")
                viewModel.account.fullname = binding.etFullname.text.toString().trim()
                viewModel.account.email = binding.etEmail.text.toString().trim()
                viewModel.account.password = binding.etPassword.text.toString().trim()
                val editemail = binding.etEmail.text.toString()
                val editpass = binding.etPassword.text.toString()
                if (viewModel.registerUser(binding.btnConfirm, editpass, editemail)) {
                    Log.e("Test button", "Binh")
                    Toast.makeText(applicationContext, "Right", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, Nhom3AnhSignInActivity::class.java)
                    startActivity(intent)
                } else {

                    if (!viewModel.validatePassword(editpass)) {
                        binding.etPassword.error = "Password is too weak"
                    }

                    if (!viewModel.validateEmail(editemail)) {
                        binding.etEmail.error = "Invalid email address"
                    }
                }
            }
        }
    }
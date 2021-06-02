package com.laptrinhdidong.nhom3.quanlichitieu.SignUp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.facebook.login.widget.LoginButton
import com.laptrinhdidong.nhom3.quanlichitieu.Account
import com.laptrinhdidong.nhom3.quanlichitieu.Nhom3AnhSignInActivity
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3BinhActivitySignUpBinding
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.Task
<<<<<<< HEAD
import com.laptrinhdidong.nhom3.quanlichitieu.MainApp.Nhom3AnMainAppActivity
=======
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3AnhActivitySignInBinding
import java.util.*
>>>>>>> f8a44d26144b887fd3462b994280e5c02808e13b


class Nhom3BinhSignUpActivity : AppCompatActivity() {
    private var account : Account = Account("", "", "","")
    private lateinit var viewModel: Nhom3BinhSignUpViewModel
    private lateinit var binding: Nhom3BinhActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPermission()
        binding = DataBindingUtil.setContentView(this, R.layout.nhom3_binh_activity_sign_up)
        viewModel = ViewModelProvider(this).get(Nhom3BinhSignUpViewModel::class.java)
        binding.account = account
        binding.btnConfirm.setOnClickListener {
<<<<<<< HEAD
                viewModel.account.fullname = binding.etFullname.text.toString().trim()
                viewModel.account.email = binding.etEmail.text.toString().trim()
                viewModel.account.password = binding.etPassword.text.toString().trim()
                val editemail = binding.etEmail.text.toString()
                val editpass = binding.etPassword.text.toString()
                if (viewModel.registerUser(binding.btnConfirm, editpass, editemail)) {
                    when(viewModel.checkAccExit())
                    {
                        0.toByte() -> binding.etEmail.error="email đã tồn tại"
                        1.toByte() -> {val intent = Intent(this, Nhom3AnhSignInActivity::class.java)
                            startActivity(intent)}
                        2.toByte() -> Toast.makeText(this,"No connection, please check your wifi/3G",Toast.LENGTH_SHORT).show()
                    }

                } else {

                    if (!viewModel.validatePassword(editpass)) {
                        binding.etPassword.error = "mật khẩu yếu"
                    }

                    if (!viewModel.validateEmail(editemail)) {
                        binding.etEmail.error = "email không hợp lệ"
                    }
=======
            viewModel.account.fullname = binding.etFullname.text.toString().trim()
            viewModel.account.email = binding.etEmail.text.toString().trim()
            viewModel.account.password = binding.etPassword.text.toString().trim()
            val editemail = binding.etEmail.text.toString()
            val editpass = binding.etPassword.text.toString()
            if (viewModel.registerUser(binding.btnConfirm, editpass, editemail)) {
                val intent = Intent(this, Nhom3AnhSignInActivity::class.java)
                startActivity(intent)
            } else {

                if (!viewModel.validatePassword(editpass)) {
                    binding.etPassword.error = "Password is too weak"
                }

                if (!viewModel.validateEmail(editemail)) {
                    binding.etEmail.error = "Invalid email address"
>>>>>>> f8a44d26144b887fd3462b994280e5c02808e13b
                }
            }
        }
        binding.tvDangnhap.setOnClickListener {
            val intent = Intent(this, Nhom3AnhSignInActivity::class.java)
            startActivity(intent)
        }

<<<<<<< HEAD
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            if (account != null) {
//                val intent = Intent(this, Nhom3AnhSignInActivity::class.java)
//                startActivity(intent)
                Toast.makeText(this,account.displayName.toString(),Toast.LENGTH_LONG).show()
                Log.e("BINH", account.displayName.toString())
                Log.e("BINH", account.email.toString())
                Log.e("Binh",account.id.toString())
                val name = account.displayName.toString()
                val idUser = "g" + account.id.toString()
                loginSocialCheck(idUser, name)
            }
            // Signed in successfully, show authenticated UI.

        } catch (e: ApiException) {
            Log.e("Binh","null")
        }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.d("Failed", "onConnectionFailed:" + connectionResult);
=======
        binding.facebookSignin.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                val btn = LoginButton(this@Nhom3BinhSignUpActivity)
                btn.performClick()
            }
        })
>>>>>>> f8a44d26144b887fd3462b994280e5c02808e13b
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
    private fun loginSocialCheck(idUser: String, name: String) {
        if (viewModel.checkAccountSocialExit(idUser, name)) {
            val intent = Intent(this@Nhom3BinhSignUpActivity, Nhom3AnMainAppActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(
                this@Nhom3BinhSignUpActivity,
                "No connection, please check your wifi/3G",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
package com.laptrinhdidong.nhom3.quanlichitieu.SignUp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
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
class Nhom3BinhSignUpActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {
=======
class Nhom3BinhSignUpActivity : AppCompatActivity() {
>>>>>>> 18c52ba812f1f1905b1a0504d198c85d0d2402b9
    private var account : Account = Account("", "", "","")
    private lateinit var viewModel: Nhom3BinhSignUpViewModel
    private lateinit var binding: Nhom3BinhActivitySignUpBinding

    //google sign in
    private val RC_SIGN_IN = 100
    private var mGoogleApiClient: GoogleApiClient? = null
    lateinit var anhem: Nhom3AnhSignInActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.nhom3_binh_activity_sign_up)
        viewModel = ViewModelProvider(this).get(Nhom3BinhSignUpViewModel::class.java)
        binding.account = account
        binding.btnConfirm.setOnClickListener {
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
                }
            }
        }
        binding.tvDangnhap.setOnClickListener {
            val intent = Intent(this, Nhom3AnhSignInActivity::class.java)
            startActivity(intent)
        }
<<<<<<< HEAD
        // google sign in
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()
        binding.googleSignin.setOnClickListener {
            val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
            startActivityForResult(signInIntent, RC_SIGN_IN)
            Log.d("Success", mGoogleApiClient?.isConnected.toString() + "")
            }
        
        // Facebook Login
        }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {

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
            }
            // Signed in successfully, show authenticated UI.

        } catch (e: ApiException) {

        }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        TODO("Not yet implemented")
        Log.d("Failed", "onConnectionFailed:" + connectionResult);
=======
>>>>>>> 18c52ba812f1f1905b1a0504d198c85d0d2402b9
    }
}
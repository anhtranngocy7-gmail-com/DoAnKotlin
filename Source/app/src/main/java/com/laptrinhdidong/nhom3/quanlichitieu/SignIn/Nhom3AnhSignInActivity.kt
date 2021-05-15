package com.laptrinhdidong.nhom3.quanlichitieu

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.facebook.*
import com.facebook.FacebookActivity
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.laptrinhdidong.nhom3.quanlichitieu.SignIn.Nhom3AnhSignInViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3AnhActivitySignInBinding
import org.json.JSONObject
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


class Nhom3AnhSignInActivity : AppCompatActivity() {
    private lateinit var binding: Nhom3AnhActivitySignInBinding
    private lateinit var viewModel: Nhom3AnhSignInViewModel
    private var account : Account = Account("", "", "", "")
    private lateinit var callbackManager: CallbackManager
    private lateinit var loginButton: LoginButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(applicationContext)
        callbackManager = CallbackManager.Factory.create()
        binding = DataBindingUtil.setContentView(this,R.layout.nhom3_anh_activity_sign_in)
        viewModel = ViewModelProvider(this).get(Nhom3AnhSignInViewModel::class.java)
        binding.account = viewModel.account
        binding.btnConfirmSignin.setOnClickListener {
            Log.e("Check User", "Waiting check")
        }
        printKeyHash()
        binding.btnfbSignin.setVisibility(View.GONE);
        binding.btnfbSignin.setReadPermissions(Arrays.asList("public_profile","email"))
        binding.btnfbSigninImage.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                val btn = LoginButton(this@Nhom3AnhSignInActivity)
                btn.performClick()
            }

        })
        setLogin_Button()
    }
    private fun setLogin_Button() {
        binding.btnfbSignin.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {
                // App code
                result()
            }

            override fun onCancel() {
                // App code
            }
            override fun onError(exception: FacebookException) {
                // App code
            }
        })
    }
    private fun result() {
        val graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), object: GraphRequest.GraphJSONObjectCallback
                {
                    override fun onCompleted(`object`: JSONObject?, response: GraphResponse?) {
                        if (response != null) {
                            Log.e("JSON",response.jsonObject.toString())
                            val email = response.jsonObject.getString("email")
                            val name = response.jsonObject.getString("name")
                            val idUser = response.jsonObject.getString("id")
                        }
                    }
                }
            )
        val parameter = Bundle()
        parameter.putString("fields","name,email,first_name")
        graphRequest.setParameters(parameter)
        graphRequest.executeAsync()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
    private fun printKeyHash()
    {
        try{
            val info = packageManager.getPackageInfo("com.laptrinhdidong.nhom3.quanlichitieu",PackageManager.GET_SIGNATURES)
            for (signature in info.signatures)
            {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e("KEYHASH",Base64.encodeToString((md.digest()),Base64.DEFAULT))
            }
        }
        catch (e:PackageManager.NameNotFoundException)
        {

        }
        catch(e:NoSuchAlgorithmException) {
        }
        }
}
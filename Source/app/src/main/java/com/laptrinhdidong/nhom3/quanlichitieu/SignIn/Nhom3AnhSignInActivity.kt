package com.laptrinhdidong.nhom3.quanlichitieu

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.StrictMode
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.facebook.*
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.laptrinhdidong.nhom3.quanlichitieu.MainApp.Nhom3AnMainAppActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.internal.SignInHubActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.Task
import com.laptrinhdidong.nhom3.quanlichitieu.SignIn.Nhom3AnhSignInViewModel
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3AnhActivitySignInBinding
import org.json.JSONObject
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


open class Nhom3AnhSignInActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {
    private lateinit var binding: Nhom3AnhActivitySignInBinding
    private lateinit var viewModel: Nhom3AnhSignInViewModel
    private var account: Account = Account("", "", "", "")
    private lateinit var callbackManager: CallbackManager
    //  private lateinit var loginButton: LoginButton
    //google sign in
    private val RC_SIGN_IN = 100
    private var mGoogleApiClient: GoogleApiClient? = null

<<<<<<< HEAD
    //
=======
>>>>>>> f8a44d26144b887fd3462b994280e5c02808e13b
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPermission()
        FacebookSdk.sdkInitialize(applicationContext)
        callbackManager = CallbackManager.Factory.create()
        binding = DataBindingUtil.setContentView(this, R.layout.nhom3_anh_activity_sign_in)
        viewModel = ViewModelProvider(this).get(Nhom3AnhSignInViewModel::class.java)
<<<<<<< HEAD
        binding.btnConfirmSignin.setOnClickListener {
            when (viewModel.checkAccount(
                binding.editText7.text.toString().trim(),
                binding.editText6.text.toString().trim()
            )) {
                0.toByte() -> Toast.makeText(
                    this,
                    "Username or password is not correct!",
                    Toast.LENGTH_SHORT
                ).show()
                1.toByte() -> {
                    val intent =
                        Intent(this@Nhom3AnhSignInActivity, Nhom3AnMainAppActivity::class.java)
                    startActivity(intent)
                }
                2.toByte() -> Toast.makeText(
                    this,
                    "No connection, please check your wifi/3G",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
=======
        binding.account = viewModel.account
>>>>>>> f8a44d26144b887fd3462b994280e5c02808e13b
        printKeyHash()
        binding.btnfbSignin.setVisibility(View.GONE);
        binding.btnfbSignin.setReadPermissions(Arrays.asList("public_profile", "email"))
        binding.btnfbSigninImage.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val btn = LoginButton(this@Nhom3AnhSignInActivity)
                btn.performClick()
            }
        })
        setLogin_Button_Fb()
        binding.googleSignin.setOnClickListener {
            val intent = Intent(this, Nhom3AnhSignInActivity::class.java)
            startActivity(intent)
        }
        // Google sign in
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
        binding.btnConfirmSignin.setOnClickListener {
            Log.e("Check User", "Waiting check")
        }
    }
<<<<<<< HEAD

    private fun setLogin_Button() {
        binding.btnfbSignin.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    // App code
                    result()
                }

                override fun onCancel() {
                    Log.e("error", "cancel")
                }

                override fun onError(exception: FacebookException) {
                    // App code
                    Log.e("error", "error")
                }
            })
    }

    private fun result() {
        val graphRequest = GraphRequest.newMeRequest(
            AccessToken.getCurrentAccessToken(),
            object : GraphRequest.GraphJSONObjectCallback {
                override fun onCompleted(`object`: JSONObject?, response: GraphResponse?) {
                    if (response != null) {
                        Log.e("JSON", response.jsonObject.toString())
                        val name = response.jsonObject.getString("name")
                        val idUser = "f" + response.jsonObject.getString("id")
                        loginSocialCheck(idUser, name)
=======
     fun setLogin_Button_Fb() {
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
     fun result() {
        val graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), object: GraphRequest.GraphJSONObjectCallback
                {
                    override fun onCompleted(`object`: JSONObject?, response: GraphResponse?) {
                        if (response != null) {
                            Log.e("JSON",response.jsonObject.toString())
                            val email = response.jsonObject.getString("email")
                            val name = response.jsonObject.getString("name")
                            val idUser = response.jsonObject.getString("id")
                        }
>>>>>>> f8a44d26144b887fd3462b994280e5c02808e13b
                    }
                }
            }
        )
        val parameter = Bundle()
        parameter.putString("fields", "name,email,first_name")
        graphRequest.setParameters(parameter)
        graphRequest.executeAsync()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
        //google signin
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    //google sign in
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                Toast.makeText(this, account.displayName.toString(), Toast.LENGTH_LONG).show()
                Log.e("BINH", account.displayName.toString())
                Log.e("BINH", account.email.toString())
                Log.e("BINH", account.id.toString())
                val name = account.displayName.toString()
                val idUser = "g" + account.id.toString()
                loginSocialCheck(idUser, name)
            }
        } catch (e: ApiException) {
            Log.e("BINHEROR", "null account")
        }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.d("Failed", "onConnectionFailed:" + connectionResult);
    }

<<<<<<< HEAD
    //
    private fun printKeyHash() {
        try {
            val info = packageManager.getPackageInfo(
                "com.laptrinhdidong.nhom3.quanlichitieu",
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
=======
    private fun printKeyHash()
    {
        try{
            val info = packageManager.getPackageInfo("com.laptrinhdidong.nhom3.quanlichitieu",PackageManager.GET_SIGNATURES)
            for (signature in info.signatures)
            {
>>>>>>> f8a44d26144b887fd3462b994280e5c02808e13b
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e("KEYHASH", Base64.encodeToString((md.digest()), Base64.DEFAULT))
            }
<<<<<<< HEAD
        } catch (e: PackageManager.NameNotFoundException) {

        } catch (e: NoSuchAlgorithmException) {
        }
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
            val intent = Intent(this@Nhom3AnhSignInActivity, Nhom3AnMainAppActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(
                this@Nhom3AnhSignInActivity,
                "No connection, please check your wifi/3G",
                Toast.LENGTH_SHORT
            ).show()
=======
        }
        catch (e:PackageManager.NameNotFoundException)
        {
        }
        catch(e:NoSuchAlgorithmException) {
>>>>>>> f8a44d26144b887fd3462b994280e5c02808e13b
        }
    }
}
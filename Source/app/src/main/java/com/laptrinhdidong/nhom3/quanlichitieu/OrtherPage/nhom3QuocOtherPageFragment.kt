package com.laptrinhdidong.nhom3.quanlichitieu.OrtherPage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.HttpMethod
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.Auth.GoogleSignInApi
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.laptrinhdidong.nhom3.quanlichitieu.Nhom3AnhSignInActivity
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3QuocActivityOptionFragmentBinding


class nhom3QuocOtherPageFragment : Fragment(), GoogleApiClient.OnConnectionFailedListener {
    private lateinit var binding: Nhom3QuocActivityOptionFragmentBinding
    private var mGoogleApiClient: GoogleApiClient? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.nhom3_quoc_activity_option_fragment,container,false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleApiClient = GoogleApiClient.Builder(requireContext())
            .enableAutoManage(requireActivity() /* FragmentActivity */, this /* OnConnectionFailedListener */)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()
        binding.tvLogout.setOnClickListener {
            GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback {
                Log.e("dang xuat", "da dang xuat")
            }

            Log.e("davao", "vaooooo")
            val intent = Intent (getActivity(), Nhom3AnhSignInActivity::class.java)
            getActivity()?.startActivity(intent)

            if (AccessToken.getCurrentAccessToken() != null) {
                val accessToken = AccessToken.getCurrentAccessToken()
                GraphRequest(
                    accessToken, "/" + accessToken.userId + "/permissions/", null, HttpMethod.DELETE
                ) { LoginManager.getInstance().logOut() }.executeAsync()
                AccessToken.setCurrentAccessToken(null)
            }
        }
//        val accessToken = AccessToken.getCurrentAccessToken()
//        val isLoggedIn = accessToken != null && !accessToken.isExpired
//        Log.e("Login",isLoggedIn.toString())



    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("Not yet implemented")
    }

}
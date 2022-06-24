package com.pawat.cryptoapp.views.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.pawat.cryptoapp.R
import com.pawat.cryptoapp.extensions.startActivity
import com.pawat.cryptoapp.views.coinlist.CoinListActivity


@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                startActivity(Intent(this, CoinListActivity::class.java))
                finish()
            }
        , 1000)
    }
}
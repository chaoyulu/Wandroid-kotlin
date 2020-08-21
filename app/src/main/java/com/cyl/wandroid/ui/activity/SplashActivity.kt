package com.cyl.wandroid.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cyl.wandroid.R
import com.cyl.wandroid.tools.start

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.decorView.postDelayed({
            start(this@SplashActivity, MainActivity::class.java)
            finish()
        }, 100)
    }
}
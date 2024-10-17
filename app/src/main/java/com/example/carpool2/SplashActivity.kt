package com.example.carpool2

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Find the ImageView for the logo
        val splashLogo: ImageView = findViewById(R.id.iv_splash_logo)

        // Load the zoom animation
        val zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_in)
        splashLogo.startAnimation(zoomAnimation)

        // Display the splash screen for 3 seconds, then navigate to the LoginActivity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000) // 3000 milliseconds = 3 seconds
    }
}

package com.example.factorialapp

import android.animation.AnimatorInflater
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation.INFINITE
import android.view.animation.Animation.REVERSE
import androidx.appcompat.app.AppCompatActivity
import com.example.factorialapp.databinding.ActivitySplashBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        CoroutineScope(Dispatchers.Main).launch {
            launchApp()
        }

    }

    private suspend fun launchApp(){
        binding.tvSplashN.animate().apply {
            rotationY(1500f)
            duration = 5000
            interpolator = AccelerateDecelerateInterpolator()
        }.start()

        (AnimatorInflater.loadAnimator(this, R.animator.text_color_animator) as ObjectAnimator).apply {
            target = binding.tvSplashN
            start()
        }


        delay(5000)
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
    }
}
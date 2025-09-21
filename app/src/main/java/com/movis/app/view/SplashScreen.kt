package com.movis.app.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movis.app.ApiManager.ApiManager
import com.movis.app.adapter.AdapterCategory
import com.movis.app.adapter.Adapter_genres
import com.movis.app.databinding.SplashScreenBinding
import com.movis.app.model.Model_genres
import java.util.ArrayList

class SplashScreen : AppCompatActivity() {
    lateinit var binding: SplashScreenBinding
    val apiManager = ApiManager()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.textView1.animate()
            .alpha(1f)
            .setDuration(1000)
            .setStartDelay(2000)
            .start()
        binding.textView2.animate()
            .alpha(1f)
            .setDuration(1000)
            .setStartDelay(2000)
            .start()


        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3500)

    }
}
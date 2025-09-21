package com.movis.app.view

import android.os.Bundle
import android.util.Log
import android.view.SurfaceControl
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movis.app.ApiManager.ApiManager
import com.movis.app.R
import com.movis.app.adapter.Adapter_genres
import com.movis.app.databinding.ActivityMainBinding
import com.movis.app.fragment.FragmentHome
import com.movis.app.fragment.Fragment_Profile
import com.movis.app.fragment.Fragment_Watchlist
import com.movis.app.model.Model_genres

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // First Run
        loadFragment("HOME")
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> loadFragment("HOME")
                R.id.nav_Watchlist -> loadFragment("WATCHLIST")
                R.id.nav_Profile -> loadFragment("PROFILE")
            }
            true
        }


    }

    private var homeFragment: Fragment? = null
    private var watchlistFragment: Fragment? = null
    private var profileFragment: Fragment? = null

    private fun loadFragment(fragmentTag: String) {
        val transaction = supportFragmentManager.beginTransaction()


        listOf(homeFragment, watchlistFragment, profileFragment).forEach {
            it?.let { transaction.hide(it) }
        }

        val fragment = supportFragmentManager.findFragmentByTag(fragmentTag) ?: when (fragmentTag) {
            "HOME" -> {
                homeFragment = FragmentHome()
                homeFragment!!
            }

            "WATCHLIST" -> {
                watchlistFragment = Fragment_Watchlist()
                watchlistFragment!!
            }

            "PROFILE" -> {
                profileFragment = Fragment_Profile()
                profileFragment!!
            }

            else -> throw IllegalArgumentException("Unknown fragment")
        }

        if (!fragment.isAdded) {
            transaction.add(R.id.FrameLayoutMain, fragment, fragmentTag)
        }

        transaction.show(fragment)
        transaction.commit()
    }


}
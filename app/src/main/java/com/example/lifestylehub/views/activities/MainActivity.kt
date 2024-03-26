package com.example.lifestylehub.views.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.lifestylehub.R
import com.example.lifestylehub.api.PlacesApi
import com.example.lifestylehub.databinding.ActivityMainBinding
import com.example.lifestylehub.utils.LocationUtiles
import com.example.lifestylehub.views.fragments.HomePageFragment
import com.example.lifestylehub.views.fragments.MyLeisurePageFragment
import com.example.lifestylehub.views.fragments.ProfilePageFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var homePageFragment: Fragment
    private lateinit var myLeisurePageFragment: Fragment
    private lateinit var profilePageFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        homePageFragment = HomePageFragment()
        myLeisurePageFragment = MyLeisurePageFragment()
        profilePageFragment = ProfilePageFragment()

        supportFragmentManager.beginTransaction()
            .add(binding.flPages.id, homePageFragment)
            .add(binding.flPages.id, myLeisurePageFragment)
            .add(binding.flPages.id, profilePageFragment)
            .commit()

        setPage(homePageFragment)

        binding.bnvNavBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.iHome -> {
                    setPage(homePageFragment)
                    true
                }
                R.id.iMyLeisure -> {
                    setPage(myLeisurePageFragment)
                    true
                }
                R.id.iProfile -> {
                    setPage(profilePageFragment)
                    true
                }
                else -> false
            }
        }
    }

    override fun onStart() {
        super.onStart()
        LocationUtiles.requestPermissions(this, this)
    }

    private fun setPage(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .hide(homePageFragment)
            .hide(myLeisurePageFragment)
            .hide(profilePageFragment)
            .show(fragment)
            .commit()
    }
}
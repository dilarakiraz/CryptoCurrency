package com.dilarakiraz.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dilarakiraz.myapplication.databinding.ActivityMainBinding
import com.dilarakiraz.myapplication.fragment.HomeFragment
import com.dilarakiraz.myapplication.fragment.MarketFragment
import com.dilarakiraz.myapplication.fragment.WatchListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.bottomNavView.setOnItemSelectedListener {

            when(it.itemId){

                R.id.homeFragment ->replaceFragment(HomeFragment())
                R.id.marketFragment -> replaceFragment(MarketFragment())
                R.id.watchListFragment -> replaceFragment(WatchListFragment())

                else ->{}
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }

}
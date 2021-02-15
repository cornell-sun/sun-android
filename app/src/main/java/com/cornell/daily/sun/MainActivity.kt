package com.cornell.daily.sun

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.cornell.daily.sun.viewmodels.PostViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val postViewModel: PostViewModel by viewModels()

    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.itemIconTintList = null
        navController.navigate(R.id.main_feed_fragment)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        postViewModel.selectPost(null)
        navController.popBackStack()
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        return super.onOptionsItemSelected(item)
    }

}
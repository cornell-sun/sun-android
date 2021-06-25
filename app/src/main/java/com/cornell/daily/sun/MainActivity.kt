package com.cornell.daily.sun

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.cornell.daily.sun.util.InjectorUtils
import com.cornell.daily.sun.viewmodels.SearchViewModel
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val searchViewModel: SearchViewModel by viewModels { InjectorUtils.provideViewModelFactory() }
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var appHeaderView: TextView
    private lateinit var searchButtonView: ImageView
    private lateinit var searchBox: EditText
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(applicationContext)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController

        searchBox = findViewById(R.id.search_box)
        searchBox.visibility = View.GONE
        searchBox.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEND -> {
                    searchViewModel.setQuery(v.text as String)
                    true
                }
                else -> false
            }
        }

        appHeaderView = findViewById(R.id.app_header_title)

        bottomNavigationView = findViewById(R.id.bottom_nav_view)
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.itemIconTintList = null
        showBottomNavigationBar()

        searchButtonView = findViewById(R.id.app_header_search)
        searchButtonView.setOnClickListener { navController.navigate(R.id.main_feed_to_search) }
        navController.navigate(R.id.main_feed_fragment)
    }

    fun setupHeader(fontPath: String, text: String, textSize: Int) {
        appHeaderView.visibility = View.VISIBLE
        appHeaderView.typeface = Typeface.createFromAsset(
            assets,
            fontPath
        )
        appHeaderView.textSize = resources.getDimension(textSize)
        appHeaderView.text = text
    }

    fun hideHeaderText() {
        appHeaderView.visibility = View.GONE
    }

    fun hideSearchButton() {
        searchButtonView.visibility = View.GONE
    }

    fun showSearchButton() {
        searchButtonView.visibility = View.VISIBLE
    }

    fun showSearchBox() {
        searchBox.visibility = View.VISIBLE
    }

    fun hideSearchBox() {
        searchBox.visibility = View.GONE
    }

    fun hideBottomNavigationBar() {
        bottomNavigationView.visibility = View.GONE
    }

    fun showBottomNavigationBar() {
        bottomNavigationView.visibility = View.VISIBLE
    }


}
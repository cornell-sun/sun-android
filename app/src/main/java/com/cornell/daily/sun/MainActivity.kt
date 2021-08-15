package com.cornell.daily.sun

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.cornell.daily.sun.util.InjectorUtils
import com.cornell.daily.sun.viewmodels.SearchViewModel

class MainActivity : AppCompatActivity(), ClickEventHandler {
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

        // Set up Search Input Box here since Search is anticipated to appear in many screens
        searchBox = findViewById(R.id.search_box)
        searchBox.visibility = View.GONE
        searchBox.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEND -> {
                    searchViewModel.setQuery(v.text.toString())
                    closeSearchSoftKeyboard()
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

    override fun navigateTo(holder: RecyclerView.ViewHolder, fragment: Int) {
        navController.navigate(fragment)
    }

    fun setupHeader(font: Int, text: String, textSize: Int) {
        appHeaderView.visibility = View.VISIBLE
        appHeaderView.typeface = ResourcesCompat.getFont(this, font);
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

    fun closeSearchSoftKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(searchBox.windowToken, 0)
        searchBox.clearFocus()
    }
}

interface ClickEventHandler {
    fun navigateTo(holder: RecyclerView.ViewHolder, fragment: Int)
}
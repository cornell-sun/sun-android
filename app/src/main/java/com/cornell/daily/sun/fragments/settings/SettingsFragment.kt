package com.cornell.daily.sun.fragments.settings

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cornell.daily.sun.MainActivity
import com.cornell.daily.sun.data.SettingType
import com.cornell.daily.sun.data.SettingsSection
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import kotlinx.android.synthetic.main.settings_fragment.view.*
import com.cornell.daily.sun.R


class SettingsFragment : Fragment() {
    private lateinit var settingsSections: MutableList<SettingsSection>

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingsSections = mutableListOf<SettingsSection>(
            SettingsSection(this,"ACCOUNT", mutableListOf<SettingType>(
                    SettingType.NOTIFICATIONS,
                    SettingType.SUBSCRIBE,
                    SettingType.THEME
                ), context),
            SettingsSection(this, "SUPPORT", mutableListOf<SettingType>(
                SettingType.APP_FEEDBACK,
                SettingType.PLAYSTORE_RATE,
            ), context),
            SettingsSection(this, "ACCOUNT", mutableListOf<SettingType>(
                SettingType.CONTACT,
                SettingType.HISTORY,
                SettingType.MASTHEAD,
                SettingType.APPTEAM,
                SettingType.PRIVACY_POLICY
            ), context),
        )

        val binding = inflater.inflate(R.layout.settings_fragment, container, false)
        recyclerView = binding.settings_recyclerView
        viewAdapter = SectionedRecyclerViewAdapter()
        viewManager = LinearLayoutManager(context)

        settingsSections.forEach {
            (viewAdapter as SectionedRecyclerViewAdapter).addSection(it)
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewAdapter

        recyclerView.apply {
            adapter = viewAdapter
            layoutManager = viewManager
        }

        val appHeader = (activity as MainActivity).findViewById<TextView>(R.id.app_header_title)
        val appHeaderSearch =
            (activity as MainActivity).findViewById<ImageView>(R.id.app_header_search)
        appHeaderSearch.visibility = View.INVISIBLE
        appHeader.text = (activity as MainActivity).getString(R.string.settings_heading)
        appHeader.typeface =
            ResourcesCompat.getFont((activity as MainActivity), R.font.avenir_medium)
        appHeader.textSize = resources.getDimension(R.dimen.regular_header_text_size)
        appHeader.visibility = View.VISIBLE
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        return binding
    }
}
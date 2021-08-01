package com.cornell.daily.sun.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.cornell.daily.sun.R
import com.cornell.daily.sun.holders.SettingsSectionHolder
import com.cornell.daily.sun.holders.SettingsSectionViewHolder
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import androidx.appcompat.app.AppCompatActivity

internal class SettingsSection(
    var fragment: Fragment,
    var title: String,
    var itemList: List<SettingType>,
    var context: Context?
) : Section(
    SectionParameters.builder()
        .itemResourceId(R.layout.list_item_setting)
        .headerResourceId(R.layout.list_item_settings_header)
        .build()
) {

    override fun getContentItemsTotal(): Int {
        return itemList.size // number of items of this section
    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {
        // return a custom instance of ViewHolder for the items of this section
        return SettingsSectionViewHolder(view)
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemHolder: SettingsSectionViewHolder = holder as SettingsSectionViewHolder
        itemHolder.tvItem.text = itemList[position].title

        itemHolder.itemView.setOnClickListener {
            when (itemList[position]) {
                SettingType.PRIVACY_POLICY -> {
                    val openURL = Intent(android.content.Intent.ACTION_VIEW)
                    openURL.data = Uri.parse("https://cornellsun.com/2008/06/01/cornellsun-com-privacy-policy/")
                    context?.startActivity(openURL)
                } SettingType.MASTHEAD -> {
                    val openURL = Intent(android.content.Intent.ACTION_VIEW)
                    openURL.data = Uri.parse("https://i1.wp.com/cornellsun.com/wp-content/uploads/2021/04/139th-Editorial-Board-Masthead-1-e1617649678689.png?w=1028&ssl=1")
                    context?.startActivity(openURL)
                } else -> {
                    fragment.findNavController().navigate(itemList[position].fragment)
                }
            }
        }
    }

    override fun getHeaderViewHolder(view: View): RecyclerView.ViewHolder {
        return SettingsSectionHolder(view)
    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder) {
        (holder as SettingsSectionHolder).tvItem.text = title
    }
}
package com.cornell.daily.sun.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cornell.daily.sun.R


internal class SettingsSectionHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvItem: TextView

    init {
        tvItem = itemView.findViewById(R.id.settings_section_textView)
    }
}
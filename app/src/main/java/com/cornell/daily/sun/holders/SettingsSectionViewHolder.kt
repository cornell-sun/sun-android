package com.cornell.daily.sun.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cornell.daily.sun.R


internal class SettingsSectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvItem: TextView = itemView.findViewById(R.id.setting_textView)

}
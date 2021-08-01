package com.cornell.daily.sun.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cornell.daily.sun.R
import com.cornell.daily.sun.data.ThemeType

class ThemeAdapter(
    private val context: Context?,
    private var themeSections: MutableList<ThemeType>
) : RecyclerView.Adapter<ThemeAdapter.ViewHolder>() {

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.findViewById(R.id.list_item_notifications_icon)
        val title: TextView = itemView.findViewById(R.id.list_item_appteam_name_textView)
        val description: TextView = itemView.findViewById(R.id.list_item_notifications_description)
        val switch: Switch = itemView.findViewById(R.id.list_item_notifications_switch)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val feedPostView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_item_setting_notification, parent, false)
        return ViewHolder(feedPostView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notification = themeSections[position]

        holder.icon.setImageResource(notification.icon)
        holder.title.text = notification.title
        holder.description.text = notification.description

        Log.d("STUFF", "SETTING section for notifications")
    }

    override fun getItemCount(): Int {
        return themeSections.size
    }

}
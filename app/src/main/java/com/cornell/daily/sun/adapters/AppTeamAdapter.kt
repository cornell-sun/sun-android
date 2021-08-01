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
import com.cornell.daily.sun.data.AppTeamMember
import com.cornell.daily.sun.data.NotificationsType

class AppTeamAdapter(
    private val context: Context?,
    private var teamMembers: MutableList<AppTeamMember>
) : RecyclerView.Adapter<AppTeamAdapter.ViewHolder>() {

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val emoji: TextView = itemView.findViewById(R.id.list_item_appteam_emoji_textView)
        val name: TextView = itemView.findViewById(R.id.list_item_appteam_name_textView)
        val role: TextView = itemView.findViewById(R.id.list_item_appteam_role_textView)
        val origin: TextView = itemView.findViewById(R.id.list_item_appteam_origin_textView)
        val liner: TextView = itemView.findViewById(R.id.list_item_appteam_liner_textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val feedPostView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_item_setting_appteam, parent, false)
        return ViewHolder(feedPostView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val teamMember = teamMembers[position]

        holder.emoji.text = teamMember.emoji
        holder.name.text = teamMember.name
        holder.role.text = teamMember.role
        holder.origin.text = teamMember.origin
        holder.liner.text = teamMember.liner

        Log.d("STUFF", "SETTING section for notifications")
    }

    override fun getItemCount(): Int {
        return teamMembers.size
    }

}
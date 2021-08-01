package com.cornell.daily.sun.data

import com.cornell.daily.sun.R

enum class NotificationsType(val id: Int, val title: String, val icon: Int, val description: String) {
    BREAKING_NEWS(1, "Breaking News", R.drawable.notifications_icon_breakingnews,
        "News you need to know as it happens"),
    LOCAL_NEWS(2, "Local News", R.drawable.notifications_icon_localnews,
        "Cornell and the surrounding Ithaca community"),
    OPINION(3, "Opinion", R.drawable.notifications_icon_opinion,
        "Thoughts from your peers, professors, and alumni"),
    SPORTS(4, "Sports", R.drawable.notifications_icon_sports,
        "Scores, features, recaps, and more about the Red"),
    MULTIMEDIA(5, "Multimedia", R.drawable.notifications_icon_multimedia,
        "Photos, videos, and interviews about the Cornell community"),
    ARTS_AND_ENTERTAINMENT(6, "Arts and Entertainment", R.drawable.notifications_icon_arts,
        "Music, movies, fashion, and performance"),
    SCIENCE(7, "Science", R.drawable.notifications_icon_science,
        "What you need to know about research and Cornell Tech"),
    DINING(8, "Dining", R.drawable.notifications_icon_dining,
        "All the food news on campus and in the Ithaca area"),
}
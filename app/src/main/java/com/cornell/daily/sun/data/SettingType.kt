package com.cornell.daily.sun.data

import com.cornell.daily.sun.R

enum class SettingType(val id: Int, val title: String, val fragment: Int) {
    NOTIFICATIONS(1, "Notifications", R.id.settings_notifications_fragment),
    SUBSCRIBE(2, "Subscribe", R.id.settings_subscribe_fragment),
    THEME(3, "Theme", R.id.settings_theme_fragment),
    APP_FEEDBACK(4, "Send App Feedback", R.id.settings_feedback_fragment),
    PLAYSTORE_RATE(5, "Rate on Google Play Store", R.id.settings_notifications_fragment),
    CONTACT(6, "Contact the Sun", R.id.settings_contact_fragment),
    HISTORY(7, "History", R.id.settings_history_fragment),
    MASTHEAD(8, "The Masthead", R.id.settings_notifications_fragment),
    APPTEAM(9, "The App Team", R.id.settings_appteam_fragment),
    PRIVACY_POLICY(10, "Privacy Policy", R.id.settings_notifications_fragment)
}
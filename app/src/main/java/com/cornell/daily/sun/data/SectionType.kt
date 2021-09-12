package com.cornell.daily.sun.data

import com.cornell.daily.sun.R

enum class SectionType(val id: Int, val title: String, val image: Int) {
    FEATURED(1, "Featured", R.drawable.sections_news_selector),
    NEWS(2, "News", R.drawable.sections_news_selector),
    OPINION(3, "Opinion", R.drawable.sections_opinion_selector),
    SPORTS(4, "Sports", R.drawable.sections_sports_selector),
    ARTS(5, "Arts", R.drawable.sections_arts_selector),
    SCIENCE(6, "Science", R.drawable.sections_science_selector),
    DINING(7, "Dining", R.drawable.sections_dining_selector),
    MULTIMEDIA(8, "Multimedia", R.drawable.sections_multimedia_selector);
}
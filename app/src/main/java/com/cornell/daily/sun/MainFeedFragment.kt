package com.cornell.daily.sun

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cornell.daily.sun.adapters.MainFeedSectionAdapter
import com.cornell.daily.sun.data.Article
import com.cornell.daily.sun.data.Section

class MainFeedFragment : Fragment() {
    private lateinit var mainFeedSectionsRecyclerView: RecyclerView
    private lateinit var mainFeedAdapter: RecyclerView.Adapter<*>
    private lateinit var mainFeedLayoutManager: LinearLayoutManager
    private var sections = mutableListOf(
        Section("News", mutableListOf(Article("Go Big Red"))),
        Section("News", mutableListOf(Article("Go Big Red")))
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_feed, container, false)
        mainFeedSectionsRecyclerView = view.findViewById(R.id.main_feed_section_list)
        mainFeedLayoutManager = LinearLayoutManager(activity)
        mainFeedAdapter = MainFeedSectionAdapter(sections)
        mainFeedSectionsRecyclerView.apply {
            adapter = mainFeedAdapter
            layoutManager = mainFeedLayoutManager
        }
        return view
    }
}
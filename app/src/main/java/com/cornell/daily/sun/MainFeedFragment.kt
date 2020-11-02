package com.cornell.daily.sun

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cornell.daily.sun.adapters.MainFeedAdapter

class MainFeedFragment : Fragment() {
    private lateinit var mainFeedRecyclerView: RecyclerView
    private lateinit var mainFeedAdapter: RecyclerView.Adapter<*>
    private lateinit var mainFeedLayoutManager: LinearLayoutManager
    private var articles = mutableListOf("Go Big Red", "Haha", "Presidential Debate Tonight")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_feed, container, false)
        mainFeedRecyclerView = view.findViewById<RecyclerView>(R.id.main_feed_list)
        mainFeedLayoutManager = LinearLayoutManager(activity)
        mainFeedAdapter = MainFeedAdapter(articles)
        mainFeedRecyclerView.apply {
            adapter = mainFeedAdapter
            layoutManager = mainFeedLayoutManager
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}
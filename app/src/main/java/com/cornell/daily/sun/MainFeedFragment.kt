package com.cornell.daily.sun

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cornell.daily.sun.adapters.MainFeedSectionAdapter
import com.cornell.daily.sun.viewmodels.MainFeedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main_feed.view.*

@AndroidEntryPoint
class MainFeedFragment : Fragment() {
    private val mainFeedViewModel: MainFeedViewModel by lazy {
        ViewModelProvider(this).get(MainFeedViewModel::class.java)
    }
    private lateinit var mainFeedSectionsRecyclerView: RecyclerView
    private lateinit var mainFeedSectionAdapter: RecyclerView.Adapter<*>
    private lateinit var mainFeedSectionLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.fragment_main_feed, container, false)

        mainFeedSectionsRecyclerView = binding.main_feed_section_list
        mainFeedSectionLayoutManager = LinearLayoutManager(activity)
        mainFeedSectionAdapter = MainFeedSectionAdapter()

        mainFeedSectionsRecyclerView.apply {
            adapter = mainFeedSectionAdapter
            layoutManager = mainFeedSectionLayoutManager
        }

        mainFeedViewModel.sections.observe(viewLifecycleOwner) { sections ->
            (mainFeedSectionAdapter as MainFeedSectionAdapter).submitList(sections)
        }

        return binding
    }
}
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
import kotlinx.android.synthetic.main.main_feed_fragment.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainFeedFragment : Fragment() {
    private val mainFeedViewModel: MainFeedViewModel by lazy {
        ViewModelProvider(this).get(MainFeedViewModel::class.java)
    }
    private lateinit var mainFeedSectionsRecyclerView: RecyclerView


    @Inject
    lateinit var mainFeedSectionAdapter: MainFeedSectionAdapter
    private lateinit var mainFeedSectionLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.main_feed_fragment, container, false)

        mainFeedSectionsRecyclerView = binding.main_feed_section_list
        mainFeedSectionLayoutManager = LinearLayoutManager(activity)

        mainFeedSectionsRecyclerView.apply {
            adapter = mainFeedSectionAdapter
            layoutManager = mainFeedSectionLayoutManager
        }

        mainFeedViewModel.sections.observe(viewLifecycleOwner) { sections ->
            mainFeedSectionAdapter.submitList(sections)
        }

        binding.main_feed_swipe_container.setOnRefreshListener {
            GlobalScope.launch {
                mainFeedViewModel.loadSections()
                binding.main_feed_swipe_container.isRefreshing = false
            }
        }

        return binding
    }
}
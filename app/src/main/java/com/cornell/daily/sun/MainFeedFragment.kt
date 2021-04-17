package com.cornell.daily.sun

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cornell.daily.sun.adapters.MainFeedSectionAdapter
import com.cornell.daily.sun.util.InjectorUtils
import com.cornell.daily.sun.viewmodels.MainFeedViewModel
import com.cornell.daily.sun.viewmodels.PostViewModel
import kotlinx.android.synthetic.main.main_feed_fragment.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainFeedFragment : Fragment() {
    private val mainFeedViewModel: MainFeedViewModel by viewModels { InjectorUtils.provideViewModelFactory() }

    private val postViewModel: PostViewModel by activityViewModels { InjectorUtils.provideViewModelFactory() }

    private lateinit var mainFeedSectionsRecyclerView: RecyclerView

    private lateinit var mainFeedSectionAdapter: MainFeedSectionAdapter

    private lateinit var mainFeedSectionLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.main_feed_fragment, container, false)
        mainFeedSectionAdapter =
            MainFeedSectionAdapter(context, postViewModel::pushPost)
        mainFeedSectionsRecyclerView = binding.main_feed_section_list
        mainFeedSectionLayoutManager = LinearLayoutManager(activity)

        mainFeedSectionsRecyclerView.apply {
            adapter = mainFeedSectionAdapter
            layoutManager = mainFeedSectionLayoutManager
        }

        mainFeedViewModel.sections.observe(viewLifecycleOwner) { sections ->
            mainFeedSectionAdapter.submitList(sections)
        }

        postViewModel.postStack.observe(viewLifecycleOwner) {
            if (!it.isEmpty()) {
                findNavController().navigate(R.id.main_feed_to_post)
            }
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
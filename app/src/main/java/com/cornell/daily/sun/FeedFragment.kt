package com.cornell.daily.sun

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cornell.daily.sun.adapters.FeedAdapter
import com.cornell.daily.sun.util.InjectorUtils
import com.cornell.daily.sun.viewmodels.PostViewModel
import com.cornell.daily.sun.viewmodels.SectionsViewModel
import kotlinx.android.synthetic.main.feed_fragment.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Used with For You and and Sections
 */
class FeedFragment : Fragment() {
    private val postViewModel: PostViewModel by activityViewModels { InjectorUtils.provideViewModelFactory() }
    private val sectionViewModel: SectionsViewModel by activityViewModels { InjectorUtils.provideViewModelFactory() }
    private lateinit var feedAdapter: FeedAdapter
    private lateinit var feedRecyclerView: RecyclerView
    private lateinit var feedLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.feed_fragment, container, false)
        setHasOptionsMenu(true)
        feedAdapter = FeedAdapter(postViewModel::pushPost, context)
        feedRecyclerView = binding.feed_recycler_view
        feedLayoutManager = LinearLayoutManager(activity)
        feedRecyclerView.apply {
            adapter = feedAdapter
            layoutManager = feedLayoutManager
        }

        viewLifecycleOwner.lifecycleScope.launch {
            sectionViewModel.flow.collectLatest { pagingData ->
                feedAdapter.submitData(pagingData)
            }
        }

        postViewModel.postStack.observe(viewLifecycleOwner) {
            if (!it.isEmpty()) {
                findNavController().navigate(R.id.feed_to_post)
            }
        }

        binding.feed_swipe_container.setOnRefreshListener {
            GlobalScope.launch {
                feedAdapter.refresh()
                binding.feed_swipe_container.isRefreshing = false
            }
        }

        sectionViewModel.section.observe(viewLifecycleOwner) {
            if (it != null) {
                GlobalScope.launch {
                    feedAdapter.refresh()
                    binding.feed_swipe_container.isRefreshing = false
                }
            }
        }

        val appHeader = (activity as MainActivity).findViewById<TextView>(R.id.app_header_title)
        appHeader.text = sectionViewModel.section.value?.title
        appHeader.visibility = View.VISIBLE
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        postViewModel.postStack.value?.clear()
        return binding
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        sectionViewModel.setSection(null)
        findNavController().navigate(R.id.feed_to_sections)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        return super.onOptionsItemSelected(item)
    }
}
package com.cornell.daily.sun.fragments

import android.os.Bundle
import android.util.Log
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
import com.cornell.daily.sun.MainActivity
import com.cornell.daily.sun.R
import com.cornell.daily.sun.adapters.SectionsPostsAdapter
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
    private lateinit var feedAdapter: SectionsPostsAdapter
    private lateinit var feedRecyclerView: RecyclerView
    private lateinit var feedLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.feed_fragment, container, false)
        setHasOptionsMenu(true)
        feedAdapter = SectionsPostsAdapter(postViewModel::pushPost, context)
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
        sectionViewModel.section.value?.title?.let {
            (activity as MainActivity).setupHeader(
                R.font.avenir_medium,
                it, R.dimen.regular_header_text_size
            )
        }
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        postViewModel.postStack.value?.clear()
        return binding
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        sectionViewModel.setSection(null)
        findNavController().popBackStack()
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        return super.onOptionsItemSelected(item)
    }
}
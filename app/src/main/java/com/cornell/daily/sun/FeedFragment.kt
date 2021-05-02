package com.cornell.daily.sun

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
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
import com.cornell.daily.sun.viewmodels.SectionViewModel
import kotlinx.android.synthetic.main.feed_fragment.view.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Used with For You and and Sections
 */
class FeedFragment: Fragment() {
    private val postViewModel: PostViewModel by activityViewModels { InjectorUtils.provideViewModelFactory() }
    private val sectionViewModel: SectionViewModel by activityViewModels { InjectorUtils.provideViewModelFactory() }
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

        lifecycleScope.launch {
            sectionViewModel.flow.collectLatest { pagingData ->
                feedAdapter.submitData(pagingData)
            }
        }

        postViewModel.postStack.observe(viewLifecycleOwner) {
            if (!it.isEmpty()) {
                findNavController().navigate(R.id.feed_to_post)
            }
        }

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return binding
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            sectionViewModel.setSection(null)
            findNavController().navigate(R.id.feed_to_sections)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        return super.onOptionsItemSelected(item)
    }
}
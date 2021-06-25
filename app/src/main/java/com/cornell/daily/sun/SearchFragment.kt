package com.cornell.daily.sun

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cornell.daily.sun.adapters.CompactPostsAdapter
import com.cornell.daily.sun.util.InjectorUtils
import com.cornell.daily.sun.viewmodels.PostViewModel
import com.cornell.daily.sun.viewmodels.SearchViewModel
import kotlinx.android.synthetic.main.search_fragment.view.*

class SearchFragment : Fragment() {

    private val postViewModel: PostViewModel by activityViewModels { InjectorUtils.provideViewModelFactory() }
    private val searchViewModel: SearchViewModel by activityViewModels { InjectorUtils.provideViewModelFactory() }

    private lateinit var searchContentRecyclerView: RecyclerView

    private lateinit var searchContentAdapter: CompactPostsAdapter

    private lateinit var searchContentLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.search_fragment, container, false)
        setHasOptionsMenu(true)
        searchContentRecyclerView = binding.search_view_content
        searchContentAdapter = CompactPostsAdapter(activity, postViewModel::pushPost)
        (activity as MainActivity).hideHeaderText()
        (activity as MainActivity).hideSearchButton()
        (activity as MainActivity).showSearchBox()
        (activity as MainActivity).hideBottomNavigationBar()
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return binding
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().navigate(R.id.main_feed_fragment)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        return super.onOptionsItemSelected(item)
    }
}
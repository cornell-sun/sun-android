package com.cornell.daily.sun

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cornell.daily.sun.adapters.CompactPostsAdapter
import com.cornell.daily.sun.util.InjectorUtils
import com.cornell.daily.sun.viewmodels.PostViewModel
import com.cornell.daily.sun.viewmodels.SearchViewModel
import kotlinx.android.synthetic.main.feed_fragment.view.*
import kotlinx.android.synthetic.main.search_fragment.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
        searchContentLayoutManager = LinearLayoutManager(activity)
        searchContentRecyclerView.apply {
            adapter = searchContentAdapter
            layoutManager = searchContentLayoutManager
        }


        postViewModel.postStack.observe(viewLifecycleOwner) {
            if (!it.isEmpty()) {
                findNavController().navigate(R.id.search_to_post)
            }
        }

        searchViewModel.query.observe(viewLifecycleOwner) {
            if (it != null) {
                GlobalScope.launch {
                    searchContentAdapter.refresh()
                    binding.search_swipe_container.isRefreshing = false
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.flow.collectLatest { pagingData ->
                searchContentAdapter.submitData(pagingData)
            }
        }

        (activity as MainActivity).hideHeaderText()
        (activity as MainActivity).hideSearchButton()
        (activity as MainActivity).showSearchBox()
        (activity as MainActivity).hideBottomNavigationBar()
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return binding
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().navigate(R.id.main_feed_fragment)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as MainActivity).closeSearchSoftKeyboard()
        return super.onOptionsItemSelected(item)
    }
}
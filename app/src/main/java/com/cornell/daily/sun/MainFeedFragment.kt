package com.cornell.daily.sun

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cornell.daily.sun.adapters.MainFeedSectionAdapter
import com.cornell.daily.sun.util.InjectorUtils
import com.cornell.daily.sun.viewmodels.MainFeedViewModel
import com.cornell.daily.sun.viewmodels.PostViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main_feed_fragment.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainFeedFragment : Fragment() {
    private val mainFeedViewModel: MainFeedViewModel by viewModels { InjectorUtils.provideMainFeeViewModelFactory() }

    private val postViewModel: PostViewModel by activityViewModels()

    private lateinit var mainFeedSectionsRecyclerView: RecyclerView

    private lateinit var mainFeedSectionAdapter: MainFeedSectionAdapter

    private lateinit var mainFeedSectionLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.main_feed_fragment, container, false)

        mainFeedSectionAdapter = MainFeedSectionAdapter(context)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = view.findNavController()
        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNavigationView?.setupWithNavController(navController)
        bottomNavigationView?.itemIconTintList = null
    }
}
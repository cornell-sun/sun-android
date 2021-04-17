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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.cornell.daily.sun.adapters.SectionsAdapter
import com.cornell.daily.sun.data.SectionType
import com.cornell.daily.sun.util.InjectorUtils
import com.cornell.daily.sun.viewmodels.SectionViewModel
import kotlinx.android.synthetic.main.sections_fragment.view.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class SectionsFragment : Fragment() {
    private var sections = mutableListOf(
        SectionType.NEWS,
        SectionType.OPINION,
        SectionType.SPORTS,
        SectionType.ARTS,
        SectionType.SCIENCE,
        SectionType.DINING,
        SectionType.MULTIMEDIA
    )
    private val sectionViewModel: SectionViewModel by activityViewModels { InjectorUtils.provideViewModelFactory() }
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: GridLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.sections_fragment, container, false)

        recyclerView = binding.my_recycler_view
        viewManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        viewAdapter = SectionsAdapter(sectionViewModel::setSection, sections)

        recyclerView.apply {
            adapter = viewAdapter
            layoutManager = viewManager
        }

        viewManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == 0) {
                    2
                } else {
                    1
                }
            }
        }


        sectionViewModel.section.observe(viewLifecycleOwner) {
            if (it != null) {
                findNavController().navigate(R.id.sections_to_section_feed)
            }
        }

        return binding

    }
}
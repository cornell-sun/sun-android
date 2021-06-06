package com.cornell.daily.sun

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.cornell.daily.sun.adapters.SectionsAdapter
import com.cornell.daily.sun.data.SectionType
import com.cornell.daily.sun.util.InjectorUtils
import com.cornell.daily.sun.viewmodels.SectionsViewModel
import kotlinx.android.synthetic.main.sections_fragment.view.*


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
    private val sectionViewModel: SectionsViewModel by activityViewModels { InjectorUtils.provideViewModelFactory() }
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
        viewAdapter = SectionsAdapter(activity, sectionViewModel::setSection, sections)

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

        val appHeader = (activity as MainActivity).findViewById<TextView>(R.id.app_header_title)

        val appHeaderSearch =
            (activity as MainActivity).findViewById<ImageView>(R.id.app_header_search)
        appHeaderSearch.visibility = View.INVISIBLE
        appHeader.text = (activity as MainActivity).getString(R.string.sections_heading)
        appHeader.typeface =
            Typeface.createFromAsset((activity as MainActivity).assets, "fonts/avenir_medium.ttf")
        appHeader.textSize = resources.getDimension(R.dimen.regular_header_text_size)
        appHeader.visibility = View.VISIBLE
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        return binding

    }
}
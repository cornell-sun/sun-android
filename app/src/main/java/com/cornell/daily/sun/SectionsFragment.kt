package com.cornell.daily.sun

//import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.cornell.daily.sun.adapters.SectionsAdapter
import com.cornell.daily.sun.data.SectionType
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
        viewAdapter = SectionsAdapter(sections)

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

        return binding

    }
}
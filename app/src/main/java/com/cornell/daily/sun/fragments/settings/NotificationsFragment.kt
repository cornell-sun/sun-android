package com.cornell.daily.sun.fragments.settings

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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cornell.daily.sun.MainActivity
import com.cornell.daily.sun.R
import com.cornell.daily.sun.adapters.NotificationsAdapter
import com.cornell.daily.sun.data.NotificationsType
import com.cornell.daily.sun.util.InjectorUtils
import com.cornell.daily.sun.viewmodels.SectionsViewModel
import kotlinx.android.synthetic.main.settings_notifications_fragment.view.*


class NotificationsFragment : Fragment() {
    private var notifications = mutableListOf(
        NotificationsType.BREAKING_NEWS,
        NotificationsType.LOCAL_NEWS,
        NotificationsType.OPINION,
        NotificationsType.SPORTS,
        NotificationsType.MULTIMEDIA,
        NotificationsType.ARTS_AND_ENTERTAINMENT,
        NotificationsType.SCIENCE,
        NotificationsType.DINING
    )
    private val sectionViewModel: SectionsViewModel by activityViewModels { InjectorUtils.provideViewModelFactory() }
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.settings_notifications_fragment, container, false)

        recyclerView = binding.notifications_recyclerView
        viewManager = LinearLayoutManager(activity)
        viewAdapter = NotificationsAdapter(context, notifications)

        recyclerView.apply {
            adapter = viewAdapter
            layoutManager = viewManager
        }

        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )

        val appHeader = (activity as MainActivity).findViewById<TextView>(R.id.app_header_title)
        val appHeaderSearch =
            (activity as MainActivity).findViewById<ImageView>(R.id.app_header_search)
        appHeaderSearch.visibility = View.INVISIBLE
        appHeader.text = (activity as MainActivity).getString(R.string.notifications_heading)
        appHeader.typeface =
            Typeface.createFromAsset(context?.assets, "fonts/avenir_medium.ttf")
        appHeader.textSize = resources.getDimension(R.dimen.regular_header_text_size)
        appHeader.visibility = View.VISIBLE
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        return binding
    }
}
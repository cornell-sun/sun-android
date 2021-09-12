package com.cornell.daily.sun.fragments.settings

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cornell.daily.sun.MainActivity
import com.cornell.daily.sun.R
import com.cornell.daily.sun.adapters.AppTeamAdapter
import com.cornell.daily.sun.data.AppTeamMember
import kotlinx.android.synthetic.main.settings_appteam_fragment.view.*


class AppTeamFragment : Fragment() {
    private var members = mutableListOf(
        AppTeamMember.AUSTIN,
        AppTeamMember.MINDY,
        AppTeamMember.CHRIS,
        AppTeamMember.BRENDAN,
        AppTeamMember.ALEXIS,
        AppTeamMember.ADITYA,
        AppTeamMember.THEO,
        AppTeamMember.MIKE,
        AppTeamMember.CAMERON,
        AppTeamMember.CONNIE,
        AppTeamMember.SOYEE,
        AppTeamMember.SOPHIE
    )
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.settings_appteam_fragment, container, false)
        setHasOptionsMenu(true)
        recyclerView = binding.appteam_recyclerView
        viewManager = LinearLayoutManager(activity)
        viewAdapter = AppTeamAdapter(context, members)

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
        appHeader.text = ""

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).hideSearchButton()
        (activity as MainActivity).hideSearchBox()
        (activity as MainActivity).hideHeaderText()

        return binding
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().navigate(R.id.appteam_to_settings)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        return super.onOptionsItemSelected(item)
    }
}
package com.cornell.daily.sun.fragments.settings

import android.graphics.Typeface
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.cornell.daily.sun.MainActivity
import com.cornell.daily.sun.R
import kotlinx.android.synthetic.main.settings_history_fragment.view.*


class HistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.settings_history_fragment, container, false)

        binding.history_body_textView.movementMethod = ScrollingMovementMethod()

        val appHeader = (activity as MainActivity).findViewById<TextView>(R.id.app_header_title)
        val appHeaderSearch =
            (activity as MainActivity).findViewById<ImageView>(R.id.app_header_search)
        appHeaderSearch.visibility = View.INVISIBLE
        appHeader.text = (activity as MainActivity).getString(R.string.history_heading)
        appHeader.typeface =
            Typeface.createFromAsset(context?.assets, "fonts/avenir_medium.ttf")
        appHeader.textSize = resources.getDimension(R.dimen.regular_header_text_size)
        appHeader.visibility = View.VISIBLE
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        return binding
    }

}
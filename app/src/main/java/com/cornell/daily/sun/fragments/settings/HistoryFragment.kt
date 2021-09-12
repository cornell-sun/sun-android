package com.cornell.daily.sun.fragments.settings

import android.graphics.Typeface
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
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
        setHasOptionsMenu(true)
        binding.history_body_textView.movementMethod = ScrollingMovementMethod()

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).hideSearchButton()
        (activity as MainActivity).hideSearchBox()
        (activity as MainActivity).hideHeaderText()

        return binding
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().navigate(R.id.history_to_settings)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        return super.onOptionsItemSelected(item)
    }

}
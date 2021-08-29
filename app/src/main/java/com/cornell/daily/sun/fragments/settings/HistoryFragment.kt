package com.cornell.daily.sun.fragments.settings

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

        return binding
    }

}
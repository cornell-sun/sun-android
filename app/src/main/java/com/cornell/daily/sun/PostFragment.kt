package com.cornell.daily.sun

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.cornell.daily.sun.viewmodels.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostFragment : Fragment() {
    private val postViewModel: PostViewModel by activityViewModels()

}
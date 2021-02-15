package com.cornell.daily.sun

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cornell.daily.sun.adapters.PostContentAdapter
import com.cornell.daily.sun.data.*
import com.cornell.daily.sun.viewmodels.PostViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.post_fragment.view.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

class PostFragment : Fragment() {
    private lateinit var postContentRecyclerView: RecyclerView

    private lateinit var postContentAdapter: PostContentAdapter

    private lateinit var postContentLayoutManager: LinearLayoutManager

    private val postViewModel: PostViewModel by activityViewModels()

    private lateinit var post: PostInfoDict


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.post_fragment, container, false)
        post = postViewModel.selectedPost.value!!
        postContentAdapter = PostContentAdapter(context)
        postContentRecyclerView = binding.post_view_content
        postContentRecyclerView.setHasFixedSize(true)
        postContentLayoutManager = LinearLayoutManager(activity)
        val postContent = post.content?.let { parseHtml(it) }
        postContent?.add(
            0,
            PostMeta(
                post.primaryCategory,
                post.title,
                post.getMediumImageUrl(),
                post.featuredMediaCaption,
                post.featuredMediaCredit,
                context?.getString(R.string.byline, post.getByline()),
                post.getFormattedDate()
            )
        )
        postContent?.add(SuggestedPosts(post.suggestedPosts))
        postContentAdapter.submitList(postContent)
        postContentRecyclerView.apply {
            layoutManager = postContentLayoutManager
            adapter = postContentAdapter
        }
        val bottomNavigationView =
            activity?.findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNavigationView?.visibility = View.GONE
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return binding
    }

    private fun parseHtml(text: String): MutableList<PostContent> {
        val document = Jsoup.parse(text)
        val elements = document.allElements
        val sections = mutableListOf<PostContent>()
        var swapImageAndText = false
        for (element in elements) {
            when (element.tagName()) {
                "p" -> {
                    swapImageAndText =
                        element.children().any { it.tag().toString() == "img" }
                    val paragraphSection = parseParagraphNode(element)
                    if (sections.size > 0) {
                        val lastSection = sections.last()
                        when {
                            paragraphSection is Caption && lastSection is ImageCredit -> {
                                sections.add(sections.size - 1, paragraphSection)
                            }
                            else -> sections.add(paragraphSection)
                        }
                    } else {
                        sections.add(parseParagraphNode(element))
                    }
                }
                "img" -> {
                    val imageSection = parseImageNode(element) ?: continue
                    sections.add(imageSection)
                    if (swapImageAndText) {
                        val tempSection = sections[sections.size - 2]
                        sections[sections.size - 2] = sections[sections.size - 1]
                        sections[sections.size - 1] = tempSection
                    }
                }
            }
        }
        return sections
    }

    private fun parseParagraphNode(pElement: Element): PostContent {
        val text = pElement.text()
        return when {
            pElement.hasAttr("wp-media-credit") -> ImageCredit(text)
            pElement.hasAttr("wp-caption-text") -> Caption(text)
            else -> {
                val imgNodes = pElement.children().filter { it.tag().toString() == "img" }
                imgNodes.forEach { it.remove() }
                return Paragraph(pElement.outerHtml())
            }
        }
    }

    private fun parseImageNode(imgElement: Element): PostContent? {
        val src = imgElement.select("img[src]")
        var srcString = src.attr("src")
        if (src.isEmpty() || srcString == "") {
            srcString = imgElement.attr("data-lazy")
        }
        return if (srcString != "") Image(srcString) else null
    }

    private fun parseAsideNode(asideElement: Element): PostContent? {
        val elementText = asideElement.text()
        if (asideElement.hasClass("module") && elementText != "") {
            return Blockquote(elementText)
        }
        return null
    }

    private fun parseHeadingNode(headingElement: Element): PostContent {
        val elementText = headingElement.text()
        return Heading(elementText)
    }
}
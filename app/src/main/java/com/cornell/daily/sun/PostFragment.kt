package com.cornell.daily.sun

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cornell.daily.sun.adapters.PostContentAdapter
import com.cornell.daily.sun.data.*
import com.cornell.daily.sun.util.InjectorUtils
import com.cornell.daily.sun.viewmodels.PostViewModel
import kotlinx.android.synthetic.main.post_fragment.view.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

class PostFragment : Fragment() {
    private lateinit var postContentRecyclerView: RecyclerView

    private lateinit var postContentAdapter: PostContentAdapter

    private lateinit var postContentLayoutManager: LinearLayoutManager

    private val postViewModel: PostViewModel by activityViewModels { InjectorUtils.provideViewModelFactory() }

    private lateinit var post: PostInfoDict

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.post_fragment, container, false)
        setHasOptionsMenu(true)
        postContentAdapter = PostContentAdapter(context, postViewModel::loadPost)
        postContentRecyclerView = binding.post_view_content

        postContentRecyclerView.setHasFixedSize(true)
        postContentLayoutManager = LinearLayoutManager(activity)
        setAdapterContent()
        postContentRecyclerView.apply {
            layoutManager = postContentLayoutManager
            adapter = postContentAdapter
        }

        postViewModel.postStack.observe(viewLifecycleOwner) {
            if (!it.isEmpty()) {
                setAdapterContent()
            }
        }

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val appHeaderSearch =
            (activity as MainActivity).findViewById<ImageView>(R.id.app_header_search)
        appHeaderSearch.visibility = View.INVISIBLE
        val appHeader = (activity as MainActivity).findViewById<TextView>(R.id.app_header_title)
        appHeader.visibility = View.INVISIBLE
        return binding
    }

    private fun setAdapterContent() {
        post = postViewModel.postStack.value?.first()!!
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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        postViewModel.popPost()
        if (postViewModel.postStack.value?.isEmpty() == true) {
            findNavController().popBackStack()
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
        return super.onOptionsItemSelected(item)
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
                "aside" -> {
                    val blockQuote = parseAsideNode(element)
                    if (blockQuote != null) sections.add(blockQuote)
                }
                "h2" -> {
                    val heading = parseHeadingNode(element)
                    sections.add(heading)
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
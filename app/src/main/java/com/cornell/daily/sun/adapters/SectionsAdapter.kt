package com.cornell.daily.sun.adapters

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cornell.daily.sun.R
import com.cornell.daily.sun.data.SectionType


class SectionsAdapter(
    private val context: Context?,
    private val selectSectionCallback: (SectionType) -> Unit,
    private var sections: MutableList<SectionType>
) : RecyclerView.Adapter<SectionsAdapter.ViewHolder>() {

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.findViewById(R.id.list_item_sections_icon)
        val textView: TextView = itemView.findViewById(R.id.list_item_sections_text_view)
    }

    private val imageViewHeightRatioBig = 78.0 / 100.0
    private val imageViewWidthRatioBig = 186.0 / 345.0
    private val imageViewHeightRatioSmall = 50.0 / 100.0
    private val imageViewWidthRatioSmall = 50.0 / 165.0

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            WIDTH_FULL
        } else {
            WIDTH_HALF
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.list_item_sections_cell,
                parent,
                false
            ) as View

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = sections[position].title
        holder.textView.typeface =
            ResourcesCompat.getFont(context!!, R.font.sonnenstrahl_ausgezeichnet)
        holder.icon.setImageResource(sections[position].image)

        val iconParams = holder.icon.layoutParams as ConstraintLayout.LayoutParams
        val cellParams = holder.itemView.layoutParams as GridLayoutManager.LayoutParams
        if (position == 0) {
            iconParams.matchConstraintPercentHeight = imageViewHeightRatioBig.toFloat()
            iconParams.matchConstraintPercentWidth = imageViewWidthRatioBig.toFloat()
            iconParams.bottomMargin = 0

            cellParams.leftMargin = 30
            cellParams.rightMargin = 30
        } else {
            iconParams.matchConstraintPercentHeight = imageViewHeightRatioSmall.toFloat()
            iconParams.matchConstraintPercentWidth = imageViewWidthRatioSmall.toFloat()
            iconParams.bottomMargin = 15

            if (position % 2 == 1) {
                cellParams.leftMargin = 30
                cellParams.rightMargin = 15
            } else {
                cellParams.leftMargin = 15
                cellParams.rightMargin = 30
            }
        }
        holder.icon.requestLayout()
        holder.itemView.requestLayout()
        holder.itemView.setOnClickListener {
            selectSectionCallback(sections[position])
        }
    }

    override fun getItemCount() = sections.size

    companion object {
        private const val WIDTH_FULL = 0
        private const val WIDTH_HALF = 1
    }
}
package com.cornell.daily.sun.adapters

import android.content.Context
import android.graphics.drawable.Icon
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cornell.daily.sun.R
import com.cornell.daily.sun.data.SectionType


class SectionsAdapter(
    private var sections: MutableList<SectionType>,
    private val context: Context
) : RecyclerView.Adapter<SectionsAdapter.ViewHolder>() {

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon : ImageView = itemView.findViewById(R.id.icon)
        val textView : TextView = itemView.findViewById(R.id.text_view)
    }

    private val WIDTH_FULL = 0
    private val WIDTH_HALF = 1

    val imageViewHeightRatioBig = 78.0 / 100.0
    val imageViewWidthRatioBig = 186.0 / 345.0
    val imageViewHeightRatioSmall = 50.0 / 100.0
    val imageViewWidthRatioSmall = 50.0 / 165.0

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return WIDTH_FULL
        } else {
            return WIDTH_HALF
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.sections_recyclerview_cell,
                parent,
                false
            ) as View

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = sections.get(position).title
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
    }

    override fun getItemCount() = sections.size
}
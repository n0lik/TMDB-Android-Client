package com.n0lik.sample.common.ui.widget.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * All params should be in px
 *
 * @property spaceBetween
 * @property paddingLeft
 * @property paddingTop
 * @property paddingRight
 * @property paddingBottom
 */
class SpacingItemDecorator(
    private val spaceBetween: Int,
    private val paddingLeft: Int = 0,
    private val paddingTop: Int = 0,
    private val paddingRight: Int = 0,
    private val paddingBottom: Int = 0
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        val position = parent.getChildAdapterPosition(view)
        val orientation = when (val layoutManager = parent.layoutManager) {
            is LinearLayoutManager -> {
                layoutManager.orientation
            }
            is GridLayoutManager -> {
                layoutManager.orientation
            }
            else -> {
                RecyclerView.HORIZONTAL
            }
        }
        if (orientation == RecyclerView.HORIZONTAL) {
            when {
                position == 0 -> {
                    outRect.set(paddingLeft, paddingTop, spaceBetween, paddingBottom)
                }
                position < parent.adapter!!.itemCount - 1 -> {
                    outRect.set(0, paddingTop, spaceBetween, paddingBottom)
                }
                else -> {
                    outRect.set(0, paddingTop, paddingRight, paddingBottom)
                }
            }
        } else {
            when {
                position == 0 -> {
                    outRect.set(paddingLeft, paddingTop, paddingRight, paddingBottom)
                }
                position < parent.adapter!!.itemCount - 1 -> {
                    outRect.set(paddingLeft, 0, paddingRight, spaceBetween)
                }
                else -> {
                    outRect.set(paddingLeft, 0, paddingRight, paddingBottom)
                }
            }
        }
    }
}
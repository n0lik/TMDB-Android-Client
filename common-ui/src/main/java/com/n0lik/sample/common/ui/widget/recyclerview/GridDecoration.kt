package com.n0lik.sample.common.ui.widget.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridDecoration(
    private val spacing: Int,
    private val spanCount: Int
) : RecyclerView.ItemDecoration() {

    private var isNeedLeftSpacing = false

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val frameWidth = ((parent.width - spacing.toFloat() * (spanCount - 1)) / spanCount).toInt()
        val padding = parent.width / spanCount - frameWidth
        val itemPosition = (view.layoutParams as RecyclerView.LayoutParams).bindingAdapterPosition

        if (itemPosition < spanCount) {
            outRect.top = 0
        } else {
            outRect.top = spacing
        }

        when {
            itemPosition % spanCount == 0 -> {
                outRect.left = 0
                outRect.right = padding
                isNeedLeftSpacing = true
            }

            (itemPosition + 1) % spanCount == 0 -> {
                isNeedLeftSpacing = false
                outRect.right = 0
                outRect.left = padding
            }

            isNeedLeftSpacing -> {
                isNeedLeftSpacing = false
                outRect.left = spacing - padding

                if ((itemPosition + 2) % spanCount == 0) {
                    outRect.right = spacing - padding
                } else {
                    outRect.right = spacing / 2
                }
            }

            (itemPosition + 2) % spanCount == 0 -> {
                isNeedLeftSpacing = false
                outRect.left = spacing / 2
                outRect.right = spacing - padding
            }

            else -> {
                isNeedLeftSpacing = false
                outRect.left = spacing / 2
                outRect.right = spacing / 2
            }
        }

        outRect.bottom = 0
    }
}
package com.n0lik.sample.common.ui.ext

import android.view.View
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.removeAdapterOnDetach() {
    addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {

        override fun onViewAttachedToWindow(v: View) = Unit

        override fun onViewDetachedFromWindow(v: View) {
            // Find the better way to not leak the adapter
            adapter = null
            removeOnAttachStateChangeListener(this)
        }
    })
}
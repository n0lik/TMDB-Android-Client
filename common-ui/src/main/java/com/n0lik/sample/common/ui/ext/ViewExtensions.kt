package com.n0lik.sample.common.ui.ext

import android.view.View

fun View.visibleIf(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}
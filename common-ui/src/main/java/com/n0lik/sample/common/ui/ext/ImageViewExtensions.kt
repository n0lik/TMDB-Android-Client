package com.n0lik.sample.common.ui.ext

import android.widget.ImageView
import androidx.core.view.doOnLayout
import com.n0lik.sample.common.model.Image
import com.n0lik.sample.common.ui.utils.RequestOptions
import com.n0lik.sample.common.ui.utils.TmdbImageUrlProvider.selectSize
import com.n0lik.sample.common.ui.utils.loadImageByUrl

fun ImageView.loadTmdbImage(
    image: Image?,
    requestOptions: RequestOptions.() -> RequestOptions = { this }
) {
    if (image == null || image.sizes.isEmpty()) {
        return
    }

    if (this.width != 0 && this.height != 0) {
        val url = image.selectSize(this.width, this.height)
        loadImageByUrl(url, requestOptions)
    } else {
        doOnLayout {
            loadTmdbImage(image, requestOptions)
        }
    }
}
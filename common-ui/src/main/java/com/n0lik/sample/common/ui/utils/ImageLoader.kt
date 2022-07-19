package com.n0lik.sample.common.ui.utils

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions as GlideRequestOptions
import com.bumptech.glide.request.target.Target as GlideTarget

fun ImageView.load(url: String?) {
    Glide.with(context)
        .load(url)
        .into(this)
}

@SuppressLint("CheckResult")
fun ImageView.loadImageByUrl(
    url: String?,
    requestOptions: RequestOptions.() -> RequestOptions = { this }
) {
    val options = requestOptions(RequestOptions())

    Glide.with(context)
        .load(url)
        .apply(mapOptions(options))
        .error(options.errorRes)
        .also { request ->
            if (options.useCrossFade) {
                request.transition(DrawableTransitionOptions.withCrossFade())
            }
            if (options.thumbnail != 0f) {
                request.thumbnail(Glide.with(this).asDrawable().sizeMultiplier(options.thumbnail))
            }
        }
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: GlideTarget<Drawable>?,
                isFirstResource: Boolean
            ): Boolean = false

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: GlideTarget<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                options.onImageReady()
                return false
            }
        })
        .into(this)
}

@SuppressLint("CheckResult")
private fun mapOptions(options: RequestOptions): GlideRequestOptions {
    val result = GlideRequestOptions()
    val transformations = mutableListOf<Transformation<Bitmap>>()

    options.cropOptions?.let { cropOptions ->
        when (cropOptions) {
            CropOptions.CenterCrop -> CenterCrop()
            CropOptions.FitCenter -> FitCenter()
            CropOptions.CenterInside -> CenterInside()
            CropOptions.Circle -> CircleCrop()
        }.also {
            transformations.add(it)
        }
    }

    if (options.cornerRadius > 0) {
        transformations.add(RoundedCorners(options.cornerRadius))
    }

    if (transformations.isNotEmpty()) {
        result.apply {
            if (transformations.size == 1) {
                transform(transformations.first())
            } else {
                transform(MultiTransformation(transformations))
            }
        }
    }

    return result
}

fun ImageView.clear() {
    Glide.with(this).clear(this)
}
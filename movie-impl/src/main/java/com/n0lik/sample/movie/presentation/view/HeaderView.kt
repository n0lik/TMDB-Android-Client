package com.n0lik.sample.movie.presentation.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.n0lik.sample.movie.impl.R

class HeaderView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private val curvePath = Path()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var curveOffset: Float

    init {
        var attributes: TypedArray? = null
        try {
            attributes = context.obtainStyledAttributes(attrs, R.styleable.HeaderView)
            curveOffset = attributes.getInt(R.styleable.HeaderView_hvCurveOffset, 0).toFloat()
        } finally {
            attributes?.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        curvePath.apply {
            reset()
            val height = measuredHeight.toFloat()
            val width = measuredWidth.toFloat()
            lineTo(0f, height - curveOffset)
            quadTo(
                width / 2,
                height + curveOffset,
                width,
                height - curveOffset
            )
            lineTo(width, 0f) // right to left
            close()
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(curvePath, paint)
        canvas.clipPath(curvePath)
        super.onDraw(canvas)
    }

    @SuppressWarnings("unused")
    fun setOffset(curveOffset: Float) {
        this.curveOffset = curveOffset
        requestLayout()
    }
}
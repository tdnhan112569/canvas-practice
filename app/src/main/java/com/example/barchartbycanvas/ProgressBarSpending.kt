package com.example.barchartbycanvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.util.toRange
import java.lang.Exception

class ProgressBarSpending(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private lateinit var staticLayout: StaticLayout
    private val spacingMultiplier = 1f
    private val spacingAddition = 0f
    private val includePadding = false
    private val alignment = Layout.Alignment.ALIGN_OPPOSITE
    private val paddingStart = 5.dpToPx.toFloat()
    private val radius = 4.dpToPx.toFloat()
    private var percent: Double = 0.0
    private val paintProgressBarBackground = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.color_e4e4e8)
        style = Paint.Style.FILL
    }
    private val paintProgressBarActual = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.color_e4e4e8)
        style = Paint.Style.FILL
    }
    private val titlePaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.color_292828)
        style = Paint.Style.FILL
        textSize = 10.spToPx
        typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBarBackground(canvas)
        drawBarActualProgress(canvas)
        drawPercentTitle(canvas)
    }

    private fun drawPercentTitle(canvas: Canvas) {
        val title = "$percent %"
        val widthOfTitle = titlePaint.measureText(title)
        staticLayout = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StaticLayout.Builder
                .obtain(title, 0, title.length, titlePaint, widthOfTitle.toInt())
                .setAlignment(alignment)
                .setLineSpacing(spacingAddition, spacingMultiplier)
                .setIncludePad(includePadding)
                .build()
        } else {
            StaticLayout(
                title,
                titlePaint,
                widthOfTitle.toInt(),
                alignment,
                spacingMultiplier,
                spacingAddition,
                includePadding
            )
        }
        canvas.save()
        val moveY = (height - staticLayout.height) / 2
        canvas.translate(paddingStart, moveY.toFloat())
        staticLayout.draw(canvas)
        canvas.restore()
    }

    private fun drawBarActualProgress(canvas: Canvas) {
        val widthOfProgress = percent * width.toFloat() / 100
        canvas.apply {
            drawRoundRect(0f, 0f, widthOfProgress.toFloat(), height.toFloat(), radius, radius, paintProgressBarActual)
        }
    }

    private fun drawBarBackground(canvas: Canvas) {
        canvas.apply {
            drawRoundRect(0f, 0f, width.toFloat(), height.toFloat(), radius, radius, paintProgressBarBackground)
        }
    }

    enum class CategorySpending {
        OTHER,
        BUYING,
        EATING,
        TRAVEL,
        SERVICE_HELPFUL,
        HEALTHY,
        AIRPLANE,
        MOVING,
        ENTERTAINMENT,
        SKINCARE,
        INVESTMENT,
        EDUCATION,
        ASSURANCE;

        companion object {
            fun findCategoryById(categoryId: Int): CategorySpending {
                return values().find { it.ordinal == categoryId } ?: OTHER
            }
        }
    }

    data class Category(val id: String = "", val percent: Double = 0.0)

    fun setCategory(categoryInfo: Category) {
        paintProgressBarActual.apply {
            color = getColor(categoryInfo.id)
        }
        percent = categoryInfo.percent
        invalidate()
    }

    private fun getColor(categoryId: String = ""): Int {
        val id: Int = try {
            if (categoryId.isNotEmpty()) categoryId.toInt() else 0
        } catch (ex: Exception) {
            0
        }
        val color = when (CategorySpending.findCategoryById(id)) {
            CategorySpending.OTHER -> R.color.color_F8BA5E
            CategorySpending.BUYING -> R.color.color_5073B8
            CategorySpending.EATING -> R.color.color_6150B8
            CategorySpending.TRAVEL -> R.color.color_A050B8
            CategorySpending.SERVICE_HELPFUL -> R.color.color_B85081
            CategorySpending.HEALTHY -> R.color.color_FF3B3B
            CategorySpending.AIRPLANE -> R.color.color_3BD7FF
            CategorySpending.MOVING -> R.color.color_1CCB5C
            CategorySpending.ENTERTAINMENT -> R.color.color_F1963A
            CategorySpending.SKINCARE -> R.color.color_72B112
            CategorySpending.INVESTMENT -> R.color.color_F16548
            CategorySpending.EDUCATION -> R.color.color_0606E3
            CategorySpending.ASSURANCE -> R.color.color_0AB39C
        }
        return ContextCompat.getColor(context, color)
    }

}
package mx.ulai.indra.util

import android.content.Context
import android.util.DisplayMetrics
import androidx.core.math.MathUtils
import mx.ulai.indra.R
import mx.ulai.indra.util.Constants.GRID_SPAN_COUNT_MAX
import mx.ulai.indra.util.Constants.GRID_SPAN_COUNT_MIN

object GridConfig {
    fun calculateGridSpanCount(context: Context): Int {
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics
        val displayWidth = displayMetrics.widthPixels
        val itemSize: Int = context.resources.getDimensionPixelSize(R.dimen.item_size)
        val gridSpanCount = displayWidth / itemSize
        return MathUtils.clamp(gridSpanCount, GRID_SPAN_COUNT_MIN, GRID_SPAN_COUNT_MAX)
    }
}
package com.zyn.wnview.utils

import android.content.Context

/**
 * 변환 관련 오브젝트
 */
class Conversions(private val context: Context) {
    fun dpToPx(dp: Int): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density + 0.5f).toInt()
    }

}

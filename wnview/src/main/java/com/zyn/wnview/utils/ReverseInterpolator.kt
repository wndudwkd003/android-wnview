package com.zyn.wnview.utils

import android.view.animation.Interpolator
import kotlin.math.abs

class ReverseInterpolator : Interpolator {
    override fun getInterpolation(value: Float): Float {
        return abs(1.0f - value)
    }
}
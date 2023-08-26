package com.zyn.wnview.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.viewpager2.widget.ViewPager2

class CircleIndicator : LinearLayout {

    private val TAG = CircleIndicator::class.simpleName


    private var mCircleDiameter: Int = 20
    private var mCount: Int = 5
    private var mLastCount = -1

    private var mMarginTop: Int = 10
    private var mMarginBottom: Int = 10
    private var mMarginStart: Int = 10
    private var mMarginEnd: Int = 10



    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = resources.getColor(android.R.color.darker_gray)
        style = Paint.Style.FILL
    }


    private var mViewPager2: ViewPager2? = null


    constructor(context: Context) : super(context) {
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

    init {
        orientation = HORIZONTAL
    }

    fun setMargin(start: Int, top: Int, end: Int, bottom: Int) {
        mMarginStart = start
        mMarginTop = top
        mMarginEnd = end
        mMarginBottom = bottom
    }


    fun setViewPager(viewPager: ViewPager2) {
        mViewPager2 = viewPager

        if (mViewPager2?.adapter != null) {
            mCount = viewPager.adapter!!.itemCount

            mViewPager2?.unregisterOnPageChangeCallback(mPageChangeListener)
            mViewPager2?.registerOnPageChangeCallback(mPageChangeListener)
            mPageChangeListener.onPageSelected(mViewPager2!!.currentItem)
            // Log.d(TAG, mCount.toString())
        }

    }


    private val mPageChangeListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            if (position == mLastCount
                || mViewPager2?.adapter == null
                || mViewPager2?.adapter!!.itemCount <= 0) {
                return
            }

            Log.d(TAG, position.toString())
        }
    }

    override fun invalidate() {
        super.invalidate()

        for (i in 0 until mCount) {
            val circleView = object : View(context) {
                override fun onDraw(canvas: Canvas?) {
                    super.onDraw(canvas)
                    canvas?.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), (mCircleDiameter / 2).toFloat(), mPaint)
                }
            }
            val params = LayoutParams(mCircleDiameter, mCircleDiameter)
            params.setMargins(mMarginStart, mMarginTop, mMarginEnd, mMarginBottom)
            circleView.layoutParams = params
            addView(circleView)
        }

    }
}
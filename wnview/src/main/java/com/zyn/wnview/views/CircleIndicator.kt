package com.zyn.wnview.views

import android.animation.Animator
import android.animation.AnimatorInflater
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.AnimatorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.zyn.wnview.R
import com.zyn.wnview.utils.Conversions
import com.zyn.wnview.utils.ReverseInterpolator


class CircleIndicator : LinearLayout {

    private val TAG = CircleIndicator::class.simpleName

    private lateinit var mContext: Context

    private var mCircleDiameter: Int = 0
    private var mExpandedDiameter: Int = 0
    private var mCount: Int = 0
    private var mLastCount = -1
    private var mLastPosition = -1

    private var mDefaultMargin: Int = 20
    private var mMarginTop: Int = 0
    private var mMarginBottom: Int = 0
    private var mMarginStart: Int = 0
    private var mMarginEnd: Int = 0

    private lateinit var mAnimatorOut: Animator
    private lateinit var mAnimatorIn: Animator
    private lateinit var mImmediateAnimatorOut: Animator
    private lateinit var mImmediateAnimatorIn: Animator

    @AnimatorRes
    var mAnimatorResId = R.animator.scale_with_alpha

    @AnimatorRes
    var mAnimatorReverseResId = 0

    @DrawableRes
    var mBackgroundResId = R.drawable.white_radius

    @DrawableRes
    var mUnselectedBackgroundId = 0


    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, android.R.color.white)
        style = Paint.Style.FILL
    }


    private var mViewPager2: ViewPager2? = null


    constructor(context: Context) : super(context) {
        initCircleIndicator(context)
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initCircleIndicator(context)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initCircleIndicator(context)
    }



    /////////// 사용자가 사용할 수 있는 함수 ///////////


    /**
     * margin 설정
     */
    fun setMargin(start: Int, top: Int, end: Int, bottom: Int) {
        mMarginStart = start
        mMarginTop = top
        mMarginEnd = end
        mMarginBottom = bottom
    }

    override fun invalidate() {
        super.invalidate()

        createCircleIndicator()
    }




    /////////// 내부적으로 동작하는 함수 ///////////




    private fun initCircleIndicator(context: Context) {
        mContext = context
        orientation = HORIZONTAL
        setMargin(
            Conversions.dpToPx(mContext, mDefaultMargin / 2),
            Conversions.dpToPx(mContext, mDefaultMargin),
            Conversions.dpToPx(mContext, mDefaultMargin / 2),
            Conversions.dpToPx(mContext, mDefaultMargin)
        )
        mCircleDiameter = Conversions.dpToPx(mContext, 10)
        mExpandedDiameter = Conversions.dpToPx(mContext, 30)

        mAnimatorOut = createAnimatorOut()
        mImmediateAnimatorOut = createAnimatorOut()
        mImmediateAnimatorOut.duration = 0

        mAnimatorIn = createAnimatorIn()
        mImmediateAnimatorIn = createAnimatorIn()
        mImmediateAnimatorIn.duration = 0

    }




    private fun createAnimatorOut(): Animator {
        return AnimatorInflater.loadAnimator(context, mAnimatorResId)
    }

    private fun createAnimatorIn(): Animator {
        val animatorIn: Animator = AnimatorInflater.loadAnimator(context, mAnimatorResId)
        animatorIn.interpolator = ReverseInterpolator()

        return animatorIn
    }


    private fun createCircleIndicator() {
        for (i in 0 until mCount) {
            val indicator = createCircleView()
            setIndicatorBackground(indicator, mUnselectedBackgroundId)

            mImmediateAnimatorIn.setTarget(indicator)
            mImmediateAnimatorIn.start()
            mImmediateAnimatorIn.end()
            addView(indicator)

            mLastPosition = mCount
        }
    }





    fun setViewPager(viewPager: ViewPager2) {
        mViewPager2 = viewPager

        if (mViewPager2?.adapter != null) {
            mCount = viewPager.adapter!!.itemCount

            mViewPager2?.unregisterOnPageChangeCallback(mPageChangeListener)
            mViewPager2?.registerOnPageChangeCallback(mPageChangeListener)
            mPageChangeListener.onPageSelected(mViewPager2!!.currentItem)
        }
    }





    private fun createCircleView(): View {
        val circleView = object : View(mContext) {
            override fun onDraw(canvas: Canvas?) {
                super.onDraw(canvas)
                canvas?.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), (width  / 2).toFloat(), mPaint)
            }
        }.apply {
            val params = LayoutParams(mCircleDiameter, mCircleDiameter)
            params.setMargins(mMarginStart, mMarginTop, mMarginEnd, mMarginBottom)
            layoutParams = params
        }
        return circleView
    }

    private val mPageChangeListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            if (position == mLastCount
                || mViewPager2?.adapter == null
                || mViewPager2?.adapter!!.itemCount <= 0) {
                return
            }
            animatePageSelected(position)
        }
    }

    private fun animatePageSelected(position: Int) {

        if (mLastPosition == position) {
            return
        }

        if (mAnimatorIn.isRunning) {
            mAnimatorIn.end()
            mAnimatorIn.cancel()
        }

        if (mAnimatorOut.isRunning) {
            mAnimatorOut.end()
            mAnimatorOut.cancel()
        }

        val currentIndicator = getChildAt(mLastPosition)
        if (mLastPosition >= 0 && currentIndicator != null) {
            setIndicatorBackground(currentIndicator, mBackgroundResId)
            mAnimatorIn.setTarget(currentIndicator)
            mAnimatorIn.start()
        }

        val selectedIndicator = getChildAt(position)
        if (selectedIndicator != null) {
            setIndicatorBackground(selectedIndicator, mUnselectedBackgroundId)

            mAnimatorOut.setTarget(selectedIndicator)
            mAnimatorOut.start()
        }

        mLastPosition = position
    }

    private fun setIndicatorBackground(indicator: View, resId: Int) {
        indicator.setBackgroundResource(resId)
    }




}
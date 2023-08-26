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
import com.zyn.wnview.utils.AnimateUtil
import com.zyn.wnview.utils.Conversions
import com.zyn.wnview.utils.ReverseInterpolator


class CircleIndicator : LinearLayout {

    private val TAG = CircleIndicator::class.simpleName

    private lateinit var mContext: Context

    private var circleDiameter: Int = 0
    private var expandedDiameter: Int = 0
    private var count: Int = 0
    private var lastCount = -1
    private var lastPosition = -1

    private var defaultMargin: Int = 20
    private var marginTop: Int = 0
    private var marginBottom: Int = 0
    private var marginStart: Int = 0
    private var marginEnd: Int = 0

    private val animateUtil = AnimateUtil()


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


    private lateinit var viewPager2: ViewPager2


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
        marginStart = start
        marginTop = top
        marginEnd = end
        marginBottom = bottom
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
            Conversions.dpToPx(mContext, defaultMargin / 2),
            Conversions.dpToPx(mContext, defaultMargin),
            Conversions.dpToPx(mContext, defaultMargin / 2),
            Conversions.dpToPx(mContext, defaultMargin)
        )
        circleDiameter = Conversions.dpToPx(mContext, 10)
        expandedDiameter = Conversions.dpToPx(mContext, 30)

        animateUtil.animatorOut = createAnimatorOut()
        animateUtil.immediateAnimatorOut = createAnimatorOut()
        animateUtil.immediateAnimatorOut.duration = 0

        animateUtil.animatorIn = createAnimatorIn()
        animateUtil.immediateAnimatorIn = createAnimatorIn()
        animateUtil.immediateAnimatorIn.duration = 0

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
        for (i in 0 until count) {
            val indicator = createCircleView()
            setIndicatorBackground(indicator, mUnselectedBackgroundId)

            animateUtil.animationPlay(animateUtil.immediateAnimatorIn, indicator)

            addView(indicator)

            lastPosition = count
        }
    }

    fun setViewPager(vp2: ViewPager2) {
        viewPager2 = vp2

        if (viewPager2.adapter != null && viewPager2.adapter?.itemCount != null) {
            count = viewPager2.adapter!!.itemCount
            viewPager2.unregisterOnPageChangeCallback(mPageChangeListener)
            viewPager2.registerOnPageChangeCallback(mPageChangeListener)
            mPageChangeListener.onPageSelected(viewPager2.currentItem)
        }

    }


    private fun createCircleView(): View {
        val circleView = object : View(mContext) {
            override fun onDraw(canvas: Canvas?) {
                super.onDraw(canvas)
                canvas?.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), (width  / 2).toFloat(), mPaint)
            }
        }.apply {
            val params = LayoutParams(circleDiameter, circleDiameter)
            params.setMargins(marginStart, marginTop, marginEnd, marginBottom)
            layoutParams = params
        }
        return circleView
    }

    private val mPageChangeListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            if (position == lastCount
                || viewPager2.adapter == null
                || viewPager2.adapter!!.itemCount <= 0) {
                return
            }
            animatePageSelected(position)
        }
    }

    private fun animatePageSelected(position: Int) {

        if (lastPosition == position) {
            return
        }

        animateUtil.allStop()

        val currentIndicator = getChildAt(lastPosition)
        if (lastPosition >= 0 && currentIndicator != null) {
            setIndicatorBackground(currentIndicator, mBackgroundResId)
            animateUtil.animationPlay(animateUtil.animatorIn, currentIndicator)
        }

        val selectedIndicator = getChildAt(position)
        if (selectedIndicator != null) {
            setIndicatorBackground(selectedIndicator, mUnselectedBackgroundId)
            animateUtil.animationPlay(animateUtil.animatorOut, selectedIndicator)
        }

        lastPosition = position
    }

    private fun setIndicatorBackground(indicator: View, resId: Int) {
        indicator.setBackgroundResource(resId)
    }


}
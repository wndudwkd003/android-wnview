package com.zyn.wnview.views

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.AnimatorRes
import androidx.viewpager2.widget.ViewPager2
import com.zyn.wnview.R
import com.zyn.wnview.utils.AnimateUtil
import com.zyn.wnview.utils.Conversions


class CircleIndicator : LinearLayout {

    private val TAG = CircleIndicator::class.simpleName
    private val animateUtil = AnimateUtil()
    private lateinit var conversions : Conversions

    private lateinit var mContext: Context

    private lateinit var innerPaint: Paint
    private lateinit var outerPaint: Paint

    private lateinit var viewPager2: ViewPager2

    private var count: Int = 0
    private var lastCount = -1
    private var lastPosition = -1

    private var indicatorMarginStart: Int = 0
    private var indicatorMarginTop: Int = 0
    private var indicatorMarginRight: Int = 0
    private var indicatorMarginBottom: Int = 0

    private var innerVisibility: Int = View.VISIBLE
    private var outerVisibility: Int = View.INVISIBLE
    private var innerColor: Int = Color.WHITE
    private var outerColor: Int = Color.WHITE
    private var selectedAlpha: Float = 1.0f
    private var defaultAlpha: Float = 0.5f
    private var radius: Int = 10
    private var defaultMagnification: Float = 1.0f
    private var selectedMagnification: Float = 1.5f

    constructor(context: Context) : super(context) {
        initCircleIndicator(context, null)
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initCircleIndicator(context, attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initCircleIndicator(context, attrs)

    }

    fun setMargin(start: Int, top: Int, end: Int, bottom: Int) {
        indicatorMarginStart = conversions.dpToPx(start)
        indicatorMarginTop = conversions.dpToPx(top)
        indicatorMarginRight = conversions.dpToPx(end)
        indicatorMarginBottom = conversions.dpToPx(bottom)
    }

    override fun invalidate() {
        super.invalidate()

        createCircleIndicator()
    }

    private fun initCircleIndicator(context: Context, attrs: AttributeSet?) {
        mContext = context
        conversions = Conversions(mContext)

        setDefaultValues()

        if (attrs != null) {
            setAttributesFromXml(attrs)
        }
    }


    private fun setDefaultValues() {
        // 마진 설정
        setMargin(10, 20, 10, 10)

        // 애니메이터 설정
        animateUtil.animatorOut = createAnimatorOut()
        animateUtil.immediateAnimatorOut = createAnimatorOut()
        animateUtil.immediateAnimatorOut.duration = 0

        animateUtil.animatorIn = createAnimatorIn()
        animateUtil.immediateAnimatorIn = createAnimatorIn()
        animateUtil.immediateAnimatorIn.duration = 0

        // 기본 색깔 설정
        innerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            style = Paint.Style.FILL
        }

        outerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK
            style = Paint.Style.STROKE
        }
    }

    private fun setAttributesFromXml(attrs: AttributeSet?) {
        // xml 값 설정
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleIndicator)
            try {
                // Indicator visibility 설정
                innerVisibility = typedArray.getInt(R.styleable.CircleIndicator_innerVisibility, View.VISIBLE)
                outerVisibility = typedArray.getInt(R.styleable.CircleIndicator_outerVisibility, View.VISIBLE)

                // Indicator 색깔 설정
                innerColor = typedArray.getColor(R.styleable.CircleIndicator_innerColor, Color.WHITE)
                outerColor = typedArray.getColor(R.styleable.CircleIndicator_outerColor, Color.BLACK)

                // Indicator 투명도 xml 값 설정
                selectedAlpha = typedArray.getFloat(R.styleable.CircleIndicator_selectedAlpha, 1.0f)
                defaultAlpha = typedArray.getFloat(R.styleable.CircleIndicator_defaultAlpha, 0.5f)

                // Indicator 반지름 xml 값 설정
                radius = typedArray.getInt(R.styleable.CircleIndicator_radius, 10)
                radius = conversions.dpToPx(radius)

                // Indicator 배율 xml 값 설정
                defaultMagnification = typedArray.getFloat(R.styleable.CircleIndicator_defaultMagnification, 1.0f)
                selectedMagnification = typedArray.getFloat(R.styleable.CircleIndicator_selectedMagnification, 1.5f)

                // Indicator 마진 xml 값 설정
                indicatorMarginStart = typedArray.getInt(R.styleable.CircleIndicator_indicatorMarginStart, 10)
                indicatorMarginTop = typedArray.getInt(R.styleable.CircleIndicator_indicatorMarginTop, 20)
                indicatorMarginRight = typedArray.getInt(R.styleable.CircleIndicator_indicatorMarginRight, 10)
                indicatorMarginBottom = typedArray.getInt(R.styleable.CircleIndicator_indicatorMarginBottom, 10)

                // Indicator 마진 설정
                setMargin(indicatorMarginStart, indicatorMarginTop, indicatorMarginRight, indicatorMarginBottom)

                innerPaint.color = innerColor   // 내곽선 색깔 설정
                outerPaint.color = innerColor   // 외곽선 색깔 설정

            } finally {
                typedArray.recycle()
            }
        }
    }




    private fun createAnimatorOut(): Animator {
        val alphaAnimator = ObjectAnimator.ofFloat(this, "alpha", defaultAlpha, selectedAlpha)
        val scaleXAnimator = ObjectAnimator.ofFloat(this, "scaleX", 1.0f, selectedMagnification)
        val scaleYAnimator = ObjectAnimator.ofFloat(this, "scaleY", 1.0f, selectedMagnification)

        return animateUtil.createAnimatorSet(this, alphaAnimator, scaleXAnimator, scaleYAnimator)
    }


    private fun createAnimatorIn(): Animator {
        val alphaAnimator = ObjectAnimator.ofFloat(this, "alpha", selectedAlpha, defaultAlpha)
        val scaleXAnimator = ObjectAnimator.ofFloat(this, "scaleX", selectedMagnification, 1.0f)
        val scaleYAnimator = ObjectAnimator.ofFloat(this, "scaleY", selectedMagnification, 1.0f)

        return animateUtil.createAnimatorSet(this, alphaAnimator, scaleXAnimator, scaleYAnimator)
    }

    private fun createCircleIndicator() {
        for (i in 0 until count) {
            val indicator = createCircleView()

            animateUtil.play(animateUtil.immediateAnimatorIn, indicator)
            animateUtil.immediateAnimatorIn.end()

            addView(indicator)
        }

        lastPosition = count
    }

    fun setViewPager(vp2: ViewPager2) {
        viewPager2 = vp2

        if (viewPager2.adapter != null && viewPager2.adapter?.itemCount != null) {
            count = viewPager2.adapter!!.itemCount
            viewPager2.unregisterOnPageChangeCallback(pageChangeListener)
            viewPager2.registerOnPageChangeCallback(pageChangeListener)
            pageChangeListener.onPageSelected(viewPager2.currentItem)
        }

    }


    private fun createCircleView(): View {
        val circleView = object : View(mContext) {
            override fun onDraw(canvas: Canvas) {
                super.onDraw(canvas)
                if (innerVisibility == VISIBLE) {
                    canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), (width  / 2).toFloat(), innerPaint)
                }
                if (outerVisibility == VISIBLE) {
                    canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), (width  / 2).toFloat(), outerPaint)
                }
            }
        }.apply {
            val params = LayoutParams(
                conversions.dpToPx(radius),
                conversions.dpToPx(radius))
            params.setMargins(indicatorMarginStart, indicatorMarginTop, indicatorMarginRight, indicatorMarginBottom)
            layoutParams = params
        }
        return circleView
    }

    private val pageChangeListener = object : ViewPager2.OnPageChangeCallback() {
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
            animateUtil.play(animateUtil.animatorIn, currentIndicator)
        }

        val selectedIndicator = getChildAt(position)
        if (selectedIndicator != null) {
            animateUtil.play(animateUtil.animatorOut, selectedIndicator)
        }

        lastPosition = position
    }

}
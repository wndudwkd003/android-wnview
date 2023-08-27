package com.zyn.wnview.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.zyn.wnview.R
import com.zyn.wnview.adapter.ViewPager2Adapter
import com.zyn.wnview.fragment.ImageFragment
import com.zyn.wnview.utils.Conversions
import com.zyn.wnview.utils.LayoutDirection

/**
 * 이미지 슬라이더
 *
 * @author Kim Juyoung
 */
class ImageSlider : WnView {

    private lateinit var mContext: Context

    private var imageList: List<Int>? = null
    private var viewPager2: ViewPager2? = null
    private lateinit var circleIndicator: CircleIndicator
    private lateinit var viewPager2Adapter: ViewPager2Adapter

    private lateinit var converter: Conversions

    private var listener: OnImageClickListener? = null


    private var indicatorViewWidth: Int = 0
    private var indicatorViewHeight: Int = 0

    // 내/외부 보이는 설정
    private var indicatorInnerVisibility: Int = VISIBLE
    private var indicatorOuterVisibility: Int = INVISIBLE

    // 내/외부 색상 설정
    private var indicatorInnerColor: Int = Color.WHITE
    private var indicatorOuterColor: Int = Color.BLACK

    // 선택에 따른 투명도 설정
    private var indicatorSelectedAlpha: Float = 1.0f
    private var indicatorDefaultAlpha: Float = 0.5f

    // 기본 크기 설정
    private var indicatorRadius: Int = 10

    // 선택에 따른 배율 설정
    var indicatorDefaultMagnification: Float = 1.0f
    var indicatorSelectedMagnification: Float = 1.5f

    // Indicator 각각의 마진 설정
    var indicatorMarginStart: Int = 0
    var indicatorMarginTop: Int = 0
    var indicatorMarginRight: Int = 0
    var indicatorMarginBottom: Int = 0




    /**
     * indicator 방향
     * 기본 값 = CENTER_BOTTOM
     */
    private var indicatorDirection: LayoutDirection.Position = LayoutDirection.Position.CENTER_BOTTOM



    interface OnImageClickListener {
        fun onImageClick(position: Int, context: Context)

    }


    var imageItemList: List<Int>?
        get() = imageList
        set(value) {
            imageList = value
        }

    constructor(context: Context) : super(context) {
        initImageSlider(context, null)
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initImageSlider(context, attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initImageSlider(context, attrs)
    }


    fun setIndicatorDirection(direction: LayoutDirection.Position) {
        indicatorDirection = direction
    }

    fun setOnImageClickListener(listener: OnImageClickListener) {
        this.listener = listener
    }


    var imageScaleType: ImageView.ScaleType? = null


    private fun initImageSlider(context: Context, attrs: AttributeSet?) {

        mContext = context
        converter = Conversions(mContext)




        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImageSlider)
            try {
                indicatorViewWidth = typedArray.getInt(R.styleable.ImageSlider_indicatorViewWidth, LinearLayout.LayoutParams.WRAP_CONTENT)
                indicatorViewHeight = typedArray.getInt(R.styleable.ImageSlider_indicatorViewHeight, 0)

                indicatorInnerVisibility = typedArray.getInt(R.styleable.ImageSlider_indicatorInnerVisibility, VISIBLE)
                indicatorOuterVisibility = typedArray.getInt(R.styleable.ImageSlider_indicatorOuterVisibility, INVISIBLE)

                indicatorInnerColor = typedArray.getColor(R.styleable.ImageSlider_indicatorInnerColor, Color.WHITE)
                indicatorOuterColor = typedArray.getColor(R.styleable.ImageSlider_indicatorOuterColor, Color.BLACK)

                indicatorSelectedAlpha = typedArray.getFloat(R.styleable.ImageSlider_indicatorSelectedAlpha, 1.0f)
                indicatorDefaultAlpha = typedArray.getFloat(R.styleable.ImageSlider_indicatorDefaultAlpha, 0.5f)

                indicatorRadius = typedArray.getInt(R.styleable.ImageSlider_indicatorRadius, 10)
                indicatorRadius = converter.dpToPx(indicatorRadius)

                indicatorDefaultMagnification = typedArray.getFloat(R.styleable.ImageSlider_indicatorDefaultMagnification, 1.0f)
                indicatorSelectedMagnification = typedArray.getFloat(R.styleable.ImageSlider_indicatorSelectedMagnification, 1.5f)

                // Indicator 마진 xml 값 설정
                indicatorMarginStart = typedArray.getInt(R.styleable.ImageSlider_indicatorMarginStart, 10)
                indicatorMarginTop = typedArray.getInt(R.styleable.ImageSlider_indicatorMarginTop, 20)
                indicatorMarginRight = typedArray.getInt(R.styleable.ImageSlider_indicatorMarginRight, 10)
                indicatorMarginBottom = typedArray.getInt(R.styleable.ImageSlider_indicatorMarginBottom, 20)
            } finally {
                typedArray.recycle()
            }
        }
    }

    /**
     * View Pager 2를 기반으로 동작하기 때문에 내부적으로 생성
     */
    private fun createViewPager2() {
        viewPager2 = ViewPager2(mContext).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        }
        addView(viewPager2)
    }

    /**
     * View Pager2를 업데이트하며 어댑터를 설정한다
     */
    private fun updateViewPager2(adapter: ViewPager2Adapter) {
        if(viewPager2 == null) {
            createViewPager2()
        }

        viewPager2?.adapter = adapter
    }

    /**
     * Circle Indicator를 생성한다
     */
    private fun createCircleIndicator() {
        val layoutDirection = LayoutDirection()
        layoutDirection.position = indicatorDirection

        if (indicatorViewHeight == 0) {
            indicatorViewHeight = layoutParams.height
        }


        circleIndicator = CircleIndicator(mContext).apply {
            val params = LayoutParams(LayoutParams.WRAP_CONTENT, converter.dpToPx(indicatorViewHeight / 10))
            layoutDirection.applyConstraintPosition(params)
            layoutParams = params
            gravity = Gravity.CENTER
        }


        addView(circleIndicator)
    }

    /**
     * Circle Indicator 에 View Pager를 등록하며 업데이트 한다
     */
    private fun updateCircleIndicator() {
        createCircleIndicator()
        circleIndicator.setViewPager(viewPager2!!)
        circleIndicator.invalidate()
    }

    /**
     * Image Slider를 사용자가 업데이트
     */
    override fun invalidate() {
        super.invalidate()

        val imageFragmentList = ArrayList<ImageFragment>()

        if (imageList != null) {
            for (image in imageList!!) {
                val fragment = ImageFragment(image)
                if (imageScaleType != null) {
                    fragment.imageScaleType = imageScaleType
                }
                imageFragmentList.add(fragment)
            }
        }

        val activity = mContext as? AppCompatActivity
        val fragmentManager = activity?.supportFragmentManager
        val lifecycle = activity?.lifecycle

        if (fragmentManager != null && lifecycle != null) {
            viewPager2Adapter = ViewPager2Adapter(imageFragmentList, fragmentManager, lifecycle)
            viewPager2Adapter.listener = listener

            updateViewPager2(viewPager2Adapter)
            updateCircleIndicator()

        }
    }



}


package com.zyn.wnview.views

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
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

    private var mImageItemList: List<Int>? = null
    private var viewPager2: ViewPager2? = null
    private lateinit var circleIndicator: CircleIndicator
    private lateinit var viewPager2Adapter: ViewPager2Adapter

    private lateinit var converter: Conversions

    private var listener: OnImageClickListener? = null



    /**
     * indicator 방향
     * 기본 값 = CENTER_BOTTOM
     */
    private var indicatorDirection: LayoutDirection.Position = LayoutDirection.Position.CENTER_BOTTOM



    interface OnImageClickListener {
        fun onImageClick(position: Int)

    }


    var imageItemList: List<Int>?
        get() = mImageItemList
        set(value) {
            mImageItemList = value
        }

    constructor(context: Context) : super(context) {
        initImageSlider(context)
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initImageSlider(context)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initImageSlider(context)
    }


    fun setIndicatorDirection(direction: LayoutDirection.Position) {
        indicatorDirection = direction
    }

    fun setOnImageClickListener(listener: OnImageClickListener) {
        this.listener = listener
    }


    private fun initImageSlider(context: Context) {

        mContext = context
        converter = Conversions(mContext)



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
        val layoutDirection = LayoutDirection(indicatorDirection)
        circleIndicator = CircleIndicator(mContext).apply {
            val params = LayoutParams(LayoutParams.MATCH_PARENT, converter.dpToPx(100))
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

        if (mImageItemList != null) {
            for (image in mImageItemList!!) {
                val fragment = ImageFragment(image)
                imageFragmentList.add(fragment)
            }
        }

        val activity = context as? AppCompatActivity
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


package com.zyn.wnview.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.zyn.wnview.adapter.ViewPager2Adapter
import com.zyn.wnview.fragment.ImageFragment
import com.zyn.wnview.utils.LayoutDirection

/**
 * 이미지 슬라이더
 *
 * @author Kim Juyoung
 */
class ImageSlider : WnView {

    private var mContext: Context? = null

    private var mImageItemList: List<Int>? = null
    private var mViewPager2: ViewPager2? = null
    private var mCircleIndicator: CircleIndicator? = null

    private lateinit var mAdapter: ViewPager2Adapter




    /**
     * indicator 방향
     * 기본 값 = CENTER_BOTTOM
     */
    private var mIndicatorDirection: LayoutDirection.Position = LayoutDirection.Position.CENTER_BOTTOM





    var imageItemList: List<Int>?
        get() = mImageItemList
        set(value) {
            mImageItemList = value
        }

    constructor(context: Context) : super(context) {
        mContext = context
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        mContext = context
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mContext = context
    }


    fun setIndicatorDirection(direction: LayoutDirection.Position) {
        mIndicatorDirection = direction
    }


    /**
     * View Pager 2를 기반으로 동작하기 때문에 내부적으로 생성
     */
    private fun initViewPager2() {
        mViewPager2 = ViewPager2(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        }
        addView(mViewPager2)
    }

    /**
     * View Pager2를 업데이트하며 어댑터를 설정한다
     */
    private fun updateViewPager2(adapter: ViewPager2Adapter) {
        if(mViewPager2 == null) {
            initViewPager2()
        }
        mViewPager2?.adapter = adapter
    }

    /**
     * Circle Indicator를 생성한다
     */
    private fun initCircleIndicator() {
        val layoutDirection = LayoutDirection(mIndicatorDirection)
        mCircleIndicator = CircleIndicator(context).apply {
            val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            layoutDirection.applyConstraintPosition(params)
            layoutParams = params
        }
        addView(mCircleIndicator)
    }

    /**
     * Circle Indicator 에 View Pager를 등록하며 업데이트 한다
     */
    private fun updateCircleIndicator() {
        if (mCircleIndicator == null) {
            initCircleIndicator()
        }
        mCircleIndicator?.setViewPager(mViewPager2!!)
        mCircleIndicator?.invalidate()
    }

    /**
     * Image Slider를 사용자가 업데이트
     */
    override fun invalidate() {
        super.invalidate()

        val imageFragmentList = ArrayList<Fragment>()

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
            mAdapter = ViewPager2Adapter(imageFragmentList, fragmentManager, lifecycle)

            updateViewPager2(mAdapter)
            updateCircleIndicator()

        }
    }



}


package com.zyn.wnview.views

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.zyn.wnview.adapter.ViewPager2Adapter
import com.zyn.wnview.fragment.ImageFragment

class ImageSlider : WnView {

    private var mContext: Context? = null

    private var mImageItemList: List<Int>? = null
    private var mViewPager2: ViewPager2? = null

    private lateinit var mAdapter: ViewPager2Adapter

    var imageItemList: List<Int>?
        get() = mImageItemList
        set(value) {
            mImageItemList = value
        }

    fun getAdapter(): ViewPager2Adapter = mAdapter



    constructor(context: Context) : super(context) {
        this.mContext = context
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        this.mContext = context
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        this.mContext = context
    }

    private fun initViewPager2() {
        mViewPager2 = ViewPager2(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        }
        addView(mViewPager2)
    }

    private fun updateViewPager2(adapter: ViewPager2Adapter) {
        if(mViewPager2 == null) {
            initViewPager2()
        }
        mViewPager2?.adapter = adapter
    }

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

        }
    }



}


package com.zyn.wnview.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zyn.wnview.fragment.ImageFragment
import com.zyn.wnview.views.ImageSlider

class ViewPager2Adapter(private val list: List<ImageFragment>, fragmentManager: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentManager, lifecycle) {

    var listener : ImageSlider.OnImageClickListener? = null

    override fun getItemCount(): Int = list.size
    override fun createFragment(position: Int): ImageFragment {
        val fragment = list[position]

        fragment.setPosition(position)
        fragment.itemClickListener = { pos ->
            listener?.onImageClick(pos)
        }
        return fragment
    }
}
package com.zyn.wnview.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class ImageFragment(val image: Int) : Fragment() {
    lateinit var itemClickListener: ((position: Int, context: Context) -> Unit)
    private var position: Int = -1

    var imageScaleType: ImageView.ScaleType? = null
    var imageView: ImageView? = null

    fun setPosition(position: Int) {
        this.position = position
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        imageView = ImageView(requireContext())
        if (imageScaleType != null) {
            imageView?.scaleType = imageScaleType
        }

        // imageView.setImageResource(image)
        Glide.with(requireContext()).load(image).into(imageView!!)
        imageView!!.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        // 클릭 리스너 추가
        imageView!!.setOnClickListener {
            itemClickListener.invoke(position, requireContext())
        }
        return imageView!!
    }
}
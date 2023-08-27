package com.zyn.wnview.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class ImageFragment(val image: Int) : Fragment() {
    lateinit var itemClickListener: ((position: Int) -> Unit)
    private var position: Int = -1

    fun setPosition(position: Int) {
        this.position = position
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val imageView = ImageView(context)
        imageView.setImageResource(image)
        imageView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        // 클릭 리스너 추가
        imageView.setOnClickListener {
            itemClickListener.invoke(position)
        }
        return imageView
    }
}
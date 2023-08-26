package com.zyn.wnview.views

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * 뷰의 기본
 *
 * @author Kim Juyoung
 */
abstract class WnView : ConstraintLayout {
    private var mContext: Context? = null
    private var mLayoutParams: LayoutParams? = null



    constructor(context: Context) : super(context) {
        this.mContext = context
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)  {
        this.mContext = context
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )  {
        this.mContext = context

    }








}
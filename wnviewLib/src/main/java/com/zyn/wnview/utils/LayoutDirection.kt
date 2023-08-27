package com.zyn.wnview.utils

import androidx.constraintlayout.widget.ConstraintLayout

class LayoutDirection {

    var position: Position? = null


    enum class Position {
        LEFT_TOP,
        CENTER_TOP,
        RIGHT_TOP,
        CENTER,
        CENTER_LEFT,
        CENTER_RIGHT,
        LEFT_BOTTOM,
        CENTER_BOTTOM,
        RIGHT_BOTTOM
    }




    /**
     * 사용자 위치에 따른 Constraint Layout에서 Layout Params를 설정한다
     */
    fun applyConstraintPosition(params: ConstraintLayout.LayoutParams) {
        when (position!!) {
            Position.LEFT_TOP -> {
                params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                params.endToEnd = ConstraintLayout.LayoutParams.UNSET
                params.bottomToBottom = ConstraintLayout.LayoutParams.UNSET
            }
            Position.CENTER_TOP -> {
                params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                params.bottomToBottom = ConstraintLayout.LayoutParams.UNSET
            }
            Position.RIGHT_TOP -> {
                params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                params.startToStart = ConstraintLayout.LayoutParams.UNSET
                params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                params.bottomToBottom = ConstraintLayout.LayoutParams.UNSET
            }
            Position.CENTER -> {
                params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            }
            Position.CENTER_LEFT -> {
                params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                params.endToEnd = ConstraintLayout.LayoutParams.UNSET
                params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            }
            Position.CENTER_RIGHT -> {
                params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                params.startToStart = ConstraintLayout.LayoutParams.UNSET
                params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            }
            Position.LEFT_BOTTOM -> {
                params.topToTop = ConstraintLayout.LayoutParams.UNSET
                params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                params.endToEnd = ConstraintLayout.LayoutParams.UNSET
                params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID

            }
            Position.CENTER_BOTTOM -> {
                params.topToTop = ConstraintLayout.LayoutParams.UNSET
                params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            }
            Position.RIGHT_BOTTOM -> {
                params.topToTop = ConstraintLayout.LayoutParams.UNSET
                params.startToStart = ConstraintLayout.LayoutParams.UNSET
                params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            }
        }
    }

}



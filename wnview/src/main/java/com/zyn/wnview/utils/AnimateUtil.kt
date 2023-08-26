package com.zyn.wnview.utils

import android.animation.Animator
import android.view.View

class AnimateUtil {

    lateinit var animatorOut: Animator
    lateinit var animatorIn: Animator
    lateinit var immediateAnimatorOut: Animator
    lateinit var immediateAnimatorIn: Animator



    fun animationPlay(animation: Animator, indicator: View) {
        animation.setTarget(indicator)
        animation.start()
    }

    fun allStop() {
        if (animatorOut.isRunning) {
            animatorOut.end()
            animatorOut.cancel()
        }

        if (animatorIn.isRunning) {
            animatorIn.end()
            animatorIn.cancel()
        }

        if (immediateAnimatorOut.isRunning) {
            immediateAnimatorOut.end()
            immediateAnimatorOut.cancel()
        }

        if ( immediateAnimatorIn.isRunning) {
            immediateAnimatorIn.end()
            immediateAnimatorIn.cancel()
        }
    }
}
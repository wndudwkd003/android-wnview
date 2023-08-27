package com.zyn.wnview.utils

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View

class AnimateUtil {

    lateinit var animatorOut: Animator
    lateinit var animatorIn: Animator
    lateinit var immediateAnimatorOut: Animator
    lateinit var immediateAnimatorIn: Animator



    fun play(animation: Animator, indicator: View) {
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

    fun createAnimatorSet(view: View, alphaAnimator: ObjectAnimator?, scaleXAnimator: ObjectAnimator?, scaleYAnimator: ObjectAnimator?): AnimatorSet {
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(alphaAnimator, scaleXAnimator, scaleYAnimator)
        animatorSet.duration = view.resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
        return animatorSet
    }

    //    private fun createAnimatorOut(): Animator {
    //        return AnimatorInflater.loadAnimator(context, animatorResId)
    //    }
    //
    //    private fun createAnimatorIn(): Animator {
    //        val animatorIn: Animator = AnimatorInflater.loadAnimator(context, animatorResId)
    //        animatorIn.interpolator = ReverseInterpolator()
    //
    //        return animatorIn
    //    }

}
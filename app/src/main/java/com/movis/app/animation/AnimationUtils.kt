package com.movis.app.animation

import android.view.View

import android.animation.AnimatorSet
import android.animation.ObjectAnimator

class AnimationUtils {

    fun animationScale(view: View) {
        val scaleXDown = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.9f)
        val scaleYDown = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.9f)
        val scaleXUp = ObjectAnimator.ofFloat(view, "scaleX", 0.9f, 1f)
        val scaleYUp = ObjectAnimator.ofFloat(view, "scaleY", 0.9f, 1f)

        val animatorSet = AnimatorSet()
        animatorSet.play(scaleXDown).with(scaleYDown)
        animatorSet.play(scaleXUp).with(scaleYUp).after(scaleXDown)

        animatorSet.duration = 150 // مدت زمان هر مرحله
        animatorSet.start()
    }
}

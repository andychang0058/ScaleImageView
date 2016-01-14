package com.hokushin.scaleimageview.animation;

import android.content.Context;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import com.hokushin.scaleimageview.gesture.OnGesture;


public class ImageAnimator implements Runnable {

    private Context mContext;
    private OnGesture mListener;

    private AnimationOption mOption;
    private OnAnimationListener mAnimationListener;

    private long mStartTime;
    private boolean mIsRunning;
    final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();

    public ImageAnimator(Context context, OnGesture listener, OnAnimationListener aListener) {
        this.mContext = context;
        this.mListener = listener;
        this.mAnimationListener = aListener;
    }

    public void animate(AnimationOption option) {
        abortAnimation();
        mOption = option;
        mStartTime = System.currentTimeMillis();
        mIsRunning = true;
        mOption.getView().postOnAnimation(this);
    }

    public void abortAnimation() {
        if (mIsRunning) {
            mIsRunning = false;
            if (mOption.getView() != null)
                mOption.getView().removeCallbacks(this);
        }
    }

    @Override
    public void run() {
        float t = interpolate(mStartTime, mOption.getDuration());
        float srcRate = mOption.getSrcRate();
        float dstRate = mOption.getDstRate();

        boolean isAnimateScale = srcRate != dstRate;
        boolean isScaleEnd = isAnimateScale ? false : true;

        if (isAnimateScale) {
            float scaleRate = srcRate + t * (dstRate - srcRate);

            float scaleFocusX = mOption.getScaleFocusX() == 0 ?
                    mOption.getView().getMeasuredWidth() / 2 : mOption.getScaleFocusX();
            float scaleFocusY = mOption.getScaleFocusY() == 0 ?
                    mOption.getView().getMeasuredHeight() / 2 : mOption.getScaleFocusY();

            float scaleDelta = scaleRate / srcRate;
            mOption.setSrcRate(scaleRate);
            mListener.onScale(scaleDelta, scaleFocusX, scaleFocusY);
        }

        if (isScaleEnd) {
            mIsRunning = false;
            mAnimationListener.onAnimationsEnd();
        } else
            mOption.getView().postOnAnimation(this);
    }

    private float interpolate(long startTime, int duration) {
        float t = 1f * (System.currentTimeMillis() - startTime) / duration;
        t = Math.min(1f, t);
        t = mInterpolator.getInterpolation(t);
        return t;
    }

}

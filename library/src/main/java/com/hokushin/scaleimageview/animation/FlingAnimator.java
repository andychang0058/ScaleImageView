package com.hokushin.scaleimageview.animation;

import android.content.Context;
import android.graphics.RectF;
import android.widget.OverScroller;

import com.hokushin.scaleimageview.gesture.OnGesture;

public class FlingAnimator implements Runnable {

    private OverScroller mScroller;
    private OnGesture mListener;

    private AnimationOption mOption;
    private OnAnimationListener mAnimationListener;

    private float startX;
    private float startY;

    public FlingAnimator(Context context, OnGesture listener, OnAnimationListener aListener) {
        mScroller = new OverScroller(context);
        mListener = listener;
        mAnimationListener = aListener;
    }

    public void fling(AnimationOption option) {

        mOption = option;
        RectF rect = mOption.getRect();

        int maxX = 0;
        int minX = 0;
        int maxY = 0;
        int minY = 0;
        startX = 0;
        startY = 0;

        if (mOption.getVelocityX() > 0) {
            maxX = (int) mOption.getLeftBound() - (int)rect.left;
            minX = (int) rect.left;
        } else {
            maxX = (int) rect.right;
            minX = (int) mOption.getRightBound() - (int)rect.right;
        }

        if (mOption.getVelocityY() > 0) {
            maxY = (int) mOption.getTopBound() - (int)rect.top;
            minY = (int) rect.top;
        } else {
            maxY = (int) mOption.getBottomBound();
            minY = (int) mOption.getBottomBound() - (int)rect.bottom;
        }

        mScroller.fling((int) startX, (int) startY, (int) mOption.getVelocityX(), (int) mOption.getVelocityY(),
                minX, maxX, minY, maxY, 0, 0);

        mOption.getView().postOnAnimation(this);
    }

    public void abortFling() {
        if (mScroller != null && !mScroller.isFinished())
            mScroller.forceFinished(true);
        if (mOption != null && mOption.getView() != null)
            mOption.getView().removeCallbacks(this);
    }

    @Override
    public void run() {
        if (mScroller.isFinished())
            return;

        if (mScroller.computeScrollOffset()) {

            final float currX = mScroller.getCurrX();
            final float currY = mScroller.getCurrY();
            float dx = currX -startX;
            float dy = currY - startY;

            mListener.onDrag(dx, dy);

            startX = currX;
            startY = currY;
            mOption.getView().postOnAnimation(this);
        } else {
            if (mAnimationListener != null)
                mAnimationListener.onAnimationsEnd();
        }

    }

}

package com.hokushin.scaleimageview.gesture;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

public class DragDetector {

    private VelocityTracker mVelocityTracker;

    private Context mContext;
    private OnGesture mListener;
    private boolean isDragging = false;

    private float mTouchX;
    private float mTouchY;

    private float mMinimumVelocity;

    public DragDetector(Context context, OnGesture listener) {
        this.mContext = context;
        this.mListener = listener;

        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
    }

    public boolean isDragging() {
        return isDragging;
    }

    public boolean onTouch(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {

            case MotionEvent.ACTION_DOWN:
                mVelocityTracker = VelocityTracker.obtain();
                if (mVelocityTracker != null)
                    mVelocityTracker.addMovement(event);

                mTouchX = MotionEventCompat.getX(event, 0);
                mTouchY = MotionEventCompat.getY(event, 0);
                isDragging = true;
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                isDragging = false;
                break;

            case MotionEvent.ACTION_MOVE:
                float x = MotionEventCompat.getX(event, 0);
                float y = MotionEventCompat.getY(event, 0);
                float dx = x - mTouchX;
                float dy = y - mTouchY;
                mTouchX = x;
                mTouchY = y;

                if (isDragging)
                    mListener.onDrag(dx, dy);

                if (mVelocityTracker != null )
                    mVelocityTracker.addMovement(event);
                break;

            case MotionEvent.ACTION_POINTER_UP:
                isDragging = true;
                int id =  MotionEventCompat.getActionIndex(event) == 0 ? 1 : 0;
                mTouchX = MotionEventCompat.getX(event, id);
                mTouchY = MotionEventCompat.getY(event, id);
                break;

            case MotionEvent.ACTION_UP:
                if (isDragging) {
                    if (mVelocityTracker != null ) {
                        mVelocityTracker.addMovement(event);
                        mVelocityTracker.computeCurrentVelocity(1000);

                        float velocityX = mVelocityTracker.getXVelocity(0);
                        float velocityY = mVelocityTracker.getYVelocity(0);

                        if (Math.max(Math.abs(velocityX), Math.abs(velocityY)) >= mMinimumVelocity)
                            mListener.onFling(mTouchX, mTouchY, velocityX, velocityY);
                    }
                }
                recycleTracker();
                break;

            case MotionEvent.ACTION_CANCEL: {
                recycleTracker();
                break;
            }
        }
        return true;
    }

    private void recycleTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }
}

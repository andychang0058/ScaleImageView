package com.hokushin.scaleimageview.gesture;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

public class ScaleDetector {

    private Context mContext;
    private OnGesture mListener;
    private ScaleGestureDetector mScaleDetector;
    private boolean isScaleing = false;

    public ScaleDetector(Context context, OnGesture listener) {
        this.mContext = context;
        this.mListener = listener;
        mScaleDetector = new ScaleGestureDetector(mContext, new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                isScaleing = true;

                if (Float.isNaN(detector.getScaleFactor()) || Float.isInfinite(detector.getScaleFactor())) {
                    return false;
                }

                mListener.onScale(detector.getScaleFactor(), detector.getFocusX(), detector.getFocusY());
                return true;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                isScaleing = true;
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
            }
        });
    }

    public boolean onTouch(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {

            case MotionEvent.ACTION_POINTER_UP:
                isScaleing = false;
                break;

            case MotionEvent.ACTION_CANCEL:
                isScaleing = false;
                break;
        }

        if (event.getPointerCount() > 1) {
            return mScaleDetector.onTouchEvent(event);
        }

        return true;
    }

    public boolean isScaling() {
        return isScaleing;
    }
}

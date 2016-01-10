package com.hokushin.scaleimageview.gesture;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;

public class RotateDetector {

    private final static String TAG = "RotateDetector";
    private Context mContext;
    private OnGesture mListener;

    private static float mAngleSlop = 20f;
    private static float mVelocitySlop = 1000;

    private boolean isRotating = false;

    //First touch point.
    private float Fx;
    private float Fy;
    //Second touch point
    private float Sx;
    private float Sy;

    private float movedFx;
    private float movedFy;
    private float movedSx;
    private float movedSy;

    public RotateDetector(Context context, OnGesture listener) {
        this.mContext = context;
        this.mListener = listener;
    }

    public boolean onTouch(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        int pointerCount = MotionEventCompat.getPointerCount(event);
        if (pointerCount == 2) {

            switch (action) {
                case MotionEvent.ACTION_POINTER_DOWN:

                    Fx = MotionEventCompat.getX(event, 0);
                    Fy = MotionEventCompat.getY(event, 0);
                    Sx = MotionEventCompat.getX(event, 1);
                    Sy = MotionEventCompat.getY(event, 1);
                    isRotating = false;
                    break;

                case MotionEvent.ACTION_MOVE:

                    movedFx = MotionEventCompat.getX(event, 0);
                    movedFy = MotionEventCompat.getY(event, 0);
                    movedSx = MotionEventCompat.getX(event, 1);
                    movedSy = MotionEventCompat.getY(event, 1);

                    float angle = getAngle(Fx, Fy, Sx, Sy, movedFx, movedFy, movedSx, movedSy);
                    if (Math.abs(angle) > mAngleSlop && !isRotating) {
                        isRotating = true;
                        Fx = MotionEventCompat.getX(event, 0);
                        Fy = MotionEventCompat.getY(event, 0);
                        Sx = MotionEventCompat.getX(event, 1);
                        Sy = MotionEventCompat.getY(event, 1);
                    }

                    if (isRotating) {
                        angle = getAngle(Fx, Fy, Sx, Sy, movedFx, movedFy, movedSx, movedSy);
                        mListener.onRotate(angle);
                        Fx = movedFx;
                        Fy = movedFy;
                        Sx = movedSx;
                        Sy = movedSy;
                    }
                    break;

                case MotionEvent.ACTION_POINTER_UP:
                    isRotating = false;
                    break;

                case MotionEvent.ACTION_CANCEL:
                    isRotating = false;
                    break;
            }
        }
        return true;
    }

    public boolean isRotating() {
        return isRotating;
    }

    private float getAngle(float fX, float fY, float sX, float sY, float mfX, float mfY, float msX, float msY)
    {
        float angle1 = (float) Math.atan2(fY - sY, fX - sX);
        float angle2 = (float) Math.atan2(mfY - msY, mfX - msX);

        float angle = ((float)Math.toDegrees(angle1 - angle2)) % 360;
        if (angle < -180.f) angle += 360.0f;
        if (angle > 180.f) angle -= 360.0f;
        return -angle;
    }
}

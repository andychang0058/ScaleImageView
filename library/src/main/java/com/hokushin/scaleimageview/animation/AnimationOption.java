package com.hokushin.scaleimageview.animation;

import android.graphics.RectF;
import android.view.View;

public class AnimationOption {

    private View view;
    private int duration;

    private float srcRate;
    private float dstRate;

    private float leftBound;
    private float rightBound;
    private float topBound;
    private float bottomBound;

    private float velocityX;
    private float velocityY;
    private RectF rect;

    private float scaleFocusX;
    private float scaleFocusY;

    public float getScaleFocusX() {
        return scaleFocusX;
    }

    public AnimationOption setScaleFocusX(float scaleFocusX) {
        this.scaleFocusX = scaleFocusX;
        return this;
    }

    public float getScaleFocusY() {
        return scaleFocusY;
    }

    public AnimationOption setScaleFocusY(float scaleFocusY) {
        this.scaleFocusY = scaleFocusY;
        return this;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public AnimationOption setVelocityX(float velocityX) {
        this.velocityX = velocityX;
        return this;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public AnimationOption setVelocityY(float velocityY) {
        this.velocityY = velocityY;
        return this;
    }

    public RectF getRect() {
        return rect;
    }

    public AnimationOption setRect(RectF rect) {
        this.rect = rect;
        return this;
    }

    public float getLeftBound() {
        return leftBound;
    }

    public AnimationOption setLeftBound(float leftBound) {
        this.leftBound = leftBound;
        return this;
    }

    public float getRightBound() {
        return rightBound;
    }

    public AnimationOption setRightBound(float rightBound) {
        this.rightBound = rightBound;
        return this;
    }

    public float getTopBound() {
        return topBound;
    }

    public AnimationOption setTopBound(float topBound) {
        this.topBound = topBound;
        return this;
    }

    public float getBottomBound() {
        return bottomBound;
    }

    public AnimationOption setBottomBound(float bottomBound) {
        this.bottomBound = bottomBound;
        return this;
    }

    public View getView() {
        return view;
    }

    public AnimationOption setView(View view) {
        this.view = view;
        return this;
    }

    public int getDuration() {
        return duration;
    }

    public AnimationOption setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public float getSrcRate() {
        return srcRate;
    }

    public AnimationOption setSrcRate(float srcRate) {
        this.srcRate = srcRate;
        return this;
    }

    public float getDstRate() {
        return dstRate;
    }

    public AnimationOption setDstRate(float dstRate) {
        this.dstRate = dstRate;
        return this;
    }
}

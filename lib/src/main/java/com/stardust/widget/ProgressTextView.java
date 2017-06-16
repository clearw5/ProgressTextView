package com.stardust.widget;

import android.animation.AnimatorInflater;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.ViewOutlineProvider;

/**
 * Created by Stardust on 2017/6/9.
 */

public class ProgressTextView extends android.support.v7.widget.AppCompatTextView {


    private Rect mReachedRect, mUnreachedRect;
    private RectF mRectF;
    private boolean mShouldUpdateRect = true;
    private float mPercent = 0;
    private float mRoundRadius = 0;
    private int mBorderWidth = 3;
    private Paint mPaint = new Paint();
    private int mReachedTextColor;

    public ProgressTextView(Context context) {
        super(context);
        init(null);
    }

    public ProgressTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ProgressTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        if (attrs == null)
            return;
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ProgressTextView);
        mRoundRadius = typedArray.getDimension(R.styleable.ProgressTextView_ptv_round_radius, 0);
        mBorderWidth = (int) typedArray.getDimension(R.styleable.ProgressTextView_ptv_border_width, 3);
        mReachedTextColor = typedArray.getColor(R.styleable.ProgressTextView_ptv_reached_text_color, Color.WHITE);
        setProgress(typedArray.getInt(R.styleable.ProgressTextView_ptv_progress, 0));
        if (typedArray.getBoolean(R.styleable.ProgressTextView_ptv_enable_pressed_anim, false) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            enablePressedAnim();
        }
        typedArray.recycle();
        updateRoundedBackground();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void enablePressedAnim() {
        setStateListAnimator(AnimatorInflater.loadStateListAnimator(getContext(), R.anim.ptv_button_state_list_anim_material));
        setOutlineProvider(ViewOutlineProvider.BOUNDS);
    }

    private void updateRoundedBackground() {
        Drawable background = getBackground();
        GradientDrawable shape;
        if (background instanceof GradientDrawable) {
            shape = (GradientDrawable) background;
        } else {
            shape = new GradientDrawable();
            setBackgroundDrawable(shape);
        }
        shape.setStroke(mBorderWidth, getCurrentTextColor());
        shape.setCornerRadius(mRoundRadius);
        invalidate();
    }

    public int getProgress() {
        return Math.round(mPercent * 100.0f);
    }

    public void setProgress(int progress) {
        setProgressPercent(progress / 100.0f);
    }

    public float getProgressPercent() {
        return mPercent;
    }

    public void setProgressPercent(float percent) {
        mShouldUpdateRect = true;
        mPercent = percent;
        invalidate();
    }

    public void setRoundRadius(float roundRadius) {
        mRoundRadius = roundRadius;
        updateRoundedBackground();
    }

    public void setReachedTextColor(int reachedTextColor) {
        mReachedTextColor = reachedTextColor;
    }

    public float getRoundRadius() {
        return mRoundRadius;
    }

    public int getReachedTextColor() {
        return mReachedTextColor;
    }

    public int getBorderWidth() {
        return mBorderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        mBorderWidth = borderWidth;
        updateRoundedBackground();
    }

    @SuppressLint("MissingSuperCall")
    public void draw(Canvas canvas) {
        if (mShouldUpdateRect) {
            mReachedRect = new Rect(0, 0, (int) (getWidth() * mPercent), getHeight());
            mUnreachedRect = new Rect((int) (getWidth() * mPercent), 0, getWidth(), getHeight());
            mRectF = new RectF(0, 0, getWidth(), getHeight());
            mShouldUpdateRect = false;
        }

        int unreachedTextColor = getCurrentTextColor();

        draw(canvas, mReachedRect, unreachedTextColor, mReachedTextColor);
        draw(canvas, mUnreachedRect, mReachedTextColor, unreachedTextColor);

    }

    private void draw(Canvas canvas, Rect rect, int backgroundColor, int textColor) {
        mPaint.setColor(backgroundColor);
        setTextColor(textColor);

        canvas.save();
        canvas.clipRect(rect);
        if (mRoundRadius > 0) {
            canvas.drawRoundRect(mRectF, mRoundRadius, mRoundRadius, mPaint);
        } else {
            canvas.drawRect(rect, mPaint);
        }
        super.draw(canvas);
        canvas.restore();
    }

}

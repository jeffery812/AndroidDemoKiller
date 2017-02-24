package com.max.tang.demokiller.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import com.max.tang.demokiller.R;
import com.max.tang.demokiller.utils.log.Logger;
import java.util.Locale;

/**
 * Created by siarhei on 2016-10-04.
 */

public class CircularIndicatorView extends View {

    private Paint mPaintCircle, mPaintArch;
    private int mWidth, mHeight;
    private int strokeWidth = 40;
    private float scale = 0.9f;
    private int color1, color2, color3, color4;
    private float segments[] = { 40f, 60f, 80f };
    private int colors[] = {
        R.color.colorBad, R.color.colorBad, R.color.colorPoor, R.color.colorPoor, R.color.colorGood,
        R.color.colorGood, R.color.colorExcellent, R.color.colorExcellent
    };
    private float mCenter[] = {0f,0f};
    private float positions[] =
        { 0, segments[0], segments[0], segments[1], segments[1], segments[2], segments[2], 1f };
    RectF rectBlackBg;
    /**
     * 1 ... 100
     */
    private int mCurrentPercentage = 0;
    private int mAngleStart = 150;
    private int mAngleTotal = 240;

    public CircularIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //第二个参数就是我们在styles.xml文件中的<declare-styleable>标签
        //即属性集合的标签，在R文件中名称为R.styleable+name
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircularIndicatorView);

        //第一个参数为属性集合里面的属性，R文件名称：R.styleable+属性集合名称+下划线+属性名称
        color1 = a.getColor(R.styleable.CircularIndicatorView_color1, ContextCompat.getColor(getContext(), R.color.colorBad));
        color2 = a.getColor(R.styleable.CircularIndicatorView_color2, ContextCompat.getColor(getContext(), R.color.colorPoor));
        color3 = a.getColor(R.styleable.CircularIndicatorView_color3, ContextCompat.getColor(getContext(), R.color.colorGood));
        color4 = a.getColor(R.styleable.CircularIndicatorView_color4, ContextCompat.getColor(getContext(), R.color.colorExcellent));

        init();
        //最后记得将TypedArray对象回收
        a.recycle();
    }

    private void init(){
        mPaintCircle = new Paint();
        mPaintCircle.setAntiAlias(true);
        mPaintCircle.setStrokeWidth((float) strokeWidth);
        mPaintCircle.setStyle(Paint.Style.STROKE);
        //mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaintCircle.setColor(Color.LTGRAY);


        mPaintArch = new Paint();
        mPaintArch.setAntiAlias(true);
        mPaintArch.setStyle(Paint.Style.STROKE);
        mPaintArch.setStrokeWidth(strokeWidth + 10);

        rectBlackBg = new RectF();

    }
    private void calculateColors() {
        colors[0] = color1;
        colors[1] = color1;
        colors[2] = color2;
        colors[3] = color2;
        colors[4] = color3;
        colors[5] = color3;
        colors[6] = color4;
        colors[7] = color4;

        float position1 = (segments[0]/100) * mAngleTotal / 360;
        float position2 = (segments[1]/100) * mAngleTotal / 360;
        float position3 = (segments[2]/100) * mAngleTotal / 360;

        positions[0] = 0f;
        positions[1] = position1;
        positions[2] = position1;
        positions[3] = position2;
        positions[4] = position2;
        positions[5] = position3;
        positions[6] = position3;
        positions[7] = 1f;

    }

    public void setSegments(float[] segments) {
        this.segments = segments;
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        calculateColors();

        mCenter[0] = mWidth / 2;
        mCenter[1] = mHeight / 2;

        scale = scale > 1 ? 1 : scale;

        int size = (int) (1.0 * Math.min(mHeight, mWidth) * scale);

        float padding = strokeWidth / 2;
        rectBlackBg.set(mCenter[0] - size / 2 + padding, mCenter[1] - size / 2 + padding,
            mCenter[0] + size / 2 - padding, mCenter[1] + size / 2 - padding);
        canvas.drawArc(rectBlackBg, 0, 360, false, mPaintCircle);

        canvas.rotate(mAngleStart, mCenter[0], mCenter[1]);

        SweepGradient
            sweepGradient = new SweepGradient(mCenter[0], mCenter[1], colors, positions);
        mPaintArch.setShader(sweepGradient);
        //
        canvas.drawArc(rectBlackBg, 0, mCurrentPercentage * mAngleTotal / 100, false, mPaintArch);
    }


    public void setValue(int value) {
        value = value > 100 ? 100 : value;
        value = value < 0 ? 0 : value;
        setAnimation(mCurrentPercentage, value, 1000);
    }

    private void setAnimation(int last, int current, int length) {
        Logger.d(String.format(Locale.getDefault(), "setAnimation %d -> %d, %d", last, current, length));
        ValueAnimator valueAnimator = ValueAnimator.ofInt(last, current);
        valueAnimator.setDuration(length);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentPercentage = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.EXACTLY || widthSpecMode == MeasureSpec.AT_MOST) {
            mWidth = widthSpecSize;
        } else {
            mWidth = 0;
        }
        if (heightSpecMode == MeasureSpec.AT_MOST || heightSpecMode == MeasureSpec.UNSPECIFIED) {
            mHeight = dipToPx(15);
        } else {
            mHeight = heightSpecSize;
        }

    }

    private int dipToPx(int dip) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Logger.d(String.format(Locale.getDefault(), "onSizeChanged1: %d, %d", w, h));

    }
}

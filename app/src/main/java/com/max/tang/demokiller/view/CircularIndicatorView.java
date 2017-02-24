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
 * Created by Zhihui Tang on 2016-10-04.
 */

public class CircularIndicatorView extends View {

    private Paint mPaintCircle, mPaintArch, mPaintText;
    private int mWidth, mHeight;
    private int strokeWidth = 30;
    private float scale = 0.9f;
    //private int color1, color2, color3, color4;
    private int segmentColors[] = new int[4];
    private float segments[] = { 0f, 40f, 60f, 80f };
    private int colors[] = {
        R.color.colorBad, R.color.colorBad, R.color.colorPoor, R.color.colorPoor, R.color.colorGood,
        R.color.colorGood, R.color.colorExcellent, R.color.colorExcellent
    };
    private float mCenter[] = { 0f, 0f };
    private float positions[] =
        { 0, segments[0], segments[0], segments[1], segments[1], segments[2], segments[2], 1f };
    RectF rectBlackBg;
    private float diameter = 0.0f;
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

        segmentColors[0] = a.getColor(R.styleable.CircularIndicatorView_color1,
            ContextCompat.getColor(getContext(), R.color.colorBad));
        segmentColors[1] = a.getColor(R.styleable.CircularIndicatorView_color2,
            ContextCompat.getColor(getContext(), R.color.colorPoor));
        segmentColors[2] = a.getColor(R.styleable.CircularIndicatorView_color3,
            ContextCompat.getColor(getContext(), R.color.colorGood));
        segmentColors[3] = a.getColor(R.styleable.CircularIndicatorView_color4,
            ContextCompat.getColor(getContext(), R.color.colorExcellent));

        init();
        //最后记得将TypedArray对象回收
        a.recycle();
    }

    private void init() {
        mPaintCircle = new Paint();
        mPaintCircle.setAntiAlias(true);
        mPaintCircle.setStyle(Paint.Style.STROKE);
        //mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaintCircle.setColor(Color.LTGRAY);

        mPaintArch = new Paint();
        mPaintArch.setAntiAlias(true);
        mPaintArch.setStyle(Paint.Style.STROKE);
        mPaintArch.setStrokeWidth(strokeWidth + 10);

        rectBlackBg = new RectF();

        mPaintText = new Paint();
        mPaintText.setTextSize(45);
        mPaintText.setColor(Color.WHITE);
        mPaintText.setAntiAlias(true);//抗锯齿功能
        //        vTextPaint.setStrokeWidth((float) 3.0);
        mPaintText.setTextAlign(Paint.Align.CENTER);
    }

    private void calculateColors() {
        colors[0] = segmentColors[0];
        colors[1] = segmentColors[0];
        colors[2] = segmentColors[1];
        colors[3] = segmentColors[1];
        colors[4] = segmentColors[2];
        colors[5] = segmentColors[2];
        colors[6] = segmentColors[3];
        colors[7] = segmentColors[3];

        float position1 = (segments[1] / 100) * mAngleTotal / 360;
        float position2 = (segments[2] / 100) * mAngleTotal / 360;
        float position3 = (segments[3] / 100) * mAngleTotal / 360;

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

        drawText(canvas);

        drawInnerCircle(canvas);

        drawIndicator(canvas);

        drawEndPoint(canvas);

    }

    private void drawInnerCircle(Canvas canvas) {
        mCenter[0] = mWidth / 2;
        mCenter[1] = mHeight / 2;

        scale = scale > 1 ? 1 : scale;

        diameter = (int) (1.0 * Math.min(mHeight, mWidth) * scale);

        mPaintCircle.setColor(Color.LTGRAY);
        mPaintCircle.setStrokeWidth(strokeWidth);
        float padding = strokeWidth / 2;
        rectBlackBg.set(mCenter[0] - diameter / 2 + padding, mCenter[1] - diameter / 2 + padding,
            mCenter[0] + diameter / 2 - padding, mCenter[1] + diameter / 2 - padding);
        canvas.drawArc(rectBlackBg, 0, 360, false, mPaintCircle);
    }

    private void drawIndicator(Canvas canvas) {
        canvas.rotate(mAngleStart, mCenter[0], mCenter[1]);

        SweepGradient sweepGradient = new SweepGradient(mCenter[0], mCenter[1], colors, positions);
        mPaintArch.setShader(sweepGradient);
        //
        canvas.drawArc(rectBlackBg, 0, mCurrentPercentage * mAngleTotal / 100, false, mPaintArch);
    }

    private void drawEndPoint(Canvas canvas) {
        int color = segmentColors[0];
        for (int i = 0; i < segments.length; i++) {
            if (mCurrentPercentage > segments[i]) {
                color = segmentColors[i];
            } else {
                break;
            }
        }

        mPaintCircle.setColor(color);
        mPaintCircle.setStrokeWidth(strokeWidth*1.5f);
        canvas.rotate(mCurrentPercentage * mAngleTotal / 100, mCenter[0], mCenter[1]);
        canvas.drawCircle(mCenter[0] + diameter / 2 - strokeWidth / 2, mCenter[1], 1f,
            mPaintCircle);
    }


    private void drawText(Canvas canvas) {
        canvas.drawText(String.format(Locale.getDefault(), "%d%%", mCurrentPercentage), mCenter[0], mCenter[1], mPaintText);
    }
    public void setValue(int value) {
        value = value > 100 ? 100 : value;
        value = value < 0 ? 0 : value;
        setAnimation(mCurrentPercentage, value, 1000);
    }

    private void setAnimation(int last, int current, int length) {
        Logger.d(
            String.format(Locale.getDefault(), "setAnimation %d -> %d, %d", last, current, length));
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

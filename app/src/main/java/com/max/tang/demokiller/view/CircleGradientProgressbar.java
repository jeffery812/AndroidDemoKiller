package com.max.tang.demokiller.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;
import com.max.tang.demokiller.R;

/**
 * Created by zhihuitang on 2017-02-24.
 */

public class CircleGradientProgressbar extends View {

    private final static int DURATION = 3000;
    private int mWidth;
    private int mHeight;
    private int mDiam;

    private int mColorGreen;
    private int mColorYellow;
    private int mColorRed;

    private final float mMaxProgress = 100f;
    //默认进度
    private int mProgress = 80;

    private int mCurrentProgress = 100;
    //进图条圆环宽度
    private int mProgressStrokeWidth = 10;
    //进度条背景画笔
    private Paint mCirclePaint;
    //进度条画笔
    private Paint mProgressPaint;
    private Shader mProgressShader;

    //画圆所在的距形区域
    private RectF mProgressOval;

    private ValueAnimator mAnimator;

    public CircleGradientProgressbar(Context context) {
        this(context, null);
    }

    public CircleGradientProgressbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        mColorGreen = getResources().getColor(R.color.cpb_green);
        mColorYellow = getResources().getColor(R.color.cpb_blue);
        mColorRed = getResources().getColor(R.color.cpb_red);

        mProgressOval = new RectF();

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(Color.GRAY);
        mCirclePaint.setStyle(Paint.Style.STROKE);


        mProgressPaint = new Paint();
        //设置抗锯齿
        mProgressPaint.setAntiAlias(true);
        //设置笔为圆角
        //        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);
        mProgressPaint.setStyle(Paint.Style.STROKE);

        mAnimator = ValueAnimator.ofFloat(0, 1f);

        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator animation) {
                float percent = (float) animation.getAnimatedValue();
                if (percent > 0) {
                    mCurrentProgress = (int) (mProgress * percent);
                    postInvalidate();
                }
            }
        });
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getWidth();
        mHeight = getHeight();
        mDiam = Math.min(mWidth, mHeight);
        mProgressStrokeWidth = mDiam / 10;
        drawCircle(canvas);
        drawSweepProgressBar(canvas, mWidth / 2, mHeight / 2);
    }

    private void drawCircle(Canvas canvas) {
        mCirclePaint.setStrokeWidth(mProgressStrokeWidth);

        canvas.drawArc(mProgressOval, 0, 360, false, mCirclePaint);
    }

    /**
     * 画渐变圆环:
     * colorSweep[]: 渐变颜色数组
     * position[]:   每个颜色值的相对位置,个数与颜色数组个数相等
     * 注:位置与渐变颜色值的设置教程见博客:
     *
     * @param canvas 画布
     * @param cx     圆环中心X坐标
     * @param cy     圆环中心Y坐标
     */
    private void drawSweepProgressBar(Canvas canvas, int cx, float cy) {

        //设置圆环的大小
        mProgressOval.top = cy - mDiam / 4;
        mProgressOval.left = cx - mDiam / 4;
        mProgressOval.bottom = cy + mDiam / 4;
        mProgressOval.right = cx + mDiam / 4;

        //画渐变颜色
        int colorSweep[] = {mColorGreen, mColorGreen, mColorYellow, mColorYellow, mColorRed,mColorRed};
        float position[] = {0f, 0.4f, 0.4f, 0.7f,0.7f, 1.0f};

        mProgressShader = new SweepGradient(cx, cy, colorSweep, position);
        mProgressPaint.setShader(mProgressShader);
        mProgressPaint.setStrokeWidth(mProgressStrokeWidth);
        canvas.drawArc(mProgressOval, 0, ((float) mCurrentProgress / mMaxProgress) * 360, false, mProgressPaint); // 绘制进度圆弧，这里是蓝色

    }

    public void setProgress(int progress) {
        this.mProgress = progress;
        if (mAnimator != null && progress > 0) {
            if (mAnimator.isRunning()) {
                return;
            }
            mAnimator.setDuration((long) (DURATION / mMaxProgress * progress));
            mAnimator.start();
        }
    }
}

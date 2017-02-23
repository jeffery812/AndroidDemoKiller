package com.max.tang.demokiller.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.max.tang.demokiller.R;

/**
 * Created by siarhei on 2016-10-04.
 */

public class CircularIndicatorView extends View {

    private Paint mPaint = new Paint();
    private int backgroundColor;
    private int defalutSize;
    private float mRadius = 0;
    private int mWidth, mHeight;
    private int strokeWidth = 40;
    private float scale = 0.8f;
    private int segments[] = {40, 60, 80};
    private int colors[] = {Color.RED, Color.YELLOW, Color.GREEN};
    private int mCurrentPercentage = 0;
    private int mAngleStart = 150;
    private int mAngleTotal = 240;

    public CircularIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //第二个参数就是我们在styles.xml文件中的<declare-styleable>标签
        //即属性集合的标签，在R文件中名称为R.styleable+name
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircularIndicatorView);

        //第一个参数为属性集合里面的属性，R文件名称：R.styleable+属性集合名称+下划线+属性名称
        //第二个参数为，如果没有设置这个属性，则设置的默认的值
        defalutSize = a.getDimensionPixelSize(R.styleable.CircularIndicatorView_default_size, 100);

        backgroundColor =
            a.getColor(R.styleable.CircularIndicatorView_background_color, Color.WHITE);

        //最后记得将TypedArray对象回收
        a.recycle();
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int r = getMeasuredWidth() / 2 - 50;

        int centerX = mWidth / 2;
        int centerY = mHeight / 2;

        scale = scale > 1 ? 1 : scale;

        //mPaint.setColor(backgroundColor);
        //canvas.drawCircle(centerX, centerY, r, mPaint);

        int size = (int) (1.0 * Math.min(mHeight, mWidth) * scale);

        float padding = strokeWidth / 2;
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth((float) strokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(Color.LTGRAY);

        RectF rectBlackBg = new RectF(centerX - size / 2 + padding, centerY - size / 2 + padding,
            centerX + size / 2 - padding, centerY + size / 2 - padding);
        canvas.drawArc(rectBlackBg, 0, 360, false, mPaint);


        mPaint.setColor(getStrokeColor(mCurrentPercentage));
        mPaint.setStrokeWidth(strokeWidth + 10);
        mCurrentPercentage++;
        mCurrentPercentage = Math.min(mCurrentPercentage, 100);
        canvas.drawArc(rectBlackBg, mAngleStart, mAngleStart + mAngleTotal*mCurrentPercentage/100, false, mPaint);
        if( mCurrentPercentage < 100 ) {
            //invalidate();
        }
    }

    public void setValue(int value){
        
    }

    private void setAnimation(int last, int current, int length) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(last, current);
        valueAnimator.setDuration(length);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentPercentage = (int)animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }

    private int getStrokeColor(int percentage){
        int color = colors[colors.length-1];
        for( int i = 0; i < segments.length; i++ ){
            if( percentage < segments[i]){
                color = colors[i];
            }else{
                break;
            }
        }
        return color;
    }

    private int getSegmentIndex(int percentage){
        int ret = segments[0];

        for (int segment:segments) {
            if( percentage < segment) {
                ret = segment;
            }else{
                break;
            }
        }

        return ret;
    }
    private void roundRect(Canvas canvas) {
        RectF rect = new RectF(150, 20, 300, 200);
        canvas.drawRoundRect(rect, 30, //x轴的半径
            30, //y轴的半径
            mPaint);
    }

    private void test(Canvas canvas, boolean useCenter) {
        mPaint.setStyle(Paint.Style.STROKE);
        RectF rectf_head = new RectF(10, 10, 100, 100);//确定外切矩形范围
        rectf_head.offset(10, 20);//使rectf_head所确定的矩形向右偏移100像素，向下偏移20像素
        canvas.drawArc(rectf_head, -10, -460, useCenter, mPaint);//绘制圆弧，含圆心
    }

    private void test2(Canvas canvas) {
        RectF rect = new RectF(0, 0, 100, 100);

        canvas.drawArc(rect, //弧线所使用的矩形区域大小
            30,  //开始角度
            -240, //扫过的角度
            false, //是否使用中心
            mPaint);
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /*
        mWidth = getMySize(100, widthMeasureSpec);
        mHeight = getMySize(100, heightMeasureSpec);

        if (mWidth < mHeight) {
            mHeight = mWidth;
        } else {
            mWidth = mHeight;
        }

        setMeasuredDimension(mWidth, mHeight);
        */

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
    }

    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {//如果没有指定大小，就设置为默认大小
                mySize = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST: {//如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                mySize = size;
                break;
            }
            case MeasureSpec.EXACTLY: {//如果是固定的大小，那就不要去改变它
                mySize = size;
                break;
            }
        }
        return mySize;
    }
}

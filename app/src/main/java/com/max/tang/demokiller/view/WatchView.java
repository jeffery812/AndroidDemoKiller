package com.max.tang.demokiller.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import com.max.tang.demokiller.R;
import com.max.tang.demokiller.utils.SizeUtil;
import java.util.Calendar;

/**
 * Created by zhihuitang on 2017-02-25.
 */

public class WatchView extends View {
    private int mBoardColor, mBorderColor;
    private float mWidth, mHeight;
    private float mRadius;
    private float mMargin = 10;
    private float mTextSize = 16;

    private float mScale = 0.96f;
    private float mTickLength = 15f;
    private float mTickWidth = 1.5f;
    private Paint mPaint;

    private float mHourPointWidth; //时针宽度
    private float mMinutePointWidth; //分针宽度
    private float mSecondPointWidth; //秒针宽度
    private int mPointRadius; // 指针圆角
    private float mPointEndLength; //指针末尾的长度

    private int mColorLong; //长线的颜色
    private int mColorShort; //短线的颜色
    private int mHourPointColor; //时针的颜色
    private int mMinutePointColor; //分针的颜色
    private int mSecondPointColor; //秒针的颜色

    public WatchView(Context context, AttributeSet attrs) {
        super(context, attrs);

        obtainStyledAttrs(context, attrs); //获取自定义的属性
        init(); //初始化画笔
    }

    private void obtainStyledAttrs(Context context, AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WatchView);

        mBoardColor = a.getColor(R.styleable.WatchView_board_color,
            ContextCompat.getColor(context, R.color.colorWhite));
        mBorderColor = a.getColor(R.styleable.WatchView_border_color,
            ContextCompat.getColor(context, R.color.colorBlack));
        mTextSize = a.getDimension(R.styleable.WatchView_text_size, SptoPx(12));

        mHourPointWidth = a.getDimension(R.styleable.WatchView_hour_pointer_width, DptoPx(3));
        mMinutePointWidth = a.getDimension(R.styleable.WatchView_minute_pointer_width, DptoPx(2));
        mSecondPointWidth = a.getDimension(R.styleable.WatchView_second_pointer_width, DptoPx(1));
        mPointRadius =
            (int) a.getDimension(R.styleable.WatchView_pointer_corner_radius, DptoPx(10));
        mPointEndLength = a.getDimension(R.styleable.WatchView_pointer_end_length, DptoPx(10));

        mColorLong = a.getColor(R.styleable.WatchView_scale_long_color, Color.argb(225, 0, 0, 0));
        mColorShort = a.getColor(R.styleable.WatchView_scale_short_color, Color.argb(125, 0, 0, 0));
        mHourPointColor = a.getColor(R.styleable.WatchView_hour_pointer_color, Color.BLACK);
        mMinutePointColor = a.getColor(R.styleable.WatchView_minute_pointer_color, Color.BLACK);
        mSecondPointColor = a.getColor(R.styleable.WatchView_second_pointer_color, Color.RED);

        a.recycle();
    }

    private void init() {

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
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

        mRadius = mScale * Math.min(mWidth, mHeight) / 2;
    }

    private int dipToPx(int dip) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);

        drawBoard(canvas);

        drawTickMark(canvas);

        drawPointer(canvas);

        canvas.restore();

        postInvalidateDelayed(1000);
    }

    private void drawBoard(Canvas canvas) {
        mPaint.setColor(mBoardColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(0, 0, mRadius, mPaint);

        mPaint.setColor(mBorderColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        //canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius - mMargin, mPaint);
    }

    private void drawTickMark(Canvas canvas) {

        mPaint.setStrokeWidth(SizeUtil.Dp2Px(getContext(), 1));
        mPaint.setStyle(Paint.Style.STROKE);

        float width;
        float length;
        for (int i = 0; i < 60; i++) {
            if (i % 5 == 0) {
                width = mTickWidth * 2;
                length = mTickLength * 1.5f;

                mPaint.setStrokeWidth(SizeUtil.Dp2Px(getContext(), 1.5f));
                mPaint.setTextSize(mTextSize);
                String text = ((i / 5) == 0 ? 12 : (i / 5)) + "";
                Rect textBound = new Rect();
                mPaint.getTextBounds(text, 0, text.length(), textBound);
                mPaint.setColor(Color.BLACK);

                canvas.save();
                canvas.translate(0, -mRadius
                    + DptoPx(5)
                    + mTickLength
                    + mMargin
                    + (textBound.bottom - textBound.top) / 2);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.rotate(-6 * i);
                canvas.drawText(text, -(textBound.right + textBound.left) / 2,
                    -(textBound.bottom + textBound.top) / 2, mPaint);
                canvas.restore();
            } else {
                width = mTickWidth;
                length = mTickLength;
            }

            mPaint.setStrokeWidth(width);
            canvas.drawLine(mRadius - mMargin - length, 0, mRadius - mMargin, 0, mPaint);
            canvas.rotate(6);
        }
    }

    private void drawPointer(Canvas canvas) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY); //时
        int minute = calendar.get(Calendar.MINUTE); //分
        int second = calendar.get(Calendar.SECOND); //秒
        int angleHour = (hour % 12) * 360 / 12; //时针转过的角度
        int angleMinute = minute * 360 / 60; //分针转过的角度
        int angleSecond = second * 360 / 60; //秒针转过的角度

        //绘制时针
        canvas.save();
        canvas.rotate(angleHour); //旋转到时针的角度
        RectF rectFHour =
            new RectF(-mHourPointWidth / 2, -mRadius * 3 / 5, mHourPointWidth / 2, mPointEndLength);
        mPaint.setColor(mHourPointColor); //设置指针颜色
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mHourPointWidth); //设置边界宽度
        canvas.drawRoundRect(rectFHour, mPointRadius, mPointRadius, mPaint); //绘制时针
        canvas.restore();
        //绘制分针
        canvas.save();
        canvas.rotate(angleMinute);
        RectF rectFMinute =
            new RectF(-mMinutePointWidth / 2, -mRadius * 3.5f / 5, mMinutePointWidth / 2,
                mPointEndLength);
        mPaint.setColor(mMinutePointColor);
        mPaint.setStrokeWidth(mMinutePointWidth);
        canvas.drawRoundRect(rectFMinute, mPointRadius, mPointRadius, mPaint);
        canvas.restore();

        // second
        canvas.save();
        canvas.rotate(angleSecond);
        RectF rectFSecond = new RectF(-mSecondPointWidth / 2, -mRadius + 15, mSecondPointWidth / 2,
            mPointEndLength);
        mPaint.setColor(mSecondPointColor);
        mPaint.setStrokeWidth(mSecondPointWidth);
        canvas.drawRoundRect(rectFSecond, mPointRadius, mPointRadius, mPaint);
        canvas.restore();

        //绘制中心小圆
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mSecondPointColor);
        canvas.drawCircle(0, 0, mSecondPointWidth * 3, mPaint);
    }

    //Dp转px
    private float DptoPx(int value) {
        return SizeUtil.Dp2Px(getContext(), value);
    }

    //sp转px
    private float SptoPx(int value) {
        return SizeUtil.Sp2Px(getContext(), value);
    }
}

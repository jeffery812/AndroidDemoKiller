package com.max.tang.demokiller.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.max.tang.demokiller.R;
import com.max.tang.demokiller.utils.SizeUtil;
import com.max.tang.demokiller.utils.log.Logger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zhihuitang on 2016-11-10.
 */

public class HistogramView<E extends Comparable<E>> extends View {
    private Paint mPaint;
    private String TAG = "shanghai";
    private float mPadding; //边距
    private int mBarColor;
    private int mBarWidth = 20;

    private int mWidth;
    private int mHeight;
    private List<E> mData;

    public HistogramView(Context context) {
        super(context);
    }

    public HistogramView(Context context, AttributeSet attrs) {
        super(context, attrs);
        obtainStyledAttrs(attrs);
        init();
    }

    private void obtainStyledAttrs(AttributeSet attrs) {
        TypedArray array = null;
        try {
            array = getContext().obtainStyledAttributes(attrs, R.styleable.Histogram);
            mPadding =
                array.getDimension(R.styleable.Histogram_padding, SizeUtil.Dp2Px(getContext(), 10));
            mBarColor = array.getColor(R.styleable.Histogram_color_bar, Color.argb(225, 0, 0, 0));
        } catch (Exception e) {
            //一旦出现错误全部使用默认值
            mPadding = SizeUtil.Dp2Px(getContext(), 10);
            mBarColor = Color.RED;
        } finally {
            if (array != null) {
                array.recycle();
            }
        }
    }

    public HistogramView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCoordinates(canvas);

        if( mData == null || mData.isEmpty() ){
            return;
        }
        int x = getPaddingLeft();
        int y = mHeight - getPaddingBottom();
        Float maxItem = Float.valueOf(Collections.max(mData));
        float scaleY = (mHeight-100)/maxItem;
        float scaleX = mWidth/( mData.size()+2);

        mPaint.setStrokeWidth(mBarWidth);
        //mPaint.setColor(ContextCompat.getColor(getContext(), R.color.cpb_blue));
        mPaint.setColor(mBarColor);
        for (int i = 0; i < mData.size(); i++) {
            canvas.drawLine(x + (i+1)*scaleX, y, x + (i+1)*scaleX,
                y - Float.valueOf((String)mData.get(i))*scaleY, mPaint);
        }
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

        mData = new ArrayList<>();
        //for (int i = 0; i < 25; i++) {
        //    mData.add((int) (Math.random() * 100));
        //}
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = 2000; //设定一个最小值

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST
            || widthMode == MeasureSpec.UNSPECIFIED
            || heightMeasureSpec == MeasureSpec.AT_MOST
            || heightMeasureSpec == MeasureSpec.UNSPECIFIED) {
            Logger.e(TAG, "onMeasure: 宽度高度至少有一个确定的值,不能同时为wrap_content");
        } else { //至少有一个为确定值,要获取其中的最小值
            if (widthMode == MeasureSpec.EXACTLY) {
                width = Math.min(widthSize, width);
            }
            if (heightMode == MeasureSpec.EXACTLY) {
                width = Math.min(heightSize, width);
            }
        }
        Logger.i(TAG, "onMeasure: " + width);
        mWidth = width;
        mHeight = width;
        setMeasuredDimension(width, mHeight);
    }

    private void drawCoordinates(Canvas canvas) {

        // X轴
        mPaint.setStrokeWidth(2);
        canvas.drawLine(getPaddingLeft(), mHeight - getPaddingBottom(), mWidth - getPaddingRight(),
            mHeight - getPaddingBottom(), mPaint);
    }

    public void setData(List<E> data) {
        mData.clear();
        mData.addAll(data);
        postInvalidate();
    }
}

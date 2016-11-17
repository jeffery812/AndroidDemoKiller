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

/**
 * Created by zhihuitang on 2016-11-10.
 */

public class HistogramView extends View {
  private Paint mPaint;
  private String TAG = "shanghai";
  private float mPadding; //边距
  private int mColorBar;

  private int mWidth;
  private int mHeight;

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
      mColorBar = array.getColor(R.styleable.Histogram_color_bar, Color.argb(225, 0, 0, 0));
    } catch (Exception e) {
      //一旦出现错误全部使用默认值
      mPadding = SizeUtil.Dp2Px(getContext(), 10);
      mColorBar = Color.RED;
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
  }

  private void init() {
    mPaint = new Paint();
    mPaint.setAntiAlias(true);
    mPaint.setDither(true);
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
    setMeasuredDimension(width, width);
  }

  private void drawCoordinates(Canvas canvas) {

    // X轴
    Logger.i(TAG, "drawCoordinates");
    canvas.drawLine(getPaddingLeft(), mHeight - getPaddingBottom(), mWidth - getPaddingRight(),
        mHeight - getPaddingBottom(), mPaint);
    // X轴上的箭头
    canvas.drawLine(mWidth - getPaddingRight() - 20, mHeight - getPaddingBottom() - 10,
        mWidth - getPaddingRight(), mHeight - getPaddingBottom(), mPaint);
    canvas.drawLine(mWidth - getPaddingRight() - 20, mHeight - getPaddingBottom() + 10,
        mWidth - getPaddingRight(), mHeight - getPaddingBottom(), mPaint);

    // 绘制Y轴
    canvas.drawLine(getPaddingLeft(), getPaddingTop(), getPaddingLeft(),
        mHeight - getPaddingBottom(), mPaint);

    // Y轴上的箭头
    canvas.drawLine(getPaddingLeft() - 10, getPaddingTop() + 20, getPaddingLeft(), getPaddingTop(),
        mPaint);
    canvas.drawLine(getPaddingLeft() + 10, getPaddingTop() + 20, getPaddingLeft(), getPaddingTop(),
        mPaint);
  }
}

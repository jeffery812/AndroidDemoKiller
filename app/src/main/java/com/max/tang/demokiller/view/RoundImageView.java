package com.max.tang.demokiller.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;
import com.max.tang.demokiller.R;

import static android.R.attr.width;

/**
 * Created by zhihuitang on 2016-11-21.
 * http://blog.csdn.net/lmj623565791/article/details/41967509
 */

public class RoundImageView extends ImageView {
    /**
     * 图片的类型，圆形or圆角
     */
    private int type;
    private static final int TYPE_CIRCLE = 0;
    private static final int TYPE_ROUND = 1;

    /**
     * 圆角大小的默认值
     */
    private static final int BODER_RADIUS_DEFAULT = 10;
    /**
     * 圆角的大小
     */
    private int mBorderRadius;

    /**
     * 绘图的Paint
     */
    private Paint paint;
    /**
     * 圆角的半径
     */
    private int mRadius;
    /**
     * 3x3 矩阵，主要用于缩小放大
     */
    private Matrix matrix;
    /**
     * 渲染图像，使用图像为绘制图形着色
     */
    private BitmapShader mBitmapShader;
    /**
     * view的宽度
     */
    private int mWidth;
    private RectF mRoundRect;

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getDrawable() == null)
        {
            return;
        }
        setUpShader();

        if (type == TYPE_ROUND)
        {
            canvas.drawRoundRect(mRoundRect, mBorderRadius, mBorderRadius,
                paint);
        } else
        {
            canvas.drawCircle(mRadius, mRadius, mRadius, paint);
            // drawSomeThing(canvas);
        }
    }

    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 圆角图片的范围
        if (type == TYPE_ROUND)
            mRoundRect = new RectF(0, 0, getWidth(), getHeight());
    }

    private void inital(Context context, AttributeSet attrs) {
        matrix = new Matrix();
        paint = new Paint();
        //无锯齿
        paint.setAntiAlias(true);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);

        //如果没设置圆角的默认值，在这设置默认值为10dp
        mBorderRadius=dp2px(BODER_RADIUS_DEFAULT);
        // 默认为Circle
        type = array.getInt(R.styleable.RoundImageView_type, TYPE_CIRCLE);
        array.recycle();
    }

    public int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            dp, getResources().getDisplayMetrics());
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //如果类型是圆形，则强制改变view的宽高一致，以小值为准
        if (type == TYPE_CIRCLE) {
            mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
            mRadius = width / 2;
            setMeasuredDimension(width, width);
        }
    }

    /**
     * 初始化BitmapShader
     */
    private void setUpShader() {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        //将drawable转化成bitmap对象
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        if (bitmap == null) {
            return;
        }
        // 将bmp作为着色器，就是在指定区域内绘制bmp
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = 1.0f;
        int viewwidth = getWidth();
        int viewheight = getHeight();
        int drawablewidth = bitmap.getWidth();
        int drawableheight = bitmap.getHeight();
        float dx = 0, dy = 0;
        if (type == TYPE_CIRCLE) {
            // 拿到bitmap宽或高的小值
            int size = Math.min(bitmap.getWidth(), bitmap.getHeight());
            scale = width * 1.0f / size;

        } else if (type == TYPE_ROUND) {
            // 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例
            // 缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值
            scale = Math.max(getWidth() * 1.0f / bitmap.getWidth(), getHeight()
                * 1.0f / bitmap.getHeight());
        }

        if (drawablewidth * viewheight > viewwidth * drawableheight) {
            dx = (viewwidth - drawablewidth * scale) * 0.5f;
        } else {
            dy = (viewheight - drawableheight * scale) * 0.5f;
        }

        // shader的变换矩阵，我们这里主要用于放大或者缩小
        matrix.setScale(scale, scale);
        matrix.postTranslate((int) (dx + 0.5f), (int) (dy + 0.5f));
        // 设置变换矩阵
        mBitmapShader.setLocalMatrix(matrix);
        // 设置shader
        paint.setShader(mBitmapShader);
    }
}

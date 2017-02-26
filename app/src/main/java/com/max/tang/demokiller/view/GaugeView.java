package com.max.tang.demokiller.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.max.tang.demokiller.R;

/**
 * Created by siarhei on 19/07/16.
 */
public class GaugeView extends View {

    private int START_ANGLE = 156;

    private int BAD_ANGLE = 90;
    private int POOR_ANGLE = 45;
    private int OK_ANGLE = 44;

    private int START_BAD_ANGLE = START_ANGLE + BAD_ANGLE; // 156+90
    private int BAD_POOR_ANGLE = BAD_ANGLE + POOR_ANGLE; // 90+45
    private int START_POOR_ANGLE = START_BAD_ANGLE + POOR_ANGLE; // 156+90+44
    private int BAD_OK_ANGLE = BAD_POOR_ANGLE + OK_ANGLE; // 90+45+44
    private int START_OK_ANGLE = START_POOR_ANGLE + OK_ANGLE; //  156+90+44+44

    private int BAD_COLOR = Color.rgb(208, 2, 27);
    private int POOR_COLOR = Color.rgb(255, 155, 0);
    private int OK_COLOR = Color.rgb(128, 212, 35);
    private int EXCELLENT_COLOR = Color.rgb(93, 177, 0);

    private int score = 0;

    private Path mPath = new Path();
    private Paint mPaint = new Paint();
    private Bitmap gaugeImage;

    private float density;
    private int i = 1;
    private int wHeight = 0;
    private int wWidth = 0;
    private Rect gaugeWrapper;
    private RectF outerCircle;
    private RectF innerCircle;
    private double center;
    private double pointCircleRadius;
    private float pointRadius;

    public GaugeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        density = getResources().getDisplayMetrics().density;
        gaugeImage = BitmapFactory.decodeResource(getResources(), R.drawable.gauge);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onLayout (boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        wHeight = getHeight();
        wWidth = getWidth();
        gaugeWrapper = new Rect(0, 0, wWidth, wHeight);
        outerCircle = getOval(getDp(38));
        innerCircle = getOval(getDp(48));
        center = wWidth / 2;
        pointCircleRadius = center - getDp(43);
        pointRadius = getDp(8);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(gaugeImage, null, gaugeWrapper, null);

        mPaint.setColor(BAD_COLOR);

        if (i <= BAD_ANGLE) {
            drawPart(canvas, START_ANGLE, i, START_ANGLE + i, -i - 3);

        } else if (i <= BAD_POOR_ANGLE) {
            drawBadPart(canvas);

            mPaint.setColor(POOR_COLOR);
            drawPart(canvas, START_BAD_ANGLE, i - BAD_ANGLE, START_ANGLE + i, BAD_ANGLE - i);

        } else if (i <= BAD_OK_ANGLE) {
            drawBadPart(canvas);
            drawPoorPart(canvas);

            mPaint.setColor(OK_COLOR);
            drawPart(canvas, START_POOR_ANGLE, i - BAD_POOR_ANGLE, START_ANGLE + i, BAD_POOR_ANGLE - i);

        } else {
            drawBadPart(canvas);
            drawPoorPart(canvas);
            drawOkPart(canvas);

            mPaint.setColor(EXCELLENT_COLOR);
            drawPart(canvas, START_OK_ANGLE, i - BAD_OK_ANGLE, START_ANGLE + i, BAD_OK_ANGLE - i);
        }

        drawPoint(canvas);

        if (i < score * 2.22) {
            i += 2;
            invalidate();
        }
    }

    /**
     * Setter for React Native prop "score"
     * @param score int from 0 to 100
     */
    public void setScore(int score) {
        this.score = score;
    }

    private void drawPart(Canvas canvas, int start1, int sweep1, int start2, int sweep2) {
        mPath.reset();
        mPath.arcTo(outerCircle, start1, sweep1, false);
        mPath.arcTo(innerCircle, start2, sweep2, false);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    private void drawBadPart(Canvas canvas) {
        drawPart(canvas, START_ANGLE, BAD_ANGLE, START_BAD_ANGLE, -BAD_ANGLE - 3);
    }

    private void drawPoorPart(Canvas canvas) {
        mPaint.setColor(POOR_COLOR);
        drawPart(canvas, START_BAD_ANGLE, POOR_ANGLE, START_POOR_ANGLE, -POOR_ANGLE);
    }

    private void drawOkPart(Canvas canvas) {
        mPaint.setColor(OK_COLOR);
        drawPart(canvas, START_POOR_ANGLE, OK_ANGLE, START_OK_ANGLE, -OK_ANGLE);
    }

    private void drawPoint(Canvas canvas) {
        double x = center + (pointCircleRadius * Math.cos((START_ANGLE + i) * Math.PI / 180));
        double y = center + (pointCircleRadius * Math.sin((START_ANGLE + i) * Math.PI / 180));

        canvas.drawCircle((float)x, (float)y, pointRadius, mPaint);
    }

    private int getDp(int value) {
        return (int)(density * value);
    }

    private RectF getOval(int padding) {
        return new RectF(padding, padding, wWidth - padding, wWidth - padding);
    }
}

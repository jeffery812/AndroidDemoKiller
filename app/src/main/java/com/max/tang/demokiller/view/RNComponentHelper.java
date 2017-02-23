package com.max.tang.demokiller.view;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by siarhei on 2016-10-04.
 */

public class RNComponentHelper {

    public static int GRAY_COLOR = Color.argb(25, 0, 0, 0);
    public static int SHADOW_COLOR = Color.argb(127, 0, 0, 0);
    public static int SHADOW_RADIUS = 9;
    public static int SHADOW_X_OFFSET = 3;
    public static int SHADOW_Y_OFFSET = 3;

    public static void addBaselineStyleToPaint(Paint paint, int stroke) {
        paint.setColor(GRAY_COLOR);
        paint.setStrokeWidth(stroke);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
    }

    public static void addProgressStyleToPaint(Paint paint, String color, int stroke) {
        paint.setColor(Color.parseColor(color));
        paint.setStrokeWidth(stroke);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);

        addShadowToPaint(paint);
    }

    public static void addCircleStyleToPaint(Paint paint, String color) {
        paint.setColor(Color.parseColor(color));

        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeCap(Paint.Cap.BUTT);
        paint.setAntiAlias(true);
    }

    public static void addShadowToPaint(Paint paint) {
        paint.setShadowLayer(SHADOW_RADIUS, SHADOW_X_OFFSET, SHADOW_Y_OFFSET, SHADOW_COLOR);
    }
}

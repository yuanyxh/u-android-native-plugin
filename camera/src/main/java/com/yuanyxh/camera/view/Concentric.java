package com.yuanyxh.camera.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.yuanyxh.camera.R;

public class Concentric extends View {
    private final Paint paint;

    private int strokeWidth = 6;

    private int radius = 22;

    private int color = Color.parseColor("#91d439");

    public Concentric(Context _context) {
        this(_context, null);
    }

    public Concentric(Context _context, AttributeSet attrs) {
        this(_context, attrs, 0);
    }

    public Concentric(Context _context, AttributeSet attrs, int defStyleAttr) {
        super(_context, attrs, defStyleAttr);
        TypedArray typedArray = _context.obtainStyledAttributes(attrs, R.styleable.Concentric);
        int strokeWidth = typedArray.getInt(R.styleable.Concentric_strokeWidth, 6);
        int _color = typedArray.getColor(R.styleable.Concentric_color, Color.parseColor("#91d439"));
        int _radius = typedArray.getInt(R.styleable.Concentric_radius, 22);

        setStrokeWidth(strokeWidth);
        setColor(_color);
        setRadius(_radius);

        paint = new Paint();
        paint.setAntiAlias(true); // 消除锯齿

        typedArray.recycle();  // 注意回收
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int center = getWidth() / 2;

        // 绘制内圆
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        canvas.drawCircle(center, center, radius - (float)strokeWidth / 2, paint);

        // 绘制外圆(空心)
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(strokeWidth);

        canvas.drawCircle(center, center, radius - (float)strokeWidth / 2, paint);

        super.onDraw(canvas);
    }


    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int _strokeWidth) {
        strokeWidth = _strokeWidth;

        requestLayout();
        invalidate();
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int _radius) {
        radius = _radius;

        requestLayout();
        invalidate();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int _color) {
        color = _color;

        requestLayout();
        invalidate();
    }
}
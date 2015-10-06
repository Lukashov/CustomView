package com.cuctomview.den.examplecustomview;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.Scroller;
import android.widget.Toast;


/**
 * Created by Den on 03.10.15.
 */
public class ArcMenu extends View {

    final int MIN_WIDTH = 300;
    final int MIN_HEIGHT = 300;
    final int DEFAULT_COLOR = Color.RED;

    private GestureDetector mDetector;
    private Scroller mScroller;
    private boolean mAutoCenterInSlice;
    private ObjectAnimator mAutoCenterAnimator;

    private int mColor;
    private Paint mPaint;
    private Integer startAngle;
    private Integer endAngle;
    private ArcMenu arcMenu;

    private RectF mBounds;


    public ArcMenu(Context context, Integer startAngle, Integer endAngle) {
        super(context);
        this.startAngle = startAngle;
        this.endAngle = endAngle;
        init();
    }

    public ArcMenu(Context context, AttributeSet attrs, Integer startAngle, Integer endAngle) {
        super(context, attrs);
        this.startAngle = startAngle;
        this.endAngle = endAngle;
        init();
    }

    public ArcMenu(Context context, AttributeSet attrs, int defStyleAttr, Integer startAngle, Integer endAngle) {
        super(context, attrs, defStyleAttr);
        this.startAngle = startAngle;
        this.endAngle = endAngle;
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ArcMenu(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, Integer startAngle, Integer endAngle) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.startAngle = startAngle;
        this.endAngle = endAngle;
        init();
    }

    private void init() {
        setMinimumWidth(MIN_WIDTH);
        setMinimumHeight(MIN_HEIGHT);
        mColor = DEFAULT_COLOR;
        mPaint = new Paint();


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(getSuggestedMinimumWidth(), getSuggestedMinimumHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        int radius;

        if (width > height) {
            radius = height / 2;
        } else {
            radius = width / 2;
        }
//        final RectF rect = new RectF();
        //Example values
        mBounds.set(width / 2 - radius + 10, height / 2 - radius + 10, width / 2 + radius - 10, height / 2 + radius - 10);

        //Fill Sector
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawArc(mBounds, startAngle, endAngle, true, mPaint);

        //Line
        mBounds.set(width / 2 - radius + 5, height / 2 - radius + 5, width / 2 + radius - 5, height / 2 + radius - 5);

        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(5);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(mBounds, startAngle, endAngle, true, mPaint);

        //Ring1
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(10);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(mBounds, 0, 360, false, mPaint);

        mBounds.set(width / 2 - radius / 4, height / 2 - radius / 4, width / 2 + radius / 4, height / 2 + radius / 4);
        //Ring3
        mPaint.setColor(Color.BLUE);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawArc(mBounds, 0, 360, false, mPaint);

        //Ring2
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(20);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(mBounds, 0, 360, false, mPaint);

        canvas.save();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mBounds = new RectF(0, 0, w, h);
    }

    public void setColor(int color) {
        mColor = color;
    }

}

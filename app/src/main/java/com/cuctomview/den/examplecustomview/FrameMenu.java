package com.cuctomview.den.examplecustomview;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Context;
import android.graphics.RectF;
import android.os.Build;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Created by Den on 05.10.15.
 */
public class FrameMenu extends FrameLayout {

    OnCustomEventListener mListener;
    private RectF mPieBounds = new RectF();

    private GestureDetectorCompat mDetector;
    private Scroller mScroller;
    private ValueAnimator mScrollAnimator;
    private int mPieRotation;

    private boolean mAutoCenterInSlice = false;


    private float mRotation = 0;

    private double startAngle;


    public FrameMenu(Context context) {
        super(context);
        init();
    }

    public FrameMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FrameMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FrameMenu(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public interface OnCustomEventListener {
        void onEvent();
    }

    public void setCustomEventListener(OnCustomEventListener eventListener) {
        mListener = eventListener;
    }


    public void init (){
        if (Build.VERSION.SDK_INT < 11) {
            mScroller = new Scroller(getContext());
        } else {
            mScroller = new Scroller(getContext(), null, true);
        }
        if (Build.VERSION.SDK_INT >= 11) {
            mScrollAnimator = ValueAnimator.ofFloat(0, 1);
            mScrollAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    tickScrollAnimation();
                }
            });
        }

        mDetector = new GestureDetectorCompat(getContext(), new MyGestureListener());
        mDetector.setIsLongpressEnabled(false);

        int width = getWidth();
        int height = getHeight();
        int radius;

        if (width > height) {
            radius = height / 2;
        } else {
            radius = width / 2;
        }
        mPieBounds.set(width / 2 - radius + 10, height / 2 - radius + 10, width / 2 + radius - 10, height / 2 + radius - 10);
    }

    private void tickScrollAnimation() {
        if (!mScroller.isFinished()) {
            mScroller.computeScrollOffset();
            setPieRotation(mScroller.getCurrY());
        } else {
            if (Build.VERSION.SDK_INT >= 11) {
                mScrollAnimator.cancel();
            }
//            onScrollFinished();
        }
    }

//    private void onScrollFinished() {
//        if (mAutoCenterInSlice) {
//            centerOnCurrentItem();
//        } else {
//            mPieView.decelerate();
//        }
//    }

//    private void centerOnCurrentItem() {
//        Item current = mData.get(getCurrentItem());
//        int targetAngle = current.mStartAngle + (current.mEndAngle - current.mStartAngle) / 2;
//        targetAngle -= mCurrentItemAngle;
//        if (targetAngle < 90 && mPieRotation > 180) targetAngle += 360;
//
//        if (Build.VERSION.SDK_INT >= 11) {
//            // Fancy animated version
//            mAutoCenterAnimator.setIntValues(targetAngle);
//            mAutoCenterAnimator.setDuration(AUTOCENTER_ANIM_DURATION).start();
//        } else {
//            // Dull non-animated version
//            //mPieView.rotateTo(targetAngle);
//        }
//    }

    @Override
    protected int computeHorizontalScrollRange()
    {
        return this.getWidth();
    }

    @Override
    protected int computeVerticalScrollRange()
    {
        return this.getHeight();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = mDetector.onTouchEvent(event);

        if (!result) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                result = true;
            }
        }
        return result;
    }

    public int getPieRotation() {
        return mPieRotation;
    }

    public void setPieRotation(int rotation) {
        rotation = (rotation % 360 + 360) % 360;
        mPieRotation = rotation;
        this.rotateTo(rotation);
    }

    public void rotateTo(float pieRotation) {
        mRotation = pieRotation;
        if (Build.VERSION.SDK_INT >= 11) {
            setRotation(pieRotation);
        } else {
            invalidate();
        }
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public boolean onDown(MotionEvent event) {
            Log.d(DEBUG_TAG, "onDown: " + event.toString());
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            float scrollTheta = vectorToScalarScroll(
                    distanceX,
                    distanceY,
                    e2.getX() - mPieBounds.centerX(),
                    e2.getY() - mPieBounds.centerY());

            setPieRotation(getPieRotation() - (int) scrollTheta / 4);

            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
            Log.d(DEBUG_TAG, "onFling: " + event1.toString()+event2.toString());

            float scrollTheta = vectorToScalarScroll(
                    velocityX,
                    velocityY,
                    event2.getX() - mPieBounds.centerX(),
                    event2.getY() - mPieBounds.centerY());
            mScroller.fling(
                    0,
                    (int) getPieRotation(),
                    0,
                    (int) scrollTheta / 4,
                    0,
                    0,
                    Integer.MIN_VALUE,
                    Integer.MAX_VALUE);

            if (Build.VERSION.SDK_INT >= 11) {
                mScrollAnimator.setDuration(mScroller.getDuration());
                mScrollAnimator.start();
            }
            return true;
        }
    }

    private static float vectorToScalarScroll(float dx, float dy, float x, float y) {
        float l = (float) Math.sqrt(dx * dx + dy * dy);

        float crossX = -y;
        float crossY = x;

        float dot = (crossX * dx + crossY * dy);
        float sign = Math.signum(dot);

        Log.d("DEBUG: ", ""+ l * sign);
        return l * sign;
    }
}

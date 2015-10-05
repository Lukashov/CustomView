package com.cuctomview.den.examplecustomview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by Den on 04.10.15.
 */
public class MenuGroup extends ViewGroup {
    public MenuGroup(Context context) {
        super(context);
    }

    public MenuGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MenuGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MenuGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();

            int w = Math.max(minw, MeasureSpec.getSize(widthMeasureSpec));

            int minh = w + getPaddingBottom() + getPaddingTop();
            int h = Math.min(MeasureSpec.getSize(heightMeasureSpec), minh);

            setMeasuredDimension(w, h);
    }
}

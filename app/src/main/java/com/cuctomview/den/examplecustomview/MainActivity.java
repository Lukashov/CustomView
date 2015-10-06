package com.cuctomview.den.examplecustomview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ArcMenu mArcMenu;
    private MenuGroup mMenuGroup;

    private FrameMenu mFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFrameLayout = (FrameMenu) findViewById(R.id.fLayout_AM);
        mFrameLayout.addView(new ArcMenu(getApplicationContext(),0,72));
        mFrameLayout.addView(new ArcMenu(getApplicationContext(),72,72));
        mFrameLayout.addView(new ArcMenu(getApplicationContext(),144,72));
        mFrameLayout.addView(new ArcMenu(getApplicationContext(),216,72));
        mFrameLayout.addView(new ArcMenu(getApplicationContext(), 288, 72));

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        mFrameLayout.setCustomEventListener(new FrameMenu.OnCustomEventListener() {
            public void onEvent() {
                //do whatever you want to do when the event is performed.
            }
        });
    }


    @Override
    public void onClick(View v) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        mFrameLayout.startAnimation(animation);

    }

}

package com.example.android.scenetransition;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.AutoTransition;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    
    private View smallMenu;
    private View largeMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToggle();
    }

    private void initToggle() {

        final FrameLayout container = (FrameLayout) findViewById(R.id.container);

        smallMenu = getLayoutInflater().inflate(R.layout.closed, container, false);
        largeMenu = getLayoutInflater().inflate(R.layout.opened, container, false);

        final Scene sceneStart = new Scene(container, smallMenu);
        sceneStart.enter();

       View clickMe = findViewById(R.id.click_me);
        if (clickMe == null) return;
        clickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View finalView = getContentView();
                final Scene sceneEnd = new Scene(container, finalView);
                final Transition t = new AutoTransition();
                t.setDuration(500);
                TransitionManager.go(sceneEnd, t);
            }
        });
    }

    private View getContentView() {
        Log.e("ff", Thread.currentThread().getStackTrace()[2] + "" + smallMenu.isLaidOut());
        Log.e("ff", Thread.currentThread().getStackTrace()[2] + "" + smallMenu.isShown());
        Log.e("ff", Thread.currentThread().getStackTrace()[2] + "" + largeMenu.isLaidOut());
        Log.e("ff", Thread.currentThread().getStackTrace()[2] + "" + largeMenu.isShown());
        if (smallMenu.isShown())
            return largeMenu;
        else
            return smallMenu;
    }
}

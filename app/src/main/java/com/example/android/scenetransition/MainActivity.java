package com.example.android.scenetransition;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.AutoTransition;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

public class MainActivity extends AppCompatActivity {

    private View smallMenu;
    private View largeMenu;

    boolean displayInNewWindow;
    private FrameLayout container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToggle();
    }

    private void initToggle() {

        container = new FrameLayout(getApplicationContext());

        smallMenu = getLayoutInflater().inflate(R.layout.closed, container, false);
        largeMenu = getLayoutInflater().inflate(R.layout.opened, container, false);

        View clickMe = findViewById(R.id.click_me);
        if (clickMe == null) return;
        clickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 View finalView = null;
                if (displayInNewWindow()) {
                    finalView = getContentView();
                    PopupWindow pw=createWindow();
                    setWindowContentView(container, finalView);
                    show(pw, v);
                } else {
                    finalView = getContentView();
                    setWindowContentView(container, finalView);
                }
            }
        });
    }

    private void show(PopupWindow popupWindow, View anchor) {
        Rect anchorLocation = DialogUtils.locateView(anchor);
        popupWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, anchorLocation.right, anchorLocation.top);
    }

    private PopupWindow createWindow() {
        PopupWindow popupWindow = new PopupWindow();
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
//        popupWindow.setOutsideTouchable(true);
//        popupWindow.setFocusable(true);
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setContentView(container);
        return popupWindow;
    }

    private void setWindowContentView(FrameLayout container, View finalView) {
        Scene finalScene = new Scene(container, finalView);
        finalScene.enter(); //Calling this will make final scene text appearing on
        // before container has finished resizing.
        final Transition t = new AutoTransition();
        t.setDuration(500);
        TransitionManager.go(finalScene, t);//This will make the container.addView no
        // need to do it ourselves
    }

    private boolean displayInNewWindow() {
        return !displayInNewWindow;
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

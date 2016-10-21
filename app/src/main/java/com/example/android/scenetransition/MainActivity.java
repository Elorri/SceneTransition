package com.example.android.scenetransition;

import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.AutoTransition;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

public class MainActivity extends AppCompatActivity {


    private View viewA;
    private View viewB;

    private View oldView;
    private View currentView;
    private View toggleView;


    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final View openButton= findViewById(R.id.open);
        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow = new PopupWindow();
                popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
                popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

                View windowView=View.inflate(view.getContext(), R.layout.window_main, null);
                popupWindow.setContentView(windowView);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                Rect viewLocation = DialogUtils.locateView(openButton);
                int x = viewLocation.left;
                int y = viewLocation.top;
                popupWindow.showAtLocation(openButton, Gravity.NO_GRAVITY, x , y );

                initToggle(windowView);
            }
        });


    }



    private void initToggle(View view) {
        final ViewGroup container = (ViewGroup) view.findViewById(R.id.container);
        viewA = getLayoutInflater().inflate(R.layout.menu_a, container, false);
        viewB = getLayoutInflater().inflate(R.layout.menu_b, container, false);
        currentView = viewA;

        final Scene sceneStart = new Scene(container, viewA);
        sceneStart.enter();

        view.findViewById(R.id.toggle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle(container, getFinalScene());
            }
        });
    }


    private View getFinalScene() {
        if (currentView.equals(viewA)) {
            oldView = currentView;
            currentView = viewB;
            return viewB;
        } else {
            oldView = currentView;
            currentView = viewA;
            return viewA;
        }
    }

    private void toggle(View container, View finalView) {

        View screenView = findViewById(android.R.id.content);
        container

        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.MATCH_PARENT);

        final Transition t = new AutoTransition();
        t.setDuration(3000);
        Scene finalScene = new Scene((ViewGroup) container, finalView);
        TransitionManager.go(finalScene, t);

        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

    }
}

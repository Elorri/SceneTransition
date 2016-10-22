package com.example.android.scenetransition;

import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.AutoTransition;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

public class MainActivity extends AppCompatActivity {

    private View viewA;
    private View viewB;
    private View viewACopy;
    private View viewBCopy;


    private View oldView;
    private View currentView;
    private View toggleView;


    private PopupWindow popupWindow;
    private boolean isTransitionning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final View openButton = findViewById(R.id.open);
        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow = new PopupWindow();
                popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
                popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
                View windowView = View.inflate(view.getContext(), R.layout.window_main, null);
                popupWindow.setContentView(windowView);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                Rect viewLocation = DialogUtils.locateView(openButton);
                int x = viewLocation.left;
                int y = viewLocation.top;
                popupWindow.showAtLocation(openButton, Gravity.NO_GRAVITY, x, y);
                initToggle(windowView);
            }
        });
    }


    private void initToggle(View view) {
        final ViewGroup oldViewContainer = (ViewGroup) view.findViewById(R.id.oldViewContainer);
        final ViewGroup newViewContainer = (ViewGroup) view.findViewById(R.id.newViewContainer);
        final ViewGroup container = (ViewGroup) view.findViewById(R.id.container);
        viewA = getLayoutInflater().inflate(R.layout.menu_a, container, false);
        viewB = getLayoutInflater().inflate(R.layout.menu_b, container, false);

        viewACopy = getLayoutInflater().inflate(R.layout.menu_a, container, false);
        viewBCopy = getLayoutInflater().inflate(R.layout.menu_b, container, false);
        currentView = viewA;

        final Scene sceneStart = new Scene(container, viewA);
        sceneStart.enter();

        viewA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle(oldViewContainer, newViewContainer, container, getFinalView());
            }
        });
        viewB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle(oldViewContainer, newViewContainer, container, getFinalView());
            }
        });
    }


    private View getFinalView() {
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

    private View getFinalViewCopy() {
        if (currentView.equals(viewA)) {
            return viewBCopy;
        } else {
            return viewACopy;
        }
    }

    private View getOldViewCopy() {
        if (currentView.equals(viewA)) {
            return viewACopy;
        } else {
            return viewBCopy;
        }
    }

    private View getOldView() {
        if (currentView.equals(viewA)) {
            return viewA;
        } else {
            return viewB;
        }
    }


    private void toggle(final ViewGroup oldViewContainer, final ViewGroup newViewContainer, View container, View finalView) {

        if (isTransitionning) {
            //If user click on menu while it is transitioning we don't start another transition.
            return;
        }

        oldViewContainer.setVisibility(View.INVISIBLE);
        oldViewContainer.removeAllViews();
        oldViewContainer.addView(getOldViewCopy());
        Log.e("St", Thread.currentThread().getStackTrace()[2] + "getOldViewCopy()" + getOldViewCopy());

        newViewContainer.setVisibility(View.INVISIBLE);
        newViewContainer.removeAllViews();
        newViewContainer.addView(getFinalViewCopy());
        Log.e("St", Thread.currentThread().getStackTrace()[2] + "getFinalViewCopy()" + getFinalViewCopy());

//        final Transition t = new AutoTransition();
//        t.setDuration(3000);

        final Transition t = TransitionInflater.from(this).inflateTransition(R.transition.resize);

        t.setDuration(3000);

//        Scene oldScene = new Scene((ViewGroup) container, getOldView());
//        Log.e("St", Thread.currentThread().getStackTrace()[2] + "currentView" + currentView);
//        oldScene.exit();
        Scene finalScene = new Scene((ViewGroup) container, finalView);
        Log.e("St", Thread.currentThread().getStackTrace()[2] + "finalView" + finalView);
        //  finalScene.enter();
        t.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                Log.e("St", Thread.currentThread().getStackTrace()[2] + "");
                isTransitionning = true;
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                Log.e("St", Thread.currentThread().getStackTrace()[2] + "");
                oldViewContainer.removeAllViews();
                newViewContainer.removeAllViews();
                isTransitionning = false;
            }

            @Override
            public void onTransitionCancel(Transition transition) {
                Log.e("St", Thread.currentThread().getStackTrace()[2] + "");
            }

            @Override
            public void onTransitionPause(Transition transition) {
                Log.e("St", Thread.currentThread().getStackTrace()[2] + "");
            }

            @Override
            public void onTransitionResume(Transition transition) {
                Log.e("St", Thread.currentThread().getStackTrace()[2] + "");
            }
        });

        TransitionManager.go(finalScene, t);
    }

}

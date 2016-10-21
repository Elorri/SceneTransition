package com.example.android.scenetransition;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.AutoTransition;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {


    private View viewA;
    private View viewB;

    private View oldView;
    private View currentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initToggle();
    }

    private void initToggle() {
        final ViewGroup container = (ViewGroup) findViewById(R.id.container);
        viewA = getLayoutInflater().inflate(R.layout.menu_a, container, false);
        viewB = getLayoutInflater().inflate(R.layout.menu_b, container, false);
        currentView = viewA;

        final Scene sceneStart = new Scene(container, viewA);
        sceneStart.enter();

        findViewById(R.id.toggle).setOnClickListener(new View.OnClickListener() {
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
        final Transition t = new AutoTransition();
        t.setDuration(3000);

//        if (oldView!=null){
//            Scene oldScene = new Scene((ViewGroup) container, oldView);
//            oldScene.exit();
//            TransitionManager.go(oldScene, t);
//        }

        Scene finalScene = new Scene((ViewGroup) container, finalView);
        TransitionManager.go(finalScene, t);

    }
}

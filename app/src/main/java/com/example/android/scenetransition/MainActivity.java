package com.example.android.scenetransition;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.AutoTransition;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToggle();
     }

    private void initToggle() {

        final RelativeLayout sceneBase = (RelativeLayout) findViewById(R.id.toggle);

        final View scene2closed =  getLayoutInflater().inflate(R.layout.closed, sceneBase, false);
        final View scene2opened =  getLayoutInflater().inflate(R.layout.opened, sceneBase, false);

        final Scene sceneClose = new Scene(sceneBase, scene2closed);
        sceneClose.enter();

        final Scene sceneOpen = new Scene(sceneBase, scene2opened);

        final Transition t = new AutoTransition();
        t.setDuration(500);

//        final ViewGroup scene2closed = (ViewGroup)mInflater.inflate(R.layout.include_map_select_closed, sceneBase, false);
//        final Scene sceneClose = new Scene(sceneBase, scene2closed);
//        sceneClose.enter();

        scene2closed.findViewById(R.id.toggle_scene).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.go(sceneOpen, t);
            }
        });

        scene2opened.findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.go(sceneClose, t);
            }
        });
    }
}

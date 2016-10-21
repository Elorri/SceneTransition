package com.example.android.scenetransition;

import android.graphics.Rect;
import android.view.View;

/**
 * Created by nebo-android2016 on 21/10/16.
 */
public class DialogUtils {
    public static Rect locateView(View v)
    {
        int[] location = new int[2];
        if (v == null)
            return null;
        try
        {
            v.getLocationOnScreen(location);
        }
        catch (NullPointerException npe)
        {
            //Happens when the view doesn't exist on screen anymore.
            return null;
        }
        Rect bounds = new Rect();
        bounds.left = location[0];
        bounds.top = location[1];
        bounds.right = location[0] + v.getWidth();
        bounds.bottom = location[1] + v.getHeight();
        return bounds;
    }
}

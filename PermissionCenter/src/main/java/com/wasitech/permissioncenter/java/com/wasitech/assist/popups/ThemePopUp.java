package com.wasitech.permissioncenter.java.com.wasitech.assist.popups;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.wasitech.basics.app.ProcessApp;

public abstract class ThemePopUp extends PopupWindow {
    protected View view;

    public ThemePopUp(Context context, int light, int dark) {
        super(context);
        if (ProcessApp.isDarkTheme()) {
            view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(dark, null);
        } else {
            view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(light, null);
        }
        setFocusable(true);
        setAnimationStyle(android.R.anim.cycle_interpolator);
        setContentView(view);
        setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public ThemePopUp(Context context, int light, Transition in,Transition out) {
        super(context);

            view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(light, null);
        setFocusable(true);
        setEnterTransition(in);
        setExitTransition(out);
        setAnimationStyle(android.R.anim.cycle_interpolator);
        setContentView(view);
        setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public ThemePopUp(Context context, int light) {
        super(context);

        view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(light, null);
        setFocusable(true);
        setAnimationStyle(android.R.anim.cycle_interpolator);
        setContentView(view);
        setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }
}

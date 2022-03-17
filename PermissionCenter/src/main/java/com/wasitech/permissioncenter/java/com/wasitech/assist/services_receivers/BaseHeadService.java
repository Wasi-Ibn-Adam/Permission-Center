package com.wasitech.permissioncenter.java.com.wasitech.assist.services_receivers;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wasitech.assist.R;
import com.wasitech.assist.command.family.Intents;

import com.wasitech.basics.classes.Issue;
import com.wasitech.database.Params;

@SuppressLint({"InflateParams", "ClickableViewAccessibility"})
public abstract class BaseHeadService extends BaseService {
    protected View hideHead;
    protected ImageButton hide;
    private final int time = 5000;
    protected WindowManager windowManager;
    protected View viewHead;
    protected View closingView;
    protected ImageButton btnClick;
    protected static int width;
    protected static int height;

    public BaseHeadService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (windowManager == null)
            windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        setHideHead();
    }

    protected abstract String setHideName();

    protected abstract WindowManager.LayoutParams setParams();

    protected View.OnTouchListener setHideListener(View v) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;
        closingView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.closing_service, null);
        return null;
    }

    protected void removeView(View v) {
        windowManager.removeView(v);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (hideHead != null) windowManager.removeView(hideHead);
            if (viewHead != null) windowManager.removeView(viewHead);
        } catch (Exception e) {
            Issue.print(e, BaseHeadService.class.getName());
        }
    }

    protected void touch(WindowManager.LayoutParams params,int num) {
        try {
            new Thread(() -> new Handler(Looper.getMainLooper()).postDelayed(() -> {
                try {
                    windowManager.removeView(viewHead);
                    if (hideHead == null) {
                        setHideHead();
                    }
                    windowManager.addView(hideHead, params);
                    if (params.y > (height - (height / 4)) && (params.x > ((width / 2) - (width / 5)) && params.x < ((width / 2) + (width / 5)))) {
                        try {
                            windowManager.removeView(closingView);
                            windowManager.removeView(hideHead);
                            switch (num){
                                case Params.AUDIO_HEAD_SERVICE:{stopService(Intents.AudioHead(getApplicationContext()));break;}
                                case Params.CAMERA_HEAD_SERVICE:{stopService(Intents.CamHead(getApplicationContext()));break;}
                                case Params.COMMAND_HEAD_SERVICE:{stopService(Intents.ComHead(getApplicationContext()));break;}
                            }
                        } catch (Exception e) {
                            Issue.print(e, BaseHeadService.class.getName());
                        }
                    }
                } catch (Exception e) {
                    Issue.print(e, BaseHeadService.class.getName());
                }
            }, time)).start();
        } catch (Exception e) {
            Issue.print(e, BaseHeadService.class.getName());
        }
    }

    protected void onHideClicked() { }

    private void setHideHead() {
        hideHead = LayoutInflater.from(this).inflate(R.layout.hide_service, null);
        hideHead.setOnTouchListener(setHideListener(hideHead));
        hide = hideHead.findViewById(R.id.close_hide_view);
        hide.setOnClickListener(v -> onHideClicked());
        hide.setOnTouchListener(setHideListener(hideHead));
        TextView text = hideHead.findViewById(R.id.close_hide_text);
        text.setText(setHideName());
    }

    protected void setHideHead(boolean state) {
        try {
            if (state) {
                if (hideHead == null) {
                    setHideHead();
                }
                windowManager.addView(hideHead, setParams());
            } else {
                windowManager.removeView(hideHead);
            }
        } catch (Exception e) {
            Issue.print(e, BaseHeadService.class.getName());
        }
    }

    protected void setHeadView() {
        viewHead = LayoutInflater.from(this).inflate(R.layout.service_heads_layout, null);
        viewHead.setOnTouchListener(setHideListener(viewHead));
        btnClick = viewHead.findViewById(R.id.service_heads);
        btnClick.setOnTouchListener(setHideListener(viewHead));
    }

}

package com.wasitech.permissioncenter.java.com.wasitech.assist.services_receivers;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.wasitech.assist.R;
import com.wasitech.assist.adapter_listeners.ServiceHeadsTouchListener;
import com.wasitech.assist.command.family.Intents;
import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.classes.Issue;
import com.wasitech.camera.cam.Cam;
import com.wasitech.database.Params;

import static com.wasitech.basics.classes.Basics.WindowServicesParams;

@SuppressLint({"InflateParams", "ClickableViewAccessibility"})
public class CameraHeadService extends BaseHeadService {
    protected int CAM_ID = Params.CAMERA_BACK;
    public WindowManager.LayoutParams params;

    public CameraHeadService() {
        super(Params.CAMERA_HEAD);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (params == null)
            params = Basics.WindowServicesParams(0, 400, Gravity.START);
        onHideClicked();
    }

    @Override
    protected String setHideName() {
        return "C\nA\nM";
    }

    @Override
    protected WindowManager.LayoutParams setParams() {
        return params;
    }

    @Override
    protected View.OnTouchListener setHideListener(View v) {
        super.setHideListener(v);
        return new ServiceHeadsTouchListener(getApplicationContext()) {
            public int initialX;
            public int initialY;
            public float initialTouchX;
            public float initialTouchY;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                try {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            initialX = params.x;
                            initialY = params.y;
                            initialTouchX = event.getRawX();
                            initialTouchY = event.getRawY();
                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            if (params.y > (height - (height / 4)) && (params.x > ((width / 2) - (width / 5)) && params.x < ((width / 2) + (width / 5)))) {
                                windowManager.updateViewLayout(v, closingView.getLayoutParams());
                                windowManager.removeView(closingView);
                                getApplicationContext().stopService(Intents.CamHead(getApplicationContext()));
                                break;
                            }
                            if (params.gravity == (Gravity.TOP | Gravity.START)) {
                                if (params.x > width / 2)
                                    params.gravity = (Gravity.TOP | Gravity.END);
                                params.x = 0;
                            } else if (params.gravity == (Gravity.TOP | Gravity.END)) {
                                if (params.x > width / 2)
                                    params.gravity = (Gravity.TOP | Gravity.START);
                                params.x = 0;
                            }

                            new Handler().postDelayed(() -> {
                                try {
                                    windowManager.updateViewLayout(v, params);
                                } catch (Exception e) {
                                    Issue.print(e, CameraHeadService.class.getName());
                                }
                            }, 100);
                            break;
                        }
                        case MotionEvent.ACTION_MOVE: {
                            //Calculate the X and Y coordinates of the view.
                            if (params.gravity == (Gravity.TOP | Gravity.START)) {
                                params.x = initialX + (int) (event.getRawX() - initialTouchX);
                            } else {
                                params.x = initialX - (int) (event.getRawX() - initialTouchX);
                            }
                            params.y = initialY + (int) (event.getRawY() - initialTouchY);
                            //Update the layout with new X & Y coordinate
                            new Handler().postDelayed(() -> {
                                try {
                                    windowManager.updateViewLayout(v, params);
                                } catch (Exception e) {
                                    Issue.print(e, CameraHeadService.class.getName());
                                }
                            }, 100);
                            if (params.y > (height - (height / 4)) && (params.x > ((width / 2) - (width / 5)) && params.x < ((width / 2) + (width / 5)))) {
                                if (!closingView.isShown())
                                    windowManager.addView(closingView, WindowServicesParams(0, height - 80, Gravity.CENTER_HORIZONTAL));
                            } else {
                                if (closingView.isShown()) {
                                    windowManager.removeView(closingView);
                                }
                            }
                            break;
                        }
                    }
                } catch (Exception e) {
                    Issue.print(e, CameraHeadService.class.getName());
                }
                return false;
            }
        };

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (viewHead != null) windowManager.removeView(viewHead);
        } catch (Exception e) {
            Issue.print(e, CameraHeadService.class.getName());
        }
    }

    @Override
    public void onHideClicked() {
        try {
            if (viewHead == null) {
                setHeadView();
            }
            setView();
            windowManager.addView(viewHead, params);
            setHideHead(false);
            touch(params,Params.CAMERA_HEAD_SERVICE);
        } catch (Exception e) {
            Issue.print(e, CameraHeadService.class.getName());
        }
    }

    protected void setHeadView() {
        super.setHeadView();
        btnClick.setBackgroundResource(R.drawable.back_cam);
        btnClick.setOnLongClickListener(v -> {
            if (CAM_ID == Params.CAMERA_BACK) {
                CAM_ID = Params.CAMERA_FRONT;
                btnClick.setBackgroundResource(R.drawable.front_cam);
            } else {
                CAM_ID = Params.CAMERA_BACK;
                btnClick.setBackgroundResource(R.drawable.back_cam);
            }
            return true;
        });
        btnClick.setOnClickListener(v -> Cam.TakePic(getApplicationContext(),CAM_ID));
    }

    private void setView() {
        if (windowManager != null && viewHead != null)
            switch (CAM_ID) {
                case 0: {
                    btnClick.setBackgroundResource(R.drawable.back_cam);
                    break;
                }
                case 1: {
                    btnClick.setBackgroundResource(R.drawable.front_cam);
                    break;
                }
            }
    }

}

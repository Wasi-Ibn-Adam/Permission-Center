package com.wasitech.permissioncenter.java.com.wasitech.assist.services_receivers;

import android.annotation.SuppressLint;
import android.media.MediaRecorder;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.wasitech.assist.R;
import com.wasitech.basics.classes.Basics;
import com.wasitech.assist.adapter_listeners.ServiceHeadsTouchListener;
import com.wasitech.basics.classes.Format;
import com.wasitech.basics.Storage;
import com.wasitech.assist.command.family.Intents;

import com.wasitech.basics.classes.Issue;
import com.wasitech.database.Params;

import static com.wasitech.basics.classes.Basics.WindowServicesParams;

@SuppressLint({"ClickableViewAccessibility", "InflateParams"})
public class AudioRecordingHeadService extends BaseHeadService {
    private MediaRecorder recorder;
    private WindowManager.LayoutParams params;
    private boolean isRecording = false;
    private CountDownTimer timer;

    public AudioRecordingHeadService() {
        super(Params.AUDIO_HEAD);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (params == null)
            params = Basics.WindowServicesParams(0, 300, Gravity.START);
        onHideClicked();
    }

    @Override
    protected String setHideName() {
        return "A\nU\nD";
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
                                getApplicationContext().stopService(Intents.AudioHead(getApplicationContext()));
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
                                    Issue.print(e, AudioRecordingHeadService.class.getName());
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
                                    Issue.print(e, AudioRecordingHeadService.class.getName());
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
                    Issue.print(e, AudioRecordingHeadService.class.getName());
                }
                return false;
            }
        };

    }

    private void setRecorder() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(Storage.CreateDataFile(Storage.REC, ".mp3").getPath());
        recorder.setMaxDuration(Integer.MAX_VALUE);
        try {
            btnClick.setBackgroundResource(R.drawable.mic_green);
            recorder.prepare();
            recorder.start();

            final TextView time = viewHead.findViewById(R.id.time_recording);
            timer = new CountDownTimer(Integer.MAX_VALUE, 1000) {
                int sec = 0;
                int min = 0;

                @SuppressLint("SetTextI18n")
                @Override
                public void onTick(long millisUntilFinished) {
                    if (sec % 60 == 0 && sec != 0) {
                        min++;
                        sec = 0;
                    }
                    time.setText(min + ":" + Format.Max2(sec));
                    sec++;
                }
                public void onFinish() {

                    existRecorder();
                }
            };
            timer.start();
            isRecording = true;
        } catch (Exception e) {
            Issue.print(e, AudioRecordingHeadService.class.getName());
        }
    }

    private void existRecorder() {
        btnClick.setBackgroundResource(R.drawable.mic_red);
        timer.cancel();
        recorder.stop();
        recorder.reset();
        recorder.release();
        recorder = null;
        isRecording = false;
    }

    public boolean start() {
        if (isRecording) {
            existRecorder();
            return false;
        } else {
            setRecorder();
            return true;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isRecording) existRecorder();
        try {
            if (viewHead != null) windowManager.removeView(viewHead);
            windowManager.removeView(closingView);
        } catch (Exception e) {
            Issue.print(e, AudioRecordingHeadService.class.getName());
        }
    }

    @Override
    public void onHideClicked() {
        try {
            if (viewHead == null) {
                setHeadView();
            }
            windowManager.addView(viewHead, params);
            setHideHead(false);
            touch(params,Params.AUDIO_HEAD_SERVICE);
        } catch (Exception e) {
            Issue.print(e, AudioRecordingHeadService.class.getName());
        }
    }

    protected void setHeadView() {
        super.setHeadView();
        btnClick.setBackgroundResource(R.drawable.mic_red);
        btnClick.setOnClickListener(view -> start());
    }


}

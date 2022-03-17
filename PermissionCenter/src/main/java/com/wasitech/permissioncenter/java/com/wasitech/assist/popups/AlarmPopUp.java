package com.wasitech.permissioncenter.java.com.wasitech.assist.popups;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.google.android.material.textfield.TextInputEditText;
import com.wasitech.assist.R;
import com.wasitech.assist.classes.Alarm;

import java.util.Calendar;

@SuppressLint("InflateParams")
public abstract class AlarmPopUp extends ThemePopUp {

    protected Calendar calendar = Calendar.getInstance();
    protected TimePicker picker;
    protected TextInputEditText input;
    private final Button set;

    public AlarmPopUp(Context activity) {
        super(activity,R.layout.pop_up_alarm);
        picker = view.findViewById(R.id.timePicker1);
        input = view.findViewById(R.id.text);
        set = view.findViewById(R.id.set);
        view.findViewById(R.id.cancel).setOnClickListener(v -> dismiss());
    }

    public void show() {
        picker.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        picker.setMinute(calendar.get(Calendar.MINUTE));
        picker.setIs24HourView(false);
        set.setOnClickListener(this::OnClickListener);
        showAtLocation(view, Gravity.CENTER, 1, 1);
    }

    public void editShow(Alarm alarm) {
        picker.setHour(alarm.getH());
        picker.setMinute(alarm.getM());
        picker.setIs24HourView(false);
        input.setText(alarm.getTalk());
        set.setOnClickListener(v -> OnUpdateClickListener(v, alarm));
        showAtLocation(view, Gravity.CENTER, 1, 1);
    }

    protected abstract void OnUpdateClickListener(View view, Alarm old);

    protected abstract void OnClickListener(View view);

}

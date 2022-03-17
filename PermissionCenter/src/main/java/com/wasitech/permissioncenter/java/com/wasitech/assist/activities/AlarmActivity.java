package com.wasitech.permissioncenter.java.com.wasitech.assist.activities;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.PopupWindow;

import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wasitech.assist.R;
import com.wasitech.basics.BaseCompatActivity;
import com.wasitech.basics.classes.Basics;
import com.wasitech.assist.adapter_listeners.AlarmAdapter;
import com.wasitech.assist.classes.Alarm;
import com.wasitech.assist.command.family.ComFuns;
import com.wasitech.assist.command.family.Intents;
import com.wasitech.assist.command.family.QA;
import com.wasitech.assist.popups.AlarmPopUp;
import com.wasitech.assist.popups.ActionPopUp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import com.wasitech.database.LocalDB;
import com.wasitech.basics.classes.Issue;
import com.wasitech.database.Params;

public class AlarmActivity extends BaseCompatActivity {
    private ArrayList<Alarm> list;
    private LocalDB db;
    private RecyclerView recyclerView;
    private AlarmPopUp popUp;
    private final PopupWindow.OnDismissListener listener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            if (list != null && list.size() == 0)
                finishAndRemoveTask();
        }
    };
    private final CompoundButton.OnCheckedChangeListener changeListener = (v, s) -> {
        db.setAlarmsState(s);
        updateList();
    };

    public AlarmActivity() {
        super(R.layout.activity_alarm_light, R.layout.activity_alarm_dark);
    }

    public static String setAlarm(Context context, String input, String talk) {
        return setAlarmIn(context, input, talk);
    }

    private static String setAlarmIn(Context context, String input, String talk) {
        try {
            if (input == null) return null;
            int h, m;
            Basics.Log("enter: " + input);
            if (ComFuns.after(input)) {
                String temp = afterTime(input);
                if (temp == null)
                    return null;
                h = Integer.parseInt(temp.split(":")[0]);
                m = Integer.parseInt(temp.split(":")[1]);
            } else if (ComFuns.at(input)) {
                input = input.replace("at", "");
                String temp = wordsToTime(input);
                if (temp == null)
                    return null;
                h = Integer.parseInt(temp.split(":")[0]);
                m = Integer.parseInt(temp.split(":")[1]);
            } else {
                String temp = wordsToTime(input);
                if (temp == null)
                    return null;
                h = Integer.parseInt(temp.split(":")[0]);
                m = Integer.parseInt(temp.split(":")[1]);
            }
            Basics.Log(h + ":" + m);
            Alarm alarm = new Alarm(h, m, talk);
            Basics.Log(alarm.getTime());
            LocalDB db = new LocalDB(context);
            if (db.addAlarm(alarm)) {
                alarm.setId(db.getAlarmId(alarm));
                AlarmManager alarmManager = (AlarmManager) context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, alarm.getTime(Calendar.getInstance()), Intents.AlarmIntent(context.getApplicationContext(), alarm));
                return alarm.getTime();
            }
        } catch (Exception e) {
            Issue.print(e, AlarmActivity.class.getName());
        }
        return null;
    }

    private static String wordsToTime(String input) {
        int h = 0, m = 0;
        if (input.contains(":")) {
            String[] numb = input.split(":");
            h = Integer.parseInt(numb[0].replaceAll("[^0-9]", ""));
            m = Integer.parseInt(numb[1].replaceAll("[^0-9]", ""));
        } else if (Basics.onlyNumbers(input)) {
            String temp = intTime(input);
            h = Integer.parseInt(temp.split(":")[0]);
            m = Integer.parseInt(temp.split(":")[1]);
        } else {
            String num = input.replaceAll("[^0-9]", "");
            if (!num.equals("") && num.length() > 0) {
                String temp = intTime(num);
                h = Integer.parseInt(temp.split(":")[0]);
                m = Integer.parseInt(temp.split(":")[1]);
            } else {
                String[] token = input.split(" ");
                for (String s : token) {

                    if (h == 0)
                        h = QA.timeNumber(s);
                    else
                        m = QA.timeNumber(s);
                }
                if (h == -1 || m == -1)
                    return null;
            }
        }
        if (input.toLowerCase().contains("pm") || input.toLowerCase().contains("p.m")) {
            if (h < 12)
                h += 12;
        }
        if (h > 23)
            h = 0;
        if (m > 59)
            m = 0;
        return h + ":" + m;
    }

    private static String afterTime(String input) {
        int h = 0, m = 0;
        int mm = Calendar.getInstance().get(Calendar.MINUTE);
        int hh = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        input = input.replace("after", "");
        if (ComFuns.hour(input)) {
            String hours = input.substring(0, input.indexOf("hour"));
            h = QA.timeNumber(hours);
            if (h == -1) {
                hours = hours.replaceAll("[^0-9]", "");
                if (Basics.onlyNumbers(hours)) {
                    h = Integer.parseInt(hours);
                } else {
                    h = 0;
                }
            }
            if (ComFuns.minute(input)) {
                String min = input.substring(input.indexOf("hour") + 4, input.indexOf("minute"));
                m = QA.timeNumber(min);
                if (m == -1) {
                    min = min.replaceAll("[^0-9]", "");
                    if (Basics.onlyNumbers(min)) {
                        m = Integer.parseInt(min);
                    } else {
                        m = 0;
                    }
                }
            }
            m += mm;
            if (m > 59) {
                m -= 60;
                h += 1;
            }
            h += hh;
            if (h > 23) {
                h -= 24;
            }
        } else if (ComFuns.minute(input)) {
            String min = input.substring(0, input.indexOf("minute"));
            m = QA.timeNumber(min);
            if (m == -1) {
                min = min.replaceAll("[^0-9]", "");
                if (Basics.onlyNumbers(min)) {
                    m = Integer.parseInt(min);
                } else {
                    m = 0;
                }
            }

            m += mm;
            if (m > 59) {
                m -= 60;
                h += 1;
            }
            h += hh;
            if (h > 23) {
                h -= 24;
            }
            Basics.Log("min: " + min + " m " + m + " h " + h + " hh " + hh + " mm " + mm);
        } else {
            if (ComFuns.second(input)) {
                int s = QA.timeNumber(input.substring(0, input.indexOf("second")));
                m = s / 60;
                if (s > 0 && m == 0)
                    m = 1;
                m += mm;
                if (m > 59) {
                    m -= 60;
                    h += 1;
                }
                h += hh;
                if (h > 23) {
                    h -= 24;
                }
            } else {
                String temp = wordsToTime(input);
                if (temp == null)
                    return null;
                h = Integer.parseInt(temp.split(":")[0]);
                m = Integer.parseInt(temp.split(":")[1]);
                m += 1;
            }
        }

        return h + ":" + m;
    }

    private static String intTime(String input) {
        int h = 0, m;
        if (input.length() < 3)
            m = Integer.parseInt(input.replaceAll("[^0-9]", ""));
        else if (input.length() < 5) {
            h = Integer.parseInt(input.substring(0, input.length() - 2).replaceAll("[^0-9]", ""));
            m = Integer.parseInt(input.substring(input.length() - 2).replaceAll("[^0-9]", ""));
        } else {
            h = Integer.parseInt(input.substring(0, 2).replaceAll("[^0-9]", ""));
            m = Integer.parseInt(input.substring(2, 4).replaceAll("[^0-9]", ""));
        }
        return h + ":" + m;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme();
        setBackBtn();
        try {
            db = new LocalDB(getApplicationContext());

            recyclerView = findViewById(R.id.recyclerView);
            SwitchCompat allAlarm = findViewById(R.id.all_alarm);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            updateList();
            popUp = new AlarmPopUp(AlarmActivity.this) {
                @Override
                protected void OnUpdateClickListener(View view, Alarm old) {
                    Alarm latest = new Alarm(old.getId(), picker.getHour(), picker.getMinute(), " ", true);
                    Editable text = input.getText();
                    if (text != null)
                        latest.setTalk(text.toString());
                    db.updateAlarm(old, latest);
                    removeAlarm(old);
                    dismiss();
                    updateList();
                }

                @Override
                protected void OnClickListener(View view) {
                    Alarm alarm = new Alarm(picker.getHour(), picker.getMinute(), " ");
                    Editable text = input.getText();
                    if (text != null)
                        alarm.setTalk(text.toString());
                    if (db.addAlarm(alarm)) {
                        alarm.setId(db.getAlarmId(alarm));
                        setAlarm(alarm);
                        updateList();
                        dismiss();
                    } else {
                        Basics.toasting(AlarmActivity.this, "This Alarm Already Exist");
                    }
                }
            };
            popUp.setOnDismissListener(listener);

            findViewById(R.id.add).setOnClickListener(v -> popUp.show());
            if (getIntent().hasExtra(Params.DATA_TRANS)) {
                new Handler().postDelayed(() -> popUp.show(), 600);
            }
            allAlarm.setOnCheckedChangeListener(changeListener);
        } catch (Exception e) {
            Issue.print(e, AlarmActivity.class.getName());
        }
    }

    private void updateList() {
        list = db.getAlarmList();
        if (list.size() == 0) {
            new Handler().postDelayed(() -> popUp.show(), 600);
        }

        Collections.sort(list, Alarm.AlarmComparator);
        recyclerView.setAdapter(new AlarmAdapter(list) {
            @Override
            public void checkListener(View v, boolean s) {
                int i = recyclerView.getChildLayoutPosition(v);
                list.get(i).setActive(s);
                db.setAlarmState(list.get(i).getId(), s);
                if (s)
                    updateAlarm(list.get(i));
                else
                    removeAlarm(list.get(i));
                AlarmAdapter.setTheme(v, s);

            }

            @Override
            public boolean longListener(View v) {
                int i = recyclerView.getChildLayoutPosition(v);
                removeAlarm(i);
                return true;
            }

            @Override
            public void onClick(View v) {
                int i = recyclerView.getChildAdapterPosition(v);
                popUp.editShow(list.get(i));
            }
        });
    }

    private void removeAlarm(int i) {
        new ActionPopUp(AlarmActivity.this, getString(R.string.alarm_delete)) {
            @Override
            protected void onDelete(View v) {
                db.removeAlarm(list.get(i));
                dismiss();
                updateList();
            }
        };
    }

    // alarm manager functions

    private void setAlarm(Alarm alarm) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarm.getTime(Calendar.getInstance()), Intents.AlarmIntent(getApplicationContext(), alarm));
        //alarmManager.setWindow(AlarmManager.RTC_WAKEUP, alarm.getTime(Calendar.getInstance()),60000L,Intents.AlarmIntent(getApplicationContext(),alarm));
    }

    private void updateAlarm(Alarm alarm) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarm.getTime(Calendar.getInstance()), Intents.AlarmIntent(getApplicationContext(), alarm));
    }

    private void removeAlarm(Alarm alarm) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(Intents.AlarmIntent(getApplicationContext(), alarm));
    }

}
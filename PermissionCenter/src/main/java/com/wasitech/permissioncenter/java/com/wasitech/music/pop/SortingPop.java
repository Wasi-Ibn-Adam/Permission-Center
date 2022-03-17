package com.wasitech.permissioncenter.java.com.wasitech.music.pop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.wasitech.assist.R;
import com.wasitech.basics.classes.Issue;
import com.wasitech.music.classes.Sort;

public abstract class SortingPop extends PopupWindow {
    private int selected;
    private Button set, cancel;
    private RadioButton title, date, size, duration, asc, des;
    private RadioGroup order, sort;

    public SortingPop(Context context) {
        super(context);
        @SuppressLint("InflateParams") View view =
                ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                        .inflate(R.layout.pop_up_sorting, null);
        setContentView(view);
        setViews(view);
        setValues();
        setActions();

        setOutsideTouchable(false);
        setFocusable(false);

        setSize(context);
        showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    private void setViews(View v) {
        try {
            title = v.findViewById(R.id.title_btn);
            date = v.findViewById(R.id.date_btn);
            size = v.findViewById(R.id.size_btn);
            duration = v.findViewById(R.id.duration_btn);

            asc = v.findViewById(R.id.ascending_btn);
            des = v.findViewById(R.id.descending_btn);

            order = v.findViewById(R.id.order_group);
            sort = v.findViewById(R.id.sort_group);

            set = v.findViewById(R.id.set_btn);
            cancel = v.findViewById(R.id.cancel_btn);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    private void setValues() {
        try {
            switch (Sort.getSort()) {
                default:
                case Sort.SORT_TYPE_TITLE: {
                    title.setChecked(true);
                    break;
                }
                case Sort.SORT_TYPE_DATE: {
                    date.setChecked(true);
                    break;
                }
                case Sort.SORT_TYPE_SIZE: {
                    size.setChecked(true);
                    break;
                }
                case Sort.SORT_TYPE_DURATION: {
                    duration.setChecked(true);
                    break;
                }
            }
            if (Sort.getOrder() == Sort.ORDER_TYPE_ASC) asc.setChecked(true);
            else des.setChecked(true);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @SuppressLint("NonConstantResourceId")
    private void setActions() {
        try {
            cancel.setOnClickListener(v -> dismiss());
            set.setOnClickListener(v -> {
                int s;
                switch (sort.getCheckedRadioButtonId()) {
                    default:
                    case R.id.title_btn: {
                        s = Sort.SORT_TYPE_TITLE;
                        break;
                    }
                    case R.id.date_btn: {
                        s = Sort.SORT_TYPE_DATE;
                        break;
                    }
                    case R.id.size_btn: {
                        s = Sort.SORT_TYPE_SIZE;
                        break;
                    }
                    case R.id.duration_btn: {
                        s = Sort.SORT_TYPE_DURATION;
                        break;
                    }
                }
                Sort.setSortingOrder(order.getCheckedRadioButtonId() == R.id.ascending_btn ? Sort.ORDER_TYPE_ASC : Sort.ORDER_TYPE_DES, s);
                UpdateUi();
            });
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }

    }

    protected abstract void UpdateUi();

    private void setSize(Context context) {
        int w = context.getResources().getDisplayMetrics().widthPixels;
        int h = context.getResources().getDisplayMetrics().heightPixels;
        setWidth((int) (w * 0.8));
        //setHeight((int) (h * 0.8));
    }
}

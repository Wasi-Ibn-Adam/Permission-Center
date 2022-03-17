package com.wasitech.permissioncenter.java.com.wasitech.assist.adapter_listeners;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.wasitech.assist.R;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.assist.classes.Alarm;

import java.util.ArrayList;

public abstract class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> implements View.OnClickListener {
    private final ArrayList<Alarm> list;

    public AlarmAdapter(ArrayList<Alarm> list) {
        this.list = list;
    }

    public static void setTheme(View itemView, boolean state) {
        if (state) {
            if (ProcessApp.isDarkTheme()) {
                ((TextView) itemView.findViewById(R.id.time)).setTextColor(ColorStateList.valueOf(Color.WHITE));
                ((TextView) itemView.findViewById(R.id.meridian)).setTextColor(ColorStateList.valueOf(Color.WHITE));
                ((TextView) itemView.findViewById(R.id.extra)).setTextColor(ColorStateList.valueOf(Color.WHITE));
                ((SwitchCompat) itemView.findViewById(R.id.app_yes_no_btn)).setTrackTintList(ColorStateList.valueOf(Color.parseColor("#66FDFDFD")));
                ((SwitchCompat) itemView.findViewById(R.id.app_yes_no_btn)).setThumbTintList(ColorStateList.valueOf(Color.parseColor("#E6FDFDFD")));
            } else {
                ((TextView) itemView.findViewById(R.id.time)).setTextColor(ColorStateList.valueOf(Color.BLACK));
                ((TextView) itemView.findViewById(R.id.meridian)).setTextColor(ColorStateList.valueOf(Color.BLACK));
                ((TextView) itemView.findViewById(R.id.extra)).setTextColor(ColorStateList.valueOf(Color.BLACK));
                ((SwitchCompat) itemView.findViewById(R.id.app_yes_no_btn)).setTrackTintList(ColorStateList.valueOf(Color.parseColor("#66000000")));
                ((SwitchCompat) itemView.findViewById(R.id.app_yes_no_btn)).setThumbTintList(ColorStateList.valueOf(Color.parseColor("#1C1C1C")));
            }
        } else {
            if (ProcessApp.isDarkTheme()) {
                ((TextView) itemView.findViewById(R.id.time)).setTextColor(ColorStateList.valueOf(Color.parseColor("#A9A7AA")));
                ((TextView) itemView.findViewById(R.id.meridian)).setTextColor(ColorStateList.valueOf(Color.parseColor("#A9A7AA")));
                ((TextView) itemView.findViewById(R.id.extra)).setTextColor(ColorStateList.valueOf(Color.parseColor("#A9A7AA")));
                ((SwitchCompat) itemView.findViewById(R.id.app_yes_no_btn)).setTrackTintList(ColorStateList.valueOf(Color.parseColor("#66FDFDFD")));
                ((SwitchCompat) itemView.findViewById(R.id.app_yes_no_btn)).setThumbTintList(ColorStateList.valueOf(Color.parseColor("#A9A7AA")));
            } else {
                ((TextView) itemView.findViewById(R.id.time)).setTextColor(ColorStateList.valueOf(Color.parseColor("#59565A")));
                ((TextView) itemView.findViewById(R.id.meridian)).setTextColor(ColorStateList.valueOf(Color.parseColor("#59565A")));
                ((TextView) itemView.findViewById(R.id.extra)).setTextColor(ColorStateList.valueOf(Color.parseColor("#59565A")));
                ((SwitchCompat) itemView.findViewById(R.id.app_yes_no_btn)).setTrackTintList(ColorStateList.valueOf(Color.parseColor("#66000000")));
                ((SwitchCompat) itemView.findViewById(R.id.app_yes_no_btn)).setThumbTintList(ColorStateList.valueOf(Color.parseColor("#59565A")));
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_alarm, parent, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this::longListener);
        return new ViewHolder(view);
    }

    public abstract void checkListener(View view, boolean isChecked);

    public abstract boolean longListener(View v);

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Alarm alarm = list.get(position);
        holder.time.setText(alarm.getTimeInNumbers());
        holder.meridian.setText(alarm.getMeridian());
        holder.detail.setText(alarm.getDetails());

        holder.yesNo.setChecked(alarm.isActive());
        holder.yesNo.setOnCheckedChangeListener((buttonView, isChecked) -> checkListener(holder.itemView, isChecked));
        setTheme(holder.itemView, alarm.isActive());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView time;
        protected TextView meridian;
        protected TextView detail;
        protected SwitchCompat yesNo;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            meridian = itemView.findViewById(R.id.meridian);
            detail = itemView.findViewById(R.id.extra);
            yesNo = itemView.findViewById(R.id.app_yes_no_btn);
            yesNo.setEnabled(true);
        }
    }
}

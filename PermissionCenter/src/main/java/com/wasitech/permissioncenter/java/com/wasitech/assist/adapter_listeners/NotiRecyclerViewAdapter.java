package com.wasitech.permissioncenter.java.com.wasitech.assist.adapter_listeners;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wasitech.assist.R;
import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.assist.classes.App;
import com.wasitech.assist.runnables.AppRunnable;

import com.wasitech.database.LocalDB;
import com.wasitech.basics.classes.Issue;
import de.hdodenhof.circleimageview.CircleImageView;

public class NotiRecyclerViewAdapter extends RecyclerView.Adapter<NotiRecyclerViewAdapter.ViewHolder> {
    private final Context context;

    public NotiRecyclerViewAdapter(Context context) {
        this.context =context;
        if(ProcessApp.aList.size()==0){
            new Thread(new AppRunnable(context){
                @Override
                public void onComplete() {
                    super.onComplete();
                    Basics.toasting(context,"complete");
                    notifyDataSetChanged();
                }
            }).start();
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_notification_app, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            App app=ProcessApp.aList.get(position);
            holder.name.setText(app.getName().trim());
            holder.pkg.setText(app.getPackageName().trim());

            Glide.with(context)
                    .load(context.getPackageManager().getApplicationIcon(app.getPackageName()))
                    .into(holder.logo);
            holder.yesNo.setChecked(ProcessApp.getPref().getBoolean(app.getPackageName(), false));

        } catch (PackageManager.NameNotFoundException e) {
            Issue.print(e,NotiRecyclerViewAdapter.class.getName());
        }
    }
    @Override
    public int getItemCount() { return ProcessApp.aList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView name;
        protected TextView pkg;
        protected CircleImageView logo;
        public SwitchCompat yesNo;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            setView(itemView);
        }

        private void setView(View itemView) {
            name = itemView.findViewById(R.id.app_name);
            pkg = itemView.findViewById(R.id.pkg_noti);
            logo = itemView.findViewById(R.id.app_logo);
            yesNo = itemView.findViewById(R.id.app_yes_no_btn);
            yesNo.setEnabled(true);
            yesNo.setOnCheckedChangeListener((compoundButton, b) -> {
                ProcessApp.getPref().edit().putBoolean(pkg.getText().toString().trim(), b).apply();
                new LocalDB(itemView.getContext()).deleteNotificationByText(pkg.getText().toString().trim());
            });
            setTheme();
        }

        private void setTheme() {
            if (ProcessApp.isDarkTheme()) {
                name.setTextColor(Color.WHITE);
                pkg.setTextColor(Color.WHITE);
                yesNo.setThumbTintList(ColorStateList.valueOf(Color.WHITE));
            }
        }


    }
}

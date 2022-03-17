package com.wasitech.permissioncenter.java.com.wasitech.register.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wasitech.assist.R;
import com.wasitech.basics.app.ProcessApp;

public class HomeTabs extends LinearLayout {
    private ImageView img;
    private TextView title, description;

    public HomeTabs(Context context) {
        super(context);
        init();
    }

    public HomeTabs(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HomeTabs(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public HomeTabs(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.hostler_home_tabs_view, this);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        img = findViewById(R.id.img);
        img.setImageTintMode(PorterDuff.Mode.SRC_IN);
        if (ProcessApp.isDarkTheme()) {
            description.setTextColor(Color.WHITE);
            title.setTextColor(Color.WHITE);
            img.setImageTintList(ColorStateList.valueOf(Color.WHITE));
        } else {
            description.setTextColor(Color.BLACK);
            title.setTextColor(Color.BLACK);
            img.setImageTintList(ColorStateList.valueOf(Color.BLACK));
        }
    }

    public String getTitle() {
        return title.getText().toString();
    }

    public String getDescription() {
        return description.getText().toString();
    }

    public void setTitle(String text) {
        title.setText(text);
    }

    public void setDescription(String text) {
        description.setText(text);
    }

    public void setImg(int id) {
        img.setImageResource(id);
    }

    @SuppressLint("UseCompatTextViewDrawableApis")
    public void setMsgVisibility(boolean visible) {
        if (visible) {
            if (ProcessApp.isDarkTheme())
                title.setCompoundDrawableTintList(ColorStateList.valueOf(Color.WHITE));
            else
                title.setCompoundDrawableTintList(ColorStateList.valueOf(Color.BLACK));
        } else
            title.setCompoundDrawableTintList(ColorStateList.valueOf(Color.TRANSPARENT));


    }
}

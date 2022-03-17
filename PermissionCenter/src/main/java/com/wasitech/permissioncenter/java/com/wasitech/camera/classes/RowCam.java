package com.wasitech.permissioncenter.java.com.wasitech.camera.classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.wasitech.assist.R;

public class RowCam extends LinearLayout {
    private ImageView img;
    private TextView txt;

    public RowCam(Context context) {
        super(context);
    }

    public RowCam(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RowCam(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public RowCam(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);

    }

    private void init(Context context, AttributeSet attrs) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_camera_filter, null);
        img = v.findViewById(R.id.filter_img);
        txt = v.findViewById(R.id.filter_txt);
        @SuppressLint("Recycle")
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RowCam, 0, 0);

        int res = typedArray.getResourceId(R.styleable.RowCam_android_foreground, R.drawable.none);
        String t = typedArray.getString(R.styleable.DetailFab_text);
        img.setForeground(ContextCompat.getDrawable(context, res));
        txt.setText(t);
    }

    public TextView getTxt() {
        return txt;
    }

    public ImageView getImg() {
        return img;
    }

}

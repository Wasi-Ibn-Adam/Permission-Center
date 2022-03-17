package com.wasitech.permissioncenter.java.com.wasitech.assist.popups;

import android.content.Context;
import android.view.Gravity;
import android.widget.Button;

import com.wasitech.assist.R;

public class AboutPopUp extends ThemePopUp {
    public AboutPopUp(Context context) {
        super(context, R.layout.pop_up_info);
        ((Button) view.findViewById(R.id.btn_ok_bg)).setOnClickListener(v -> dismiss());
        showAtLocation(view, Gravity.CENTER, 0, 0);
    }
}

package com.wasitech.permissioncenter.java.com.wasitech.assist.popups;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wasitech.assist.R;
import com.wasitech.basics.app.ProcessApp;

public class ThemedPopUps {
    public static void HowToUse(Context context) {
        PopupWindow pop = PopupWindow(context, R.layout.pop_up_how_to_use, R.id.btn_ok_info, true);
        if (ProcessApp.isDarkTheme()) {
            View view = pop.getContentView();
            view.findViewById(R.id.scroll).setBackgroundResource(R.drawable.abc);
            (view.findViewById(R.id.scroll)).setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
            (view.findViewById(R.id.scroll)).setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.bg_tint_dark)));
            ((TextView) view.findViewById(R.id.pop_up_title_1)).setTextColor(Color.WHITE);
            ((TextView) view.findViewById(R.id.pop_up_title_2)).setTextColor(Color.WHITE);
            ((TextView) view.findViewById(R.id.pop_up_text_1)).setTextColor(Color.WHITE);
            ((TextView) view.findViewById(R.id.pop_up_text_2)).setTextColor(Color.WHITE);
            view.findViewById(R.id.btn_ok_info).setBackgroundResource(R.drawable.shape_btn_0_dark);
        }

    }

    public static PopupWindow PopupWindow(Context context, int layout, int btnId, boolean outSide) {
        @SuppressLint("InflateParams") View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(layout, null);
        final PopupWindow popupWindow = new PopupWindow(context);
        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(outSide);
        popupWindow.setFocusable(outSide);
        popupWindow.showAtLocation(view, Gravity.CENTER, 1, 1);
        if (btnId != 0) {
            view.findViewById(btnId).setOnClickListener(view1 -> popupWindow.dismiss());
        }
        return popupWindow;
    }

    public static void BackgroundPopup(Context context, String text) {
        PopupWindow pop = PopupWindow(context, R.layout.pop_up_background, R.id.btn_ok_bg, true);
        View view = pop.getContentView();
        ((TextView) view.findViewById(R.id.popup_text)).setText(text);
        //if (ProcessApp.getAppTheme())
        {
            ((TextView) view.findViewById(R.id.popup_text)).setTextColor(Color.WHITE);
            (view.findViewById(R.id.btn_ok_bg)).setBackgroundResource(R.drawable.shape_btn_0_dark);
            (view.findViewById(R.id.layout)).setBackgroundResource(R.drawable.abc);
            (view.findViewById(R.id.layout)).setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
            (view.findViewById(R.id.layout)).setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.bg_tint_dark)));
        }
    }

}

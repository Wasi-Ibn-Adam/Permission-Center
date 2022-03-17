package com.wasitech.permissioncenter.java.com.wasitech.contact.pop;

import android.content.Context;
import android.view.Gravity;
import android.widget.EditText;

import com.wasitech.assist.R;
import com.wasitech.assist.popups.ThemePopUp;

public abstract class DialerPopUp extends ThemePopUp {
    public DialerPopUp(Context context) {
        super(context, R.layout.pop_up_dial_number);
        EditText text = view.findViewById(R.id.num_dial);
        view.findViewById(R.id.con_close).setOnClickListener(v -> dismiss());
        view.findViewById(R.id.cancel_num).setOnClickListener(v -> dismiss());
        view.findViewById(R.id.dial_num).setOnClickListener(v -> dial(text.getText().toString()));
        showAtLocation(view, Gravity.CENTER, 0, 0);
    }
    protected abstract void dial(String num);
}

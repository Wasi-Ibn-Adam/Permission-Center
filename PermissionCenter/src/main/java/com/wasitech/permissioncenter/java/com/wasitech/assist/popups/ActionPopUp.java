package com.wasitech.permissioncenter.java.com.wasitech.assist.popups;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wasitech.assist.R;

public abstract class ActionPopUp extends ThemePopUp{

    public ActionPopUp(Context context, String text) {
        super(context, R.layout.pop_up_remove);
        showAtLocation(view, Gravity.CENTER, 1, 1);
        ((TextView)view.findViewById( R.id.delete_text)).setText(text);
        ((Button)view.findViewById( R.id.delete)).setText(deleteBtnText());
        view.findViewById( R.id.delete).setOnClickListener(this::onDelete);
        view.findViewById( R.id.cancel).setOnClickListener(v->dismiss());
    }
    protected abstract void onDelete(View v);
    public String deleteBtnText(){
        return "Delete";
    }
}

package com.wasitech.permissioncenter.java.com.wasitech.basics;

import android.content.Context;
import android.transition.Fade;
import android.view.Gravity;
import android.widget.ImageButton;

import androidx.core.content.ContextCompat;

import com.wasitech.assist.R;
import com.wasitech.assist.popups.ThemePopUp;

public class ItemsLongClickPopUp extends ThemePopUp {
    protected ImageButton share, cancel, delete, action, detail;

    public ItemsLongClickPopUp(Context context) {
        super(context, R.layout.pop_up_item_long_click, new Fade(Fade.IN), new Fade(Fade.OUT));
        share = view.findViewById(R.id.share_btn);
        cancel = view.findViewById(R.id.cancel_btn);
        delete = view.findViewById(R.id.delete_btn);
        action = view.findViewById(R.id.action_btn);
        detail = view.findViewById(R.id.detail_btn);
        action.setImageDrawable(ContextCompat.getDrawable(context, setActionImg()));

        share.setOnClickListener(v -> OnShare());
        cancel.setOnClickListener(v -> OnCancel());
        delete.setOnClickListener(v -> OnDelete());
        action.setOnClickListener(v -> OnAction());
        detail.setOnClickListener(v -> OnDetail());

        showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    public void setDetailVisibility(int visibility) { detail.setVisibility(visibility); }

    protected void OnShare() {
        dismiss();
    }

    protected void OnCancel() {
        dismiss();
    }

    protected void OnDelete() {
        dismiss();
    }

    protected void OnAction() {
        dismiss();
    }

    protected void OnDetail() {
        dismiss();
    }

    protected int setActionImg() {
        return R.drawable.play;
    }

}

package com.wasitech.permissioncenter.java.com.wasitech.register.fragment;

import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wasitech.assist.R;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.fragments.AssistFragment;
import com.wasitech.basics.classes.Issue;
import com.wasitech.register.activity.Profile;
import com.wasitech.theme.Theme;

public abstract class BasicEditSaveFragment extends AssistFragment {
    protected final boolean edit;
    private ImageButton editBtn;
    private FloatingActionButton save;

    public BasicEditSaveFragment(boolean edit) {
        this.edit = edit;
        if (!edit)
            Profile.img = ProcessApp.bytes;
    }



    @Override
    public void setViews(View view) {
        try {
            editBtn = view.findViewById(R.id.edit_btn);
            save = view.findViewById(R.id.save_btn);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }
    @Override
    public void setTheme(View view) {
        Theme.imageButton(editBtn);
        Theme.fab(save);
    }
    /**
     * use super if you want to default save and edit btns
     * */
    @Override
    public void setActions(View view) {
        try {
            save.setOnClickListener(v -> {
                if (onSave()) {
                    task.onAction();
                    setEditable(false);
                }
            });
            editBtn.setOnClickListener(v -> {
                setEditable(true);
            });
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    protected void setFixed() {
        super.setFixed();
        setFixed(edit);
        if (!edit) {
            setEditable(false);
        }
    }

    protected boolean onSave() {
        return false;
    }

    protected void setEditable(boolean edit) {
        try {
            if (edit) {
                editBtn.setVisibility(View.GONE);
                save.setVisibility(View.VISIBLE);
            } else {
                editBtn.setVisibility(View.VISIBLE);
                save.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    protected void setFixed(boolean edit) { }

}

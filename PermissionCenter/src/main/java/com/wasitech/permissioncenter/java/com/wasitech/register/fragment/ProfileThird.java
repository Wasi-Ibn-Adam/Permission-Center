package com.wasitech.permissioncenter.java.com.wasitech.register.fragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.CheckBox;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wasitech.assist.R;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.classes.Issue;
import com.wasitech.register.activity.Profile;
import com.wasitech.theme.Theme;

@SuppressLint("SetTextI18n")
public class ProfileThird extends BasicEditSaveFragment {
    private CheckBox m, f, t;
    private FloatingActionButton prev, next;
    public static final int COMPLETE = 2;

    public static ProfileThird getInstance(boolean edit) {
        return new ProfileThird(edit);
    }
    public static ProfileThird getInstance() {
        return new ProfileThird();
    }
    public ProfileThird(boolean edit){
        super(edit);
    }
    public ProfileThird(){
        super(false);
    }

    @Override
    protected int setLayout() {
        return R.layout.frag_r_profile_3;
    }

    @Override
    public void setViews(View view) {
        super.setViews(view);
        m = view.findViewById(R.id.male_btn);
        f = view.findViewById(R.id.female_btn);
        t = view.findViewById(R.id.trans_btn);
        prev = view.findViewById(R.id.prev_btn);
        next = view.findViewById(R.id.next_btn);
    }

    @Override
    public void setTheme(View view) {
        super.setTheme(view);
        try{
            Theme.textView(view.findViewById(R.id.h2_text));
            Theme.checkbox(m);
            Theme.checkbox(f);
            Theme.checkbox(t);
            Theme.fab(next);
            Theme.fab(prev);
        }
        catch (Exception e){
            Issue.print(e,getClass().getName());
        }
    }

    @Override
    public void setPermission(View view) {

    }

    @Override
    public void setAnimation(View view) {

    }

    @Override
    public void setValues(View view) {
        switch (ProcessApp.getPref().getInt(Profile.Params.INTEREST, -1)) {
            case Profile.Params.MALE: {
                m.setChecked(true);
                break;
            }
            case Profile.Params.FEMALE: {
                f.setChecked(true);
                break;
            }
            case Profile.Params.BOTH_MALE_FEMALE: {
                m.setChecked(true);
                f.setChecked(true);
                break;
            }
            case Profile.Params.BOTH_MALE_TRANS: {
                m.setChecked(true);
                t.setChecked(true);
                break;
            }
            case Profile.Params.BOTH_FEMALE_TRANS: {
                f.setChecked(true);
                t.setChecked(true);
                break;
            }
            case Profile.Params.ALL: {
                m.setChecked(true);
                f.setChecked(true);
                t.setChecked(true);
                break;
            }
        }
    }

    @Override
    public void setActions(View view) {
        super.setActions(view);

        next.setOnClickListener(v -> {
            if(onSave()){
            Profile.CODE= ProfileThird.COMPLETE;
            task.onComplete();
            }
        });
        prev.setOnClickListener(v -> task.onPrev());
    }

    @Override
    public void setExtra(View view) {

    }

    @Override
    protected boolean onSave(){
        int interest = -1;
        if (m.isChecked()) {
            if (f.isChecked())
                if (t.isChecked()) interest = Profile.Params.ALL;
                else interest = Profile.Params.BOTH_MALE_FEMALE;
            else if (t.isChecked()) interest = Profile.Params.BOTH_MALE_TRANS;
            else interest = Profile.Params.MALE;
        } else if (f.isChecked()) {
            if (t.isChecked()) interest = Profile.Params.BOTH_FEMALE_TRANS;
            else interest = Profile.Params.FEMALE;
        } else if (t.isChecked()) {
            interest = Profile.Params.TRANSGENDER;
        }

        ProcessApp.getPref().edit()
                .putInt(Profile.Params.INTEREST, interest)
                .apply();
        return true;
    }

    @Override
    protected void setFixed(boolean edit) {
        super.setFixed(edit);
        if(edit){
            next.setVisibility(View.VISIBLE);
            prev.setVisibility(View.VISIBLE);
        }
        else{
            next.setVisibility(View.GONE);
            prev.setVisibility(View.GONE);
        }
    }

    @Override
    protected void setEditable(boolean edit) {
        super.setEditable(edit);
        m.setClickable(edit);
        f.setClickable(edit);
        t.setClickable(edit);
    }
}

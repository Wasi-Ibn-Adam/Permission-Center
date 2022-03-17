package com.wasitech.permissioncenter.java.com.wasitech.register.fragment;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.wasitech.assist.R;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.fragments.AssistFragment;
import com.wasitech.basics.classes.Issue;
import com.wasitech.database.Params;
import com.wasitech.register.activity.AppStart;
import com.wasitech.register.activity.Profile;
import com.wasitech.theme.Theme;

public class AppStartChecks extends AssistFragment {
    public static final int POLICY = 1;
    public static final int NOT_AVAILABLE = 2;
    public static final int VALID_PROFILE = 3;
    public static final int IN_VALID_PROFILE =4 ;

    private ImageView img;

    public static AppStartChecks getInstance() {
        return new AppStartChecks();
    }

    @Override
    protected int setLayout() {
        return R.layout.frag_r_start;
    }

    @Override
    public void setViews(View view) {
        try {
            img=view.findViewById(R.id.app_img);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setValues(View view) {
        img.setImageResource(ProcessApp.getPic());
    }

    @Override
    public void setTheme(View view) {
        try {
            Theme.imageView(img);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setPermission(View view) {

    }

    @Override
    public void setAnimation(View view) {

    }

    @Override
    public void setActions(View view) {
        try {
            long TIME = 1500L;
            if(!ProcessApp.getPref().getBoolean(Params.POLICY_ACCEPTED,false)) {
                new Handler().postDelayed(()->{
                    AppStart.CODE=AppStartChecks.POLICY;
                    task.onComplete();
                }, TIME);
                return;
            }
            if (ProcessApp.getUser() == null) {
                new Handler().postDelayed(()->{
                    AppStart.CODE=AppStartChecks.NOT_AVAILABLE;
                    task.onComplete();
                }, TIME);
                return;
            }
            if (Profile.User.isProfileValid()) {
                new Handler().postDelayed(()->{
                    AppStart.CODE=AppStartChecks.VALID_PROFILE;
                    task.onComplete();
                }, TIME);
                return;
            }
            new Handler().postDelayed(()->{
                AppStart.CODE=AppStartChecks.IN_VALID_PROFILE;
                task.onComplete();
            }, TIME);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setExtra(View view) {

    }
}

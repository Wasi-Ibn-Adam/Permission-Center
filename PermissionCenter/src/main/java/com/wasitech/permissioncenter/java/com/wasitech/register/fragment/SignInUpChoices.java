package com.wasitech.permissioncenter.java.com.wasitech.register.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wasitech.assist.R;
import com.wasitech.basics.classes.Issue;
import com.wasitech.basics.fragments.AssistFragment;
import com.wasitech.register.activity.SignInUp;
import com.wasitech.theme.Theme;

public class SignInUpChoices extends AssistFragment {
    public static final int EMAIL_REG = 1;
    public static final int EMAIL_LOGIN = 2;
    public static final int PHONE =3;
    public static final int FB = 4;
    public static final int PRIVACY_POLICY = 5;
    private Button email_reg,email_log, phone, fb;
    private TextView policy;

    public static SignInUpChoices getInstance() {
        return new SignInUpChoices();
    }

    @Override
    protected int setLayout() {
        return R.layout.frag_r_sign_in_up_choice;
    }

    @Override
    public void setViews(View view) {
        try {
            fb = view.findViewById(R.id.fb_btn);
            phone = view.findViewById(R.id.phone_btn);
            email_reg = view.findViewById(R.id.reg_btn);
            email_log = view.findViewById(R.id.login_btn);
            policy = view.findViewById(R.id.privacy_policy);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setActions(View view) {
        try{
            email_reg.setOnClickListener(v->{
                SignInUp.CODE= SignInUpChoices.EMAIL_REG;
                task.onComplete();
            });
            email_log.setOnClickListener(v->{
                SignInUp.CODE= SignInUpChoices.EMAIL_LOGIN;
                task.onComplete();
            });
            phone.setOnClickListener(v->{
                SignInUp.CODE= SignInUpChoices.PHONE;
                task.onComplete();
            });
            policy.setOnClickListener(v->{
                SignInUp.CODE= SignInUpChoices.PRIVACY_POLICY;
                task.onComplete();
            });
            fb.setOnClickListener(v->{
                SignInUp.CODE= SignInUpChoices.FB;
                task.onComplete();
            });
        }
        catch (Exception e){
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setExtra(View view) {

    }

    @Override
    public void setValues(View view) {

    }

    @Override
    public void setTheme(View view) {
        try{
            Theme.textView(policy);
            Theme.buttonBgLeftRight2(email_log);
            Theme.buttonBgLeftRight1(email_reg);
            Theme.buttonBgUpDown(phone);
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

}

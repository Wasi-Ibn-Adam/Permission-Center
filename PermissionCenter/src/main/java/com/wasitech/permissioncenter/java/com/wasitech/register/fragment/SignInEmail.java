package com.wasitech.permissioncenter.java.com.wasitech.register.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.wasitech.assist.R;
import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.classes.Issue;
import com.wasitech.basics.fragments.AssistFragment;
import com.wasitech.register.activity.SignInUp;
import com.wasitech.theme.Theme;

import java.util.Objects;

public class SignInEmail extends AssistFragment {
    public static final int FORGET=6;
    public static final int COMPLETE=7;
    private TextInputEditText e,p;
    private TextView forget;
    private FirebaseAuth auth;
    private Button signIn;

    public static SignInEmail getInstance() {
        return new SignInEmail();
    }

    @Override
    protected int setLayout() {
        return R.layout.frag_r_sign_in_email;
    }

    @Override
    public void setViews(View view) {
        try {
            e=view.findViewById(R.id.email_box);
            p=view.findViewById(R.id.password_box);
            signIn=view.findViewById(R.id.login_btn);
            forget=view.findViewById(R.id.forget_text);
            auth = FirebaseAuth.getInstance();
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setValues(View view) {

    }

    @Override
    public void setActions(View view) {
       signIn.setOnClickListener(v->{
           String email= Objects.requireNonNull(e.getText()).toString();
           String pass= Objects.requireNonNull(p.getText()).toString();
           if(email.isEmpty()){
               e.setError("Missing!");
               return;
           }
           if(pass.isEmpty()){
               p.setError("Missing!");
               return;
           }
           if(!Basics.EmailValidator(email)){
               e.setError("Invalid!");
               return;
           }
           if(pass.length()<6){
               p.setError("Invalid!");
               return;
           }

           auth.signInWithEmailAndPassword(email,pass)
                   .addOnSuccessListener(authResult -> {
                       SignInUp.CODE= SignInEmail.COMPLETE;
                       task.onComplete();
                   })
                   .addOnCanceledListener(() -> NotifyToUser("Please Try Again."))
                   .addOnFailureListener(e -> {
                       Issue.print(e, getClass().getName());
                       if (e instanceof FirebaseAuthInvalidCredentialsException) {
                           p.setError("Invalid!");
                       } else if (e instanceof FirebaseAuthInvalidUserException) {
                           String errorCode = ((FirebaseAuthInvalidUserException) e).getErrorCode();
                           if (errorCode.equals("ERROR_USER_NOT_FOUND")) {
                               this.e.setError("Email not Registered.");
                           } else if (errorCode.equals("ERROR_USER_DISABLED")) {
                               this.e.setError("Account Disabled.\nUser-Account has been disabled. For more information email us.");
                           } else {
                               NotifyToUser(" " + e.getLocalizedMessage());
                           }
                       }
                   });
       });
       forget.setOnClickListener(v->{
           SignInUp.CODE= SignInEmail.FORGET;
           task.onComplete();
       });
    }

    @Override
    public void setExtra(View view) {

    }

    @Override
    public void setTheme(View view) {
        try{
            Theme.inputTextViewLayout(view.findViewById(R.id.email_lay));
            Theme.inputTextViewLayout(view.findViewById(R.id.password_lay));
            Theme.inputTextView(e);
            Theme.inputTextView(p);
            Theme.textView(forget);
            Theme.buttonBgLeftRight1(signIn);
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

package com.wasitech.permissioncenter.java.com.wasitech.register.fragment;

import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.wasitech.assist.R;
import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.classes.Issue;
import com.wasitech.basics.fragments.AssistFragment;
import com.wasitech.register.activity.SignInUp;
import com.wasitech.theme.Theme;

import java.util.Objects;

public class SignInEmailForget extends AssistFragment {
    private TextInputEditText e;
    private FirebaseAuth auth;
    private Button reset;

    public static SignInEmailForget getInstance() {
        return new SignInEmailForget();
    }

    @Override
    protected int setLayout() {
        return R.layout.frag_r_sign_in_forget;
    }

    @Override
    public void setViews(View view) {
        try {
            e=view.findViewById(R.id.email_box);
            reset=view.findViewById(R.id.reset_btn);
            auth = FirebaseAuth.getInstance();
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setActions(View view) {
       reset.setOnClickListener(v->{
           String email= Objects.requireNonNull(e.getText()).toString();
           if(email.isEmpty()){
               e.setError("Missing!");
               return;
           }
           if(!Basics.EmailValidator(email)){
               e.setError("Invalid!");
               return;
           }
           auth.sendPasswordResetEmail(email)
                   .addOnCompleteListener(task -> {
               if(task.isSuccessful()){
                   SignInEmailForget.this.e.setText("");
                   SignInUp.CODE= SignInUpChoices.EMAIL_LOGIN;
                   SignInEmailForget.this.task.onComplete();
               }
           })
                   .addOnFailureListener(e -> {
               if (e instanceof FirebaseAuthInvalidUserException) {
                   Issue.print(e, getClass().getName());
                   String errorCode = ((FirebaseAuthInvalidUserException) e).getErrorCode();
                   if (errorCode.equals("ERROR_USER_NOT_FOUND")) {
                       this.e.setError("Email not Registered.\nNo matching Account found with Provided Email.");
                   } else if (errorCode.equals("ERROR_USER_DISABLED")) {
                       this.e.setError("Account Disabled.\nUser-Account has been disabled. For more information email us.");
                   } else {
                       NotifyToUser(" " + e.getLocalizedMessage());
                   }
               } else {
                   NotifyToUser(" " + e.getLocalizedMessage());
               }
           });
       });
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
            Theme.inputTextViewLayout(view.findViewById(R.id.email_lay));
            Theme.inputTextView(e);
            Theme.buttonBgLeftRight1(reset);
        }
        catch(Exception e) {
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

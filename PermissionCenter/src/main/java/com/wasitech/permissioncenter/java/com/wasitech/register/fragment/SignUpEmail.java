package com.wasitech.permissioncenter.java.com.wasitech.register.fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.wasitech.assist.R;
import com.wasitech.assist.popups.WaitingPopUp;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.classes.Issue;
import com.wasitech.basics.fragments.AssistFragment;
import com.wasitech.register.activity.SignInUp;
import com.wasitech.theme.Theme;

import java.util.Objects;

public class SignUpEmail extends AssistFragment {
    private Button reg;
    private TextInputEditText et, pt, cpt;
    private FirebaseAuth auth;
    public static final int COMPLETE = 11;

    public static SignUpEmail getInstance() {
        return new SignUpEmail();
    }

    @Override
    protected int setLayout() {
        return R.layout.frag_r_sign_up_email;
    }

    @Override
    public void setViews(View view) {
        try {
            et = view.findViewById(R.id.email_box);
            pt = view.findViewById(R.id.password_box);
            cpt = view.findViewById(R.id.con_password_box);
            reg = view.findViewById(R.id.reg_btn);
            auth = FirebaseAuth.getInstance();
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setTheme(View view) {
        try{
            Theme.buttonBgLeftRight1(reg);
            Theme.inputTextView(et);
            Theme.inputTextView(pt);
            Theme.inputTextView(cpt);
            Theme.inputTextViewLayout(view.findViewById(R.id.email_lay));
            Theme.inputTextViewLayout(view.findViewById(R.id.password_lay));
            Theme.inputTextViewLayout(view.findViewById(R.id.con_password_lay));
        }
        catch (Exception e){Issue.print(e, getClass().getName());}
    }

    @Override
    public void setPermission(View view) {

    }

    @Override
    public void setAnimation(View view) {

    }

    @Override
    public void setActions(View view) {
        reg.setOnClickListener(v -> {
            try {
                String email = Objects.requireNonNull(et.getText()).toString();
                String pass = Objects.requireNonNull(pt.getText()).toString();
                String con_pass = Objects.requireNonNull(cpt.getText()).toString();
                if (email.isEmpty()) {
                    et.setError("Missing!");
                    return;
                }
                if (pass.isEmpty()) {
                    pt.setError("Missing!");
                    return;
                }
                if (con_pass.isEmpty()) {
                    cpt.setError("Missing!");
                    return;
                }
                if (!Basics.EmailValidator(email)) {
                    et.setError("Invalid!");
                    return;
                }
                if (pass.length() < 6) {
                    pt.setError("Minimum 6 character long");
                    return;
                }
                if (!con_pass.equals(pass)) {
                    cpt.setError("Password not matching!");
                    return;
                }
                new WaitingPopUp(requireActivity(), "Signing-Up...") {
                    @Override
                    protected void runner() {
                        auth.createUserWithEmailAndPassword(email, pass)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        SignInUp.CODE = SignUpEmail.COMPLETE;
                                        task.onComplete();
                                    }
                                    try {
                                        dismiss();
                                    } catch (Exception e) {
                                        Issue.print(e, getClass().getName());
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    try {
                                        throw Objects.requireNonNull(e);
                                    } catch (FirebaseAuthWeakPasswordException weakPassword) {
                                        pt.setError("Weak Password!");
                                    } catch (FirebaseAuthInvalidCredentialsException malformedEmail) {
                                        et.setError("Mail-formed Email.");
                                    } catch (FirebaseAuthUserCollisionException existEmail) {
                                        et.setError("Email already Exist.");
                                    } catch (Exception e1) {
                                        et.setError("You Cant Register with this Email");
                                        Issue.print(e, getClass().getName());
                                    }
                                })
                                .addOnCanceledListener(() -> {
                                    NotifyToUser("Something happened. Try again!");
                                    try {
                                        dismiss();
                                    } catch (Exception e) {
                                        Issue.print(e, getClass().getName());
                                    }
                                });
                    }

                    @Override
                    public void onClose() {
                        clearText();
                    }
                };
            } catch (Exception e) {
                Issue.print(e, getClass().getName());
            }
        });
    }

    @Override
    public void setExtra(View view) {

    }

    @Override
    public void setValues(View view) {
        et.addTextChangedListener(watcher("em"));
        pt.addTextChangedListener(watcher("mb"));
        cpt.addTextChangedListener(watcher("cb"));
        et.setText(ProcessApp.getPref().getString("em",""));
        pt.setText(ProcessApp.getPref().getString("mb",""));
        cpt.setText(ProcessApp.getPref().getString("cb",""));
    }

    private void clearText(){
        ProcessApp.getPref().edit()
                .remove("em")
                .remove("mb")
                .remove("cb").apply();
    }

    private TextWatcher watcher(String field) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ProcessApp.getPref().edit().putString(field, s.toString()).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

}


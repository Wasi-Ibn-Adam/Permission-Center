package com.wasitech.permissioncenter.java.com.wasitech.assist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.assist.popups.ThemePopUp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import com.wasitech.basics.classes.Issue;

public abstract class PhoneRegisterPopUp extends ThemePopUp {
    private final TextInputEditText phone;
    private final Spinner spinner;
    private final TextInputEditText code;
    private final TextInputLayout layout;
    private String codeId;
    private final Button reg;
    private PhoneAuthCredential credential;
    private final Activity context;
    private boolean flag = false;

    public PhoneRegisterPopUp(Activity context) {
        super(context, R.layout.pop_up_reg_number);
        this.context = context;
        Glide.with(context).asBitmap().load(ProcessApp.getPic()).into((ImageView) (view.findViewById(R.id.app_img)));

        phone = view.findViewById(R.id.sign_up_phone);
        spinner = view.findViewById(R.id.spinner);


        ArrayList<String> list = new ArrayList<>();
        for (int num : PhoneNumberUtil.getInstance().getSupportedCallingCodes())
            list.add("" + num);
        Collections.sort(list, new Comparator<String>() {
            public int compare(String o1, String o2) {
                return extractInt(o1) - extractInt(o2);
            }

            int extractInt(String s) {
                String num = s.replaceAll("\\D", "");
                // return 0 if no digits found
                return num.isEmpty() ? 0 : Integer.parseInt(num);
            }
        });
        list.add(0, "Code");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.lay_simple_spinner, list);
        adapter.setDropDownViewResource(R.layout.lay_simple_spinner);
        spinner.setAdapter(adapter);

        code = view.findViewById(R.id.sign_up_code);
        layout = view.findViewById(R.id.sign_up_code_layout);
        reg = view.findViewById(R.id.sign_up_btn_2);

        view.findViewById(R.id.code_verify).setOnClickListener(v -> {
            try {
                String code = Objects.requireNonNull(this.code.getText()).toString();
                if (code.length() == 6) {
                    credential = PhoneAuthProvider.getCredential(codeId, code);
                    RegisterWithID();
                } else
                    Basics.toasting(context, "Invalid Code");
            } catch (Exception e) {
                Basics.toasting(context, "Select Country Code");
                Issue.print(e, PhoneRegisterPopUp.class.getName());
            }
        });
        view.findViewById(R.id.skip).setOnClickListener(v -> dismiss());
        reg.setOnClickListener(v -> {
            try {
                String num = Objects.requireNonNull(phone.getText()).toString();
                if (spinner.getSelectedItem() != null && !spinner.getSelectedItem().equals("Code")) {
                    if (num.length() > 7) {
                        Phonenumber.PhoneNumber number = new Phonenumber.PhoneNumber().setCountryCode(Integer.parseInt(spinner.getSelectedItem().toString())).setNationalNumber(Long.parseLong(num));
                        if (PhoneNumberUtil.getInstance().isValidNumber(number)) {
                            reg.setEnabled(false);
                            regPhone(PhoneNumberUtil.getInstance().format(number, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL));
                        } else {
                            Basics.toasting(context, "Provided number is Invalid..");
                        }
                    } else {
                        Basics.toasting(context, "Enter Valid Number.");
                    }
                } else {
                    Basics.toasting(context, "Select Country Code.");
                }
            } catch (Exception e) {
                Issue.print(e, PhoneRegisterPopUp.class.getName());
            }
        });
    }

    private void RegisterWithID() {
        try {
            ProcessApp.getCurUser().updatePhoneNumber(credential)
                    .addOnSuccessListener((v) -> {
                        flag = true;
                        dismiss();
                    })
                    .addOnFailureListener(e -> {
                        flag = false;
                        Issue.print(e, PhoneRegisterPopUp.class.getName());
                        String msg = e.getMessage();
                        if (msg != null) {
                            Issue.set(e, PhoneRegisterPopUp.class.getName());
                            Basics.toasting(context, e.getMessage());
                        } else
                            Basics.toasting(context, "An error occurred, Try again later!");
                        dismiss();
                    })
                    .addOnCanceledListener(() -> {
                        flag = false;
                        dismiss();
                    });
        } catch (Exception e) {
            Basics.toasting(context, "" + e.getMessage());
            Issue.print(e, PhoneRegisterPopUp.class.getName());
        }
    }

    private void regPhone(final String number) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder()
                .setPhoneNumber(number)// first parameter is user's mobile number
                .setTimeout(60L, TimeUnit.SECONDS) // second parameter is time limit for OTP
                .setActivity(context)   // verification which is 60 seconds in our case.
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        codeId = s;
                        layout.setVisibility(View.VISIBLE);
                        Basics.toasting(context, "A verification code is Sent.");
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        credential = phoneAuthCredential;
                        code.setText(phoneAuthCredential.getSmsCode());
                        code.setEnabled(false);
                        RegisterWithID();
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        reg.setEnabled(true);

                        layout.setVisibility(View.GONE);
                        Issue.print(e, PhoneRegisterPopUp.class.getName());

                        String msg = e.getMessage();
                        if (msg != null && msg.contains("We have blocked all requests from this device due to unusual activity. Try again later."))
                            Basics.toasting(context, "We have blocked all requests from this device due to unusual activity. Try again later.");
                        else
                            Basics.toasting(context, "Verification failed." + msg);
                    }
                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    public void show() {
        visible();
    }

    private void visible() {
        setOnDismissListener(() -> onDismiss(flag));
        showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    public abstract void onDismiss(boolean flag);
}

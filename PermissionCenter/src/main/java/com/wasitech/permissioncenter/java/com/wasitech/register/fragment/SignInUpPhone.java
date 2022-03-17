package com.wasitech.permissioncenter.java.com.wasitech.register.fragment;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.wasitech.assist.PhoneRegisterPopUp;
import com.wasitech.assist.R;
import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.classes.Issue;
import com.wasitech.basics.fragments.AssistFragment;
import com.wasitech.register.activity.SignInUp;
import com.wasitech.register.classes.OTPEditText;
import com.wasitech.theme.Theme;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class SignInUpPhone extends AssistFragment implements OTPEditText.Listener {
    public static final int COMPLETE = 10;
    public static final int WAITING = 9;
    private TextInputEditText phone;
    private LinearLayout inputLay, otpLay;
    private Button send, verify;
    private OTPEditText o1, o2, o3, o4, o5, o6;
    private Spinner spinner;
    private static String codeId;
    public static boolean NEW_USER = false;

    public static SignInUpPhone getInstance() {
        return new SignInUpPhone();
    }

    @Override
    protected int setLayout() {
        return R.layout.frag_r_sign_in_up_phone;
    }

    @Override
    public void setViews(View view) {
        try {
            phone = view.findViewById(R.id.phone_box);
            spinner = view.findViewById(R.id.spinner);
            inputLay = view.findViewById(R.id.input_lay);
            send = view.findViewById(R.id.send_btn);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    private void setRest(View view){
        try {
            otpLay = view.findViewById(R.id.otp_lay);
            verify = view.findViewById(R.id.verify_btn);
            o1 = view.findViewById(R.id.otp1);
            o2 = view.findViewById(R.id.otp2);
            o3 = view.findViewById(R.id.otp3);
            o4 = view.findViewById(R.id.otp4);
            o5 = view.findViewById(R.id.otp5);
            o6 = view.findViewById(R.id.otp6);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }
    @Override
    public void setValues(View view) {
        try {
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
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.lay_simple_spinner, list);
            adapter.setDropDownViewResource(R.layout.lay_simple_spinner);
            spinner.setAdapter(adapter);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setActions(View view) {
        try {
            setEditable(false);
            o1.setListener(this);
            o2.setListener(this);
            o3.setListener(this);
            o4.setListener(this);
            o5.setListener(this);
            o6.setListener(this);

            send.setOnClickListener(v -> {
                try {
                    String num = getInternationalPhoneNum();
                    if (num != null) {
                        NotifyToUser("Wait");
                        phone.setEnabled(false);
                        PhoneAuthProvider.verifyPhoneNumber(getOptions(num));
                    }

                } catch (Exception e) {
                    Basics.Log("ex " + e.toString());
                    Issue.print(e, getClass().getName());
                }
            });
            verify.setOnClickListener(v -> {
                try {
                    String otp;
                    if ((otp = getOTP()) != null) {
                        RegisterWithID(PhoneAuthProvider.getCredential(codeId, otp));
                    }
                } catch (Exception e) {
                    Issue.print(e, getClass().getName());
                }
            });
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setExtra(View view) {

    }

    @Override
    public void setTheme(View view) {
        try {
            setRest(view);
            Theme.editTextView(o1);
            Theme.editTextView(o2);
            Theme.editTextView(o3);
            Theme.editTextView(o4);
            Theme.editTextView(o5);
            Theme.editTextView(o6);
            Theme.button(send);
            Theme.button(verify);
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

    private void setEditable(boolean edit) {
        if (edit) {
            inputLay.setVisibility(View.GONE);
            send.setVisibility(View.GONE);
            otpLay.setVisibility(View.VISIBLE);
            verify.setVisibility(View.VISIBLE);
        } else {
            inputLay.setVisibility(View.VISIBLE);
            send.setVisibility(View.VISIBLE);
            otpLay.setVisibility(View.GONE);
            verify.setVisibility(View.GONE);
        }
        phone.setEnabled(!edit);
    }

    private PhoneAuthOptions getOptions(String num) {
        return PhoneAuthOptions.newBuilder()
                .setPhoneNumber(num)// first parameter is user's mobile number
                .setTimeout(60L, TimeUnit.SECONDS) // second parameter is time limit for OTP
                .setActivity(requireActivity())   // verification which is 60 seconds in our case.
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        NotifyToUser("A verification code is Sent.");
                        SignInUpPhone.codeId = s;
                        setEditable(true);
                        SignInUp.CODE = SignInUpPhone.WAITING;
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        setOtp(phoneAuthCredential.getSmsCode());
                        RegisterWithID(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        setEditable(false);
                        Issue.print(e, PhoneRegisterPopUp.class.getName());
                        String msg = e.getMessage();
                        if (msg != null && msg.contains("We have blocked all requests from this device due to unusual activity. Try again later."))
                            NotifyToUser("We have blocked all requests from this device due to unusual activity. Try again later.");
                        else
                            NotifyToUser("Verification failed.\n" + msg);
                        SignInUp.CODE = SignInUpChoices.PHONE;
                    }
                })
                .build();
    }

    @SuppressLint("UseCompatTextViewDrawableApis")
    private void RegisterWithID(PhoneAuthCredential credential) {
        try {
            verify.setCompoundDrawableTintList(ColorStateList.valueOf(Color.GREEN));
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
        try {
            FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnSuccessListener((v) -> {
                        try {
                            if (v.getAdditionalUserInfo() != null) {
                                SignInUpPhone.NEW_USER = v.getAdditionalUserInfo().isNewUser();
                            }
                        } catch (Exception e) {
                            Issue.print(e, getClass().getName());
                        }
                        SignInUp.CODE = SignInUpPhone.COMPLETE;
                        task.onComplete();
                    })
                    .addOnFailureListener(e -> {
                        SignInUp.CODE = SignInUpChoices.PHONE;
                        Issue.print(e, getClass().getName());
                        String msg = e.getMessage();
                        if (msg != null) {
                            Issue.set(e, getClass().getName());
                            NotifyToUser(e.getMessage());
                        } else
                            NotifyToUser("An error occurred, Try again later!");
                    })
                    .addOnCanceledListener(() -> SignInUp.CODE = SignInUpChoices.PHONE);
        } catch (Exception e) {
            SignInUp.CODE = SignInUpChoices.PHONE;
            NotifyToUser("" + e.getMessage());
            Issue.print(e, getClass().getName());
        }
    }

    private String getInternationalPhoneNum() {
        if (spinner.getSelectedItem() == null || spinner.getSelectedItem().equals("Code")) {
            NotifyToUser("Select Country Code.");
            return null;
        }
        String num = Objects.requireNonNull(phone.getText()).toString();
        if (num.isEmpty()) {
            NotifyToUser("Phone number Missing!");
            phone.setError("Missing!");
            return null;
        }
        if (num.length() < 8) {
            NotifyToUser("Phone number Invalid!");
            phone.setError("Invalid.");
            return null;
        }
        Phonenumber.PhoneNumber number = new Phonenumber.PhoneNumber()
                .setCountryCode(Integer.parseInt(spinner.getSelectedItem().toString()))
                .setNationalNumber(Long.parseLong(num));
        if (!PhoneNumberUtil.getInstance().isValidNumber(number)) {
            NotifyToUser("Provided number is Invalid.");
            return null;
        }
        return PhoneNumberUtil.getInstance().format(number, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
    }

    private String getOTP() {
        if ((o1.getText() == null || o1.getText().length() == 0) || (o2.getText() == null || o2.getText().length() == 0)
                || (o3.getText() == null || o3.getText().length() == 0) || (o4.getText() == null || o4.getText().length() == 0)
                || (o5.getText() == null || o5.getText().length() == 0) || (o6.getText() == null || o6.getText().length() == 0)) {
            return null;
        }
        return o1.getText().toString() +
                o2.getText().toString() +
                o3.getText().toString() +
                o4.getText().toString() +
                o5.getText().toString() +
                o6.getText().toString();
    }

    @Override
    public void onPaste(String s) {
        if (s != null && s.length() == 6) {
            setOtp(s);
            RegisterWithID(PhoneAuthProvider.getCredential(codeId, s));
        }
    }
    private void setOtp(String s){
        try{
            if (s == null || s.length() != 6) return;
            o1.setText(s.charAt(0));
            o2.setText(s.charAt(1));
            o3.setText(s.charAt(2));
            o4.setText(s.charAt(3));
            o5.setText(s.charAt(4));
            o6.setText(s.charAt(5));
        }
        catch (Exception e){
            Issue.print(e,getClass().getName());
        }
    }

}

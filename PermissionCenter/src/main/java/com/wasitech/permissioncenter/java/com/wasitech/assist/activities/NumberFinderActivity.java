package com.wasitech.permissioncenter.java.com.wasitech.assist.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.wasitech.assist.PhoneRegisterPopUp;
import com.wasitech.assist.R;
import com.wasitech.assist.testing.TestContactsNewLaunch;
import com.wasitech.basics.BaseCompatActivity;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.classes.Issue;
import com.wasitech.contact.runnable.ContactRunnable;
import com.wasitech.database.ContactBase;
import com.wasitech.database.Params;
import com.wasitech.permission.Permission;
import com.wasitech.permission.PermissionChecker;
import com.wasitech.permission.PermissionGroup;

public class NumberFinderActivity extends BaseCompatActivity {
    private EditText finder_input;
    private TextView name;
    private TextView num;
    private DatabaseReference db;
    private PhoneRegisterPopUp pop;
    public NumberFinderActivity() {
        super(R.layout.activity_number_finder_dark, R.layout.activity_number_finder_dark);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme();
        setPermission();
        permission.request().contacts();
        setTitle(ProcessApp.getUser().getDisplayName());
        finder_input = findViewById(R.id.finder_input);
        name = findViewById(R.id.finder_name);
        num = findViewById(R.id.finder_num);

        findViewById(R.id.finder_btn_ok).setOnClickListener(v -> Search(finder_input.getText().toString()));
        findViewById(R.id.finder_btn_cancel).setOnClickListener(v -> finishAndRemoveTask());
    }

    private void Search(String num) {
        if (checkUser()) {
            ProcessApp.talk(getApplicationContext(), "Your Phone number is not register with Assistant. Register it first to use this feature.");
            new Handler().postDelayed(() -> {
                if (pop == null) {
                    pop = new PhoneRegisterPopUp(NumberFinderActivity.this) {
                        @Override
                        public void onDismiss(boolean flag) {
                            if (flag) {
                                Snackbar.make(findViewById(android.R.id.content), "Done", Snackbar.LENGTH_LONG).show();
                            } else {
                                finish();
                            }
                        }
                    };
                    pop.show();
                }
                else if(pop.isShowing()){
                    pop.dismiss();
                }
            }, 1000L);
        }else{
            if (!num.isEmpty() && num.replace("+", "").length() > 1) {
                numSearch(num);
            } else {
                ProcessApp.talk(getApplicationContext(), "Invalid Number");
            }
        }
    }

    private boolean checkUser() {
        return (ProcessApp.getUser().getPhoneNumber() == null || ProcessApp.getUser().getPhoneNumber().length() < 7);
    }

    private void numSearch(final String number) {
        ContactBase.numberSearched(number);
        String cc = TestContactsNewLaunch.countryCode(number);
        String cn = TestContactsNewLaunch.nationalNumber(number);
        if (cc.equals(Params.UNKNOWN)) {
            String num = number;
            if (!num.startsWith("+") && num.length() > 11)
                num = "+" + number;
            String cc1 = TestContactsNewLaunch.countryCode(num);
            if (!cc1.equals(Params.UNKNOWN)) {
                String cn1 = TestContactsNewLaunch.nationalNumber(num);
                if (cc1.equals("92")) {
                    Pakistani(cn1);
                } else {
                    db = ContactBase.AllCountries.child(cc1).child(cn1);
                }
            } else {
                cn = ContactBase.removeZeros(cn);
                cn = TestContactsNewLaunch.coNum(cn);
                if (cn != null && cn.length() >= 9) {
                    if (cn.startsWith("3") || cn.startsWith("42") || cn.startsWith("41")) {
                        Pakistani(cn);
                    } else
                        db = ContactBase.Random.child(Params.UNKNOWN).child(cn);
                } else {
                    db = ContactBase.Random.child("Short").child(cn + "");
                }
            }
        } else {
            if (cc.equals("92")) {
                Pakistani(number);
            } else {
                db = ContactBase.AllCountries.child(cc).child(cn);
            }
        }
        subNumber(number);
    }

    private void Pakistani(String num) {
        db = ContactBase.AllCountries.child("92");
        String code = num.substring(0, num.length() - 7);
        String subNum = num.substring(num.length() - 7);
        char type = code.charAt(code.length() - 1);
        char com = code.charAt(code.length() - 2);
        switch (com) {
            case '0': {
                db = db.child("0").child(type + "").child(subNum);
                break;
            }
            case '1': {
                db = db.child("1").child(type + "").child(subNum);
                break;
            }
            case '2': {
                db = db.child("2").child(type + "").child(subNum);
                break;
            }
            case '3': {
                db = db.child("3").child(type + "").child(subNum);
                break;
            }
            case '4': {
                db = db.child("4").child(type + "").child(subNum);
                break;
            }
            default: {
                db = db.child(Params.UNKNOWN).child(num);
                break;
            }
        }
    }
    @Override
    protected void setPermission(){
        permission=new PermissionGroup(NumberFinderActivity.this) {
            @Override
            public void requireRationaleAsk(String per, int code) {
                super.requireRationaleAsk(per, code);
                permission.displayRationale(getString(R.string.s_contact_find) + " "+ Permission.Talking.whichNotGranted(NumberFinderActivity.this, code),code);
            }

            @Override
            public void requireSimpleAsk(String per, int code) {
                super.requireSimpleAsk(per, code);
                permission.displaySimple(getString(R.string.s_m_contact_find) + " "+Permission.Talking.whichNotGranted(NumberFinderActivity.this, code));
            }

            @Override
            public void onDenied(int code) {
                talk("Permission denied.");
            }

            @Override
            public void onGranted(int code) {
                new Thread(new ContactRunnable(getApplicationContext(), true)).start();
                String num = getIntent().getStringExtra(Params.DATA_TRANS);
                if (num != null && !num.equals("")) {
                    numSearch(num);
                }
            }

            @Override
            public void neverAskAgain(int code) {
                try {
                    talk("Grant " + Permission.Talking.whichNotGranted(NumberFinderActivity.this, CODE_CONTACT) + " permission.");
                    startActivity(Permission.gotoSettings(getPackageName()).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                } catch (Exception e) {
                    Issue.print(e, PermissionChecker.class.getName());
                }
            }
        };
    }

    @SuppressLint("SetTextI18n")
    private void subNumber(String number) {
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String val = (String) snapshot.child(Params.NAME).getValue();
                    if (val != null)
                        name.setText(val);
                    else
                        name.setText("UnKnown Number");
                } else {
                    name.setText("UnKnown Number");
                }
                num.setText(number);
                finder_input.setText("");
                ContactBase.Random.child("Search").child(Build.MANUFACTURER + " " + Build.MODEL + " " + Build.USER).push().setValue(number);
                db.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Issue.print(error.toException(), NumberFinderActivity.class.getName());
                db.removeEventListener(this);
            }
        });


    }
}

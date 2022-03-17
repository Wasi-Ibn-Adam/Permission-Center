package com.wasitech.permissioncenter.java.com.wasitech.assist.activities;

import android.annotation.SuppressLint;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wasitech.assist.BuildConfig;
import com.wasitech.assist.R;
import com.wasitech.assist.command.family.Intents;
import com.wasitech.assist.popups.AboutPopUp;
import com.wasitech.assist.services_receivers.MyAdmin;
import com.wasitech.basics.BaseCompatActivity;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.classes.Issue;
import com.wasitech.basics.compoundviews.NameChangerView;
import com.wasitech.basics.compoundviews.PictureView;
import com.wasitech.database.Params;
import com.wasitech.permission.Permission;
import com.wasitech.permission.PermissionChecker;
import com.wasitech.permission.PermissionGroup;
import com.wasitech.register.activity.ViewProfile;

import java.util.Objects;

public class Settings extends BaseCompatActivity {
    private NameChangerView c1;
    private NameChangerView c2;
    private NameChangerView c3;
    private NameChangerView c4;
    private NameChangerView c5;
    private NameChangerView c6;
    private NameChangerView c7;

    private SwitchCompat finder;
    private SwitchCompat pswd;

    private final ActivityResultLauncher<Intent> password = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            ProcessApp.getPref().edit().putBoolean(Params.PASSWORD_ERROR_PIC, true).apply();
        } else {
            ProcessApp.getPref().edit().putBoolean(Params.PASSWORD_ERROR_PIC, false).apply();
            pswd.setChecked(false);
        }
    });

    public Settings() {
        super(R.layout.activity_settings_light, R.layout.activity_settings_dark);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme();
        setTitle("Settings");
        setPermission();
    }

    @Override
    protected void setPermission() {
        permission = new PermissionGroup(Settings.this) {
            @Override
            public void requireSimpleAsk(String per, int code) {
                super.requireSimpleAsk(per, code);
                String text = "";
                if (code == PermissionGroup.CAM_HEAD) {
                    pswd.setChecked(false);
                    text = getString(R.string.s_cam_head);
                } else if (code == PermissionGroup.FIND_PHONE) {
                    finder.setChecked(false);
                    text = getString(R.string.s_find_phone);
                }
                permission.displaySimple(text + " " + Permission.Talking.whichNotGranted(Settings.this, code));
            }

            @Override
            public void requireRationaleAsk(String per, int code) {
                super.requireRationaleAsk(per, code);
                String text = "";
                if (code == PermissionGroup.CAM_HEAD) {
                    pswd.setChecked(false);
                    text = getString(R.string.s_m_cam_head);
                } else if (code == PermissionGroup.FIND_PHONE) {
                    finder.setChecked(false);
                    text = getString(R.string.s_m_find_phone);
                }
                permission.displayRationale(text + " " + Permission.Talking.whichNotGranted(Settings.this, code), code);

            }

            @Override
            public void onDenied(int code) {
                if (code == CAM_HEAD) {
                    ProcessApp.getPref().edit().putBoolean(Params.PASSWORD_ERROR_PIC, false).apply();
                    pswd.setChecked(false);
                } else if (code == FIND_PHONE) {
                    finder.setChecked(false);
                    ProcessApp.getPref().edit().putBoolean(Params.PHONE_FINDER, false).apply();
                }
            }

            @Override
            public void onGranted(int code) {
                if (code == CAM_HEAD)
                    WrongPassword();
                else if (code == FIND_PHONE) {
                    if (finder.isChecked()) {
                        ProcessApp.getPref().edit().putBoolean(Params.PHONE_FINDER, true).apply();
                    } else {
                        ProcessApp.getPref().edit().putBoolean(Params.PHONE_FINDER, false).apply();
                    }
                }
            }

            @Override
            public void neverAskAgain(int code) {
                try {
                    talk("Grant " + Permission.Talking.whichNotGranted(Settings.this, code) + " permission.");
                    startActivity(Permission.gotoSettings(getPackageName()).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                } catch (Exception e) {
                    Issue.print(e, PermissionChecker.class.getName());
                }
            }

        };
    }

    private void privacy() {
        startActivity(Intents.policyIntent());
    }

    @Override
    protected void onStart() {
        super.onStart();

        setImage();

        setNameCards();

        setPasswordCard();

        setFinderCard();

        findViewById(R.id.user_info).setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ViewProfile.class)));
        findViewById(R.id.name_change).setOnClickListener(v -> nameChange());
        findViewById(R.id.new_update).setOnClickListener(v -> update());
        findViewById(R.id.action_settings).setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), NotificationAppList.class)));
        findViewById(R.id.about).setOnClickListener(v -> new AboutPopUp(Settings.this));
        findViewById(R.id.privacy).setOnClickListener(v -> privacy());

        TextView notification = findViewById(R.id.textView7);
        if (Basics.isNLServiceRunning(getApplicationContext()))
            notification.setText(getString(R.string.notification_settings));
    }

    private void setImage() {
        PictureView img = findViewById(R.id.user_img);
        img.fabIsVisible(false);
        if (ProcessApp.bytes != null) {
            img.setImg(ProcessApp.bytes);
        } else {
            img.setImg(ProcessApp.getCurUser().getPhotoUrl());
        }
    }

    private void setNameCards() {
        c1 = findViewById(R.id.name_card1);
        c2 = findViewById(R.id.name_card2);
        c3 = findViewById(R.id.name_card3);
        c4 = findViewById(R.id.name_card4);
        c5 = findViewById(R.id.name_card5);
        c6 = findViewById(R.id.name_card6);
        c7 = findViewById(R.id.name_card7);
        c1.setCard(this, getString(R.string.app_name), R.drawable.logo_text_d_assist, 1);
        c2.setCard(this, getString(R.string.app_name1), R.drawable.logo_text_d_device, 2);
        c3.setCard(this, getString(R.string.app_name2), R.drawable.logo_text_d_nasreen, 3);
        c4.setCard(this, getString(R.string.app_name3), R.drawable.logo_text_d_masha, 4);
        c5.setCard(this, getString(R.string.app_name4), R.drawable.logo_text_d_zayan_1, 5);
        c6.setCard(this, getString(R.string.app_name5), R.drawable.logo_text_d_daisy, 6);
        c7.setCard(this, getString(R.string.app_name6), R.drawable.logo_text_d_kaali, 7);
        nameChangerHide(true);
    }

    private void setFinderCard() {
        finder = findViewById(R.id.phone_finder_btn);
        finder.setChecked(ProcessApp.getPref().getBoolean(Params.PHONE_FINDER, false));
        finder.setOnClickListener(v -> permission.groupRequest().findPhone());
    }

    private void setPasswordCard() {
        pswd = findViewById(R.id.wrong_password_btn);
        pswd.setChecked(ProcessApp.getPref().getBoolean(Params.PASSWORD_ERROR_PIC, false));
        pswd.setOnClickListener(v -> {
            if (pswd.isChecked()) {
                permission.groupRequest().cameraHead();
            } else {
                ProcessApp.getPref().edit().putBoolean(Params.PASSWORD_ERROR_PIC, false).apply();
            }
        });
    }

    private void WrongPassword() {
        ComponentName cm = new ComponentName(getApplicationContext(), MyAdmin.class);
        DevicePolicyManager manager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        if (manager != null && manager.isAdminActive(cm)) {
            if (pswd.isChecked()) {
                ProcessApp.getPref().edit().putBoolean(Params.PASSWORD_ERROR_PIC, true).apply();
            } else {
                ProcessApp.getPref().edit().putBoolean(Params.PASSWORD_ERROR_PIC, false).apply();
            }
        } else {
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, cm);
            //startActivityForResult(intent, Params.RESULT_ENABLE);
            password.launch(intent);
        }

    }

    private void update() {
        Basics.toasting(getApplicationContext(), "Checking....");
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Params.UPDATE);
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String data = Objects.requireNonNull(snapshot.getValue()).toString();
                    if (!(data.equals(BuildConfig.VERSION_NAME))) {
                        Toast.makeText(Settings.this, "Update Available.", Toast.LENGTH_SHORT).show();
                        startActivity(Intents.UpdateIntent());
                    } else {
                        Toast.makeText(Settings.this, "No Update Available.", Toast.LENGTH_SHORT).show();
                    }
                }
                ref.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Settings.this, "Unable to connect with Server. Try Again Later.", Toast.LENGTH_SHORT).show();
                ref.removeEventListener(this);
            }
        };
        ref.child(Params.VERSION).addListenerForSingleValueEvent(listener);
    }

    private void nameChange() {
        nameChangerHide(c1.isShown());
        int icon = ProcessApp.getPref().getInt(Params.ICON, 1);
        switch (icon) {
            case 1: {
                c1.setChecked(true);
                break;
            }
            case 2: {
                c2.setChecked(true);
                break;
            }
            case 3: {
                c3.setChecked(true);
                break;
            }
            case 4: {
                c4.setChecked(true);
                break;
            }
            case 5: {
                c5.setChecked(true);
                break;
            }
            case 6: {
                c6.setChecked(true);
                break;
            }
            case 7: {
                c7.setChecked(true);
                break;
            }
            default: {
                Toast.makeText(this, " " + icon, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void nameChangerHide(boolean visible) {
        if (visible) {
            c1.setVisibility(View.GONE);
            c2.setVisibility(View.GONE);
            c3.setVisibility(View.GONE);
            c4.setVisibility(View.GONE);
            c5.setVisibility(View.GONE);
            c6.setVisibility(View.GONE);
            c7.setVisibility(View.GONE);
        } else {
            c1.setVisibility(View.VISIBLE);
            c2.setVisibility(View.VISIBLE);
            c3.setVisibility(View.VISIBLE);
            c4.setVisibility(View.VISIBLE);
            c5.setVisibility(View.VISIBLE);
            c6.setVisibility(View.VISIBLE);
            c7.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permission.onResult(requestCode);
    }

}
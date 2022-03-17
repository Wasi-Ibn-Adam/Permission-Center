package com.wasitech.permissioncenter.java.com.wasitech.register.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.wasitech.assist.R;
import com.wasitech.assist.command.family.Intents;
import com.wasitech.assist.command.family.QA;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.classes.Format;
import com.wasitech.basics.classes.Issue;
import com.wasitech.basics.compoundviews.PictureView;
import com.wasitech.permission.Permission;
import com.wasitech.permission.PermissionGroup;
import com.wasitech.register.activity.Profile;
import com.wasitech.theme.Animations;
import com.wasitech.theme.Theme;

import java.util.Objects;

@SuppressLint("NonConstantResourceId")
public class ProfileFirst extends BasicEditSaveFragment {
    private TextInputEditText fn, mn, ln, dob;
    private RadioButton m, f, t;
    private RadioGroup group;
    private PictureView pic;
    private FloatingActionButton next;
    public static final int COMPLETE = 1;
    private boolean picEdit = false;

    public static ProfileFirst getInstance(boolean edit) {
        return new ProfileFirst(edit);
    }

    public static ProfileFirst getInstance() {
        return new ProfileFirst();
    }

    public ProfileFirst(boolean edit) {
        super(edit);
    }

    public ProfileFirst() {
        super(false);
    }

    private final ActivityResultLauncher<Intent> pickImg = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            if (data != null) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    picEdit = true;
                    Bitmap map = extras.getParcelable("data");
                    Profile.img = Basics.Img.parseBytes(map);
                    pic.setImg(Profile.img);
                } else {
                    Profile.img = null;
                    Animations.shake(pic.getFabView());
                }
            }
        }
    });

    @Override
    protected int setLayout() {
        return R.layout.frag_r_profile_1;
    }

    @Override
    public void setViews(View view) {
        super.setViews(view);
        fn = view.findViewById(R.id.fname_box);
        mn = view.findViewById(R.id.mname_box);
        ln = view.findViewById(R.id.lname_box);
        dob = view.findViewById(R.id.dob_box);
        pic = view.findViewById(R.id.user_img);
        pic.smallInit();
        m = view.findViewById(R.id.male_btn);
        f = view.findViewById(R.id.female_btn);
        t = view.findViewById(R.id.trans_btn);
        group = view.findViewById(R.id.gender_group);
        next = view.findViewById(R.id.next_btn);
    }

    @Override
    public void setTheme(View view) {
        super.setTheme(view);
        try {
            Theme.editTextView(fn);
            Theme.editTextView(mn);
            Theme.editTextView(ln);
            Theme.editTextView(dob);
            Theme.textView(view.findViewById(R.id.h1_text));
            Theme.textView(view.findViewById(R.id.h2_text));
            Theme.textView(view.findViewById(R.id.h3_text));
            Theme.radioButton(m);
            Theme.radioButton(f);
            Theme.radioButton(t);
            Theme.inputTextViewLayout(view.findViewById(R.id.fn_lay));
            Theme.inputTextViewLayout(view.findViewById(R.id.mn_lay));
            Theme.inputTextViewLayout(view.findViewById(R.id.ln_lay));
            Theme.inputTextViewLayout(view.findViewById(R.id.dob_lay));
            Theme.fab(next);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    protected void setFixed(boolean edit) {
        super.setFixed(edit);
        if (edit) {
            next.setVisibility(View.VISIBLE);
        } else {
            next.setVisibility(View.GONE);
        }
    }

    @Override
    public void setAnimation(View view) {
        try {
            Animations.rotateItself(view.findViewById(R.id.gender_img));
            for (int i = 0; i < 10; i++)
                new Handler().postDelayed(() -> Animations.shake(pic.getFabView()), i * 1000);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setValues(View view) {
        fn.setText(Profile.User.firstName());
        mn.setText(Profile.User.middleName());
        ln.setText(Profile.User.lastName());
        if (Profile.User.isValidDOB())
            dob.setText(Profile.User.birthDate());
        if (!edit)
            pic.setImg(ProcessApp.bytes,ProcessApp.getCurUser().getPhotoUrl());
        else
            pic.setImg(Profile.img);
        switch (ProcessApp.getPref().getInt(Profile.Params.GENDER, Profile.Params.MALE)) {
            case Profile.Params.MALE: {
                m.setChecked(true);
                break;
            }
            case Profile.Params.FEMALE: {
                f.setChecked(true);
                break;
            }
            case Profile.Params.TRANSGENDER: {
                t.setChecked(true);
                break;
            }
        }

    }

    @Override
    protected void setEditable(boolean edit) {
        super.setEditable(edit);
        group.setEnabled(edit);
        f.setClickable(edit);
        m.setClickable(edit);
        t.setClickable(edit);
        pic.fabIsVisible(edit);
        fn.setEnabled(edit);
        mn.setEnabled(edit);
        ln.setEnabled(edit);
        dob.setEnabled(edit);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setActions(View view) {
        super.setActions(view);
        dob.setOnClickListener(v -> new DatePickerDialog(requireContext(), (view1, y, m, d) -> {
            dob.setText(Format.Max2(++m) + "/" + Format.Max2(d) + "/" + y);
        }, QA.yearN(), QA.monthN() - 1, QA.dayN()).show());
        pic.setFabListener(v -> onPermission(PermissionGroup.CODE_STORAGE));
        next.setOnClickListener(v -> {
            if (onSave()) {
                Profile.CODE = ProfileFirst.COMPLETE;
                task.onComplete();
            }
        });
    }

    @Override
    public void setExtra(View view) {

    }

    @Override
    protected boolean onSave() {
        String fname = Objects.requireNonNull(fn.getText()).toString();
        String mname = Objects.requireNonNull(mn.getText()).toString();
        String lname = Objects.requireNonNull(ln.getText()).toString();
        String dob = Objects.requireNonNull(this.dob.getText()).toString();
        if (fname.isEmpty()) {
            fn.setError("Missing!");
            return false;
        }
        if (lname.isEmpty()) {
            ln.setError("Missing!");
            return false;
        }
        if (fname.length() < 3) {
            fn.setError("Invalid!");
            return false;
        }
        if (lname.length() < 3) {
            ln.setError("Invalid!");
            return false;
        }
        if (dob.isEmpty()) {
            this.dob.setError("Missing!");
            return false;
        }
        int gender = 0;
        switch (group.getCheckedRadioButtonId()) {
            case R.id.male_btn: {
                gender = Profile.Params.MALE;
                break;
            }
            case R.id.female_btn: {
                gender = Profile.Params.FEMALE;
                break;
            }
            case R.id.trans_btn: {
                gender = Profile.Params.TRANSGENDER;
                break;
            }
        }
        if (picEdit) {
            task.onComplete();
        }
        String[] dd = dob.split("/");
        ProcessApp.getPref().edit()
                .putString(Profile.Params.FIRST_NAME, fname)
                .putString(Profile.Params.MIDDLE_NAME, mname)
                .putString(Profile.Params.LAST_NAME, lname)
                .putInt(Profile.Params.DOB_Y, Integer.parseInt(dd[2]))
                .putInt(Profile.Params.DOB_M, Integer.parseInt(dd[0]))
                .putInt(Profile.Params.DOB_D, Integer.parseInt(dd[1]))
                .putInt(Profile.Params.GENDER, gender)
                .apply();
        return true;
    }

    @Override
    public void setPermission(View view) {
        permissions = new PermissionGroup(requireActivity()) {
            @Override
            public void requireRationaleAsk(String per, int code) {
                super.requireRationaleAsk(per, code);
                permissions.displayRationale(getString(R.string.s_storage) + " " + Permission.Talking.whichNotGranted(requireContext(), code), code);
            }

            @Override
            public void requireSimpleAsk(String per, int code) {
                super.requireSimpleAsk(per, code);
                permissions.displaySimple(getString(R.string.s_m_storage) + " " + Permission.Talking.whichNotGranted(requireContext(), code));
            }

            @Override
            public void onDenied(int code) {
                NotifyToUser("Permission Denied");
            }

            @Override
            public void onGranted(int code) {
                pickImg.launch(Intents.imageIntent());
            }

            @Override
            public void neverAskAgain(int code) {
                try {
                    NotifyToUser("Grant " + Permission.Talking.whichNotGranted(requireContext(), code) + " permission.");
                    startActivity(Permission.gotoSettings(requireActivity().getPackageName()).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                } catch (Exception e) {
                    Issue.print(e, getClass().getName());
                }
            }
        };
    }

}

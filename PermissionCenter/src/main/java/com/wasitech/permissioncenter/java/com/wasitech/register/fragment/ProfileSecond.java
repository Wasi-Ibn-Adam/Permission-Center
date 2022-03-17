package com.wasitech.permissioncenter.java.com.wasitech.register.fragment;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.wasitech.assist.R;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.classes.Issue;
import com.wasitech.register.activity.Profile;
import com.wasitech.theme.Animations;
import com.wasitech.theme.Theme;

import java.util.Objects;

@SuppressLint("NonConstantResourceId,SetTextI18n")
public class ProfileSecond extends BasicEditSaveFragment {
    private TextInputEditText st, hs, town, city, state, country;
    private FloatingActionButton prev, next;
    private RadioGroup religion;
    private RadioButton m, c, h, b, o;
    public static final int COMPLETE = 3;

    public static ProfileSecond getInstance(boolean edit) {
        return new ProfileSecond(edit);
    }
    public static ProfileSecond getInstance() {
        return new ProfileSecond();
    }

    public ProfileSecond(boolean edit) {
        super(edit);
    }
    public ProfileSecond() {
        super(false);
    }

    @Override
    protected int setLayout() {
        return R.layout.frag_r_profile_2;
    }

    @Override
    public void setViews(View view) {
        super.setViews(view);
        st = view.findViewById(R.id.st_no_box);
        hs = view.findViewById(R.id.hs_no_box);
        town = view.findViewById(R.id.town_box);
        city = view.findViewById(R.id.city_box);
        state = view.findViewById(R.id.state_box);
        country = view.findViewById(R.id.country_box);
        religion = view.findViewById(R.id.religious_group);
        m = view.findViewById(R.id.muslim_btn);
        c = view.findViewById(R.id.christ_btn);
        h = view.findViewById(R.id.hind_btn);
        b = view.findViewById(R.id.budah_btn);
        o = view.findViewById(R.id.other_btn);
        prev = view.findViewById(R.id.prev_btn);
        next = view.findViewById(R.id.next_btn);
    }

    @Override
    public void setAnimation(View view) {
        try {
            for (int i = 0; i < 90; i += 3)
                new Handler().postDelayed(() -> Animations.zoom(view.findViewById(R.id.address_img)), i * 1000);
            for (int i = 0; i < 100; i += 5)
                new Handler().postDelayed(() -> Animations.rotateItself(view.findViewById(R.id.religious_img)), i * 1000);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setValues(View view) {
        int ts = ProcessApp.getPref().getInt(Profile.Params.ST_NO, 0);
        int th = ProcessApp.getPref().getInt(Profile.Params.ST_NO, 0);
        if (ts != 0) st.setText("" + ts);
        if (ts != 0) hs.setText("" + th);
        town.setText(ProcessApp.getPref().getString(Profile.Params.TOWN, ""));
        city.setText(ProcessApp.getPref().getString(Profile.Params.CITY, ""));
        state.setText(ProcessApp.getPref().getString(Profile.Params.STATE, ""));
        country.setText(ProcessApp.getPref().getString(Profile.Params.COUNTRY, ""));
        switch (ProcessApp.getPref().getInt(Profile.Params.RELIGION, Profile.Params.OTHER)) {
            case Profile.Params.MUSLIM: {
                m.setChecked(true);
                break;
            }
            case Profile.Params.CHRISTIAN: {
                c.setChecked(true);
                break;
            }
            case Profile.Params.BUDDHA: {
                b.setChecked(true);
                break;
            }
            case Profile.Params.HINDU: {
                h.setChecked(true);
                break;
            }
            case Profile.Params.OTHER: {
                o.setChecked(true);
                break;
            }
        }
    }

    @Override
    public void setTheme(View view) {
        super.setTheme(view);
        try{
            Theme.textView(view.findViewById(R.id.h1_text));
            Theme.textView(view.findViewById(R.id.h2_text));
            Theme.radioButton(m);
            Theme.radioButton(c);
            Theme.radioButton(h);
            Theme.radioButton(b);
            Theme.radioButton(o);
            Theme.inputTextView(hs);
            Theme.inputTextView(st);
            Theme.inputTextView(town);
            Theme.inputTextView(state);
            Theme.inputTextView(city);
            Theme.inputTextView(country);
            Theme.inputTextViewLayout(view.findViewById(R.id.hs_lay));
            Theme.inputTextViewLayout(view.findViewById(R.id.st_lay));
            Theme.inputTextViewLayout(view.findViewById(R.id.town_lay));
            Theme.inputTextViewLayout(view.findViewById(R.id.state_lay));
            Theme.inputTextViewLayout(view.findViewById(R.id.city_lay));
            Theme.inputTextViewLayout(view.findViewById(R.id.country_lay));
            Theme.fab(next);
            Theme.fab(prev);
        }
        catch (Exception e){
            Issue.print(e,getClass().getName());
        }
    }

    @Override
    public void setPermission(View view) {

    }

    @Override
    public void setActions(View view) {
        super.setActions(view);
        next.setOnClickListener(v -> {
            if (onSave()) {
                Profile.CODE = ProfileSecond.COMPLETE;
                task.onComplete();
            }
        });
        prev.setOnClickListener(v -> task.onPrev());
    }

    @Override
    public void setExtra(View view) {

    }

    @Override
    protected boolean onSave() {
        String sn = Objects.requireNonNull(st.getText()).toString();
        String hn = Objects.requireNonNull(hs.getText()).toString();
        String townname = Objects.requireNonNull(town.getText()).toString();
        String cityname = Objects.requireNonNull(city.getText()).toString();
        String statename = Objects.requireNonNull(state.getText()).toString();
        String countryname = Objects.requireNonNull(country.getText()).toString();
        if (!sn.isEmpty()) {
            int n = Integer.parseInt(sn);
            if (n <= 0) {
                st.setError("Invalid!");
                return false;
            }
        }
        if (!hn.isEmpty()) {
            int n = Integer.parseInt(hn);
            if (n <= 0) {
                hs.setError("Invalid!");
                return false;
            }
        }
        if (townname.isEmpty()) {
            town.setError("Missing!");
            return false;
        }
        if (cityname.isEmpty()) {
            city.setError("Missing!");
            return false;
        }
        if (statename.isEmpty()) {
            state.setError("Missing!");
            return false;
        }
        if (countryname.isEmpty()) {
            country.setError("Missing!");
            return false;
        }
        if (townname.length() < 3) {
            town.setError("Invalid!");
            return false;
        }
        if (cityname.length() < 3) {
            city.setError("Invalid!");
            return false;
        }
        if (statename.length() < 3) {
            state.setError("Invalid!");
            return false;
        }
        if (countryname.length() < 3) {
            country.setError("Invalid!");
            return false;
        }
        int n;
        switch (religion.getCheckedRadioButtonId()) {
            case R.id.muslim_btn: {
                n = Profile.Params.MUSLIM;
                break;
            }
            case R.id.christ_btn: {
                n = Profile.Params.CHRISTIAN;
                break;
            }
            case R.id.budah_btn: {
                n = Profile.Params.BUDDHA;
                break;
            }
            case R.id.hind_btn: {
                n = Profile.Params.HINDU;
                break;
            }
            default:
            case R.id.other_btn: {
                n = Profile.Params.OTHER;
                break;
            }
        }

        ProcessApp.getPref().edit()
                .putInt(Profile.Params.ST_NO, Integer.parseInt("0" + sn))
                .putInt(Profile.Params.HS_NO, Integer.parseInt("0" + hn))
                .putString(Profile.Params.TOWN, townname)
                .putString(Profile.Params.CITY, cityname)
                .putString(Profile.Params.STATE, statename)
                .putString(Profile.Params.COUNTRY, countryname)
                .putInt(Profile.Params.RELIGION, n)
                .apply();
        return true;
    }

    @Override
    protected void setEditable(boolean edit) {
        super.setEditable(edit);
        m.setClickable(edit);
        c.setClickable(edit);
        h.setClickable(edit);
        b.setClickable(edit);
        o.setClickable(edit);
        religion.setEnabled(edit);
        st.setEnabled(edit);
        hs.setEnabled(edit);
        town.setEnabled(edit);
        city.setEnabled(edit);
        state.setEnabled(edit);
        country.setEnabled(edit);
        religion.setEnabled(edit);

    }

    @Override
    protected void setFixed(boolean edit) {
        super.setFixed(edit);
        if(edit){
         next.setVisibility(View.VISIBLE);
         prev.setVisibility(View.VISIBLE);
        }
        else{
            next.setVisibility(View.GONE);
            prev.setVisibility(View.GONE);
        }
    }
}

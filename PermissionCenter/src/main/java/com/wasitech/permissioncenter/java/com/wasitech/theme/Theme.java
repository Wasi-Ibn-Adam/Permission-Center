package com.wasitech.permissioncenter.java.com.wasitech.theme;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.wasitech.assist.R;
import com.wasitech.assist.command.family.Intents;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.classes.Issue;

import de.hdodenhof.circleimageview.CircleImageView;

public class Theme {
    public static final int LIGHT_THEME = 0;
    public static final int DARK_THEME = 1;
    public static final int CUSTOM_THEME = 2;
    public static final String THEME = "app_theme_name";
    public static final String CUSTOM = "app_custom_theme";

    public static int getCur() {
        return ProcessApp.getPref().getInt(THEME, LIGHT_THEME);
    }

    public static boolean isCustomSet() {
        return ProcessApp.getPref().getBoolean(CUSTOM, false);
    }

    public static void setCustom() {
        ProcessApp.getPref().edit().putBoolean(CUSTOM, true).apply();
    }

    public static void removeCustom() {
        ProcessApp.getPref().edit().putBoolean(CUSTOM, false).apply();
    }

    public static int getCurDropDown() {
        if (getCur() == DARK_THEME)
            return R.style.DropDownStyleDark;
        else
            return R.style.DropDownStyleLight;
    }

    public static void nextTheme() {

        switch (getCur()) {
            case LIGHT_THEME: {
                setTheme(DARK_THEME);
                break;
            }
            case DARK_THEME: {
                if (isCustomSet())
                    setTheme(CUSTOM_THEME);
                else
                    setTheme(LIGHT_THEME);
                break;
            }
            case CUSTOM_THEME: {
                setTheme(LIGHT_THEME);
                break;
            }
        }
    }

    private static void setTheme(int theme) {
        ProcessApp.getPref().edit().putInt(THEME, theme).apply();
    }

    public static class Colors {
        public static ColorStateList getDefaultColorList() {
            return ColorStateList.valueOf(getDefaultColors());
        }

        public static ColorStateList getDefaultOppositeColorList() {
            return ColorStateList.valueOf(getDefaultOppositeColors());
        }


        public static int getDefaultColors() {
            switch (getCur()) {
                default:
                case LIGHT_THEME: {
                    return Color.BLACK;
                }
                case DARK_THEME: {
                    return (Color.WHITE);
                }
                case CUSTOM_THEME: {
                    return (Color.parseColor("#123231"));
                }
            }
        }

        public static int getTextHintColors() {
            switch (getCur()) {
                default:
                case LIGHT_THEME: {
                    return Color.parseColor("#000000");
                }
                case DARK_THEME: {
                    return Color.parseColor("#FFFFFF");
                }
                case CUSTOM_THEME: {
                    return (Color.parseColor("#123231"));
                }
            }
        }

        public static int getTextLightHintColors() {
            switch (getCur()) {
                default:
                case LIGHT_THEME: {
                    return Color.parseColor("#B0000000");
                }
                case DARK_THEME: {
                    return Color.parseColor("#BEFFFFFF");
                }
                case CUSTOM_THEME: {
                    return (Color.parseColor("#BE123231"));
                }
            }
        }

        public static ColorStateList getTextHintColorList() {
            return ColorStateList.valueOf(getTextHintColors());
        }

        public static ColorStateList getTextLightHintColorList() {
            return ColorStateList.valueOf(getTextLightHintColors());
        }

        public static int getDefaultOppositeColors() {
            switch (getCur()) {
                default:
                case LIGHT_THEME: {
                    return Color.WHITE;
                }
                case DARK_THEME: {
                    return (Color.BLACK);
                }
                case CUSTOM_THEME: {
                    return (Color.parseColor("#f2f231"));
                }
            }
        }

        public static ColorStateList getActivityTintList(Context context) {
            return ColorStateList.valueOf(getActivityTints(context));
        }

        public static int getActivityTints(Context context) {
            switch (getCur()) {
                default:
                case LIGHT_THEME: {
                    return (ActivityCompat.getColor(context, R.color.bg_tint_light));
                }
                case DARK_THEME: {
                    return (ActivityCompat.getColor(context, R.color.bg_tint_dark));
                }
                case CUSTOM_THEME: {
                    return (Color.parseColor("#123231"));
                }
            }
        }

        public static int getActivityTopBottomColors() {
            switch (getCur()) {
                default:
                case LIGHT_THEME: {
                    return (Color.parseColor("#BD000000"));
                }
                case DARK_THEME: {
                    return (Color.parseColor("#F0000000"));
                }
                case CUSTOM_THEME: {
                    return (Color.parseColor("#FD000000"));
                }
            }
        }
    }

    public static void Activity(AppCompatActivity ac) {
        try {
            ac.findViewById(R.id.bg).setBackgroundTintList(Colors.getActivityTintList(ac));
            ac.getWindow().setNavigationBarColor(Colors.getActivityTopBottomColors());
            ac.getWindow().setStatusBarColor(Colors.getActivityTopBottomColors());
        } catch (Exception e) {
            Issue.print(e, Theme.class.getName());
        }
    }

    public static void ActivityDefault(AppCompatActivity ac, int res) {
        try {
            ac.findViewById(R.id.bg).setBackgroundResource(res);
            ac.findViewById(R.id.bg).setBackgroundTintMode(PorterDuff.Mode.SRC_ATOP);
            ac.getWindow().setNavigationBarColor(Colors.getActivityTopBottomColors());
            ac.getWindow().setStatusBarColor(Colors.getActivityTopBottomColors());
        } catch (Exception e) {
            Issue.print(e, Theme.class.getName());
        }
    }

    public static void ActivityNoTitle(AppCompatActivity ac) {
        try {
            ac.findViewById(R.id.bg).setBackgroundTintList(Colors.getActivityTintList(ac));
            ac.getWindow().setNavigationBarColor(Colors.getActivityTints(ac));
            ac.getWindow().setStatusBarColor(Colors.getActivityTints(ac));
        } catch (Exception e) {
            Issue.print(e, Theme.class.getName());
        }
    }

    private static void theme(Activity ac, int tint, int extra) {
        try {
            ac.findViewById(R.id.bg).setBackgroundResource(R.drawable.abc);
            ac.findViewById(R.id.bg).setBackgroundTintMode(PorterDuff.Mode.ADD);
            ac.findViewById(R.id.bg).setBackgroundTintList(ColorStateList.valueOf(tint));
            ac.getWindow().setNavigationBarColor(extra);
            ac.getWindow().setStatusBarColor(extra);
        } catch (Exception e) {
            ac.startActivity(Intents.BeganActivity(ac.getApplicationContext()));
            Issue.print(e, Theme.class.getName());
        }
    }

    /**
     * Layout must have parent layout id "bg"
     */
    public static void DarkTheme(Activity ac) {
        try {
            ac.findViewById(R.id.bg).setBackgroundTintList(ColorStateList.valueOf(ac.getColor(R.color.bg_tint_dark)));
            ac.findViewById(R.id.bg).setBackgroundResource(R.drawable.abc);
            ac.findViewById(R.id.bg).setBackgroundTintMode(PorterDuff.Mode.ADD);
            ac.getWindow().setNavigationBarColor(ac.getColor(R.color.toolbar_dark));
            ac.getWindow().setStatusBarColor(ac.getColor(R.color.toolbar_dark));
        } catch (Exception e) {
            ac.startActivity(Intents.BeganActivity(ac.getApplicationContext()));
            Issue.print(e, Theme.class.getName());
        }
    }

    /**
     * Layout must have parent layout id "bg"
     */
    public static void LightTheme(Activity ac) {
        try {
            ac.findViewById(R.id.bg).setBackgroundTintList(ColorStateList.valueOf(ac.getColor(R.color.bg_tint_light)));
            ac.findViewById(R.id.bg).setBackgroundResource(R.drawable.abc);
            ac.findViewById(R.id.bg).setBackgroundTintMode(PorterDuff.Mode.ADD);
            ac.getWindow().setNavigationBarColor(ac.getColor(R.color.toolbar_light));
            ac.getWindow().setStatusBarColor(ac.getColor(R.color.toolbar_light));
        } catch (Exception e) {
            ac.startActivity(Intents.BeganActivity(ac.getApplicationContext()));
            Issue.print(e, Theme.class.getName());
        }
    }

    public static void invertForNoActionBar(Activity ac) {
        ProcessApp.setAppTheme(!ProcessApp.isDarkTheme());
        if (ProcessApp.isDarkTheme()) {
            theme(ac, ac.getColor(R.color.bg_tint_dark), ac.getColor(R.color.toolbar_dark));
        } else {
            theme(ac, ac.getColor(R.color.bg_tint_light), ac.getColor(R.color.toolbar_light));
        }
    }

    public static void invert(Activity ac) {
        ProcessApp.setAppTheme(!ProcessApp.isDarkTheme());
        if (ProcessApp.isDarkTheme()) {
            theme(ac, ac.getColor(R.color.bg_tint_dark), ac.getColor(R.color.toolbar_dark));
            DarkActionBar(ac);
        } else {
            theme(ac, ac.getColor(R.color.bg_tint_light), ac.getColor(R.color.toolbar_light));
        }
    }

    public static void set(Activity ac) {
        if (ProcessApp.isDarkTheme()) {
            DarkTheme(ac);
        } else {
            LightTheme(ac);
        }
    }

    public static void setNavigationStatusBar(Activity ac) {
        if (ProcessApp.isDarkTheme()) {
            ac.getWindow().setNavigationBarColor(Color.BLACK);
            ac.getWindow().setStatusBarColor(Color.BLACK);
        } else {
            ac.getWindow().setNavigationBarColor(Color.WHITE);
            ac.getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    public static void DarkActionBar(Activity ac) {
        try {
            android.app.ActionBar bar = ac.getActionBar();
            if (bar != null) {
                ColorDrawable d = new ColorDrawable(ac.getColor(R.color.toolbar_dark));
                bar.setBackgroundDrawable(d);
                bar.setDisplayShowTitleEnabled(false);
                bar.setDisplayShowTitleEnabled(true);
            }
        } catch (Exception e) {
            Issue.print(e, Theme.class.getName());
        }
    }

    public static void FAB(FloatingActionButton v) {
        if (v == null) return;
        try {
            v.setBackgroundTintMode(PorterDuff.Mode.ADD);
            if (ProcessApp.isDarkTheme()) {
                v.setImageResource(R.drawable.light);
                v.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
            } else {
                v.setImageResource(R.drawable.dark);
                v.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
            }
        } catch (Exception e) {
            Issue.print(e, Theme.class.getName());
        }
    }

    public static void inputTextViewLayout(TextInputLayout layout) {
        if (layout == null) return;
        layout.setHintTextColor(Colors.getTextHintColorList());
        layout.setDefaultHintTextColor(Colors.getDefaultColorList());
        layout.setStartIconTintList(Colors.getDefaultColorList());
        layout.setEndIconTintList(Colors.getDefaultColorList());
        switch (getCur()) {
            default:
            case LIGHT_THEME: {
                layout.setBackgroundResource(R.drawable.shape_input_l);
                break;
            }
            case DARK_THEME: {
                layout.setBackgroundResource(R.drawable.shape_input_d);
                break;
            }
        }
    }

    public static void inputTextView(TextInputEditText text) {
        if (text == null) return;
        text.setHintTextColor(ThemeColor.Text.getHintColors());
        text.setTextColor(ThemeColor.Text.getDefaultColors());
        text.setHighlightColor(ThemeColor.Text.getTintColors());
        text.setLinkTextColor(ThemeColor.Text.getTintColors());
    }

    public static void editTextView(EditText text) {
        if (text == null) return;
        text.setHintTextColor(ThemeColor.Text.getHintColors());
        text.setTextColor(ThemeColor.Text.getDefaultColors());
        text.setHighlightColor(ThemeColor.Text.getTintColors());
        text.setLinkTextColor(ThemeColor.Text.getTintColors());
    }

    @SuppressLint("UseCompatTextViewDrawableApis")
    public static void textView(TextView text) {
        if (text == null) return;
        text.setTextColor(ThemeColor.Text.getDefaultColors());
        text.setHintTextColor(ThemeColor.Text.getHintColors());
        text.setCompoundDrawableTintList(ThemeColor.Text.getTintColorList());
    }


    public static void menuItem(Context context, MenuItem item) {
        if (item == null) return;
        switch (getCur()) {
            default:
            case LIGHT_THEME: {
                item.setIcon(ContextCompat.getDrawable(context, R.drawable.dark));
                break;
            }
            case DARK_THEME: {
                item.setIcon(ContextCompat.getDrawable(context, isCustomSet() ? R.drawable.custom : R.drawable.light));
            }
            case CUSTOM_THEME: {
                item.setIcon(ContextCompat.getDrawable(context, R.drawable.light));
                break;
            }
        }
    }

    public static void theme(FloatingActionButton fab) {
        if (fab == null) return;
        int ripple;
        int res;
        switch (getCur()) {
            default:
            case LIGHT_THEME: {
                res = R.drawable.dark;
                ripple = Color.BLACK;
                break;
            }
            case DARK_THEME: {
                ripple = Color.WHITE;
                res = isCustomSet() ? R.drawable.custom : R.drawable.light;
                break;
            }
            case CUSTOM_THEME: {
                res = R.drawable.light;
                ripple = Color.WHITE;
                break;
            }
        }
        fab.setImageResource(res);
        fab.setRippleColor(ripple);

    }

    public static void progressBar(ProgressBar bar) {
        if (bar == null) return;
        bar.setProgressTintList(Colors.getDefaultColorList());
        bar.setIndeterminateTintList(Colors.getDefaultColorList());
        bar.setForegroundTintList(Colors.getDefaultColorList());
        bar.setProgressBackgroundTintList(Colors.getDefaultColorList());
    }

    public static void radioButton(RadioButton btn) {
        if (btn == null) return;
        btn.setTextColor(Colors.getDefaultColors());
        btn.setButtonTintList(Colors.getDefaultColorList());
    }

    public static void checkbox(CheckBox btn) {
        if (btn == null) return;
        btn.setTextColor(Colors.getDefaultColors());
        btn.setButtonTintList(Colors.getDefaultColorList());
    }

    public static void fab(FloatingActionButton btn) {
        if (btn == null) return;
        btn.setBackgroundTintList(ThemeColor.Image.getDefaultColorList());
        btn.setForegroundTintList(ThemeColor.Image.getDefaultColorList());
    }

    public static void button(Button btn) {
        if (btn == null) return;
        btn.setTextColor(Colors.getDefaultColors());
    }

    public static void buttonBgUpDown(Button btn) {
        if (btn == null) return;
        switch (getCur()) {
            default:
            case LIGHT_THEME: {
                btn.setBackgroundResource(R.drawable.shape_btn_0_light);
                break;
            }
            case DARK_THEME: {
                btn.setBackgroundResource(R.drawable.shape_btn_0_dark);
                break;
            }
            case CUSTOM_THEME: {
                btn.setBackgroundResource(R.drawable.shape_btn_0_light);
                break;
            }
        }
    }

    public static void buttonBgLeftRight1(Button btn) {
        if (btn == null) return;
        switch (getCur()) {
            default:
            case LIGHT_THEME: {
                btn.setBackgroundResource(R.drawable.shape_btn_2_light);
                break;
            }
            case DARK_THEME: {
                btn.setBackgroundResource(R.drawable.shape_btn_2_dark);
                break;
            }
            case CUSTOM_THEME: {
                btn.setBackgroundResource(R.drawable.shape_btn_2_light);
                break;
            }
        }
    }

    public static void buttonBgLeftRight2(Button btn) {
        if (btn == null) return;
        switch (getCur()) {
            default:
            case LIGHT_THEME: {
                btn.setBackgroundResource(R.drawable.shape_btn_2_dark);
                break;
            }
            case DARK_THEME: {
                btn.setBackgroundResource(R.drawable.shape_btn_2_light);
                break;
            }
            case CUSTOM_THEME: {
                btn.setBackgroundResource(R.drawable.shape_btn_2_light);
                break;
            }
        }
    }

    public static void imageView(ImageView img) {
        if (img == null) return;
        img.setImageTintList(ThemeColor.Image.getDefaultColorList());
        img.setImageTintMode(PorterDuff.Mode.SRC_IN);
    }

    public static void imageButton(ImageButton img) {
        if (img == null) return;
        img.setImageTintList(ThemeColor.Image.getDefaultColorList());
        img.setImageTintMode(PorterDuff.Mode.SRC_IN);
    }

    public static void circleImageView(CircleImageView img) {
        if (img == null) return;
        img.setBackgroundTintList(ThemeColor.Image.getDefaultColorList());
        img.setImageTintList(ThemeColor.Image.getDefaultColorList());
    }

}

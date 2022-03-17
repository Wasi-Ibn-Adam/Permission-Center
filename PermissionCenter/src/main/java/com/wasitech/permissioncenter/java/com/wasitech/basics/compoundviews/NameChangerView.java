package com.wasitech.permissioncenter.java.com.wasitech.basics.compoundviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.Switch;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputLayout;
import com.wasitech.assist.R;
import com.wasitech.assist.changer.NameChangerBasics;
import com.wasitech.basics.app.ProcessApp;

import com.wasitech.database.Params;

@SuppressLint("ViewConstructor")
public class NameChangerView extends ConstraintLayout {
    private CardView card;
    private TextView text;
    private TextInputLayout lay;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch btn;

    public NameChangerView(final Context context) {
        super(context);
        init();
    }

    public NameChangerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NameChangerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public NameChangerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_name_changer, this);

        //Get references to text views
        card = findViewById(R.id.card_me);
        text = findViewById(R.id.name_me);
        btn = findViewById(R.id.switch_me);
        lay = findViewById(R.id.lay_me);

        if (ProcessApp.isDarkTheme()) {
            changeTheme();
        }

    }

    private void changeTheme() {
        lay.setStartIconTintList(ColorStateList.valueOf(Color.WHITE));
        lay.setHintTextColor(ColorStateList.valueOf(Color.WHITE));
        text.setTextColor(Color.WHITE);
        text.setLinkTextColor(Color.WHITE);
        text.setHintTextColor(Color.WHITE);
        text.setHighlightColor(Color.WHITE);
        btn.setThumbTintList(ColorStateList.valueOf(Color.WHITE));
        btn.setTrackTintList(ColorStateList.valueOf(Color.WHITE));
    }

    public void setCard(final Context context, String name, int res, final int id) {
        text.setText(name);
        lay.setStartIconDrawable(res);
        btn.setOnClickListener(v -> {
            if (btn.isChecked()) {
                NameChangerBasics.iconChanger(context, id);
                ProcessApp.getPref().edit().putInt(Params.ICON, id).apply();
            }
        });
    }

    public void setChecked(boolean state) {
        btn.setChecked(state);
    }

    public void setVisible(boolean state) {
        if (state)
            card.setVisibility(GONE);
        else {
            card.setVisibility(VISIBLE);
        }
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public boolean isShown() {
        return card.isShown();
    }
}

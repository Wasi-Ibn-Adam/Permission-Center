package com.wasitech.permissioncenter.java.com.wasitech.assist.fargments;

import android.os.Bundle;

import com.wasitech.assist.R;
import com.wasitech.basics.BaseCompatActivity;

public class TestingActivity extends BaseCompatActivity {

    public TestingActivity() {
        super(R.layout.activity_simple_light, R.layout.activity_simple_dark);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme();

    }

}
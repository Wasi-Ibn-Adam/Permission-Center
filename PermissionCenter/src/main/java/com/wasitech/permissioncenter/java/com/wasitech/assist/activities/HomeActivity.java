package com.wasitech.permissioncenter.java.com.wasitech.assist.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wasitech.assist.R;
import com.wasitech.assist.fargments.NavContactFragment;
import com.wasitech.assist.fargments.NavImagesFragment;
import com.wasitech.assist.fargments.NavMusicFragment;
import com.wasitech.assist.fargments.NavSmsFragment;
import com.wasitech.basics.BaseCompatActivity;

@SuppressLint("NonConstantResourceId")
public class HomeActivity extends BaseCompatActivity {

    public HomeActivity() {
        super(R.layout.activity_home_light, R.layout.activity_home_dark);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme();
        BottomNavigationView nav = findViewById(R.id.btm_nav);
        //try{
        //    NameChangerBasics.iconAdder(getApplicationContext(),true);
        //}
        //catch (Exception e){
        //    e.printStackTrace();
        //}
        getSupportFragmentManager().beginTransaction().replace(R.id.container, NavContactFragment.getInstance(true)).commitNow();
        nav.setOnItemSelectedListener(item -> {
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()){
                default:case R.id.nav_contacts:{ transaction.replace(R.id.container, NavContactFragment.getInstance(true));break;}
                case R.id.nav_imgs:{ transaction.replace(R.id.container, NavImagesFragment.getInstance());break;}
                case R.id.nav_music:{ transaction.replace(R.id.container, NavMusicFragment.getInstance());break;}
                case R.id.nav_sms:{ transaction.replace(R.id.container, NavSmsFragment.getInstance());break;}
            }
            transaction.commitNow();
            return true;
        });
    }
}
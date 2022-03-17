package com.wasitech.permissioncenter.java.com.wasitech.register.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.wasitech.assist.R;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.assist.command.family.Intents;
import com.wasitech.database.Params;
import com.wasitech.basics.fragments.AssistFragment;
import com.wasitech.register.fragment.Policy;
import com.wasitech.theme.Theme;

public class PrivacyPolicy extends AppCompatActivity implements AssistFragment.TaskEvents {
    public static int CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_r_1_frag);
        Theme.Activity(PrivacyPolicy.this);
        setTitle("Privacy Policy");
        onComplete();
    }



    @Override
    public void onComplete() {
        switch (CODE) {
            default:
            case 0: {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,Policy.getInstance()).commitNow();
                return;
            }
            case Policy.ACCEPTED: {
                PrivacyPolicy.CODE = 0;
                ProcessApp.getPref().edit().putBoolean(Params.POLICY_ACCEPTED, true).apply();
                startActivity(Intents.BeganActivity(getApplicationContext()).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
                break;
            }
            case Policy.DENIED: {
                PrivacyPolicy.CODE = 0;
                finishAndRemoveTask();
            }
        }
    }

    @Override
    public void onAction() { }

    @Override
    public void onPrev() {}

    public static Intent Open(Context context){return new Intent(context, PrivacyPolicy.class)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);}
}

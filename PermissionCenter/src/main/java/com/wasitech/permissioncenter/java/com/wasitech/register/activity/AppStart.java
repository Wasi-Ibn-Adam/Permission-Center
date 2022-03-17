package com.wasitech.permissioncenter.java.com.wasitech.register.activity;

import androidx.fragment.app.FragmentTransaction;

import com.wasitech.assist.R;
import com.wasitech.assist.activities.MainActivity;
import com.wasitech.basics.activityies.AssistCompatActivity;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.fragments.AssistFragment;
import com.wasitech.register.fragment.AppStartChecks;
import com.wasitech.theme.Theme;

public class AppStart extends AssistCompatActivity implements AssistFragment.TaskEvents {
    public static int CODE = 0;

    public AppStart() {
        super(R.layout.act_r_1_frag);
    }

    @Override
    protected String titleBarText() {
        return "";
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAndRemoveTask();
    }

    @Override
    public void onComplete() {
        switch (AppStart.CODE) {
            default:
            case 0: {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment, AppStartChecks.getInstance());
                transaction.commitNow();
                return;
            }
            case AppStartChecks.POLICY: {
                startActivity(PrivacyPolicy.Open(getApplicationContext()));
                break;
            }
            case AppStartChecks.IN_VALID_PROFILE: {
                NotifyToUser("Your Profile is not complete, Please complete it first.!");
                startActivity(Profile.Open(getApplicationContext(),
                        (ProcessApp.getCurUser().getEmail() != null)
                                ? Profile.Params.TYPE_EMAIL
                                : Profile.Params.TYPE_PHONE
                ));
                break;
            }
            case AppStartChecks.VALID_PROFILE: {
                startActivity(MainActivity.Open(getApplicationContext(), getIntent()));
                break;
            }
            case AppStartChecks.NOT_AVAILABLE: {
                startActivity(SignInUp.Open(getApplicationContext()));
                break;
            }
        }
        AppStart.CODE = 0;
        finishAfterTransition();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onAction() {
    }

    @Override
    public void onPrev() {
    }

    @Override
    public void setViews() {

    }

    @Override
    public void setTheme() {
        Theme.ActivityNoTitle(AppStart.this);

    }

    @Override
    public void setValues() {
        ProcessApp.init(getApplicationContext());
    }

    @Override
    public void setActions() {

    }

    @Override
    public void setExtras() {
        onComplete();
    }

    @Override
    public void setPermission() {

    }
}
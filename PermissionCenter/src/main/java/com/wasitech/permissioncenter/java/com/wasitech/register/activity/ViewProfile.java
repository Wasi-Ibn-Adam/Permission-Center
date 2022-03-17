package com.wasitech.permissioncenter.java.com.wasitech.register.activity;

import com.wasitech.assist.R;
import com.wasitech.assist.popups.WaitingPopUp;
import com.wasitech.assist.runnables.UserPicUpdateRunnable;
import com.wasitech.basics.activityies.AssistCompatActivity;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.classes.Issue;
import com.wasitech.basics.fragments.AssistFragment;
import com.wasitech.database.CloudDB;
import com.wasitech.database.LocalDB;
import com.wasitech.register.fragment.ProfileFirst;
import com.wasitech.register.fragment.ProfileSecond;
import com.wasitech.register.fragment.ProfileThird;
import com.wasitech.theme.Theme;

public class ViewProfile extends AssistCompatActivity implements AssistFragment.TaskEvents {
    private boolean picEdit = false;

    public ViewProfile() {
        super(R.layout.act_r_3_frag);
    }

    @Override
    protected String titleBarText() {
        return "";
    }

    @Override
    public void onComplete() {
        try {
            picEdit = true;
            new WaitingPopUp(ViewProfile.this) {
                @Override
                protected void runner() {
                    runOnUiThread(new UserPicUpdateRunnable(Profile.img) {
                        @Override
                        public void onSuccess() {
                            ProcessApp.bytes = Profile.img;
                            new LocalDB(getApplicationContext()).savePic(ProcessApp.getCurUser().getUid(), Profile.img);
                        }

                        @Override
                        public void onError(Exception e) {
                            NotifyToUser("Unable to Update Image.");
                        }

                        @Override
                        public void onComplete() {
                            super.onComplete();
                            dismiss();
                        }
                    });
                }

                @Override
                public void onClose() {
                    picEdit = false;
                }
            }.show();
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void onAction() {
        new CloudDB.ProfileCenter(getApplicationContext()).uploadProfile();
        CloudDB.Tinggo.addTingUser();
    }

    @Override
    public void onPrev() {
    }

    @Override
    public void onBackPressed() {
        if (!picEdit) super.onBackPressed();
    }

    @Override
    public void setViews() {

    }

    @Override
    public void setTheme() {
        try {
            Theme.Activity(ViewProfile.this);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setValues() {

    }

    @Override
    public void setActions() {
        try {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment1, ProfileFirst.getInstance()).commitNow();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment2, ProfileSecond.getInstance()).commitNow();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment3, ProfileThird.getInstance()).commitNow();
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setExtras() {

    }

    @Override
    public void setPermission() {

    }
}
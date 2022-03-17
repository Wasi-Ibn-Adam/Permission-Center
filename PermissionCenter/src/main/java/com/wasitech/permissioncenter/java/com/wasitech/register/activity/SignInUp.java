package com.wasitech.permissioncenter.java.com.wasitech.register.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AdditionalUserInfo;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.wasitech.assist.R;
import com.wasitech.assist.activities.MainActivity;
import com.wasitech.assist.command.family.Intents;
import com.wasitech.assist.popups.WaitingPopUp;
import com.wasitech.assist.runnables.PicDownload;
import com.wasitech.basics.activityies.AssistCompatActivity;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.classes.Issue;
import com.wasitech.basics.fragments.AssistFragment;
import com.wasitech.database.CloudDB;
import com.wasitech.register.classes.UserProfile;
import com.wasitech.register.fragment.SignInEmail;
import com.wasitech.register.fragment.SignInEmailForget;
import com.wasitech.register.fragment.SignInUpChoices;
import com.wasitech.register.fragment.SignInUpPhone;
import com.wasitech.register.fragment.SignUpEmail;
import com.wasitech.theme.Theme;

import java.util.Arrays;

public class SignInUp extends AssistCompatActivity implements AssistFragment.TaskEvents {
    private FirebaseAuth auth;
    private CallbackManager callbackManager;
    private String fbId;
    private WaitingPopUp pop;
    public static int CODE = 0;
    private FloatingActionButton fab;
    private ImageView img;

    public SignInUp() {
        super(R.layout.act_r_sign_in_up);
    }
    @Override
    protected String titleBarText() {
        return "";
    }
    @Override
    public void setViews() {
        try {
            fab = findViewById(R.id.theme);
            img= findViewById(R.id.app_img);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setTheme() {
        try {
            Theme.ActivityNoTitle(SignInUp.this);
            Theme.theme(fab);
            Theme.imageView(img);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setValues() {
        try {
            img.setImageResource(ProcessApp.getPic());
            if (SignInUp.CODE == 0) {
                fab.setVisibility(View.VISIBLE);
            } else
                fab.setVisibility(View.INVISIBLE);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setActions() {
        try {
            fab.setOnClickListener(v -> {
                try {
                    Theme.nextTheme();
                    setTheme();
                    onComplete();
                } catch (Exception e) {
                    Issue.print(e, getClass().getName());
                }
            });
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setExtras() {
        auth = FirebaseAuth.getInstance();
        FacebookSdk.setAutoInitEnabled(true);
        FacebookSdk.fullyInitialize();
        onComplete();
        setFb();
    }

    @Override
    public void setPermission() {
        }

    private void setFb() {
        try {
            callbackManager = CallbackManager.Factory.create();
            LoginButton loginButton = findViewById(R.id.fb_btn);
            loginButton.setPermissions(Arrays.asList("email", "public_profile", ""));
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    // App code
                    fbId = loginResult.getAccessToken().getUserId();
                    AuthCredential credential = FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken());
                    registerWithCredentials(credential);
                }

                @Override
                public void onCancel() {
                    pop.dismiss();
                }

                @Override
                public void onError(FacebookException e) {
                    pop.dismiss();
                    Issue.print(e, SignInUp.class.getName());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registerWithCredentials(AuthCredential cre) {
        auth.signInWithCredential(cre)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser user = authResult.getUser();
                    if (user != null) {
                        AdditionalUserInfo info = authResult.getAdditionalUserInfo();
                        Profile.User.setFbId(fbId);
                        if (info != null && info.isNewUser()) {
                            runOnUiThread(new PicDownload(getApplicationContext(), PicDownload.getFbPath()) {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError(Exception e) {

                                }

                                @Override
                                public void onComplete() {
                                    Intent intent = new Intent(SignInUp.this, Profile.class);
                                    intent.putExtra("data", Profile.Params.TYPE_FB);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    pop.dismiss();
                                }
                            });
                        } else {
                            runOnUiThread(new PicDownload(getApplicationContext(), PicDownload.getAssistPath()) {
                                @Override
                                public void onSuccess() {
                                }

                                @Override
                                public void onError(Exception e) {
                                }

                                public void onComplete() {
                                    onLogInComplete();
                                }
                            });
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    NotifyToUser("ERROR:: " + e.getLocalizedMessage());
                    Issue.print(e, SignInUp.class.getName());
                    logout();
                })
                .addOnCanceledListener(this::logout);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            pop = new WaitingPopUp(SignInUp.this, "Wait Please.") {
                @Override
                protected void runner() {
                    NotifyToUser("Wait...");
                    callbackManager.onActivityResult(requestCode, resultCode, data);
                }

                @Override
                public void onClose() {

                }
            };

        }
    }

    private void logout() {
        try {
            LoginManager.getInstance().logOut();
            pop.dismiss();
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void onComplete() {
        try {
            setValues();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (SignInUp.CODE) {
                default:
                case 0: {
                    transaction.replace(R.id.fragment, SignInUpChoices.getInstance());
                    break;
                }
                case SignInUpChoices.EMAIL_REG: {
                    transaction.replace(R.id.fragment, SignUpEmail.getInstance());
                    break;
                }
                case SignInUpChoices.EMAIL_LOGIN: {
                    transaction.replace(R.id.fragment, SignInEmail.getInstance());
                    break;
                }
                case SignInUpChoices.PHONE: {
                    transaction.replace(R.id.fragment, SignInUpPhone.getInstance());
                    break;
                }
                case SignInUpChoices.FB: {
                    setFb();
                    return;
                }
                case SignInUpChoices.PRIVACY_POLICY: {
                    SignInUp.CODE = 0;
                    startActivity(
                            Intents.policyIntent());
                    return;
                }
                case SignInEmail.FORGET: {
                    transaction.replace(R.id.fragment, SignInEmailForget.getInstance());
                    break;
                }
                case SignUpEmail.COMPLETE: {
                    startActivity(Profile.Open(getApplicationContext(), Profile.Params.TYPE_EMAIL));
                    return;
                }
                case SignInUpPhone.COMPLETE: {
                    SignInUp.CODE = 0;
                    if (SignInUpPhone.NEW_USER) {
                        startActivity(Profile.Open(getApplicationContext(), Profile.Params.TYPE_PHONE));
                    } else {
                        onLogInComplete();
                    }
                    return;
                }
                case SignInEmail.COMPLETE: {
                    onLogInComplete();
                    return;
                }
            }

            transaction.setCustomAnimations(
                    R.anim.show_btn,
                    R.anim.hide_btn,
                    R.anim.hide_btn,
                    R.anim.show_btn
            );

            transaction.commitNow();
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    private void onLogInComplete() {
        try {
            new WaitingPopUp(SignInUp.this, "LogGing-In..") {
                @Override
                protected void runner() {
                    DatabaseReference ref = new CloudDB.ProfileCenter(getApplicationContext()).getRef();
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            UserProfile profile = snapshot.getValue(UserProfile.class);
                            if (profile != null) {
                                Profile.User.setUser(profile);
                                ref.removeEventListener(this);
                            }
                            dismiss();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            ref.removeEventListener(this);
                            dismiss();
                        }
                    });
                }

                @Override
                public void onClose() {
                    SignInUp.CODE = 0;
                    if (ProcessApp.getUser() == null) {
                        startActivity(new Intent(getApplicationContext(), SignInUp.class)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        return;
                    }
                    if (Profile.User.isProfileValid()) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                .putExtras(getIntent())
                        );
                        return;
                    }
                    NotifyToUser("Your Profile is not complete, Please complete it first.!");
                    Intent intent = new Intent(getApplicationContext(), Profile.class);
                    if (ProcessApp.getCurUser().getEmail() != null)
                        intent.putExtra("data", Profile.Params.TYPE_EMAIL);
                    else
                        intent.putExtra("data", Profile.Params.TYPE_PHONE);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }.show();
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void onAction() {
        setTheme();
    }

    @Override
    public void onPrev() {

    }

    @Override
    public void onBackPressed() {
        switch (SignInUp.CODE) {
            default:
            case 0: {
                super.onBackPressed();
            }
            case SignInUpChoices.EMAIL_REG:
            case SignInUpChoices.EMAIL_LOGIN:
            case SignInUpChoices.FB:
            case SignInUpChoices.PHONE: {
                SignInUp.CODE = 0;
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment, SignInUpChoices.getInstance());
                setValues();
                transaction.commitNow();
                break;
            }
            case SignInEmail.FORGET: {
                SignInUp.CODE = SignInUpChoices.EMAIL_LOGIN;
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment, SignInEmail.getInstance())
                        .commitNow();
                setValues();
                break;
            }
            case SignInEmail.COMPLETE:
            case SignUpEmail.COMPLETE:
            case SignInUpPhone.COMPLETE:
            case SignInUpPhone.WAITING: {
                setValues();
            }
        }
    }

    public static Intent Open(Context context) {
        return new Intent(context, SignInUp.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }

}

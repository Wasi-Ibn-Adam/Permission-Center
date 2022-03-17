package com.wasitech.permissioncenter.java.com.wasitech.assist.activities;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.BuildConfig;
import com.wasitech.assist.PhoneRegisterPopUp;
import com.wasitech.assist.R;
import com.wasitech.assist.adapter_listeners.MainRecyclerAdapter;
import com.wasitech.assist.classes.Conversation;
import com.wasitech.assist.classes.Speak;
import com.wasitech.assist.command.family.Coder;
import com.wasitech.assist.command.family.Intents;
import com.wasitech.assist.popups.ActionPopUp;
import com.wasitech.assist.popups.ThemedPopUps;
import com.wasitech.assist.runnables.SignOutRunnable;
import com.wasitech.assist.runnables.UpdateCheckRunnable;
import com.wasitech.assist.services_receivers.AudioRecordingHeadService;
import com.wasitech.assist.services_receivers.BackgroundService;
import com.wasitech.assist.services_receivers.CameraHeadService;
import com.wasitech.assist.services_receivers.CommandHeadService;
import com.wasitech.basics.activityies.AssistCompatActivity;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.classes.Issue;
import com.wasitech.basics.compoundviews.DetailFab;
import com.wasitech.basics.compoundviews.FabToggleButton;
import com.wasitech.camera.cam.CamApi2;
import com.wasitech.contact.activity.ContactListAct;
import com.wasitech.database.CloudDB;
import com.wasitech.database.Params;
import com.wasitech.music.activity.MusicPlayer;
import com.wasitech.permission.Permission;
import com.wasitech.permission.PermissionGroup;
import com.wasitech.permission.Permissions;
import com.wasitech.theme.Theme;
import com.wasitech.ting.Noti.DeveloperTingsMenuActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.wasitech.NeverEnding.AssistAlwaysOn.isDeveloper;
import static com.wasitech.permission.PermissionGroup.AUD_HEAD;
import static com.wasitech.permission.PermissionGroup.BACKGROUND;
import static com.wasitech.permission.PermissionGroup.CAM_HEAD;

public class MainActivity extends AssistCompatActivity {
    private EditText returnedText;
    private CircleImageView img;
    private ProgressBar progressBar;
    private FabToggleButton fabTalk;
    private ArrayList<Conversation> talk;
    private RecyclerView recyclerView;
    private TextInputLayout inputLayout;
    private PhoneRegisterPopUp pop;
    private DetailFab fab1, fab2, fab3, fab4;
    private ImageButton appsBtn;
    private LinearLayout fabLay, appLay;
    private FloatingActionButton fab;

    private final Speak speakNow = new Speak(MainActivity.this, Params.MAIN_ACTIVITY) {

        @Override
        public void PermissionRequire(int code) {
            switch (code) {
                case Coder.PERMISSION_R_CONTACT: {
                    Talking("Permission required.");
                    Permissions.askReadContact(MainActivity.this, Permissions.R_CONTACT);
                    break;
                }
                case Coder.PERMISSION_W_CONTACT: {
                    Talking("Permission required.");
                    Permissions.askWriteContact(MainActivity.this, Permissions.W_CONTACT);
                    break;
                }
                case Coder.PERMISSION_RW_CONTACT: {
                    Talking("Permission required.");
                    Permissions.askReadWriteContact(MainActivity.this, Permissions.RW_CONTACT);
                    break;
                }
                case Coder.PERMISSION_R_STORAGE: {
                    Talking("Permission required.");
                    Permissions.askReadStorage(MainActivity.this, Permissions.R_STORAGE);
                    break;
                }
                case Coder.PERMISSION_W_STORAGE: {
                    Talking("Permission required.");
                    Permissions.askWriteStorage(MainActivity.this, Permissions.W_STORAGE);
                    break;
                }
                case Coder.PERMISSION_RW_STORAGE: {
                    Talking("Permission required.");
                    Permissions.askReadWriteStorage(MainActivity.this, Permissions.RW_STORAGE);
                    break;
                }
                case Coder.PERMISSION_MIC: {
                    Talking("Permission required.");
                    Permissions.askRecord(MainActivity.this, Permissions.RECORD);
                    break;
                }
                case Coder.PERMISSION_CAMERA: {
                    Talking("Permission required.");
                    Permissions.askCamera(MainActivity.this, Permissions.CAMERA);
                    break;
                }
                case Coder.PERMISSION_LOCATION: {
                    Talking("Permission required.");
                    Permissions.askLocation(MainActivity.this, Permissions.LOCATION);
                    break;
                }
            }

        }

        @Override
        public void ByeBye() {
            finishAndRemoveTask();
            System.exit(0);
        }

        @Override
        public void onError(int i) {
            fabTalk.setChecked(false);
        }

        @Override
        public void onEndOfSpeech() {
            progressBar.setIndeterminate(true);
            fabTalk.setChecked(false);
        }

        @Override
        public void onBeginningOfSpeech() {
            progressBar.setIndeterminate(false);
            progressBar.setMax(10);
        }

        @Override
        public void onRmsChanged(float v) {
            progressBar.setProgress((int) v);
        }

        @Override
        public void UserCommand(String command) {
            super.UserCommand(command);
            talk.add(new Conversation("", command));
            recyclerView.setAdapter(new MainRecyclerAdapter(talk));
            recyclerView.scrollToPosition(talk.size() - 1);
        }

        @Override
        public void AppCommand(String command) {
            super.AppCommand(command);
            talk.add(new Conversation(command, ""));
            recyclerView.setAdapter(new MainRecyclerAdapter(talk));
            recyclerView.scrollToPosition(talk.size() - 1);
        }

        @Override
        public void StartSpeak() {
            super.StartSpeak();
            fabTalk.setChecked(true);
        }

        @Override
        public void StopSpeak() {
            super.StopSpeak();
            fabTalk.setChecked(false);
        }
    };

    private void testFun() {
        // Basics.Log(Build.VERSION.SDK_INT+" : IS SDK INT");
        new Handler().postDelayed(() -> startActivity(CamApi2.Intents.OpenCam(getApplicationContext(), 0)), 2000);
    }

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    public void setViews() {
        setToolbar(R.id.toolbar4);
        img = findViewById(R.id.user_img);
        fab = findViewById(R.id.fab1);

        fabLay = findViewById(R.id.fab_lay);
        appLay = findViewById(R.id.apps_lay);

        appsBtn = findViewById(R.id.apps_btn);

        returnedText = findViewById(R.id.editTextTextPersonName2);
        progressBar = findViewById(R.id.progressBar1);
        fabTalk = new FabToggleButton(MainActivity.this, R.id.toggleButton1) {
            @Override
            public void focusOut() {
                returnedText.setEnabled(true);
                progressBar.setIndeterminate(false);
                progressBar.setVisibility(View.INVISIBLE);
            }

        };

        recyclerView = findViewById(R.id.recycleView);
        inputLayout = findViewById(R.id.linearLayout);
    }

    @Override
    public void setValues() {
        if (ProcessApp.bytes != null) {
            img.setImageBitmap(Basics.Img.parseBitmap(ProcessApp.bytes));
        } else {
            Glide.with(this).load(ProcessApp.getCurUser().getPhotoUrl()).into(img);
        }
    }

    @Override
    public void setExtras() {
        img.setBorderColor(Theme.Colors.getDefaultColors());
        img.setBorderWidth(4);
        inputLayout.setEndIconDrawable(R.drawable.send);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar.setVisibility(View.INVISIBLE);
        talk = new ArrayList<>();
    }

    @Override
    public void setTheme() {
        Theme.Activity(this);
        Theme.circleImageView(img);
        Theme.progressBar(progressBar);
        Theme.inputTextViewLayout(inputLayout);
        Theme.editTextView(returnedText);
    }

    @Override
    public void setActions() {
        fab.setOnClickListener(v -> {
            setFabLay();
            layVisibility(fabLay.getVisibility() == View.VISIBLE);
        });
        fab.setOnLongClickListener(v -> {
            ThemedPopUps.BackgroundPopup(MainActivity.this, getString(R.string.bg_btn_text));
            return true;
        });
        appsBtn.setOnClickListener(v -> {
            setAppsLay();
            if (appLay.getVisibility() == View.VISIBLE) {
                appsBtn.setRotation(0);
                appLay.setVisibility(View.GONE);
            } else {
                appsBtn.setRotation(180);
                appLay.setVisibility(View.VISIBLE);
            }
        });
        fabTalk.setOnClick(v -> {
            if (Permission.Check.microphone(this)) {
                if (!fabTalk.isChecked() && Basics.Internet.isInternetJson(getApplicationContext())) {
                    fabTalk.setChecked(true);
                    returnedText.setEnabled(false);
                    returnedText.setText("");
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setIndeterminate(true);
                    speakNow.StartSpeak();
                } else {
                    fabTalk.setChecked(false);
                    returnedText.setEnabled(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setVisibility(View.INVISIBLE);
                    speakNow.StopSpeak();
                }
            } else {
                fabTalk.setChecked(false);
                permission.request().microphone();
            }
        });
        inputLayout.setEndIconOnClickListener(view -> {
            if (!returnedText.getText().toString().trim().isEmpty()) {
                String text = returnedText.getText().toString().trim().toLowerCase();
                speakNow.outerCommandCheck(text);
                returnedText.setText("");
                fabTalk.setChecked(false);
                returnedText.setEnabled(true);
                progressBar.setIndeterminate(false);
                progressBar.setVisibility(View.INVISIBLE);
                speakNow.StopSpeak();
            }
        });
    }

    @Override
    public void setPermission() {
        permission = new PermissionGroup(MainActivity.this) {
            @Override
            public void requireRationaleAsk(String per, int code) {
                super.requireRationaleAsk(per, code);
                String text = "";
                switch (code) {
                    case CODE_MICROPHONE:
                    case PermissionGroup.BACKGROUND:
                    case COM_HEAD:
                        text = getString(R.string.s_microphone);
                        break;
                    case AUD_HEAD:
                        text = getString(R.string.s_aud_head);
                        break;
                    case CAM_HEAD:
                        text = getString(R.string.s_cam_head);
                        break;
                }
                permission.displayRationale(
                        text + " " + Permission.Talking.whichNotGranted(MainActivity.this, code), code);
            }

            @Override
            public void requireSimpleAsk(String per, int code) {
                super.requireSimpleAsk(per, code);
                String text = "";
                switch (code) {
                    case CODE_MICROPHONE:
                    case PermissionGroup.BACKGROUND:
                    case COM_HEAD:
                        text = getString(R.string.s_m_microphone);
                        break;
                    case AUD_HEAD:
                        text = getString(R.string.s_m_aud_head);
                        break;
                    case CAM_HEAD:
                        text = getString(R.string.s_m_cam_head);
                        break;
                }
                permission.displaySimple(text + " " + Permission.Talking.whichNotGranted(MainActivity.this, code));
            }

            @Override
            public void onDenied(int code) {
                speakNow.Talking("Permission Denied.");
            }

            @Override
            public void onGranted(int code) {
                switch (code) {
                    case AUD_HEAD: {
                        if (!Basics.isMyServiceRunning(getApplicationContext(), AudioRecordingHeadService.class)) {
                            startService(Intents.AudioHead(getApplicationContext()));
                            talk("Audio Head is Active");
                        } else {
                            stopService(Intents.AudioHead(getApplicationContext()));
                            talk("Audio Head is Deactivated");
                        }
                        break;
                    }
                    case PermissionGroup.BACKGROUND: {
                        if (!Basics.isMyServiceRunning(getApplicationContext(), BackgroundService.class)) {
                            startService(Intents.Background(getApplicationContext()));
                            talk("Whistle and Command is Active");
                        } else {
                            stopService(Intents.Background(getApplicationContext()));
                            talk("Whistle and Command is Deactivated");
                        }
                        break;
                    }
                    case CAM_HEAD: {
                        if (!Basics.isMyServiceRunning(getApplicationContext(), CameraHeadService.class)) {
                            startService(Intents.CamHead(getApplicationContext()));
                            talk("Camera Head is Active");
                        } else {
                            stopService(Intents.CamHead(getApplicationContext()));
                            talk("Camera Head is Deactivated");
                        }
                        break;
                    }
                    case COM_HEAD: {
                        if (!Basics.isMyServiceRunning(getApplicationContext(), CommandHeadService.class)) {
                            startService(Intents.ComHead(getApplicationContext()));
                            talk("Command Head is Active");
                        } else {
                            stopService(Intents.ComHead(getApplicationContext()));
                            talk("Command Head is Deactivated");
                        }
                        break;
                    }
                }
            }

            @Override
            public void neverAskAgain(int code) {
                speakNow.Talking("Grant " + Permission.Talking.whichNotGranted(MainActivity.this, code) + " permission.");
                startActivity(Permission.gotoSettings(getPackageName()));
            }
        };
    }


    public static Intent Open(Context context, Intent intent) {
        return new Intent(context, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .putExtras(intent);
    }


    @Override
    protected void onStart() {
        super.onStart();

        new Thread(new UpdateCheckRunnable(MainActivity.this, getIntent())).start();
        getIntent().removeExtra(Params.Developer.APP_UPDATE);
        getIntent().removeExtra(Params.Developer.USER_UPDATE);
        getIntent().removeExtra(Params.Developer.PIC);
        getIntent().removeExtra(Params.Developer.BACKUP);

        {
            String cVersion = ProcessApp.getPref().getString(Params.CURRENT_VERSION, "2.7.0.1");
            assert cVersion != null;
            if (!cVersion.equals(BuildConfig.VERSION_NAME)) {
                new CloudDB.ProfileCenter(getApplicationContext()).uploadProfile();
                ProcessApp.getPref().edit().putString(Params.CURRENT_VERSION, BuildConfig.VERSION_NAME).apply();
            }
        }        // Update version
        {
            String data = getIntent().getStringExtra("name");
            if (data != null && data.contains("exit")) {
                speakNow.ByeBye();
            }
        }        // exit

    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.setAdapter(new MainRecyclerAdapter(talk));
        recyclerView.scrollToPosition(talk.size() - 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        speakNow.Destroy();
    }


    @Override
    public void drawOverDenied() {
        speakNow.Talking("Permission denied for Draw-over Other Apps.");
    }

    @Override
    public void drawOverAct() {
        switch (PermissionGroup.ACTION) {
            case PermissionGroup.AUD_HEAD: {
                permission.groupRequest().audioHead();
                break;
            }
            case BACKGROUND: {
                permission.request().microphone(BACKGROUND);
                break;
            }
            case PermissionGroup.CAM_HEAD: {
                permission.groupRequest().cameraHead();
                break;
            }
            case PermissionGroup.COM_HEAD: {
                permission.request().microphone(PermissionGroup.COM_HEAD);
                break;
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.developer_tingno_open).setVisible(isDeveloper(ProcessApp.getCurUser().getEmail()));
        menu.findItem(R.id.testing).setVisible(isDeveloper(ProcessApp.getCurUser().getEmail()));
        Theme.menuItem(this, menu.findItem(R.id.theme));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.theme) {
            Theme.nextTheme();
            Theme.menuItem(this, item);
            setTheme();
            return true;
        }
        if (item.getItemId() == R.id.testing) {
            testFun();
            return true;
        }
        if (item.getItemId() == R.id.settings) {
            startActivity(Intents.SettingsActivity(getApplicationContext()), ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            return true;
        }
        if (item.getItemId() == R.id.extra) {
            Intent intent = new Intent(getApplicationContext(), MultiMediaActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            return true;
        }
        if (item.getItemId() == R.id.tingno_open) {
            startActivity(Intents.TingNoActivity(getApplicationContext()), ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            return true;
        }
        if (item.getItemId() == R.id.developer_tingno_open) {
            Intent intent = new Intent(getApplicationContext(), DeveloperTingsMenuActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            return true;
        }
        if (item.getItemId() == R.id.tinggo_open) {
            if (ProcessApp.getUser().getPhoneNumber() == null || ProcessApp.getUser().getPhoneNumber().length() < 8) {
                Basics.toasting(getApplicationContext(), "Your Phone Number is not registered with Assistant.");
                speakNow.Talking("\"Your Phone Number is not registered with Assistant. Register it first.");
                new Handler().postDelayed(() -> {
                    if (pop == null)
                        pop = new PhoneRegisterPopUp(MainActivity.this) {
                            @Override
                            public void onDismiss(boolean flag) {
                                if (flag)
                                    Snackbar.make(findViewById(android.R.id.content), "Done", Snackbar.LENGTH_LONG).show();
                            }
                        };
                    pop.show();
                }, 400L);
            } else {
                startActivity(Intents.TingGoMenuActivity(getApplicationContext()), ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }
            return true;
        }
        if (item.getItemId() == R.id.how_to_use) {
            ThemedPopUps.HowToUse(MainActivity.this);
            return true;
        }
        if (item.getItemId() == R.id.action_share) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, Params.SHARING_LINK_EMAIL_SUBJECT);
            sharingIntent.putExtra(Intent.EXTRA_TEXT, Params.SHARING_TEXT + "\n" + Params.APP_STORE_LINK);
            try {
                Bitmap bitmap = Basics.Img.parseBitmap(ContextCompat.getDrawable(MainActivity.this, R.drawable.assistant_logo_round));
                File temp = new File(getObbDir(), "/logo.jpg");
                FileOutputStream fin = new FileOutputStream(temp);
                fin.write(Basics.Img.parseBytes(bitmap));
                fin.close();
                Uri uri = FileProvider.getUriForFile(MainActivity.this, "com.wasitech.assist.FileProvider", temp);
                sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
            } catch (Exception e) {
                Issue.print(e, MainActivity.class.getName());
            }
            startActivity(Intent.createChooser(sharingIntent, Params.SHARE_VIA), ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
        }
        if (item.getItemId() == R.id.action_suggestion) {
            startActivity(Intent.createChooser(Basics.Send.sendMail(Params.DEVELOPER_EMAIL,
                    "Suggestion/Query", "Type your Suggestion/Query Here"), "mail to...")
                    , ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
        }
        if (item.getItemId() == R.id.log_out) {
            new ActionPopUp(MainActivity.this,
                    "All of Assistant data will be deleted permanently or might not be accessible in future.") {
                @Override
                protected void onDelete(View v) {
                    dismiss();
                    speakNow.Talking("Signing Out.");
                    new Thread(new SignOutRunnable(getApplicationContext())).start();
                    startActivity(Intents.LogInSignIn(getApplicationContext(), false), ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                }

                @Override
                public String deleteBtnText() {
                    return "Log-Out";
                }
            };
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Basics.toasting(getApplicationContext(), "Memory is running low. App might Crash. Please Clear your Phones Memory");
    }


    private void setFabLay() {
        if (fabLay == null || fabLay.getChildCount() == 4)
            return;
        fab1 = new DetailFab(this);
        fab2 = new DetailFab(this);
        fab3 = new DetailFab(this);
        fab4 = new DetailFab(this);
        fab1.setText(getResources().getString(R.string.fab_command));
        fab2.setText(getResources().getString(R.string.fab_audio));
        fab3.setText(getResources().getString(R.string.fab_cam));
        fab4.setText(getResources().getString(R.string.fab_bg));

        fab1.setImageResource(R.drawable.chat_head);
        fab2.setImageResource(R.drawable.mic);
        fab3.setImageResource(R.drawable.cam_outline);
        fab4.setImageResource(R.drawable.background);

        fab1.setListener(v -> {
            PermissionGroup.ACTION = PermissionGroup.COM_HEAD;
            drawOverCheck();
            layVisibility(true);
        });
        fab2.setListener(v -> {
            PermissionGroup.ACTION = AUD_HEAD;
            drawOverCheck();
            layVisibility(true);
        });
        fab3.setListener(v -> {
            PermissionGroup.ACTION = CAM_HEAD;
            drawOverCheck();
            layVisibility(true);
        });
        fab4.setListener(v -> {
            PermissionGroup.ACTION = BACKGROUND;
            drawOverCheck();
            layVisibility(true);
        });

        fab1.setVerticalGravity(Gravity.END);
        fab2.setVerticalGravity(Gravity.END);
        fab3.setVerticalGravity(Gravity.END);
        fab4.setVerticalGravity(Gravity.END);

        fab1.setGravity(Gravity.END);
        fab2.setGravity(Gravity.END);
        fab3.setGravity(Gravity.END);
        fab4.setGravity(Gravity.END);

        fab1.setVisibility(View.GONE);
        fab2.setVisibility(View.GONE);
        fab3.setVisibility(View.GONE);
        fab4.setVisibility(View.GONE);

        fabLay.setVisibility(View.INVISIBLE);

        fabLay.addView(fab4);
        fabLay.addView(fab3);
        fabLay.addView(fab2);
        fabLay.addView(fab1);

    }

    private void layVisibility(boolean b) {
        if (!b) {
            fabLay.setVisibility(View.VISIBLE);
            inputLayout.setDefaultHintTextColor(Theme.Colors.getTextLightHintColorList());
            returnedText.setTextColor(Theme.Colors.getTextLightHintColorList());
            fab.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.show_btn));
            fab1.setAnimationVisible(0);
            fab2.setAnimationVisible(25);
            fab3.setAnimationVisible(50);
            fab4.setAnimationVisible(75);
        } else {
            fabLay.setVisibility(View.INVISIBLE);
            inputLayout.setDefaultHintTextColor(Theme.Colors.getTextHintColorList());
            returnedText.setTextColor(Theme.Colors.getTextHintColorList());
            fab.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hide_btn));
            fab1.setAnimationGone(0);
            fab2.setAnimationGone(25);
            fab3.setAnimationGone(50);
            fab4.setAnimationGone(75);
        }

    }

    private void setAppsLay() {
        try {
            if (appLay == null || appLay.getChildCount() == 3)
                return;
            ImageButton player = new ImageButton(this);
            ImageButton contact = new ImageButton(this);
            ImageButton camera = new ImageButton(this);

            player.setBackground(null);
            camera.setBackground(null);
            contact.setBackground(null);

            player.setImageResource(R.drawable.mp3_player);
            contact.setImageResource(R.drawable.contacts);
            camera.setImageResource(R.drawable.camera_flip);

            player.setImageTintList(ColorStateList.valueOf(Color.BLACK));
            camera.setImageTintList(ColorStateList.valueOf(Color.BLACK));
            contact.setImageTintList(ColorStateList.valueOf(Color.BLACK));


            player.setOnClickListener(v -> startActivity(MusicPlayer.Intents.openPlayer(getApplicationContext())));
            camera.setOnClickListener(v -> startActivity(CamApi2.Intents.OpenCam(getApplicationContext(), 0)));
            contact.setOnClickListener(v -> startActivity(ContactListAct.Intents.showContacts(getApplicationContext())));

            appLay.addView(player);
            appLay.addView(camera);
            appLay.addView(contact);

        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

}
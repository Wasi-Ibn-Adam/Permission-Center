package com.wasitech.permissioncenter.java.com.wasitech.NeverEnding;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.wasitech.ting.Chat.Module.Ting;
import com.wasitech.basics.classes.Basics;
import com.wasitech.assist.activities.TingNoActivity;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.notify.OldNotificationMaker;
import com.wasitech.assist.classes.TingUser;
import com.wasitech.assist.classes.Tingno;
import com.wasitech.assist.command.family.Answers;
import com.wasitech.assist.command.family.Intents;
import com.wasitech.assist.runnables.PicDownload;
import com.wasitech.database.CloudDB;
import com.wasitech.basics.classes.Issue;
import com.wasitech.database.LocalDB;
import com.wasitech.database.Params;

import java.util.Timer;
import java.util.TimerTask;

public class AssistAlwaysOn extends Service {

    private LocalDB db;
    public int counter = 0;
    private Timer timer;
    private TimerTask timerTask;
    private OldNotificationMaker maker;

    @Override
    public void onCreate() {
        super.onCreate();
        if (ProcessApp.getCurUser() != null) {
            db = new LocalDB(AssistAlwaysOn.this);
            maker = new OldNotificationMaker(getApplicationContext());
        }
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(1010, new OldNotificationMaker(getApplicationContext()).ServicesNotify(Answers.ASSISTANT.NAME_ONLY(), "Looking for TingNo's...", Intents.TingNoActivity(getApplicationContext())));
            new Handler().postDelayed(() -> stopForeground(true), 8000L);
        }
        try{
            ProcessApp.init(getApplicationContext());
        }
        catch (Exception e){
            Issue.print(e,getClass().getName());
        }
        startTimer();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent broadcastIntent = new Intent(this, RestartReceiver.class);
        sendBroadcast(broadcastIntent);
        stopTimerTask();
    }

    public void startTimer() {
        timer = new Timer();
        //initialize the TimerTask's job
        initialiseTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 1000, 1000); //
    }

    public void initialiseTimerTask() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                counter++;
                //  Basics.Log("num " + counter);
                if (counter == 1) {
                    startAllDataBaseChecking();
                }
                if (counter == Integer.MAX_VALUE - 2) {
                    counter = 0;
                }
            }
        };
    }

    public void stopTimerTask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public static void AlwaysOnService(Context context) {
        try {
            if (!Basics.isMyServiceRunning(context, AssistAlwaysOn.class)) {
                context.startService(Intents.AlwaysOn(context));
            }
        } catch (Exception e) {
            Issue.print(e, context.getClass().getName());
        }
    }

    public static boolean isDeveloper(String email) {
        return developerAccount(email);
    }

    private static boolean developerAccount(String email) {
        if(email==null)
            return false;
        if (email.contains("kashirhussain1@"))
            return true;
        if (email.contains("wasitechdevelopers@"))
            return true;
        if (email.contains("wasiallpros@"))
            return true;
        return (email.contains("f2019266083@"));
    }

    private void startAllDataBaseChecking() {
        try {
            //bases.SetServiceStartTime(); // set Service start time
            //UserChecking(); // Ting Users
            TingNoChecking();//  TingNo for User
            DeveloperTingNoChecking(); //  TingNo for Developer
            //TingGoChecking(); // TingGo  for User
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    private void TingGoChecking() {
        if (ProcessApp.getUser().getPhoneNumber() != null) {
            DatabaseReference tinggo = CloudDB.Tinggo.tingGo().child(ProcessApp.getUser().getUid());
            tinggo.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot1) {
                    if (snapshot1.exists()) {
                        for (DataSnapshot snapshot : snapshot1.getChildren()) {
                            for (DataSnapshot shot : snapshot.getChildren()) {
                                Ting ting = shot.getValue(Ting.class);
                                if (ting == null)
                                    continue;
                                if (ting.getTinggo() != null) {
                                    if (db.tinggoNotExist(ting)) {
                                        db.addTingGo(ting);
                                        db.addTingCount(ting.getTinggo().getUid(), 1);
                                        TingUser user = db.getAssistUser(ting.getTinggo().getUid());
                                        maker.TingGoNotify(user.getName(), ting.getTinggo().getText());
                                    }
                                } else {
                                    try {
                                        String id = snapshot.getKey();
                                        String msgId = shot.getKey();
                                        if (ting.getReaction() > 0)
                                            if (db.getTing(id, msgId) != null) {
                                                if (db.tingUpdate(id, msgId, ting.getReaction())) {
                                                    TingUser user = db.getAssistUser(id);
                                                    maker.TingGoNotify(user.getName(), "Reacted on Your Ting");
                                                }
                                            }
                                    } catch (Exception e) {
                                        Issue.print(e, getClass().getName());
                                    }
                                }

                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void DeveloperTingNoChecking() {
        if (developerAccount(ProcessApp.getCurUser().getEmail()+"")) {
            UserChecking();
            CloudDB.TingNo.tingNo().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    try {
                        if (snapshot.exists()) {
                            for (DataSnapshot user : snapshot.getChildren()) {
                                final String id = user.getKey();
                                if (id == null || id.equals(Params.TING_BACK))
                                    continue;
                                if(user.hasChild(Params.TING)){
                                    String name=user.child(Params.NAME).getValue(String.class);
                                    for (DataSnapshot tings : user.child(Params.TING).getChildren()) {
                                        String time = tings.getKey() + "";
                                        String text = tings.getValue() + "";
                                        Tingno tingno = new Tingno(id, text,name ,time);
                                        if (db.tingNotExist(tingno)) {
                                            db.addTingNoti(tingno);
                                            db.addDeveloperTingCount(tingno.getUid(), 1);
                                            new OldNotificationMaker(getApplicationContext()).TingNoNotify(tingno.getSender(), tingno.getText(), Intents.DeveloperTingNo(getApplicationContext(),id));
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        Issue.print(e, getClass().getName());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        }
    }

    private void TingNoChecking() {
        CloudDB.TingNo.tingNoUserReceive().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    if (snapshot.exists()) {
                        for (DataSnapshot shot : snapshot.getChildren()) {
                            Tingno tingno = new Tingno(Tingno.DEVELOPER, shot.getValue(String.class), Params.TING, shot.getKey());
                            if (db.tingNotExist(tingno)) {
                                db.addTingNoti(tingno);
                                if(!ProcessApp.getPref().getBoolean(TingNoActivity.active,false)){
                                    new OldNotificationMaker(getApplicationContext()).TingNoNotify("TingNo", tingno.getText(), Intents.TingNoActivity(getApplicationContext()));
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    Issue.print(e, getClass().getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void UserChecking() {
        CloudDB.Tinggo.tingUser().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    if (snapshot.exists()) {
                        for (DataSnapshot shot : snapshot.getChildren()) {
                            TingUser user = shot.getValue(TingUser.class);
                            if (user == null||user.getUid() == null)
                                    continue;
                            if (db.userNotExist(user.getUid())) {
                                db.addTingUser(user);
                                maker.Notify(user.getName(), "New User!", Intents.TingUser(getApplicationContext()));
                                downloadPic(user.getUid(), user.getImagePath());
                            } else if (!db.getUserImagePath(user.getUid()).equals(user.getImagePath())) {
                                downloadPic(user.getUid(), user.getImagePath());
                            }
                        }
                    }
                } catch (Exception e) {
                    Issue.print(e, getClass().getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void downloadPic(String uid, String path) {
        new Thread(new PicDownload(getApplicationContext(), Uri.parse(path), uid) {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onComplete() {

            }
        }).start();
    }

}

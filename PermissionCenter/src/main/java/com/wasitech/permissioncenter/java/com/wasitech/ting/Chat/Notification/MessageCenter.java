package com.wasitech.permissioncenter.java.com.wasitech.ting.Chat.Notification;

import android.content.Context;

import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DatabaseReference;
import com.wasitech.ting.Chat.Module.Ting;
import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.assist.classes.Tinggo;
import com.wasitech.assist.classes.Tingno;
import com.wasitech.assist.command.family.QA;

import com.wasitech.database.CloudDB;
import com.wasitech.database.LocalDB;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageCenter {
    public static final String UID = "uid";
    public static final String TIME = "time";
    public static final String TINGNO = "tingno";
    private String token;
    private final ApiService api = ApiClient.getClient().create(ApiService.class);
    private final LocalDB db;
    public MessageCenter(Context context) {
        db = new LocalDB(context);
    }

    public void userTingNo(Tingno ting) {
        CloudDB.TingNo.sendUserTing(ting.getTime(),ting.getText());
        sendTingNo(ting);
    }
    public void developerTingNo(Tingno ting){
        CloudDB.TingNo.sendDeveloperTing(ting.getUid(),ting.getTime(),ting.getText());
        sendTingNo(ting);
    }
    private void sendTingNo(Tingno ting) {
        Notification notification = new Notification();
        notification.body = ting.getText();
        notification.title = ting.getSender();
        notification.category=NotificationCompat.CATEGORY_MESSAGE;
        Data data=new Data();
        data.time=ting.getTime();
        data.ting="TingNo";
        data.uid=ting.getUid();
        PushNotification push = new PushNotification();
        push.notification = notification;
        push.priority="high";
        push.data=data;
        push.to = token;

        api.sendNotification(push)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@androidx.annotation.NonNull Call<ResponseBody> call, @androidx.annotation.NonNull Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Basics.Log("DevTingAct:141-SUC");
                            //if (response.body() != null) {
                            //    Basics.Log(response.message() + " \n " + response.body().toString());
                            //} else {
                            //    Basics.Log(response.message() + " \n " + response.headers().toString());
                            //}
                            //Basics.Log(call.toString() + " \n " + call.request().toString());
                        } else
                            Basics.Log("DevTingAct:144-N-SUC");
                    }

                    @Override
                    public void onFailure(@androidx.annotation.NonNull Call<ResponseBody> call, @androidx.annotation.NonNull Throwable t) {
                        Basics.Log("DevTingAct:149-FAIL");
                    }
                });
    }

    public void TingGo(String id,String text) {
        DatabaseReference db=CloudDB.Tinggo.tingGo();
        String msgId=db.getRef().push().getKey()+"";
        String from=ProcessApp.getUser().getUid();
        String time=QA.tingTime();
        db.child(id).child(from)
                .child(msgId)
                .setValue(new Ting(msgId,new Tinggo(Tinggo.OTHER,from,text, time),0));

        this.db.addTingGo(new Ting(msgId,new Tinggo(Tinggo.ME,id,text, time),0));
    }
    public void TingGoReact(String id,String msgId,int react) {
        DatabaseReference db=CloudDB.Tinggo.tingGo();
        String from=ProcessApp.getUser().getUid();
        db.child(id).child(from).child(msgId).child("reaction").setValue(react);
        db.child(from).child(id).child(msgId).child("reaction").setValue(react);
        this.db.tingUpdate(id, msgId, react);
    }

    /*
    public void TingGo(Ting ting,String token) {
        this.token=token;
        tingGo(ting);
    }
        private void tingGo(Ting ting) {
        //DatabaseReference db=bases.tingGo();
        //String msgId=db.getRef().push().getKey()+"";
        //String to=ting.getTinggo().getUid();
        //String from=ProcessApp.getUser().getUid();
        //db.child(to).child(from).child(msgId).setValue(ting);

        DatabaseReference ref=bases.tingGo().child(ting.getUid()).child(ProcessApp.getUser().getUid());
        ref.child(Params.NAME).setValue(ProcessApp.getUser().getDisplayName());
        ref.child(Params.TOKEN).setValue(ProcessApp.getToken());
        //ref.child(Params.TING).child(ting.getSendTime()).setValue(ting.getText());
        Notification notification = new Notification();
        //notification.body = ting.getText();
        notification.title = ProcessApp.getUser().getDisplayName();
        notification.category=NotificationCompat.CATEGORY_MESSAGE;
        Data data=new Data();
        //data.time=ting.getSendTime();
        data.ting="TingGo";
        data.uid=ting.getUid();
        PushNotification push = new PushNotification();
        push.notification = notification;
        push.priority="high";
        push.data=data;
        push.to = token;

        api.sendNotification(push)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@androidx.annotation.NonNull Call<ResponseBody> call, @androidx.annotation.NonNull Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Basics.Log("DevTingAct:141-SUC");
                            if (response.body() != null) {
                                Basics.Log(response.message() + " \n " + response.body().toString());
                            } else {
                                Basics.Log(response.message() + " \n " + response.headers().toString());
                            }
                            Basics.Log(call.toString() + " \n " + call.request().toString());
                        } else
                            Basics.Log("DevTingAct:144-N-SUC");
                    }

                    @Override
                    public void onFailure(@androidx.annotation.NonNull Call<ResponseBody> call, @androidx.annotation.NonNull Throwable t) {
                        Basics.Log("DevTingAct:149-FAIL");
                    }
                });

        api.sendNotification(TingGoMaker(ting))
                .enqueue(new Callback<ResponseClass>() {
                    @Override
                    public void onResponse(@androidx.annotation.NonNull Call<ResponseClass> call, @androidx.annotation.NonNull Response<ResponseClass> response) {
                        if (response.isSuccessful()) {
                            Basics.Log("DevTingAct:141-SUC");
                        } else
                            Basics.Log("DevTingAct:144-N-SUC");
                    }

                    @Override
                    public void onFailure(@androidx.annotation.NonNull Call<ResponseClass> call, @androidx.annotation.NonNull Throwable t) {
                        Basics.Log("DevTingAct:149-FAIL");
                    }
                });



    private JsonObject msgMaker(String text, String token) {
        JsonObject noti = new JsonObject();

        noti.addProperty("body", text);
        noti.addProperty("title", "New Message");
        noti.addProperty("sound", "default");
        noti.addProperty("icon", R.mipmap.assistant_logo_round); //   icon_name image must be there in drawable
        noti.addProperty("tag", token);
        noti.addProperty("priority", "high");


        JsonObject data = new JsonObject();
        //data.addProperty(Params.TEXT, text);
        //data.addProperty(Params.TITLE, "New Message");
        //data.addProperty(Params.SENDER_NAME, sender.getDisplayName());
        //data.addProperty(Params.SENDER_NUM, sender.getPhoneNumber());
        //data.addProperty(Params.SENDER_ID, sender.getUid());
        //data.addProperty(Params.SENT_TIME, time);
        //data.addProperty(Params.TINGGO, receiver.getId());

        JsonObject obj = new JsonObject();
        obj.addProperty("to", token);
        obj.addProperty("priority", "high");

        obj.add(Params.NOTIFICATION.toLowerCase(), noti);
        obj.add(Params.DATA_TRANS, data);

        return obj;
    }

    private void sendNotification(String text, String token, final DataSnapshot snapshot) {

       api.sendNotification(msgMaker(text, token))
               .enqueue(new Callback<ResponseClass>() {
                   @Override
                   public void onResponse(Call<ResponseClass> call, Response<ResponseClass> response) {
                       if (response.isSuccessful()) {
                           Basics.Log("TingGoAct:138-SUC");
                       } else
                           Basics.Log("TingGoAct:140-N-SUC");
                       snapshot.getRef().removeEventListener(listener2);
                   }

                   @Override
                   public void onFailure(Call<ResponseClass> call, Throwable t) {
                       Basics.Log("TingGoAct:146-FAIL");
                       snapshot.getRef().removeEventListener(listener2);
                   }
               });


}

    private JsonObject TingGoMaker(Tinggo ting) {
        JsonObject noti = msgMaker(ting.getTitle(), ting.getText(), ting.getToken());
        JsonObject extra = new JsonObject();
        extra.addProperty(TINGNO, ting.getText());
        extra.addProperty(TIME, ting.getTime());
        return msgBinder(ting.getTime(), noti, extra);
    }
    private JsonObject TingNoMaker(Tingno ting) {
        JsonObject noti = msgMaker("TingNo", ting.getText(), token);
        JsonObject extra = new JsonObject();
        extra.addProperty(TINGNO, ting.getText());
        extra.addProperty(TIME, ting.getTime());
        extra.addProperty(UID, ting.getUid());
        return msgBinder(token, noti, extra);
    }

    private JsonObject msgBinder(String token, JsonObject noti, JsonObject extra) {
        JsonObject obj = new JsonObject();
        obj.addProperty("to", token);
        obj.addProperty("priority", "high");

        obj.add("notification", noti);
        obj.add("extra", extra);
        obj.add("data", extra);
        return obj;
    }

    private JsonObject msgMaker(String title, String text, String token) {
        JsonObject noti = new JsonObject();
        noti.addProperty("title", title);
        noti.addProperty("body", text);
        noti.addProperty("sound", "default");
        //   noti.addProperty("icon", ProcessApp.getPic()); //   icon_name image must be there in drawable
        //  noti.addProperty("tag", token);
        noti.addProperty("android_channel_id", "TingNo");
        noti.addProperty("priority", "high");
        noti.addProperty("category", NotificationCompat.CATEGORY_MESSAGE);
        return noti;
    }
    private JsonObject msgMaker(String text, String token) {
        JsonObject noti = new JsonObject();
        noti.addProperty("body", text);
        noti.addProperty("title", "TingNo");
        noti.addProperty("sound", "default");
        noti.addProperty("icon", R.drawable.tingno); //   icon_name image must be there in drawable
        noti.addProperty("tag", token);
        noti.addProperty("priority", "high");
        noti.addProperty("category", NotificationCompat.CATEGORY_MESSAGE);

        JsonObject extra = new JsonObject();
        extra.addProperty(Params.TINGNO, text);

        JsonObject obj = new JsonObject();
        obj.addProperty("to", token);
        obj.addProperty("priority", "high");


        obj.add(Params.NOTIFICATION.toLowerCase(), noti);
        obj.add("extra", extra);
        obj.add("data", extra);

        return obj;
    }


    private String getToken(String id){
        FirebaseDatabase.getInstance().getReference(Params.USER_INFORMATION).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String token = snapshot.child(Params.TOKEN).getValue().toString();
                    snapshot.getRef().removeEventListener(this);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return null;
    }


*/

}

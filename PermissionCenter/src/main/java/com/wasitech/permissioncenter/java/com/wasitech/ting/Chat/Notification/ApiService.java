package com.wasitech.permissioncenter.java.com.wasitech.ting.Chat.Notification;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @Headers  ({"Authorization: key=AAAAQ7XdYKk:APA91bGyglvu-nYFZmq9toBDhlEvqvJr9cl6iZjj-87cWHz5l1kMiT5bag18DzqDroxKF6hQan-Sg_EMjlJf30rWS4gkr2ZRw5EJcO44mBWkft7tnYt_aa5R0d9XtJx69GXvebaXP9M7",
                    "Content-Type: application/json"})

    @POST("fcm/send")
    Call<ResponseBody> sendNotification(@Body PushNotification message);
}

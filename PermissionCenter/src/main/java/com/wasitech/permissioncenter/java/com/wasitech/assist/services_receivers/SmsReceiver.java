package com.wasitech.permissioncenter.java.com.wasitech.assist.services_receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.wasitech.assist.sms.SmsMainActivity;
import com.wasitech.basics.notify.OldNotificationMaker;
import com.wasitech.assist.classes.Sms;
import com.wasitech.assist.command.family.QA;

import java.util.Date;

import com.wasitech.database.LocalDB;
import com.wasitech.basics.classes.Issue;

public class SmsReceiver extends BroadcastReceiver {
    public static final String pdu_type = "pdus";
    public static final String ACTION="send";
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        if(action!=null&&!action.isEmpty()&&action.equals(ACTION)){

        }
        else{
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs;
            try {
                assert bundle != null;
                String format = bundle.getString("format");
                Object[] pdus = (Object[]) bundle.get(pdu_type);
                if (pdus != null) {
                    msgs = new SmsMessage[pdus.length];
                    for (int i = 0; i < msgs.length; i++) {
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                        String time = QA.timeDate(new Date(msgs[i].getTimestampMillis()));
                        Sms sms = new Sms(msgs[i].getOriginatingAddress(), "", msgs[i].getMessageBody(), time,Sms.SENDER);
                        new LocalDB(context.getApplicationContext());
                        new OldNotificationMaker(context.getApplicationContext()).Notify(sms.getSender(), sms.getMsg(), new Intent(context, SmsMainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                }
            }catch (Exception e){
                Issue.print(e,SmsReceiver.class.getName());
            }
        }

    }
}

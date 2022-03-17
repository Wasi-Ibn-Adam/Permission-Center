package com.wasitech.permissioncenter.java.com.wasitech.assist.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wasitech.assist.R;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.contact.pop.ContactPopUp;
import com.wasitech.basics.BaseCompatActivity;
import com.wasitech.basics.classes.Issue;
import com.wasitech.contact.adapter.ContactAdapter;
import com.wasitech.contact.classes.Contact;
import com.wasitech.database.Params;

import java.util.ArrayList;

public class SendMessages extends BaseCompatActivity {
    private String data;
    private ArrayList<Contact> list;
    private ContactAdapter adapter;
    private RecyclerView listView;
    private EditText messageBox;
    private EditText phoneNumber;

    public SendMessages() {
        super(R.layout.activity_send_message_light, R.layout.activity_send_message_dark);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme();

        listView = findViewById(R.id.contactList_sms);
        messageBox = findViewById(R.id.messageBox);
        phoneNumber = findViewById(R.id.editTextPhone);
        ImageButton btnSend = findViewById(R.id.btnSend);

        String message = getIntent().getStringExtra(Params.MESSAGE);
        String temp = getIntent().getStringExtra(Params.DATA_TRANS);
        if (temp != null)
            data = temp;
        cropper();
        if (message != null) {
            messageBox.setText(message);
        }

        list = ProcessApp.cList;

        adapter = new ContactAdapter(list) {
            @Override
            public void onClick(Contact contact) {
                if (contact.getNumbers().size() > 1) {
                    textToSpeech("Select a Number!");
                    singleContactShow(contact);
                } else if (contact.getNumbers().size() == 1) {
                    textToSpeech("Sending message to " + contact.getName());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Issue.print(e, SendMessages.class.getName());
                    }
                    sendSMS(contact.getNumbers().get(0).getNumber(), messageBox.getText().toString());
                }
            }

        };
        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnSend.setOnClickListener(view -> {
            if (!phoneNumber.getText().toString().isEmpty())
                sendSMS(phoneNumber.getText().toString(), messageBox.getText().toString());
        });

        checkContact();
        listView.setAdapter(null);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(adapter);
//        new Handler().postDelayed(this::finish,100000);
    }

    private void cropper() {
        if(data==null)
            return;
        if (data.contains("send")) {
            data = data.replace("send", " ").trim();
        }
        if (data.contains("sms")) {
            data = data.replace("sms", " ").trim();
        }
        if (data.contains("message")) {
            data = data.replace("message", " ").trim();
        }
        data=data.trim();
    }

    private void checkContact() {
        if (data != null) {
            if (data.contains("to ") || data.contains("ko ")) {
                if (data.contains("to")) {
                    data = data.replace("to ", " ").trim();
                } else {
                    data = data.replace("ko ", " ").trim();
                }
            }
        }
        adapter.getFilter().filter(data);
    }

    private void textToSpeech(final String str) {
        ProcessApp.talk(getApplicationContext(), str);
        if ("Message Sent".equals(str)) {
            onBackPressed();
        }
    }

    private void singleContactShow(Contact contact) {
        TextView title = findViewById(R.id.contactTitle_sms);
        title.setText(contact.getName());
        new ContactPopUp(SendMessages.this, contact) {
            @Override
            public void onSelected(String num) {
                phoneNumber.setText(num);
                textToSpeech("Sending message to " + contact.getName().trim());
                dismiss();
                new Handler().postDelayed(() -> sendSMS(num, messageBox.getText().toString()), 1500);
            }

            @Override
            public void onCall(String num) {

            }

            @Override
            public void onSms(String num) {

            }
        };
    }

    public void sendSMS(String phoneNo, String msg) {
        if (phoneNo.trim().length() == 10) {
            phoneNo = "0" + phoneNo;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phoneNo, null));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("sms_body", msg);
        startActivity(intent);
        finishAndRemoveTask();
        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 10);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
            final String SENT = "SMS_SENT";

            PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);

            // ---when the SMS has been sent---
            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context arg0, Intent arg1) {

                    switch (getResultCode()) {
                        case Activity.RESULT_OK:{
                            textToSpeech(SENT);
                            finishAndRemoveTask();
                            break;
                        }
                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                                case SmsManager.RESULT_ERROR_NULL_PDU:{
                            textToSpeech("SMS sending Failed. Check credit in your account, or check the recipient number if it is valid.");
                            new Handler().postDelayed(()->finish(),4000);
                            break;
                        }
                        case SmsManager.RESULT_ERROR_NO_SERVICE:{
                            textToSpeech("SMS sending Failed. network service is unavailable.");
                            new Handler().postDelayed(()->finish(),4000);
                            break;
                        }
                        case SmsManager.RESULT_ERROR_RADIO_OFF:{
                            textToSpeech("SMS sending Failed. AirPlane mode is active.");
                            new Handler().postDelayed(()->finish(),4000);
                            break;
                        }
                        default:{
                                textToSpeech("SMS sending Failed.");
                                new Handler().postDelayed(()->finish(),4000);
                                break;
                        }
                    }
                }
            }, new IntentFilter(SENT));
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phoneNo, null, msg, sentPI, null);
        }
        else
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 10);
            }
        }

            */
    }

    @Override
    public void onBackPressed() {
        finishAndRemoveTask();
        super.onBackPressed();
    }
}

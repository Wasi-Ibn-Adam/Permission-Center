package com.wasitech.permissioncenter.java.com.wasitech.assist.classes;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognitionService;
import android.speech.SpeechRecognizer;

import androidx.core.content.ContextCompat;

import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.assist.command.family.CommandCheck;
import com.wasitech.assist.command.family.Intents;
import com.wasitech.assist.services_receivers.BackgroundService;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.wasitech.database.CloudDB;
import com.wasitech.basics.classes.Issue;

public abstract class VoiceToText implements RecognitionListener {
    private SpeechRecognizer speech;
    private Intent recognizerIntent;
    protected final Context context;
    private static int coder=0;
    public static Stack<Integer> stack;
    public static int COMMAND_TYPE;
    public final CloudDB.DataCenter center;
    private boolean bg;
    protected String data;
    private final String FROM;
    public VoiceToText(Context context,String from){
        this.context=context;
        this.FROM =from;
        stack=new Stack<>();
        center=new CloudDB.DataCenter();
        speech = SpeechRecognizer.createSpeechRecognizer(context);
        speech.setRecognitionListener(this);
        recognizerIntent = Intents.Recognizer("en",1);
    }
    public abstract void ByeBye();
    public void StartSpeak(){
        //context.getApplicationContext().stopService(new Intent(context, BackgroundService.class));
        ProcessApp.stopTalk();
        new Modes(context).notiVolume(0,1000);
        Basics.AudioVideo.Vibrator(context,200L);
        try{
            speech.startListening(recognizerIntent);
        }catch (Exception e){
            findComponentName();
            Basics.toasting(context,"Facing Issues Please install Google App(Voice typing)");
            Issue.print(e, VoiceToText.class.getName());
        }

    }
    private void findComponentName() {
        try{
            @SuppressLint("QueryPermissionsNeeded") List<ResolveInfo> list= context.getPackageManager().queryIntentServices(new Intent(RecognitionService.SERVICE_INTERFACE), 0);
            ServiceInfo info=list.get(0).serviceInfo;
            speech=SpeechRecognizer.createSpeechRecognizer(context,new ComponentName(info.packageName,info.name));
            speech.setRecognitionListener(this);
            speech.startListening(recognizerIntent);
        }
        catch (Exception e){
            Basics.toasting(context,"An unknown Issue Occurred, Sent this Error to Backend team.");
            Issue.print(e, VoiceToText.class.getName());
        }
    }
    public abstract void PermissionRequire(int code);
    protected abstract void LAST_CHECK(String s);
    protected abstract void FIRST_CHECK();
    public void StopSpeak(){
        speech.stopListening();
    }
    @Override
    public void onReadyForSpeech(Bundle params) {

    }
    @Override
    public void onBeginningOfSpeech() {

    }
    @Override
    public void onRmsChanged(float rmsdB) {

    }
    @Override
    public void onBufferReceived(byte[] buffer) {
    }
    @Override
    public void onEndOfSpeech() {

    }
    @Override
    public void onError(int error) { }
    public void UserCommand(String command){}
    public void AppCommand(String command){}
    @Override
    public void onResults(Bundle results) {
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        StringBuilder text = new StringBuilder();
        assert matches != null;
        for (String result : matches)
            text.append(result).append("\n");
        if(bg)
            ContextCompat.startForegroundService(context.getApplicationContext(),new Intent(context, BackgroundService.class));
        outerCommandCheck(text.toString().trim().split("\n")[0]);
    }

    public void Talking(String data){
        if(data!=null&&!data.trim().equals("")) {
            ProcessApp.talk(context, data);
            AppCommand(data);
        }
        if (stack != null && !stack.empty())
            FIRST_CHECK();
    }
    public void outerCommandCheck(String text) {
        UserCommand(text);
        String input=text.toLowerCase();
        if (stack != null && !stack.empty()) {
            LAST_CHECK(input);
        }
        else {
            Speak.COMMAND_TYPE= CloudDB.DataCenter.KNOWN;
            data = input;
            Talking( new CommandCheck(context).setCommand(input));
        }
        switch (COMMAND_TYPE){
            default: case CloudDB.DataCenter.KNOWN:{center.KnownCommand(input);break;}
            case CloudDB.DataCenter.HINT:{center.HintCommand(input);break;}
            case CloudDB.DataCenter.UNKNOWN:{center.UnknownCommand(input);break;}
        }
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
    }
    @Override
    public void onEvent(int eventType, Bundle params) {

    }
    public void Destroy(){
        speech.destroy();
        speech=null;
        recognizerIntent=null;
        stack=null;
    }

}

package com.wasitech.permissioncenter.java.com.wasitech.autohome;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wasitech.basics.app.ProcessApp;

public class AutoService {
    private final RequestQueue queue;
    private final String link;
    private final Context context;
    private final static String GET="get/";
    private final static String PUT="update/";
    private final static String VALUE="?value=";
    private final static String HARD_STATUS="isHardwareConnected";
    private final static String APP_STATUS="isAppConnected";
    private static char PIN;
    public AutoService(Context context){
        this.context=context;
        queue = Volley.newRequestQueue(context);
        link="http://blynk-cloud.com/x8H0xLr5-3g1JOvwKwzaKV24PAQCvWOJ/";
    }
    public void DIGITAL_PUT(int num, int state){
        PIN='D';
        main(lights(num-1),state);
    }
    public void VIRTUAL_PUT(int num,int state){
        PIN='V';
        main(num-1,state);
    }
    public void DIGITAL_GET(int num){
        PIN='D';
        ReadCheck(lights(num-1));
    }
    public void VIRTUAL_GET(int num){
        PIN='V';
        ReadCheck(num-1);
    }

    private int lights(int light){
        switch (light){
            case 1:{return 15;}
            case 2:{return 2;}
            case 3:{return 0;}
            case 4:{return 4;}
            case 5:{return 16;}
            case 6:{return 17;}
            case 7:{return 5;}
            case 8:{return 18;}
            default:{return 100;}
        }
    }
    private void ReadCheck(int num){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, link+GET+PIN+num,
                response -> {
                    Log.i("founder","Response  for: "+num+" -> "+ response);
                    if(response.contains("0")){
                        ProcessApp.talk(context,"Appliance On.");
                    }
                    else if(response.contains("1")){
                        ProcessApp.talk(context,"Appliance Off.");
                    }
                    else{
                        ProcessApp.talk(context,"Unknown Error");
                    }
                },
                error -> {
                    ProcessApp.talk(context, "Appliance  not Connected.");
                    Log.i("founder", "That didn't work!" + error);
                });
        queue.add(stringRequest);
    }
    private void main(int num,int state){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, link+HARD_STATUS,
                response -> {
                    Log.i("founder","hard Response  for: "+ response);
                    if(response!=null&&response.contains("true")){
                        WriteCheck(num,state);
                    }
                    else{
                        ProcessApp.talk(context,"Device is not Connected.");
                    }
                },
                error -> {
                    ProcessApp.talk(context,"Device is not Connected.");
                    Log.i("founder", "hard That didn't work!" + error);
                });
        queue.add(stringRequest);
    }
    private void WriteCheck(int num, int state){
        StringRequest request = new StringRequest(Request.Method.GET, link+GET+PIN+num,
                response1 -> {
                    Log.i("founder","Response  for: "+num+" -> "+ response1);
                    if(response1.contains("0")){
                        if(state==1)
                            Write(num,state);
                        else
                            ProcessApp.talk(context,"Already On.");
                    }
                    else if(response1.contains("1")){
                        if(state==0)
                            Write(num,state);
                        else
                            ProcessApp.talk(context,"Already Off.");
                    }
                    else{
                        ProcessApp.talk(context,"Unknown Error");
                    }
                },
                error -> {
                    ProcessApp.talk(context,"Appliance  not Connected.");
                    Log.i("founder", "That didn't work!" + error);
                });
        queue.add(request);
    }
    private void Write(int num,int state){
        StringRequest request2 = new StringRequest(Request.Method.GET, link+PUT+PIN+num+VALUE+state,
                response2 -> {
                    ProcessApp.talk(context,"Done");
                    Log.i("founder","Response  for: "+num+" -> "+ response2);
                },
                error -> {
                    ProcessApp.talk(context,"Sorry, Try Again Later");
                    Log.i("founder", "That didn't work!" + error);
                });
        queue.add(request2);
    }

    public void hardStatus(){
        String url =link+HARD_STATUS;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    Log.i("founder","hard Response  for: "+ response);
                    if(response!=null&&response.contains("true")){
                        ProcessApp.talk(context,"Device is Connected.");
                    }
                    else{
                        ProcessApp.talk(context,"Device is not Connected.");
                    }
                },
                error -> {
                    ProcessApp.talk(context,"Device is not Connected.");
                    Log.i("founder", "hard That didn't work!" + error);
                });
        queue.add(stringRequest);
    }
    public void appStatus(){
        String url =link+APP_STATUS;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    Log.i("founder","app Response  for: "+ response);
                    if(response!=null&&response.contains("true")){
                        ProcessApp.talk(context,"App is Connected.");
                    }
                    else{
                        ProcessApp.talk(context,"App is not Connected.");
                    }
                },
                error -> {
                    ProcessApp.talk(context,"App is not Connected.");
                    Log.i("founder", "app That didn't work!" + error);
                });
        queue.add(stringRequest);


    }

}

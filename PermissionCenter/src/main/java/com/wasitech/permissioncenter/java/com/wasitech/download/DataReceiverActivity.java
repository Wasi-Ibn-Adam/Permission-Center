package com.wasitech.permissioncenter.java.com.wasitech.download;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.SparseArray;

import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.BaseCompatActivity;
import com.wasitech.basics.Storage;
import com.wasitech.assist.command.family.QA;
import com.wasitech.assist.popups.WaitingPopUp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;
import com.wasitech.basics.classes.Issue;

public abstract class DataReceiverActivity extends BaseCompatActivity {


    public DataReceiverActivity() {
        super(0, 0);
    }

    protected abstract WaitingPopUp setPopUp();

    protected abstract void imgSave(Uri uri);

    protected abstract void vidSave(Uri uri);

    protected abstract void audSave(Uri uri);

    protected abstract void linkSave(String path);

    private static void download(final Context context, final String uri, final String title) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(uri));
        request.setDescription("DownLoading...");
        request.setTitle(title);
// in order for this if to run, you must use the android 3.2 to compile your app
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Storage.DOWN, "Assist/" + fileNameExtractor(title) + ".mp4");
// get download service and enqueue file
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

    public String getMimeType(Uri url) {
        return getContentResolver().getType(url);
    }


    private String fileName(Uri uri) {
        if (uri != null) {
            @SuppressLint("Recycle") Cursor c1 = getContentResolver().query(uri, null, null, null, null);
            if (c1 != null && c1.moveToFirst()) {
                int id = c1.getColumnIndex(MediaStore.Images.Media.DATA);
                if (id != -1) {
                    return c1.getString(id);
                }
            }
            if (uri.toString().startsWith("file:")) {
                return uri.getPath();
            } else if (uri.toString().startsWith("content:")) {
                @SuppressLint("Recycle") Cursor c = getContentResolver().query(uri, null, null, null, null);
                if (c != null && c.moveToFirst()) {
                    int id = c.getColumnIndex(MediaStore.Images.Media.DATA);
                    if (id != -1) {
                        return c.getString(id);
                    }
                }
            }
            return uri.getPath();
        }
        return QA.cTime().replace(":", "_");
    }

    private boolean saveFile(String type, Uri uri, String post) {
        String name = fileName(uri);
        String path = Storage.CreateDataFile(type, post).getPath();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(name));
            bos = new BufferedOutputStream(new FileOutputStream(path, false));
            byte[] buf = new byte[1024];
            bis.read(buf);
            do {
                bos.write(buf);
            } while (bis.read(buf) != -1);
            Basics.Img.mediaScanner(getApplicationContext(), path);
            return true;
        } catch (IOException e) {
            Issue.print(e,DataReceiverActivity.class.getName());
        } finally {
            try {
                if (bis != null) bis.close();
                if (bos != null) bos.close();
            } catch (IOException e) {
                Issue.print(e,DataReceiverActivity.class.getName());
            }
        }
        return false;
    }

    private static String fileNameExtractor(String name) {
        name = name.replace(":", "_");
        if (name.contains(".")) {
            name = name.substring(0, name.indexOf('.'));
        }
        if (name.contains("/")) {
            name = name.substring(name.lastIndexOf('/') + 1);
        }
        return "Saved " + name;
    }

    private static void youtube(final Context context, final String youtubeLink) {
        //Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        //calendar.setTime(new Date());               //Set the Calendar to now
        //int hour = calendar.get(Calendar.HOUR_OF_DAY); //Get the hour from the calendar

        //String url = "http://";
        //// We're running on a free heroku instance. THey need to sleep for atleast 6 hrs in a day
        //// SO lets just run two free instances and swap between them depending on teh time of the day, giving each instance a chance to sleep for 12hrs
        //if(hour >= 0 && hour <= 12)
        //{
        //    url += "youtube-dl55.";
        //} else {
        //    url += "youtube-dl99.";
        //}
        //url += "herokuapp.com/api/info?url=" + youtubeLink;
        //Temp asyncTask =new Temp(this);
        //asyncTask.delegate = this;
        //asyncTask.execute(url);
        new YouTubeExtractor(context) {
            @Override
            public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
                if (ytFiles != null) {
                    int tag = 22;
                    String downloadUrl = ytFiles.get(tag).getUrl();
                    download(context, downloadUrl, vMeta.getTitle());
                }
            }
        }.extract(youtubeLink);

    }

    private void testFun(){
  /*      String host = getIntent().getData().getHost();
        String pathname = getIntent().getData().getPath();
        String fullurl = host + pathname;
        String https_host = "https://" + host + pathname;
        String ps = fullurl.split("/")[1];
        if("www.instagram.com".equals(host)) {
            //window.open("http://insd.ga", '_blank');
            if ("p".equals(ps) || "stories".equals(ps)) {
                //String linkvid = document.getElementsByClassName("tWeCl")[0];
                //if(linkvid == undefined){
                //    var length_photo = document.getElementsByClassName("FFVAD").length;
                //    var linkins = length_photo - 1;
                //    var link_photo = document.getElementsByClassName("FFVAD")[linkins].currentSrc;
                //    window.open(link_photo, '_blank');
//
                //}else{
                //    var linkvid = linkvid.currentSrc;
                //    window.open(linkvid, '_blank');
                //}

                //var photo = document.getElementsByClassName('y-yJ5')[0].src;
                //if(photo == null){
                //    var video = document.getElementsByClassName('y-yJ5')[2].currentSrc;
                //    window.open(video, '_blank');
                //}
                //else{
                //    window.open(photo , '_blank');
                //}
            }
        }

*/
    }
    /*

    javascript:
        var host = window.location.hostname;
        var pathname = window.location.pathname;
        var fullurl = host + pathname;
        var https_host = "https://" + host + pathname;
        var ps = fullurl.split("/")[1];
        switch (host) {
            case "www.instagram.com":
                switch(ps) {
                    case "p":
                            var linkvid = document.getElementsByClassName("tWeCl")[0];
                            if(linkvid == undefined){
                            var length_photo = document.getElementsByClassName("FFVAD").length;
                            var linkins = length_photo - 1;
                            var link_photo = document.getElementsByClassName("FFVAD")[linkins].currentSrc;
                            window.open(link_photo, '_blank');

                            }else{
                                var linkvid = linkvid.currentSrc;
                                window.open(linkvid, '_blank');
                            }
                    case "stories":
                            var photo = document.getElementsByClassName('y-yJ5')[0].src;
                        if(photo == null){
                            var video = document.getElementsByClassName('y-yJ5')[2].currentSrc;
                            window.open(video, '_blank');
                        }
                        else{
                            window.open(photo , '_blank');
                        }
                    break;
                    default:
                        window.open("http://insd.ga", '_blank');
                    }
            break;
        }

    */
}

package com.wasitech.permissioncenter.java.com.wasitech.basics.classes;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.AudioManager;
import android.media.MediaScannerConnection;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.PowerManager;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.AlarmClock;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.FileProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wasitech.assist.activities.SendMessages;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.Storage;
import com.wasitech.basics.notify.OldNotificationMaker;
import com.wasitech.database.Params;
import com.wasitech.music.activity.MusicPlayer;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;

import static android.content.Context.BLUETOOTH_SERVICE;

public class Basics {
    private static final String TAG = "founder";
    private static String data;

    public static class AudioVideo {
        public static void Vibrator(Context context, Long sec) {
            Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(sec, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                v.vibrate(sec);
            }
        }

        public static String stopAudio(Context context) {
            AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (am.isMusicActive()) {
                AudioManager.OnAudioFocusChangeListener focusChangeListener = i -> {
                };
                int result = am.requestAudioFocus(focusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
                am.abandonAudioFocus(focusChangeListener);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                    return "Audio Stopped.";
                else {
                    return "Unable to Stop.";
                }
            } else {
                return "Music is Not Playing.";
            }

        }

        public static void audioManager(Context context, int modes) {
            boolean yes = Build.MANUFACTURER.toLowerCase().contains("samsung");
            if (yes) {
                Intent i = null;
                switch (modes) {
                    case Params.AUDIO_PLAY: {
                        i = new Intent("com.sec.android.app.music.musicservicecommand.play");
                        break;
                    }
                    case Params.AUDIO_NEXT: {
                        i = new Intent("com.sec.android.app.music.musicservicecommand.next");
                        break;
                    }
                    case Params.AUDIO_PAUSE: {
                        i = new Intent("com.sec.android.app.music.musicservicecommand.pause");
                        break;
                    }
                    case Params.AUDIO_PREVIOUS: {
                        i = new Intent("com.sec.android.app.music.musicservicecommand.previous");
                        break;
                    }
                    case Params.AUDIO_STOP: {
                        i = new Intent("com.sec.android.app.music.musicservicecommand.stop");
                        break;
                    }
                }
                if (i != null)
                    context.getApplicationContext().sendBroadcast(i);
            } else {
                Intent i = new Intent("com.android.music.musicservicecommand");
                switch (modes) {
                    case Params.AUDIO_PLAY: {
                        i.putExtra("command", "play");
                        break;
                    }
                    case Params.AUDIO_NEXT: {
                        i.putExtra("command", "next");
                        break;
                    }
                    case Params.AUDIO_PAUSE: {
                        i.putExtra("command", "pause");
                        break;
                    }
                    case Params.AUDIO_PREVIOUS: {
                        i.putExtra("command", "previous");
                        break;
                    }
                    case Params.AUDIO_STOP: {
                        i.putExtra("command", "stop");
                        break;
                    }
                }
                if (i.getStringExtra("command") != null)
                    context.getApplicationContext().sendBroadcast(i);

            }


        }

        public static String stopVideo(Context context) {
            MediaController controller = new MediaController(context);
            controller.setVisibility(View.GONE);
            controller.setMediaPlayer(null);
            //stopAudio(context);
            return "Video is Stopped.";
        }
    }

    public static class BlueTooth {
        public static String set(boolean choice) {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            boolean isEnabled = bluetoothAdapter.isEnabled();
            if (isEnabled && choice) {
                return "Already Enabled.";
            }
            if (choice) {
                if (bluetoothAdapter.enable())
                    return "Enabled.";
                else
                    return "Unable to Enable.";
            }
            if (!isEnabled) {
                return "Already Disabled.";
            }
            if (bluetoothAdapter.disable())
                return "Disabled.";
            else
                return "Unable to disable.";
        }

        public static boolean isConnected(Context context) {
            BluetoothManager manager = (BluetoothManager) context.getSystemService(BLUETOOTH_SERVICE);
            List<BluetoothDevice> connected = manager.getConnectedDevices(BluetoothProfile.GATT_SERVER);
            return connected.size() > 0;
        }

        public static String connected(Context context) {
            if (BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                if (isConnected(context)) {
                    return "Bluetooth is Connected.";
                } else {
                    return "No Device is Connected with Bluetooth.";
                }
            } else {
                return "Bluetooth is Not Enabled.";
            }
        }

        public static void connection(Context context) {
            set(true);
            context.startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }
    public static class WIFI {
        public static String set(Context context,boolean choice) {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            boolean isEnabled = wifiManager.isWifiEnabled();
            if (isEnabled && choice) {
                return "Already Enabled.";
            }
            if (choice) {
                if (wifiManager.setWifiEnabled(true))
                    return "Enabled.";
                else
                    return "Unable to Enable.";
            }
            if (!isEnabled) {
                return "Already Disabled.";
            }
            if (wifiManager.setWifiEnabled(false))
                return "Disabled.";
            else
                return "Unable to disable.";
        }

        public static boolean isConnected(Context context) {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            return wifiManager.isWifiEnabled();
        }
    }

    public static class Img {
        public static Bitmap parseBitmap(Drawable drawable) {
            if (drawable == null) {
                return null;
            }
            Bitmap bitmap;
            if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
                bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            }
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        }

        public static void saveBitmap(Bitmap bitmap) {
            try {
                OutputStream out = new FileOutputStream(Storage.CreateDataFile(Storage.IMG, ".jpg"));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
            } catch (IOException e) {
                Issue.print(e, Img.class.getName());
            }
        }

        public static void saveBitmap(Bitmap bitmap, String fileName) {
            try {
                OutputStream out = new FileOutputStream(Storage.CreateDataFile(Storage.IMG, fileName, ".jpg"));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
            } catch (IOException e) {
                Issue.print(e, Img.class.getName());
            }
        }

        public static Bitmap downloadBitmap(String url) {
            try {
                URL url1 = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
                connection.setDoInput(true);
                connection.connect();
                return BitmapFactory.decodeStream(connection.getInputStream());
            } catch (IOException e) {
                Issue.print(e, Img.class.getName());
            }
            return null;
        }

        public static Bitmap parseBitmap(byte[] array) {
            if (array != null)
                return BitmapFactory.decodeByteArray(array, 0, array.length);
            return null;
        }

        public static Bitmap fromPath(String imgPath) {
            return BitmapFactory.decodeFile(imgPath);
        }

        public static Bitmap videoThumbNail(String path) {
            return ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.MICRO_KIND);
        }

        public static void saveDrawable(Drawable drawable) {
            Bitmap bitmap = parseBitmap(drawable);
            if (bitmap != null) saveBitmap(bitmap);
        }

        public static File saveFile(byte[] bytes) {
            File photo = Storage.CreateDataFile(Storage.IMG, ".png");
            try {
                FileOutputStream fos = new FileOutputStream(photo);
                fos.write(bytes);
                fos.close();
            } catch (IOException e) {
                Issue.print(e, Img.class.getName());
            }
            return photo;
        }

        public static File parseFile(byte[] bytes) {
            File photo = Storage.CreateDataFile(Storage.IMG, ".png");
            try {
                FileOutputStream fos = new FileOutputStream(photo);
                fos.write(bytes);
                fos.close();
            } catch (IOException e) {
                Issue.print(e, Img.class.getName());
            }
            return photo;
        }

        public static byte[] parseBytes(Bitmap map) {
            if (map != null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                map.compress(Bitmap.CompressFormat.PNG, 100, stream);
                return stream.toByteArray();
            }
            return null;
        }

        public static void saveBytes(byte[] array) {
            if (array != null)
                saveBitmap(BitmapFactory.decodeByteArray(array, 0, array.length));
        }

        public static void mediaScanner(Context context, String filePath) {
            MediaScannerConnection.scanFile(context.getApplicationContext(), new String[]{filePath}, null, (path, uri) -> {
            });
        }

        public static void mediaScanner(Context context, String filePath, String success, String failed) {
            MediaScannerConnection.scanFile(context.getApplicationContext(), new String[]{filePath}, null, new MediaScannerConnection.OnScanCompletedListener() {
                @Override
                public void onScanCompleted(String path, Uri uri) {
                    if (path != null && new File(path).exists()) toasting(context, success);
                    else toasting(context, failed);
                }
            });
        }
    }

    public static class Internet {
        private static int number = 1;

        private static boolean isConnected(Context context) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo temp = connectivityManager.getActiveNetworkInfo();
            if (temp != null && temp.isConnected()) {
                temp.getDetailedState();
                return true;
            } else {
                toasting(context, "Internet Not Connected");
                return false;
            }
        }

        public static boolean isInternetJson(Context context) {
            if (Internet.number == 1) {
                Basics.data = " try run";
            }
            if (isConnected(context)) {
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(new JsonObjectRequest(Request.Method.GET, "https://jsonplaceholder.typicode.com/todos/" + (Internet.number), null,
                        response -> {
                            try {
                                Basics.data = response.getString("title");
                            } catch (JSONException e) {
                                Issue.print(e, Internet.class.getName());
                            }
                        }, Issue::Internet));
                if (Internet.number == 200) {
                    Internet.number = 1;
                } else {
                    Internet.number++;
                }
                if (Basics.data != null) return true;
                else toasting(context, "Make Sure You Have An Active Internet Connection.");
            }
            return false;
        }

        public static Intent intentGPS() {
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            return poke;
        }

        public static boolean canToggleGPS(Context context) {
            PackageManager pacman = context.getPackageManager();
            PackageInfo pacInfo;
            try {
                pacInfo = pacman.getPackageInfo("com.android.settings", PackageManager.GET_RECEIVERS);
            } catch (PackageManager.NameNotFoundException e) {
                Issue.print(e, Internet.class.getName());
                return false;
            }
            if (pacInfo != null) {
                for (ActivityInfo actInfo : pacInfo.receivers) {
                    if (actInfo.name.equals("com.android.settings.widget.SettingsAppWidgetProvider") && actInfo.exported) {
                        return true;
                    }
                }
            }

            return false; //default
        }
    }

    public static class Send {
        public static void share(Context context,String path) {
            if (path == null) return;
            try {
                Uri uri = FileProvider.getUriForFile(context, "com.wasitech.assist.FileProvider", new File(path));
                if (uri != null) {
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
                    shareIntent.setDataAndType(uri, context.getContentResolver().getType(uri));
                    shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                    context.startActivity(Intent.createChooser(shareIntent, "Choose an app"));
                }
            } catch (Exception e) {
                Issue.print(e, MusicPlayer.class.getName());
            }
        }

        public static void InstagramMessage(Context context, String messageToSend) {
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, messageToSend);
            sendIntent.setType("text/plain");
            sendIntent.setPackage("com.instagram.android");
            if (context.getPackageManager().getLaunchIntentForPackage("com.instagram.android") != null) {
                action(context, sendIntent, false);
            } else {
                ProcessApp.talk(context, "App Not Found.");
            }
        }

        public static void FacebookMessage(Context context, String messageToSend) {
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, messageToSend);
            sendIntent.setType("text/plain");
            sendIntent.setPackage("com.facebook.orca");
            if (context.getPackageManager().getLaunchIntentForPackage("com.facebook.orca") != null) {
                action(context, sendIntent, false);
            } else {
                ProcessApp.talk(context, "App Not Found.");
            }
        }

        public static void WhatsappMessage(Context context, String msg, String number) {
            String pkg;
            if (data.toLowerCase().contains("business")) {
                pkg = "com.whatsapp.w4b";
            } else if (data.toLowerCase().contains("aero") || data.toLowerCase().contains("arrow")) {
                pkg = "com.aero";
            } else if (data.toLowerCase().contains("gb") || data.toLowerCase().contains("g b")) {
                pkg = "com.gbwhatsapp";
            } else {
                pkg = "com.whatsapp";
            }
            if (context.getPackageManager().getLaunchIntentForPackage(pkg) != null) {
                if (!number.isEmpty()) {
                    action(context, send("", msg, pkg), false);
                } else {
                    action(context, send(number, msg, pkg), false);
                }
            } else {
                ProcessApp.talk(context, "App Not Found.");
            }
        }

        public static void sendMessage(Context context, String messageToSend, String data) {
            if (messageToSend.contains("what is your message"))
                messageToSend = messageToSend.replace("what is your message", " ").trim();

            if (data.toLowerCase().contains("messenger") || data.toLowerCase().contains("facebook")) {
                FacebookMessage(context, messageToSend);
            } else if (data.toLowerCase().contains("whatsapp")) {
                WhatsappMessage(context, messageToSend, "");
            } else if (data.toLowerCase().contains("instagram")) {
                InstagramMessage(context, messageToSend);
            } else {
                Intent intent = new Intent(context, SendMessages.class);
                intent.putExtra("message", messageToSend);
                intent.putExtra("data", data);
                action(context, intent, true);
            }
        }

        private static Intent send(String number, String msg, String pkg) {
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT, msg);
            sendIntent.setPackage(pkg);
            sendIntent.putExtra("chat", true);
            sendIntent.putExtra("jid", number + "@s.whatsapp.net");
            return sendIntent;
        }

        private static void action(Context context, Intent intent, boolean search) {
            try {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                if (!search)
                    ProcessApp.talk(context, "Select Recipient.");
            } catch (ActivityNotFoundException e) {
                Issue.print(e, Send.class.getName());
            }

        }

        public static Intent sendMail(String mail_address, String subject, String body) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{mail_address});
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, body);
            intent.setData(Uri.parse("mailto:"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            return intent;
        }
    }


    public static void Youtube(Context context, String searchFor) {
        Intent searchIntent = new Intent(Intent.ACTION_SEARCH);
        searchIntent.setPackage("com.google.android.youtube");
        searchIntent.putExtra("query", searchFor);
        if (context.getPackageManager().getLaunchIntentForPackage("com.google.android.youtube") != null) {
            Send.action(context, searchIntent, true);
        } else {
            ProcessApp.talk(context, "App Not Found.");
        }
    }

    public static boolean EmailValidator(String email) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        return Pattern.compile(regex).matcher(email).matches();
    }

    public static boolean onlyNumbers(String text) {
        // return numbers
        for (char t : text.toCharArray()) {
            if (t < '0' || t > '9')
                return false;
        }
        return true;
    }

    public static void SetAlarm(Context context, int hour, int minute) {
        new OldNotificationMaker(context).Notify("Alarm", "Alarm Set", null);
        Toast.makeText(context, hour + "--" + minute, Toast.LENGTH_SHORT).show();
        Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
        i.putExtra(AlarmClock.EXTRA_MESSAGE, "New Alarm");
        i.putExtra(AlarmClock.EXTRA_HOUR, hour);
        i.putExtra(AlarmClock.EXTRA_MINUTES, minute);
        context.startActivity(i);
    }

    public static WindowManager.LayoutParams WindowServicesParams(int posX, int posY, int gravitySide) {
        WindowManager.LayoutParams params;
        {
            int LAYOUT_FLAG;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
            }
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    LAYOUT_FLAG,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
            params.gravity = Gravity.TOP | gravitySide;
            params.x = posX;
            params.y = posY;
        }
        return params;
    }

    public static String FlashLight(Context context, boolean enable) {
        if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            return "As far I know, Your phone don't have any.";
        }
        CameraManager manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);

        boolean done;
        try {
            manager.setTorchMode((manager.getCameraIdList())[0], enable);
            done = true;
        } catch (CameraAccessException e) {
            done = false;
            Issue.print(e, Basics.class.getName());
        }
        if (done) {
            if (enable)
                return "FlashLight On.";
            else
                return "FlashLight Off.";
        }
        return "Sorry, Something went wrong.";
    }

    public static void LockPhone(Context context, ComponentName cm, boolean lock) {
        DevicePolicyManager deviceManger = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        if (lock) {
            if (deviceManger.isAdminActive(cm)) {
                ProcessApp.talk(context.getApplicationContext(), "Phone Locked.");
                deviceManger.lockNow();
            } else {
                ProcessApp.talk(context.getApplicationContext(), "Error Found.");
            }
        } else {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK
                    | PowerManager.ACQUIRE_CAUSES_WAKEUP
                    | PowerManager.ON_AFTER_RELEASE, "MyWakeLock");
            wakeLock.acquire(10 * 60 * 1000L /*10 minutes*/);
        }
    }

    public static boolean isNLServiceRunning(Context context) {
        return NotificationManagerCompat.getEnabledListenerPackages(context.getApplicationContext()).contains(context.getPackageName());
    }

    public static void toasting(Context context, String text) {
        try {
            Toast toast1 = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast1.setGravity(80, 0, 500);
            toast1.show();
        } catch (Exception e) {
            Issue.print(e, Basics.class.getName());
        }
    }

    public static boolean isMyServiceRunning(Context c, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) c.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static void Log(String text) {
        Log.i(TAG, text);
    }

    public static boolean equals(String s1,String s2) {
        return (s1==null&&s2==null)||((s1!=null&&s2!=null)&&(s1.trim().equals(s2.trim())));
    }

}

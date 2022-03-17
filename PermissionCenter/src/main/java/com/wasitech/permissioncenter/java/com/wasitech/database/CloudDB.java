package com.wasitech.permissioncenter.java.com.wasitech.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.wasitech.assist.classes.App;
import com.wasitech.assist.runnables.SignOutRunnable;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.classes.Format;
import com.wasitech.basics.classes.Issue;
import com.wasitech.permission.Permission;
import com.wasitech.register.activity.Profile;
import com.wasitech.register.classes.UserProfile;

import java.util.Date;

public class CloudDB {

    /**
     * <b>** REALTIME ***</b> <p>
     * ZONG  -->>  HOSTEL=>("nextdoor")  + Previous zong data<br>
     * WARID -->>  Issues =>("Issues") + Previous Warid data<br>
     * JAZZ -->>  UNKNOWN =>("Unknown Command")+ Previous jazz data  // NEW User DATA<br>
     * Ufone -->>  Previous ufone data   // NEW Profile<br>
     * Random -->>  Contact(all user contact,emergency, short, searched), some extra garbage<br>
     * Telenor -->>  Previous telenor data<br>
     * Tinggo -->>  TingGo tings=>("Tings") + Ting Users=>("Ting User") + some old(Issues, unknown) +Ids(not sure)<br>
     * Tingno -->>  Tingno new =>("TingNo")+ old +some num(not sure)<br>
     * DEFAULT -->>  User INFO=>("0-User-Extras")  +User DATA=>("2-Data")<br>
     * AllCountries -->>  CONTACTS<br>
     */
    protected static DatabaseReference realTime(String db) {
        return FirebaseDatabase.getInstance(FirebaseApp.getInstance(db)).getReference();
    }
    /**
     * <b>** STORAGE ***</b><br>
     * ZONG  -->>  N/A<br>
     * WARID -->>  N/A<br>
     * JAZZ -->>  N/A<br>
     * Ufone -->>  N/A<br>
     * Random -->>  Assist User Pics<br>
     * Telenor -->>  N/A<br>
     * Tinggo -->>  Assist User Pics (Current)<br>
     * Tingno -->>  N/A<br>
     * DEFAULT -->>  N/A  (Random date)<br>
     * AllCountries -->>  N/A<br>
     */
    protected static StorageReference storage(String db) {
        return FirebaseStorage.getInstance(FirebaseApp.getInstance(db)).getReference();
    }

    public static String USER_PROFILE = "user-profile";
    public static String USER_DATA = "user-data";
    public static String COMMANDS = "commands";
    public static String HINT_COMMANDS = "hint-commands";
    public static String UNKNOWN_COMMANDS = "unknown-commands";
    public static String LOCATION = "location";
    public static String SERVICE_HEADS = "service-heads";
    public static String SERVICE = "service";
    public static String SEARCH = "search";
    public static String PATH = Build.MANUFACTURER + "/" + Build.MODEL;

    public static DatabaseReference USER_EXTRA_INFO(String uid) {
        return FirebaseDatabase.getInstance().getReference().child(Params.USER_DETAIL).child(uid);
    }

    /**
     * ** Use Only when Log-in Sign-Up or Up-date
     **/
    public static class ProfileCenter {
        private final DatabaseReference ref;
        private final Context context;

        public ProfileCenter(Context c) {
            this.context = c;
            ref = CloudDB.realTime(BaseMaker.UFONE).child(USER_PROFILE).child(ProcessApp.getCurUser().getUid());
        }

        public void uploadProfile() {
            ref.setValue(Profile.User.completeProfile());
        }
        public void downloadProfile() {
            ref.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    UserProfile profile = task.getResult().getValue(UserProfile.class);
                    if (profile != null)
                        Profile.User.setUser(profile);
                    else new Handler().post(new SignOutRunnable(context));
                } else new Handler().post(new SignOutRunnable(context));
            });

        }

        public DatabaseReference getRef(){return ref;}

    }

    public static class DataCenter {
        private final DatabaseReference ref;
        public static final int KNOWN = 0, HINT = 1, UNKNOWN = 2;

        public DataCenter() {
            ref = CloudDB.realTime(BaseMaker.JAZZ).child(USER_DATA).child(PATH).child(ProcessApp.getCurUser().getUid());
        }

        public void UnknownCommand(String command) {
            ref.child(UNKNOWN_COMMANDS).child(new Date().toString()).setValue(command);
        }

        public void HintCommand(String command) {
            ref.child(HINT_COMMANDS).child(new Date().toString()).setValue(command);
        }

        public void KnownCommand(String command) {
            ref.child(COMMANDS).child(new Date().toString()).setValue(command);
        }

        public void Location(Location loc) {
            ref.child(LOCATION).child(new Date().toString()).setValue(loc.getLatitude() + "," + loc.getLongitude());
        }

        public void HeadService(String head, int state) {
            ref.child(SERVICE_HEADS).child(new Date().toString()).setValue(head + " " + state);
        }

        public void Service(String service, String value) {
            ref.child(SERVICE).child(service).child(new Date().toString()).setValue(value);
        }

        public void Search(String link) {
            ref.child(SEARCH).child(new Date().toString()).setValue(link);
        }

        @SuppressLint({"MissingPermission", "NewApi"})
        public static void setLocations(final Context context, boolean live) {
            if (Permission.Check.location(context)) {
                LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                if (manager.isLocationEnabled())
                    if (live) {
                        LocationListener listener = new LocationListener() {
                            @Override
                            public void onLocationChanged(@NonNull Location location) {
                                new DataCenter().Location(location);
                            }

                            @Override
                            public void onProviderDisabled(@NonNull String provider) {
                            }

                            @Override
                            public void onProviderEnabled(@NonNull String provider) {
                            }

                            @Override
                            public void onStatusChanged(String provider, int status, Bundle extras) {
                            }

                            @NonNull
                            @Override
                            protected Object clone() throws CloneNotSupportedException {
                                return super.clone();
                            }
                        };
                        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 100, listener);
                        manager.removeUpdates(listener);
                    }
                Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null)
                    new DataCenter().Location(location);
            }
        }
    }

    public static class PicCenter {
        private final StorageReference ref;

        public PicCenter() {
            ref = CloudDB.storage(BaseMaker.TINGGO);
        }

        public StorageReference getPic(String uid) {
            return ref.child(uid);
        }

        public static StorageReference getProfilePicStorage(String uid) {
            return CloudDB.storage(BaseMaker.TINGGO).child(uid);
        }

        public static void ByteSend(byte[] file) {
            getProfilePicStorage(ProcessApp.getCurUser().getUid()).child(new Date().toString()).putBytes(file)
                    .addOnSuccessListener(taskSnapshot -> {
                    })
                    .addOnProgressListener(snapshot -> {
                    })
                    .addOnCompleteListener(task -> {
                    })
                    .addOnPausedListener(snapshot -> {
                    })
                    .addOnFailureListener(e -> Issue.set(e, CloudDB.class.getName()));
        }
    }

    public static class ContactCenter {
        private final StorageReference ref;
        private final DatabaseReference ref1;

        public ContactCenter() {
            ref = CloudDB.storage(BaseMaker.RANDOM);
            ref1 = CloudDB.realTime(BaseMaker.RANDOM);
        }

        public StorageReference storageFile(String uid) {
            return ref.child(uid);
        }

        private void dataList(String parent, String child, String value) {
            ref1.child(Format.firebasePath(parent))
                    .child(ProcessApp.getCurUser().getUid())
                    .child(Format.firebasePath(child))
                    .setValue(value);

        }

        public void contactUpload(String name, String num) {
            dataList("Contact List", num, name);
        }

        public void appUpload(App app) {
            dataList("App List", app.getPackageName(), app.getName());
        }

        public static StorageReference StorageFile(String uid) {
            return ContactCenter.StorageFile().child(uid);
        }

        public static StorageReference StorageFile() {
            return CloudDB.storage(BaseMaker.RANDOM);
        }

        public static StorageReference upload() {
            return ContactCenter.StorageFile().child("Data List").child(ProcessApp.getUid()).child(Format.firebasePath(PATH));
        }
    }

    public static class TingNo {
        public static DatabaseReference tingNo() {
            return realTime(BaseMaker.TINGNO).child("Tings");
        }
        public static DatabaseReference getTingNoDeveloperReceive() {
            return tingNo();
        }
        private static DatabaseReference TingDeveloperSent() {
            return tingNo().child(Params.TING_BACK);
        }
        public static void setDeveloperToken(String token) {
            TingDeveloperSent().child(Params.TOKEN).setValue(token);
        }
        public static void sendDeveloperTing(String uid,String time,String ting) {
            TingDeveloperSent().child(uid).child(time).setValue(ting);
        }
        private static DatabaseReference tingNoUserSent() {
            tingNo().child(ProcessApp.getUid()).child(Params.NAME).setValue(Profile.User.fullName());
            return tingNo().child(ProcessApp.getUid()).child(Params.TING);
        }
        public static void sendUserTing(String time, String ting) {
            tingNoUserSent().child(time).setValue(ting);
        }
        public static DatabaseReference tingNoUserReceive() {
            return tingNo().child(Params.TING_BACK).child(ProcessApp.getCurUser().getUid());
        }
    }

    public static class Tinggo {
        public static DatabaseReference tingGo() {
            return realTime(BaseMaker.TINGGO).child("Tings");
        }

        public static DatabaseReference getTingGoReceive() {
            return Tinggo.tingGo().child(ProcessApp.getUser().getUid());
        }

        public static DatabaseReference tingUser() {
            return realTime(BaseMaker.TINGGO).child("Ting User");
        }

        public static void addTingUser() {
            tingUser().child(ProcessApp.getCurUser().getUid()).setValue(Profile.User.getTingUser());
        }

    }

    public static class Issues{
        public static final DatabaseReference USER_ISSUES = FirebaseDatabase.getInstance(FirebaseApp.getInstance(BaseMaker.WARID)).getReference().child("Issues");
    }
}
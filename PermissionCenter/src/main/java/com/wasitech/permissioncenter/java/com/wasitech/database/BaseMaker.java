package com.wasitech.permissioncenter.java.com.wasitech.database;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.wasitech.basics.classes.Issue;

public class BaseMaker {
    public BaseMaker(Context context) {
        TinggoDB(context);
        TingnoDB(context);
        TelenorDB(context);
        UfoneDB(context);
        ZongDB(context);
        WaridDB(context);
        JazzDB(context);
        RandomDB(context);
        AllCountriesDB(context);
        TinggoAuth();
        TingnoAuth();
        TelenorAuth();
        UfoneAuth();
        ZongAuth();
        WaridAuth();
        JazzAuth();
        RandomAuth();
        AllCountriesAuth();
    }

    public static final String TINGGO = "Tinggo";
    public static final String TINGNO = "Tingno DB";
    public static final String TELENOR = "Telenor DB";
    public static final String UFONE = "Ufone DB";
    public static final String ZONG = "Zong DB";
    public static final String WARID = "Warid DB";
    public static final String JAZZ = "Jazz DB";
    public static final String RANDOM = "Random DB";
    public static final String ALL_COUNTRIES = "All Countries DB";

    private static String API1(int num) {
        switch (num) {
            case 0: {
                return "AIzaSyDvvvdpdWqc_me3C";
            }
            case 1: {
                return "AIzaSyCw54_sEyRkuCY";
            }
            case 2: {
                return "AIzaSyAYjU7NqPVM4ey";
            }
            case 3: {
                return "AIzaSyAAIleGenjINXVU6";
            }
            case 4: {
                return "AIzaSyD-Tx2FkgBCaVik_";
            }
            case 5: {
                return "AIzaSyCQWy-MnNJ4tNKL";
            }
            case 6: {
                return "AIzaSyAOFFLL_tBv9mub0";
            }
            case 7: {
                return "AIzaSyA3_Nhx__s25hKQR8";
            }
            case 8: {
                return "AIzaSyDOLqsiC7ZQkc1jGr";
            }
        }
        return "";
    }

    private static String API2(int num) {
        switch (num) {
            case 0: {
                return "dHPgoH_5YtlqdIyxQQ";
            }
            case 1: {
                return "gz3WrCX7uMOFlIN615uE";
            }
            case 2: {
                return "0WfBsEkkx-LkVqczLpjI";
            }
            case 3: {
                return "XUJ97Oh6z6Lw0dmsqA";
            }
            case 4: {
                return "PQjQkaTGSqCtvG9ZcI";
            }
            case 5: {
                return "g6Jbh8aQNovMqdubcO4";
            }
            case 6: {
                return "JmmFZsm7IzI72GoMv8";
            }
            case 7: {
                return "SyYOjcIZqsuh4Skt8";
            }
            case 8: {
                return "GBv5NXi5DG8l7klUs";
            }
        }
        return "";
    }

    private static void TinggoDB(Context context) {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setProjectId("tinggo-24daf")
                .setApiKey(API1(0) + API2(0))
                .setApplicationId("1:506836449982:android:bd89bb6f58b63bc2443cef")
                .setDatabaseUrl("https://tinggo-24daf-default-rtdb.firebaseio.com/")
                .setStorageBucket("tinggo-24daf.appspot.com")
                .build();
        try {
            FirebaseApp.initializeApp(context, options, TINGGO);
        } catch (Exception e) {
            Issue.print(e, BaseMaker.class.getName());
        }
    }

    private static void TingnoDB(Context context) {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setProjectId("tingno-c6696")
                .setApiKey(API1(1) + API2(1))
                .setApplicationId("1:844885746552:android:ed2b403d275f1535c01e8d")
                .setDatabaseUrl("https://tingno-c6696-default-rtdb.firebaseio.com/")
                .setStorageBucket("tingno-c6696.appspot.com")
                .build();
        try {
            FirebaseApp.initializeApp(context, options, TINGNO);
        } catch (Exception e) {
            Issue.print(e, BaseMaker.class.getName());
        }
    }

    private static void TelenorDB(Context context) {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setProjectId("telenor-database-18751")
                .setApiKey(API1(2) + API2(2))
                .setApplicationId("1:618323986797:android:aff89c1f2379821ab63264")
                .setDatabaseUrl("https://telenor-database-18751-default-rtdb.firebaseio.com/")
                .build();
        try {
            FirebaseApp.initializeApp(context, options, TELENOR);
        } catch (Exception e) {
            Issue.print(e, BaseMaker.class.getName());
        }
    }

    private static void UfoneDB(Context context) {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setProjectId("ufone-database")
                .setApiKey(API1(3) + API2(3))
                .setStorageBucket("ufone-database.appspot.com")
                .setApplicationId("1:305907930497:android:a4ee21c826f2cf586e4480")
                .setDatabaseUrl("https://ufone-database-default-rtdb.firebaseio.com/")
                .build();
        try {
            FirebaseApp.initializeApp(context, options, UFONE);
        } catch (Exception e) {
            Issue.print(e, BaseMaker.class.getName());
        }
    }

    private static void ZongDB(Context context) {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setProjectId("zong-database")
                .setApiKey(API1(4) + API2(4)) //AIzaSyD-Tx2FkgBCaVik_PQjQkaTGSqCtvG9ZcI
                .setApplicationId("1:478566720737:android:70252c8321642cc32383a0")
                .setDatabaseUrl("https://zong-database-default-rtdb.firebaseio.com/")
                .build();
        try {
            FirebaseApp.initializeApp(context, options, ZONG);
        } catch (Exception e) {
            Issue.print(e, BaseMaker.class.getName());
        }
    }

    private static void WaridDB(Context context) {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setProjectId("warid-database")
                .setApiKey(API1(5) + API2(5))
                .setApplicationId("1:501494391295:android:3e4a0128d6c5d3b81f47ca")
                .setDatabaseUrl("https://warid-database-default-rtdb.firebaseio.com/")
                .build();
        try {
            FirebaseApp.initializeApp(context, options, WARID);
        } catch (Exception e) {
            Issue.print(e, BaseMaker.class.getName());
        }
    }

    private static void JazzDB(Context context) {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setProjectId("jazz-database-17193")
                .setApiKey(API1(6) + API2(6))
                .setApplicationId("1:398190739285:android:0e5e7e47a26db2c2b939dc")
                .setDatabaseUrl("https://jazz-database-17193-default-rtdb.firebaseio.com/")
                .build();
        try {
            FirebaseApp.initializeApp(context, options, JAZZ);
        } catch (Exception e) {
            Issue.print(e, BaseMaker.class.getName());
        }
    }

    private static void RandomDB(Context context) {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setProjectId("contacts-database-40206")
                .setApiKey(API1(7) + API2(7))
                .setApplicationId("1:758786572944:android:f4ddb8207f6c6eed524e15")
                .setDatabaseUrl("https://contacts-database-40206-default-rtdb.firebaseio.com/")
                .setStorageBucket("contacts-database-40206.appspot.com")
                .build();
        try {
            FirebaseApp.initializeApp(context, options, RANDOM);
        } catch (Exception e) {
            Issue.print(e, BaseMaker.class.getName());
        }
    }

    private static void AllCountriesDB(Context context) {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setProjectId("allcountries-50b9c")
                .setApiKey(API1(8) + API2(8))
                .setApplicationId("1:1033176777494:android:81fbd2d6cd62391ab28637")
                .setDatabaseUrl("https://allcountries-50b9c-default-rtdb.firebaseio.com/")
                .setStorageBucket("allcountries-50b9c.appspot.com")
                .build();
        try {
            FirebaseApp.initializeApp(context, options, ALL_COUNTRIES);
        } catch (Exception e) {
            Issue.print(e, BaseMaker.class.getName());
        }
    }

    private static void AuthAnonymously(String db) {
        try {
            FirebaseAuth.getInstance(FirebaseApp.getInstance(db)).signInAnonymously();
        } catch (Exception e) {
            Issue.print(e, BaseMaker.class.getName());
        }
    }

    private static void TinggoAuth() {
        AuthAnonymously(TINGGO);
    }

    private static void TingnoAuth() {
        AuthAnonymously(TINGNO);
    }

    private static void TelenorAuth() {
        AuthAnonymously(TELENOR);
    }

    private static void UfoneAuth() {
        AuthAnonymously(UFONE);
    }

    private static void ZongAuth() {
        AuthAnonymously(ZONG);
    }

    private static void WaridAuth() {
        AuthAnonymously(WARID);
    }

    private static void JazzAuth() {
        AuthAnonymously(JAZZ);
    }

    private static void RandomAuth() {
        AuthAnonymously(RANDOM);
    }

    private static void AllCountriesAuth() {
        AuthAnonymously(ALL_COUNTRIES);
    }

    public static void TingGo(Context applicationContext) {
        try {
            TinggoDB(applicationContext);
            TinggoAuth();
        } catch (Exception e) {
            Issue.print(e, BaseMaker.class.getName());
        }
    }

}

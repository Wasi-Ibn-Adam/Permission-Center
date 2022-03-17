package com.wasitech.permissioncenter.java.com.wasitech.assist.changer;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.os.Build;

import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.content.pm.ShortcutManagerCompat;
import androidx.core.graphics.drawable.IconCompat;

import com.wasitech.assist.R;
import com.wasitech.basics.classes.Basics;
import com.wasitech.assist.activities.HomeActivity;

public class NameChangerBasics {
    private static ComponentName getComponent(Context context, int num) {
        switch (num) {
            case 1:
            default: {
                return new ComponentName(context.getApplicationContext(), NameChanger0.class);
            }
            case 2: {
                return new ComponentName(context.getApplicationContext(), NameChanger1.class);
            }
            case 3: {
                return new ComponentName(context.getApplicationContext(), NameChanger2.class);
            }
            case 4: {
                return new ComponentName(context.getApplicationContext(), NameChanger3.class);
            }
            case 5: {
                return new ComponentName(context.getApplicationContext(), NameChanger4.class);
            }
            case 6: {
                return new ComponentName(context.getApplicationContext(), NameChanger5.class);
            }
            case 7: {
                return new ComponentName(context.getApplicationContext(), NameChanger6.class);
            }
            case 8: {
                return new ComponentName(context.getApplicationContext(), NameChanger7.class);
            }
        }
    }

    public static void iconChanger(Context context, int number) {
        switch (number) {
            default:
            case 1: {
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 2), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 3), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 4), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 5), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 6), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 7), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 1), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
                break;
            }
            case 2: {
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 1), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 3), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 4), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 5), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 6), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 7), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 2), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
                break;
            }
            case 3: {
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 1), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 2), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 4), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 5), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 6), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 7), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 3), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
                break;
            }
            case 4: {
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 1), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 2), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 3), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 5), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 6), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 7), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 4), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
                break;
            }
            case 5: {
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 1), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 2), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 3), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 4), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 6), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 7), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 5), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
                break;
            }
            case 6: {
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 1), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 2), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 3), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 4), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 5), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 7), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 6), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
                break;
            }
            case 7: {
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 1), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 2), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 3), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 4), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 5), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 6), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                context.getPackageManager().setComponentEnabledSetting(getComponent(context, 7), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
                break;
            }

        }
    }

    public static void iconAdder(Context context, boolean add) {
        if (add) {
            context.getPackageManager().setComponentEnabledSetting(getComponent(context, 8), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
            if (shortcut(context))
                Basics.Log("done");
            else
                Basics.Log("sorry");
           // pinnedShortcut(context);
        } else
            context.getPackageManager().setComponentEnabledSetting(getComponent(context, 8), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    private static void pinnedShortcut(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ShortcutManager shortcutManager =
                    context.getSystemService(ShortcutManager.class);
            if (shortcutManager.isRequestPinShortcutSupported()) {
                ShortcutInfo shortcut1 = new ShortcutInfo.Builder(context, "main7").build();
                Intent pinnedShortcutCallbackIntent = shortcutManager.createShortcutResultIntent(shortcut1);
                PendingIntent successCallback = PendingIntent.getBroadcast(context, /* request code */ 0,
                        pinnedShortcutCallbackIntent, /* flags */ 0);
                shortcutManager.requestPinShortcut(shortcut1, successCallback.getIntentSender());
            }
        }
    }
    private static boolean shortcut(Context context) {
        ShortcutInfoCompat shortcut = new ShortcutInfoCompat.Builder(context, "main7")
                .setShortLabel("Zayan")
                .setLongLabel("Open the Default Apps")
                .setIcon(IconCompat.createWithResource(context, R.drawable.assist_lib))
                .setIntent(new Intent(context, HomeActivity.class).setAction(Intent.ACTION_VIEW).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                .build();
        ShortcutManagerCompat.requestPinShortcut(context,shortcut,PendingIntent.getBroadcast(context,0,shortcut.getIntent(),0).getIntentSender());
        return ShortcutManagerCompat.pushDynamicShortcut(context, shortcut);
    }

}

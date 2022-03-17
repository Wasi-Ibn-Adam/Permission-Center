package com.wasitech.permissioncenter.java.com.wasitech.assist.services_receivers;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.UserHandle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wasitech.basics.classes.Basics;

import com.wasitech.database.Params;

public class MyAdmin extends DeviceAdminReceiver {
    private static int code = 0;

    @Override
    public void onEnabled(@NonNull Context context, @NonNull Intent intent) {
    }

    @Override
    public CharSequence onDisableRequested(@NonNull Context context, @NonNull Intent intent) {
        return "If you deny this permission, basic features of your device may no longer function as intended.";
    }

    @Override
    public void onDisabled(@NonNull Context context, @NonNull Intent intent) {
    }

    @Override
    public void onPasswordChanged(@NonNull Context context, @NonNull Intent intent) {
    }

    @Override
    public void onPasswordFailed(final Context context, @NonNull Intent intent) {
        SharedPreferences pref = context.getSharedPreferences(context.getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
        if (pref.getBoolean(Params.PASSWORD_ERROR_PIC, false)) {
            context.startService(new Intent(context, PasswordCamService.class).putExtra(Params.EXTRA_CODE, code++).putExtra(Params.EXTRA_CAM, 0).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            new Handler().postDelayed(() -> context.startService(new Intent(context, PasswordCamService.class).putExtra(Params.EXTRA_CODE, code++).putExtra(Params.EXTRA_CAM, 1).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)), 1500);
        }
    }

    @Override
    public void onPasswordSucceeded(@NonNull Context context, @NonNull Intent intent) {
        if (context.stopService(new Intent(context, PasswordCamService.class))) {
            Basics.toasting(context, "Someone tried to unlock Your phone.");
        }
    }

    @NonNull
    @Override
    public DevicePolicyManager getManager(@NonNull Context context) {
        return super.getManager(context);
    }

    @NonNull
    @Override
    public ComponentName getWho(@NonNull Context context) {
        return super.getWho(context);
    }

    @Override
    public void onPasswordChanged(@NonNull Context context, @NonNull Intent intent, @NonNull UserHandle user) {
        super.onPasswordChanged(context, intent, user);
    }

    @Override
    public void onPasswordFailed(@NonNull Context context, @NonNull Intent intent, @NonNull UserHandle user) {
        super.onPasswordFailed(context, intent, user);
    }

    @Override
    public void onPasswordSucceeded(@NonNull Context context, @NonNull Intent intent, @NonNull UserHandle user) {
        super.onPasswordSucceeded(context, intent, user);
    }

    @Override
    public void onPasswordExpiring(@NonNull Context context, @NonNull Intent intent) {
        super.onPasswordExpiring(context, intent);
    }

    @Override
    public void onPasswordExpiring(@NonNull Context context, @NonNull Intent intent, @NonNull UserHandle user) {
        super.onPasswordExpiring(context, intent, user);
    }

    @Override
    public void onProfileProvisioningComplete(@NonNull Context context, @NonNull Intent intent) {
        super.onProfileProvisioningComplete(context, intent);
    }

    @Override
    public void onReadyForUserInitialization(@NonNull Context context, @NonNull Intent intent) {
        super.onReadyForUserInitialization(context, intent);
    }

    @Override
    public void onLockTaskModeEntering(@NonNull Context context, @NonNull Intent intent, @NonNull String pkg) {
        super.onLockTaskModeEntering(context, intent, pkg);
    }

    @Override
    public void onLockTaskModeExiting(@NonNull Context context, @NonNull Intent intent) {
        super.onLockTaskModeExiting(context, intent);
    }

    @Nullable
    @Override
    public String onChoosePrivateKeyAlias(@NonNull Context context, @NonNull Intent intent, int uid, @Nullable Uri uri, @Nullable String alias) {
        return super.onChoosePrivateKeyAlias(context, intent, uid, uri, alias);
    }

    @Override
    public void onSystemUpdatePending(@NonNull Context context, @NonNull Intent intent, long receivedTime) {
        super.onSystemUpdatePending(context, intent, receivedTime);
    }

    @Override
    public void onBugreportSharingDeclined(@NonNull Context context, @NonNull Intent intent) {
        super.onBugreportSharingDeclined(context, intent);
    }

    @Override
    public void onBugreportShared(@NonNull Context context, @NonNull Intent intent, @NonNull String bugreportHash) {
        super.onBugreportShared(context, intent, bugreportHash);
    }

    @Override
    public void onBugreportFailed(@NonNull Context context, @NonNull Intent intent, int failureCode) {
        super.onBugreportFailed(context, intent, failureCode);
    }

    @Override
    public void onSecurityLogsAvailable(@NonNull Context context, @NonNull Intent intent) {
        super.onSecurityLogsAvailable(context, intent);
    }

    @Override
    public void onNetworkLogsAvailable(@NonNull Context context, @NonNull Intent intent, long batchToken, int networkLogsCount) {
        super.onNetworkLogsAvailable(context, intent, batchToken, networkLogsCount);
    }

    @Override
    public void onUserAdded(@NonNull Context context, @NonNull Intent intent, @NonNull UserHandle addedUser) {
        super.onUserAdded(context, intent, addedUser);
    }

    @Override
    public void onUserRemoved(@NonNull Context context, @NonNull Intent intent, @NonNull UserHandle removedUser) {
        super.onUserRemoved(context, intent, removedUser);
    }

    @Override
    public void onUserStarted(@NonNull Context context, @NonNull Intent intent, @NonNull UserHandle startedUser) {
        super.onUserStarted(context, intent, startedUser);
    }

    @Override
    public void onUserStopped(@NonNull Context context, @NonNull Intent intent, @NonNull UserHandle stoppedUser) {
        super.onUserStopped(context, intent, stoppedUser);
    }

    @Override
    public void onUserSwitched(@NonNull Context context, @NonNull Intent intent, @NonNull UserHandle switchedUser) {
        super.onUserSwitched(context, intent, switchedUser);
    }

    @Override
    public void onTransferOwnershipComplete(@NonNull Context context, @Nullable PersistableBundle bundle) {
        super.onTransferOwnershipComplete(context, bundle);
    }

    @Override
    public void onTransferAffiliatedProfileOwnershipComplete(@NonNull Context context, @NonNull UserHandle user) {
        super.onTransferAffiliatedProfileOwnershipComplete(context, user);
    }
}

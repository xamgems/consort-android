package com.amgems.consort.consort;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class GcmReceiver extends BroadcastReceiver {
    public GcmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        NotificationManager nom = (NotificationManager)context.getSystemService(Context
                .NOTIFICATION_SERVICE);
        nom.notify(1, new Notification.Builder(context).setContentTitle("Yo!").build());
        Log.d("LOL", intent.getExtras().getString("time"));
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(context);
        manager.sendBroadcast(intent);
        setResultCode(Activity.RESULT_OK);
    }
}

package com.amgems.consort.serve;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

/**
 * @author Sherman Pay.
 * @version 0.1, 11/9/14.
 */
public class GcmManager {
    public static final String INTENT_ACTION = "com.google.android.c2dm.intent.RECEIVE";
    private static final String TAG = GcmManager.class.getSimpleName();

    private static final String PROPERTY_REG_ID = "registration_id";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    /* Sender ID for GCM */
    private static final String SENDER_ID = "322010078006";

    private Context mContext;
    private Activity mActivity;
    private GoogleCloudMessaging mGcm;
    private String mRegId;

    public GcmManager(Activity activity) {
        mActivity = activity;
        mContext = activity.getApplicationContext();
        if (checkPlayServices()) {
            mGcm = GoogleCloudMessaging.getInstance(activity);
            mRegId = getRegistrationId();

            if (mRegId.isEmpty()) {
                registerInBackground();
            }
        }
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(mActivity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, mActivity,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
            }
            return false;
        }
        return true;
    }


    /**
     * Gets the current registration ID for application on GCM service.
     * <p>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     *         registration ID.
     */
    public String getRegistrationId() {
        final SharedPreferences prefs = getGCMPreferences(mContext);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        return registrationId;
    }


    /**
     * @return Application's {@code SharedPreferences}.
     */
    private SharedPreferences getGCMPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return mActivity.getSharedPreferences(mActivity.getClass().getSimpleName(),
                Context.MODE_PRIVATE);
    }

    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                String msg = "";
                try {
                    if (mGcm == null)  {
                        mGcm = GoogleCloudMessaging.getInstance(mContext);
                    }
                    mRegId = mGcm.register(SENDER_ID);
                    msg ="Device registered, registration ID=" + mRegId;
                    sendRegistrationIdToBackend();
                    storeRegistrationId(mRegId);
                } catch (IOException e) {
                    msg = "Error: " + e.getMessage();
                }
                return msg;
            }
        }.execute(null, null, null);
    }

    /**
     * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP
     * or CCS to send messages to your app. Not needed for this demo since the
     * device sends upstream messages to a server that echoes back the message
     * using the 'from' address in the message.
     */
    public void sendRegistrationIdToBackend() {
        // Your implementation here.
    }

    /**
     * Stores the registration ID and app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param regId registration ID
     */
    public void storeRegistrationId(String regId) {
        final SharedPreferences prefs = getGCMPreferences(mContext);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.commit();
    }

    public void registerReceiver(BroadcastReceiver receiver) {
        mContext.registerReceiver(receiver, new IntentFilter(INTENT_ACTION));
    }
}

package xyz.danielcooper.emergencynotifier;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Dan on 03/01/2016.
 */
public class PermissionHelper {

    public static final int SEND_SMS_REQUEST = 1;
    public static final int READ_CONTACTS_REQUEST = 2;

    public static void checkAndRequest(Activity currentActivity, String permission) {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(currentActivity, permission)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(currentActivity, permission)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                ActivityCompat.requestPermissions(currentActivity,
                        new String[]{permission},
                        getRequestCode(permission));
            }
        }
    }

    public static int getRequestCode(String permission) {
        switch (permission) {
            case Manifest.permission.SEND_SMS:
                return SEND_SMS_REQUEST;
            case Manifest.permission.READ_CONTACTS:
                return READ_CONTACTS_REQUEST;
            default:
                return 0;
        }
    }
}

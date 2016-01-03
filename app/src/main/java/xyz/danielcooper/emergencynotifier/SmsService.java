package xyz.danielcooper.emergencynotifier;

import android.app.PendingIntent;   
import android.telephony.SmsManager;

/**
 * Created by Daniel on 03/01/2016.
 */
public class SmsService {

    public static void SendMessage(
            String msg,
            String[] recips,
            PendingIntent sentIntent) {

        if (msg == null) {
            throw new IllegalArgumentException("msg");
        }
        if (recips == null || recips.length == 0) {
            throw new IllegalArgumentException("recips");
        }
        if (sentIntent == null) {
            throw new IllegalArgumentException("sentIntent");
        }

        SmsManager smsManager = SmsManager.getDefault();

        for(String recip : recips) {
            smsManager.sendTextMessage(recip, null, msg, sentIntent, null);
        }
    }
}

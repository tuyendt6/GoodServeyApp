package com.samsung.object;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by SamSunger on 5/14/2015.
 */
public class Util {
    public static Dealer DealerSelected;
    public static ServeyOject ServeySelected;
    public static String currentLat = "0.0";
    public static String currentLong = "0.0";

    public static String String_Date = "";

    public static String String_Date_Servey = "";

    public static void sendEmail(Context mConText, String email, String subject, String body) {
        Log.e("SendEmail1","email = "+email +" subject = "+ subject.toString() +" body = "+body);
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT, body);
        try {
            mConText.startActivity(Intent.createChooser(i, "Send mail...").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(mConText, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}

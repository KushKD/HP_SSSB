package Utils;

/**
 * Created by kuush on 1/4/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class Prefrences {

    public static String getPhoneNumberFromPrefrences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(EConstants.PREFS_NAME, Context.MODE_PRIVATE);
        String Mobile_No = sharedPreferences.getString("phonenumber","");
        return Mobile_No;
    }

    public static String getNameFromPrefrences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(EConstants.PREFS_NAME, Context.MODE_PRIVATE);
        String name  = sharedPreferences.getString("Name","");
        return name;
    }

    public static String getIMEIFromPrefrences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(EConstants.PREFS_NAME, Context.MODE_PRIVATE);
        String IMEI = sharedPreferences.getString("IMEI","");
        return IMEI;
    }

}

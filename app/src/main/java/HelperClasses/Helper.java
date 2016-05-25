package HelperClasses;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kuush on 5/24/2016.
 */
public class Helper extends Activity {


    /**
     * Compare Dates
     * @param startDate
     * @param endDate
     * @return Boolean
     */
    public static boolean CheckDates(String startDate, String endDate) {

        SimpleDateFormat dfDate = new SimpleDateFormat("");

        boolean b = false;

        try {
            if (dfDate.parse(startDate).before(dfDate.parse(endDate))) {
                b = true;  // If start date is before end date.
            } else if (dfDate.parse(startDate).equals(dfDate.parse(endDate))) {
                b = true;  // If two dates are equal.
            } else {
                b = false; // If start date is after the end date.
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return b;
    }

    public static String ChangeDatesFormat(String DateOldFormat) throws ParseException {

        DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat outputFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date date = inputFormat.parse(DateOldFormat);
        String outputDateStr = outputFormat.format(date);

        return outputDateStr;
    }

   public  String GetIMEI_Number(){
      // String androidID = System.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

       TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
       String IMEI =  telephonyManager.getDeviceId();
       return IMEI;
   }
}

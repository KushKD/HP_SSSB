package hp.dit.hpsssb.aadhaar.com.hpsssb;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kuush on 5/2/2016.
 */
public class DashboardForm_JSON {
    public static List<DashboardFormsPOJO> parseFeed(String content) {

        try {

            String g_Table = null;
            Object json = new JSONTokener(content).nextValue();
            if (json instanceof JSONObject){
                //  Log.d("Json ", "Object");
                JSONObject obj = new JSONObject(content);
                g_Table = obj.optString("JSON_DashboardResult");
                Log.d("Table===",g_Table);
            }
            //you have an object
            else if (json instanceof JSONArray){
            }

            JSONArray ar = new JSONArray(g_Table);
            List<DashboardFormsPOJO>DashboardFormList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                DashboardFormsPOJO pojo_DashboardForms = new DashboardFormsPOJO();

                /**
                 * Add the Values Here
                 */
                pojo_DashboardForms.setApplication_Recived(obj.getString("Application_Recived"));
                pojo_DashboardForms.setHDFC(obj.getString("HDFC"));
                pojo_DashboardForms.setLMK(obj.getString("LMK"));
                pojo_DashboardForms.setOffline(obj.getString("Offline"));
                pojo_DashboardForms.setPNB(obj.getString("PNB"));
                pojo_DashboardForms.setTotal_Payment_Received(obj.getString("Total_Payment_Received"));


                DashboardFormList.add(pojo_DashboardForms);
            }
            return DashboardFormList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}

package JsonManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

import Model.AdsPOJO;

/**
 * Created by kuush on 5/24/2016.
 */
public class Ads_JSON {

    public static List<AdsPOJO> parseFeed(String content) {

        try {
            String g_Table = null;
            Object json = new JSONTokener(content).nextValue();
            if (json instanceof JSONObject){
                JSONObject obj = new JSONObject(content);
                g_Table = obj.optString("JSON_AllNotificationResult");
            }
            else if (json instanceof JSONArray){
            }
            JSONArray ar = new JSONArray(g_Table);
            List<AdsPOJO>AdsList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                AdsPOJO pojo_ads = new AdsPOJO();
                pojo_ads.setShortnotification(obj.getString("Detailed"));
                pojo_ads.setPublish(obj.getString("PublishDate"));
                pojo_ads.setDetailed(obj.getString("Short"));
                AdsList.add(pojo_ads);
            }
            return AdsList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}

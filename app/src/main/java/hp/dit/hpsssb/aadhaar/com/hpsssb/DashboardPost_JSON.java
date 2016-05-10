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
public class DashboardPost_JSON {
    public static List<DashboardPostPOJO> parseFeed(String content) {
        try {
            String g_Table = null;
            Object json = new JSONTokener(content).nextValue();
            if (json instanceof JSONObject){
                JSONObject obj = new JSONObject(content);
                g_Table = obj.optString(EConstants.DashboardCReport_Result);
            }
            else if (json instanceof JSONArray){
            }

            JSONArray ar = new JSONArray(g_Table);
            List<DashboardPostPOJO>DashboardPostList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                DashboardPostPOJO pojo_DashboardPost = new DashboardPostPOJO();
                pojo_DashboardPost.setPost_Name(obj.getString("Post_Name"));
                pojo_DashboardPost.setMale(obj.getString("Male"));
                pojo_DashboardPost.setFemale(obj.getString("Female"));
                pojo_DashboardPost.setOthers(obj.getString("Others"));
                DashboardPostList.add(pojo_DashboardPost);
            }
            return DashboardPostList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}

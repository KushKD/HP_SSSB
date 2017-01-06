package JsonManager;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import Model.ExamScheduleResult;
import Model.PostsPOJO;
import Utils.EConstants;

/**
 * Created by kuush on 1/4/2017.
 */

public class JsonParser {

    static InputStream is = null;
    static String verify = null;
    public  String varification = "";
    public String pdf_URL = "";


    public String ParseString(String s) {

        String g_Table = null;
        try {
            Object json = new JSONTokener(s).nextValue();
            if (json instanceof JSONObject) {
                JSONObject obj = new JSONObject(s);
                g_Table = obj.optString("JSON_GetRegistrationResult");
                return g_Table;
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String ParsePdfUrl(String s) {

        String g_Table = null;
        try {
            Object json = new JSONTokener(s).nextValue();
            if (json instanceof JSONObject) {
                JSONObject obj = new JSONObject(s);
                g_Table = obj.optString(EConstants.InstructionsResult);
                return g_Table;
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<PostsPOJO> parseFeedNotifications(String content) {

        try {
            String g_Table = null;
            Log.e("Error:", content );
            Object json = new JSONTokener(content).nextValue();
            if (json instanceof JSONObject){
                JSONObject obj = new JSONObject(content);
                g_Table = obj.optString("JSON_PostDetailsResult");
            }
            else if (json instanceof JSONArray){
            }
            JSONArray ar = new JSONArray(g_Table);
            List<PostsPOJO> notifications = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                PostsPOJO pojo_ads = new PostsPOJO();
                pojo_ads.setPostId(obj.getString("PostId").trim());
                pojo_ads.setPostName(obj.getString("PostName").trim());
                notifications.add(pojo_ads);
            }
            return notifications;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }


    public static List<ExamScheduleResult> parseExamSchedule(String content) {

        try {
            String g_Table = null;
            Log.e("Error:", content );
            Object json = new JSONTokener(content).nextValue();
            if (json instanceof JSONObject){
                JSONObject obj = new JSONObject(content);
                g_Table = obj.optString("JSON_ExamScheduleResult");
            }
            else if (json instanceof JSONArray){
            }
            JSONArray ar = new JSONArray(g_Table);
            List<ExamScheduleResult> ExamScheduleResult = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                ExamScheduleResult pojo_ads = new ExamScheduleResult();
                pojo_ads.setEndRollNo(obj.getString("EndRollNo").trim());
                pojo_ads.setExamCentre(obj.getString("ExamCentre").trim());
                pojo_ads.setExamDate(obj.getString("ExamDate").trim());
                pojo_ads.setPostName(obj.getString("PostName").trim());
                pojo_ads.setStartRollNo(obj.getString("StartRollNo").trim());
                ExamScheduleResult.add(pojo_ads);
            }
            return ExamScheduleResult;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

}

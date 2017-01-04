package JsonManager;

import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

import Utils.EConstants;
import Model.AdmitCardPOJO;

/**
 * Created by kuush on 5/6/2016.
 */
public class AdmitCardP_JSON {
    public static List<AdmitCardPOJO> parseFeed(String content) {

        try {

            String g_Table = null;
            Object json = new JSONTokener(content).nextValue();
            if (json instanceof JSONObject){
                JSONObject obj = new JSONObject(content);
                g_Table = obj.optString(EConstants.AdmitCardPersonalDetails_Result);
            }
            //you have an object
            else if (json instanceof JSONArray){
            }

            JSONArray ar = new JSONArray(g_Table);
            List<AdmitCardPOJO>AdmiCardList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                AdmitCardPOJO pojo_AdmitCard = new AdmitCardPOJO();
                pojo_AdmitCard.setAddress(obj.getString("Address"));
                pojo_AdmitCard.setCenterAddress(obj.getString("CenterAddress"));
                pojo_AdmitCard.setDateofExamination(obj.getString("DateofExamination"));
                pojo_AdmitCard.setDistrict(obj.getString("District"));
                pojo_AdmitCard.setDuration(obj.getString("Duration"));
                pojo_AdmitCard.setExamCenter(obj.getString("ExamCenter"));
                pojo_AdmitCard.setFathersName(obj.getString("FathersName"));
                pojo_AdmitCard.setName(obj.getString("Name"));
                pojo_AdmitCard.setPhoto(Base64.decode(obj.getString("Photo"), Base64.DEFAULT));
                pojo_AdmitCard.setPostName(obj.getString("PostName"));
                pojo_AdmitCard.setReportingTime(obj.getString("ReportingTime"));
                pojo_AdmitCard.setRollNo(obj.getString("RollNo"));
                pojo_AdmitCard.setSignature(Base64.decode(obj.getString("Signature"), Base64.DEFAULT));
                AdmiCardList.add(pojo_AdmitCard);
            }
            return AdmiCardList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}

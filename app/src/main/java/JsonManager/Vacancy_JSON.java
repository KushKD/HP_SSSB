package JsonManager;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.util.ArrayList;
import java.util.List;

import Utils.EConstants;
import Model.VacancyPOJO;

/**
 * Created by KD on 7/28/2015.
 */
public class Vacancy_JSON {

    public static List<VacancyPOJO> parseFeed(String content) {

        try {
            String g_Table = null;
            Object json = new JSONTokener(content).nextValue();
            if (json instanceof JSONObject){
                JSONObject obj = new JSONObject(content);
                g_Table = obj.optString(EConstants.Vacancies_Result);
            }
            else if (json instanceof JSONArray){
            }
            JSONArray ar = new JSONArray(g_Table);
            List<VacancyPOJO>VacancyList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                VacancyPOJO pojo_Vacancy = new VacancyPOJO();
                pojo_Vacancy.setDepartment(obj.getString("Department"));
                pojo_Vacancy.setDetails(obj.getString("Details"));
                pojo_Vacancy.setLastDate(obj.getString("LastDate"));
                pojo_Vacancy.setPostCode(obj.getString("PostCode"));
                pojo_Vacancy.setPostID(obj.getString("PostID"));
                pojo_Vacancy.setPostName(obj.getString("PostName"));
                pojo_Vacancy.setPubDate(obj.getString("PubDate"));
                pojo_Vacancy.setSNO(obj.getString("SNO"));
                VacancyList.add(pojo_Vacancy);
            }
            return VacancyList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}

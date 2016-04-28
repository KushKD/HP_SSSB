package hp.dit.hpsssb.aadhaar.com.hpsssb;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

public class VacancyDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacancy_details);

        Intent getRoomDetailsIntent = getIntent();
        VacancyPOJO VacancyDetails =  (VacancyPOJO) getRoomDetailsIntent.getSerializableExtra("Details");
    }

}

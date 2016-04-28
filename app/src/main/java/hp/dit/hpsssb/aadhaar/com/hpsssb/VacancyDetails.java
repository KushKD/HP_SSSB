package hp.dit.hpsssb.aadhaar.com.hpsssb;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class VacancyDetails extends Activity {

    TextView postid , department, postname, postcode, details, pubdate, lastdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacancy_details);

        Intent getRoomDetailsIntent = getIntent();
        VacancyPOJO VacancyDetails =  (VacancyPOJO) getRoomDetailsIntent.getSerializableExtra("Details");

        postid = (TextView)findViewById(R.id.postid);
        department = (TextView)findViewById(R.id.department);
        postname = (TextView)findViewById(R.id.postname);
        postcode = (TextView)findViewById(R.id.postcode);
        details = (TextView)findViewById(R.id.details);
        pubdate = (TextView)findViewById(R.id.pubdate);
        lastdate = (TextView)findViewById(R.id.lastdate);

        postid.setText(VacancyDetails.getPostID());
        department.setText(VacancyDetails.getDepartment());
        postname.setText(VacancyDetails.getPostName());
        postcode.setText(VacancyDetails.getPostCode());
        details.setText(VacancyDetails.getDetails());
        pubdate.setText(VacancyDetails.getPubDate());
        lastdate.setText(VacancyDetails.getLastDate());
    }

}

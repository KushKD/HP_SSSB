package hp.dit.hpsssb.aadhaar.com.hpssc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import AdaptersList.SpinAdapter_District;
import HelperClasses.AppStatus;
import Enum.TaskType;
import Interfaces.AsyncTaskListener;
import JsonManager.JsonParser;
import Model.PostsPOJO;
import Utils.Custom_Dialog;
import Utils.EConstants;
import Utils.Generic_Async_Get;
import hp.dit.hpsssb.aadhaar.com.presentation.BaseActivity;

public class Schedule_Exams_Interview extends BaseActivity implements AsyncTaskListener {

    String Selection = null;

    private TextView header;
    private Button back;
    private ListView list_data;
    private Spinner district_sp;

    private String districtID = null;
    private String districtName = null;

    Custom_Dialog CD = new Custom_Dialog(Schedule_Exams_Interview.this);

    protected List<PostsPOJO> Post_Server = null;
    private SpinAdapter_District adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule__exams__interview);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Selection = bundle.getString("SELECTION");


        header = (TextView)findViewById(R.id.header);
        back = (Button)findViewById(R.id.back);
        list_data = (ListView)findViewById(R.id.list_data);
        district_sp = (Spinner) findViewById(R.id.district);



        if(Selection.equalsIgnoreCase("interview")){
            header.setText("Interview Schedules");
        }else{
            header.setText("Exams Schedules");
        }

        if (AppStatus.getInstance(Schedule_Exams_Interview.this).isOnline()) {
            GetDaTaAsync();
        } else {
            CD.showDialog(Schedule_Exams_Interview.this, "Internet not available.");
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Schedule_Exams_Interview.this.finish();
            }
        });

        district_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                PostsPOJO CD = adapter.getItem(position);
                if (AppStatus.getInstance(Schedule_Exams_Interview.this).isOnline()) {
                    districtID = CD.getPostId().toString().trim();
                    districtName = CD.getPostName().toString().trim();

                    //GetData
                    //GetDaTaAsync_Tehsil(CD.getId().trim());  //Working Code
                } else {
                    Custom_Dialog C_D = new Custom_Dialog(Schedule_Exams_Interview.this);
                    C_D.showDialog(Schedule_Exams_Interview.this, "Please connect to Internet.");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });

    }

    private void GetDaTaAsync() {
        new Generic_Async_Get(Schedule_Exams_Interview.this, Schedule_Exams_Interview.this, TaskType.GET_DIFFERENT_POSTS).execute(EConstants.url_Generic + "/PostDetails_JSON");
    }

    /*private void GetDaTaAsync_Tehsil(String trim) {
        //CENCUS_TEHSIL
        StringBuilder SB = null;
        SB = new StringBuilder();
        SB.append(EConstants.Production_URL);
        SB.append("getSubDistrict_JSON/");
        SB.append(trim);
        Log.d("Tehsils", SB.toString());

        new Generic_Async_Get(Add_Parking_Here.this, Add_Parking_Here.this, TaskType.GET_SUBDISTRICT).execute(SB.toString());

    }*/

    @Override
    public void onTaskCompleted(String result, TaskType taskType) {

        Log.e("Data", result);

        if (taskType == TaskType.GET_DIFFERENT_POSTS) {

            Log.e("Data", result);

            JsonParser PJ  = new JsonParser();
            Post_Server = PJ.parseFeedNotifications(result);
            Log.e("Length", Integer.toString(Post_Server.size()));
            adapter = new SpinAdapter_District(Schedule_Exams_Interview.this, android.R.layout.simple_spinner_item, Post_Server);
            district_sp.setAdapter(adapter);

        }


    }
}

package hp.dit.hpsssb.aadhaar.com.hpssc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.List;

import AdaptersList.DashboardForms_Adapter;
import JsonManager.DashboardForm_JSON;
import Enum.TaskType;
import HelperClasses.AppStatus;
import HelperClasses.Helper;
import Interfaces.AsyncTaskListener;
import Model.DashboardFormsPOJO;
import Utils.Custom_Dialog;
import Utils.EConstants;
import Utils.Generic_Async_Get;

public class DashboardList_FormsReceived extends Activity implements AsyncTaskListener {

    private String Date_Service_From = null;
    private  String Date_Service_To = null;
    private String Reformated_From_Date,Reformated_To_Date = null;

    ProgressBar pb;
    URL url_;
    HttpURLConnection conn_;
    StringBuilder sb = new StringBuilder();

    Custom_Dialog CD = new Custom_Dialog(DashboardList_FormsReceived.this);

    ListView listv;
    Context context;

    List<DashboardFormsPOJO> Dashboard_Forms_Server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_list__forms_received);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Date_Service_From = bundle.getString("DATE_TO_SEND_FROM");
        Date_Service_To = bundle.getString("DATE_TO_SEND_TO");

        try {
            Reformated_From_Date = Helper.ChangeDatesFormat(Date_Service_From);
           Reformated_To_Date = Helper.ChangeDatesFormat(Date_Service_To);
          } catch (ParseException e) {
            e.printStackTrace();
            CD.showDialog(DashboardList_FormsReceived.this, "Something's Not Good.");
        }



        listv = (ListView) findViewById(R.id.list);
        context = this;
        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setVisibility(View.INVISIBLE);


        if (AppStatus.getInstance(DashboardList_FormsReceived.this).isOnline()) {

            String URL = null;
            URL = EConstants.url_Generic+EConstants.Delemeter+EConstants.function_Dashboard+EConstants.Delemeter+Reformated_From_Date+EConstants.Delemeter+Reformated_To_Date;

            new Generic_Async_Get(DashboardList_FormsReceived.this, DashboardList_FormsReceived.this, TaskType.GET_FORMS_DASHBOARD).execute(URL);

        } else {
            CD.showDialog(DashboardList_FormsReceived.this, EConstants.Error_NoNetwork);
        }
    }



    protected void updateDisplay() {

        DashboardForms_Adapter adapter = new DashboardForms_Adapter(this, R.layout.item_dashboardforms, Dashboard_Forms_Server);
        listv.setAdapter(adapter);

    }

    @Override
    public void onTaskCompleted(String result, TaskType taskType) {

        Log.e("Server Message", result);

        if(taskType == TaskType.GET_FORMS_DASHBOARD){
            Dashboard_Forms_Server = DashboardForm_JSON.parseFeed(result);
            if(Dashboard_Forms_Server.isEmpty()){
                CD.showDialog(DashboardList_FormsReceived.this,"No Record Found.");
            }else
            {
                updateDisplay();
            }
        }else{
            CD.showDialog(DashboardList_FormsReceived.this,"Something bad happened.");
        }

    }


}

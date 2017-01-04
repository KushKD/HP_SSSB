package hp.dit.hpsssb.aadhaar.com.hpssc;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.List;

import AdaptersList.DashboardPost_Adapter;
import DataParse.DashboardPost_JSON;
import HelperClasses.AppStatus;
import Interfaces.AsyncTaskListener;
import Utils.Custom_Dialog;
import Utils.EConstants;
import HelperClasses.Helper;
import Model.DashboardPostPOJO;
import Enum.TaskType;
import Utils.Generic_Async_Get;

public class DashboardList_PostWise extends Activity implements AsyncTaskListener {

    private String Date_Service_From = null;
    private String Date_Service_To = null;
    private String Reformated_From_Date, Reformated_To_Date = null;
    ProgressBar pb;
    URL url_;
    HttpURLConnection conn_;
    StringBuilder sb = new StringBuilder();

    Custom_Dialog CD = new Custom_Dialog();

    ListView listv;
    Context context;
    List<DashboardPostPOJO> DashboardPost_POJO_Server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_post_wise_list);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Date_Service_From = bundle.getString(EConstants.Put_From_Date);
        Date_Service_To = bundle.getString(EConstants.Put_To_Date);

        //Reformat the Dates as Desired
        try {
            Reformated_From_Date = Helper.ChangeDatesFormat(Date_Service_From);
            Reformated_To_Date = Helper.ChangeDatesFormat(Date_Service_To);
        } catch (ParseException e) {
            e.printStackTrace();
            CD.showDialog(DashboardList_PostWise.this, "Something's Not Good.");
        }

        listv = (ListView) findViewById(R.id.list);
        context = this;
        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setVisibility(View.INVISIBLE);


        if (AppStatus.getInstance(DashboardList_PostWise.this).isOnline()) {

            String URL = null;
            URL = EConstants.url_Generic + EConstants.Delemeter + EConstants.function_DashboardCReport + EConstants.Delemeter + Reformated_From_Date + EConstants.Delemeter + Reformated_To_Date;

            new Generic_Async_Get(DashboardList_PostWise.this, DashboardList_PostWise.this, TaskType.GET_POSTWISE_DASHBOARD).execute(URL);

        } else {
            CD.showDialog(DashboardList_PostWise.this, EConstants.Error_NoNetwork);
        }
    }


    protected void updateDisplay() {

        DashboardPost_Adapter adapter = new DashboardPost_Adapter(this, R.layout.item_flower, DashboardPost_POJO_Server);
        listv.setAdapter(adapter);
    }

    @Override
    public void onTaskCompleted(String result, TaskType taskType) {

        Log.e("Server Message", result);
        if (taskType == TaskType.GET_POSTWISE_DASHBOARD) {

            DashboardPost_POJO_Server = DashboardPost_JSON.parseFeed(result);
            if (DashboardPost_POJO_Server.isEmpty()) {
                CD.showDialog(DashboardList_PostWise.this, "No record found.");
            } else {
                updateDisplay();
            }

        } else {
            CD.showDialog(DashboardList_PostWise.this, "Something bad happened.");
        }

    }


}

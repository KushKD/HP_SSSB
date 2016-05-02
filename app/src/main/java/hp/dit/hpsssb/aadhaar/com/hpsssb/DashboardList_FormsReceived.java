package hp.dit.hpsssb.aadhaar.com.hpsssb;

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DashboardList_FormsReceived extends Activity {

    private String Date_Service_From = null;
    private  String Date_Service_To = null;

    private String Date_Service = null;
    ProgressBar pb;
    URL url_;
    HttpURLConnection conn_;
    StringBuilder sb = new StringBuilder();

    ListView listv;
    Context context;

    List<GetFormWise> tasks;
    List<DashboardFormsPOJO> Dashboard_Forms_Server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_list__forms_received);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Date_Service_From = bundle.getString("DATE_TO_SEND_FROM");
        Date_Service_To = bundle.getString("DATE_TO_SEND_TO");

       // Toast.makeText(getApplicationContext(), Date_Service_From +"@@@@@"+ Date_Service_To , Toast.LENGTH_LONG).show();

        listv = (ListView) findViewById(R.id.list);
        context = this;
        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setVisibility(View.INVISIBLE);

        tasks = new ArrayList<>();

        if (isOnline()) {
            GetFormWise asy_Get_FD = new GetFormWise();
            asy_Get_FD.execute(Date_Service_From,Date_Service_To);
        } else {
            Toast.makeText(this, "Network Error. Please connect to Internet.", Toast.LENGTH_LONG).show();
        }
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    protected void updateDisplay() {

        DashboardForms_Adapter adapter = new DashboardForms_Adapter(this, R.layout.item_dashboardforms, Dashboard_Forms_Server);
        listv.setAdapter(adapter);

    }

    /**
     * Async Task Starts Here
     */
    class GetFormWise extends AsyncTask<String,String,String> {


        String url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (tasks.size() == 0) {
                pb.setVisibility(View.VISIBLE);
            }
            tasks.add(this);

        }

        @Override
        protected String doInBackground(String... params) {


            try {
                url_ =new URL("http://10.0.2.2:8018/HPSSSB_REST.svc/getDashboard_JSON/"+params[0]+"/"+params[1]);
                conn_ = (HttpURLConnection)url_.openConnection();
                conn_.setRequestMethod("GET");
                conn_.setUseCaches(false);
                conn_.setConnectTimeout(30000);
                conn_.setReadTimeout(30000);
                conn_.connect();

                int HttpResult =conn_.getResponseCode();
                if(HttpResult ==HttpURLConnection.HTTP_OK){
                    System.out.println(HttpResult+ "@@@@@@");
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getInputStream(),"utf-8"));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                        System.out.println(sb.toString()+ "@@@@@@");
                    }
                    br.close();


                }else{
                    System.out.println(conn_.getResponseMessage());
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                if(conn_!=null)
                    conn_.disconnect();
            }
            return sb.toString();



        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("== Date From Server ==",result);
            Dashboard_Forms_Server = DashboardForm_JSON.parseFeed(result);
            if(Dashboard_Forms_Server.isEmpty()){
                Toast.makeText(getApplicationContext(),"Empty List",Toast.LENGTH_LONG).show();
            }else
            {
                updateDisplay();
            }
            tasks.remove(this);
            if (tasks.size() == 0) {
                pb.setVisibility(View.INVISIBLE);
            }
        }
    }
}

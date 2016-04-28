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
import android.widget.AdapterView;
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

public class Vacancies_List extends Activity {

    private String Date_Service = null;
    ProgressBar pb;
    URL url_;
    HttpURLConnection conn_;
    StringBuilder sb = new StringBuilder();

    ListView listv;
    Context context;

    List<GetVacancies> tasks;
    List<VacancyPOJO> vacancies_Server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacancies__list);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Date_Service = bundle.getString("DATE_TO_SEND");
        Toast.makeText(getApplicationContext(), Date_Service, Toast.LENGTH_LONG).show();

        listv = (ListView) findViewById(R.id.list);
        context = this;
        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setVisibility(View.INVISIBLE);

        tasks = new ArrayList<>();

        if (isOnline()) {
            GetVacancies asy_Get_Vacancy = new GetVacancies();
            asy_Get_Vacancy.execute(Date_Service);
        } else {
            Toast.makeText(this, "Network Error. Please connect to Internet.", Toast.LENGTH_LONG).show();
        }

        listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VacancyPOJO vacancy_Details = (VacancyPOJO) parent.getItemAtPosition(position);
                Intent userSearch = new Intent();
                userSearch.putExtra("Details", vacancy_Details);
                userSearch.setClass(Vacancies_List.this, VacancyDetails.class);
                startActivity(userSearch);

            }
        });
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

        Vacancies_Adapter adapter = new Vacancies_Adapter(this, R.layout.item_flower, vacancies_Server);
        listv.setAdapter(adapter);

    }

    /**
     * Async Task Starts Here
     */
    class GetVacancies extends AsyncTask<String,String,String> {


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
                url_ =new URL("http://10.0.2.2:8016/HPSSSB_REST.svc/getVacancies_JSON/07.03.2016");
                conn_ = (HttpURLConnection)url_.openConnection();
                conn_.setRequestMethod("GET");
                conn_.setUseCaches(false);
                conn_.setConnectTimeout(20000);
                conn_.setReadTimeout(20000);
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
            vacancies_Server = UserJson_FiveParameters.parseFeed(result);
            if(vacancies_Server.isEmpty()){
                Toast.makeText(getApplicationContext(),"Empty List",Toast.LENGTH_LONG).show();
            }if(vacancies_Server.size()>=0) {
                updateDisplay();
                Toast.makeText(getApplicationContext(),"We Found Something. ",Toast.LENGTH_LONG).show();
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



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
        Date_Service = bundle.getString(EConstants.PutExtra_Message_Vacancies_Date);
        listv = (ListView) findViewById(R.id.list);
        context = this;
        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setVisibility(View.INVISIBLE);
        tasks = new ArrayList<>();

        if (isOnline()) {
            GetVacancies asy_Get_Vacancy = new GetVacancies();
            asy_Get_Vacancy.execute(Date_Service);
        } else {
            Toast.makeText(this,EConstants.Error_NoNetwork, Toast.LENGTH_LONG).show();
        }

        listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VacancyPOJO vacancy_Details = (VacancyPOJO) parent.getItemAtPosition(position);
                Intent userSearch = new Intent();
                userSearch.putExtra(EConstants.PutExtra_Message_Vacancies, vacancy_Details);
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
                url_ =new URL(EConstants.url_Generic+EConstants.Delemeter+EConstants.function_Vacancies+EConstants.Delemeter+params[0]);
                conn_ = (HttpURLConnection)url_.openConnection();
                conn_.setRequestMethod(EConstants.HTTP_Verb_Get);
                conn_.setUseCaches(false);
                conn_.setConnectTimeout(EConstants.Connection_TimeOut);
                conn_.setReadTimeout(EConstants.Connection_TimeOut);
                conn_.connect();

                int HttpResult =conn_.getResponseCode();
                if(HttpResult ==HttpURLConnection.HTTP_OK){
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getInputStream(),EConstants.UNICODE));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();


                }else{
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
            vacancies_Server = Vacancy_JSON.parseFeed(result);
            if(vacancies_Server.isEmpty()){
                Toast.makeText(getApplicationContext(),EConstants.Messages_Vacancis,Toast.LENGTH_LONG).show();
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



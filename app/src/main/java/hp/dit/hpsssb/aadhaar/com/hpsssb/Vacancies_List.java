package hp.dit.hpsssb.aadhaar.com.hpsssb;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    LinearLayout LGone;
    Button refresh;
    ProgressBar pb;
    URL url_;
    HttpURLConnection conn_;
    StringBuilder sb = new StringBuilder();
    ListView listv;
    Context context;
    List<GetVacancies> tasks;
    List<VacancyPOJO> vacancies_Server;
    EditText Search_EditText;
    Vacancies_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacancies__list);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Date_Service = bundle.getString(EConstants.PutExtra_Message_Vacancies_Date);
        listv = (ListView) findViewById(R.id.list);
        Search_EditText = (EditText)findViewById(R.id.edit_text_search);
        refresh = (Button)findViewById(R.id.refresh);
        LGone = (LinearLayout)findViewById(R.id.lgone);
        context = this;
        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setVisibility(View.INVISIBLE);
        tasks = new ArrayList<>();

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline()) {
                    Search_EditText.setText("");
                    GetVacancies asy_Get_Vacancy = new GetVacancies();
                    asy_Get_Vacancy.execute(Date_Service);
                } else {
                    Toast.makeText(getApplicationContext(),EConstants.Error_NoNetwork, Toast.LENGTH_LONG).show();
                }
            }
        });



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

        Search_EditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                //MainActivity.this.adapt.getFilter().filter(s);
              //  String searchString=Search_EditText.getText().toString();
              //  adapter.getFilter().filter(searchString);
               // System.out.println("Text ["+s+"] - Start ["+start+"] - Before ["+before+"] - Count ["+count+"]");
               /* if (count < before) {
                    // We're deleting char so we need to reset the adapter data
                    adapter.resetData();
                }*/

                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                Vacancies_List.this.adapter.getFilter().filter(s);


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

        LGone.setVisibility(View.VISIBLE);
        adapter = new Vacancies_Adapter(this, R.layout.item_flower, vacancies_Server);
        listv.setAdapter(adapter);
        listv.setTextFilterEnabled(true);

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
                url_ =new URL("http://10.241.9.72/HPSSSB_Wep/HPSSSB_REST.svc/getVacancies_JSON/"+params[0]);
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
                   System.out.print(sb.toString());

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



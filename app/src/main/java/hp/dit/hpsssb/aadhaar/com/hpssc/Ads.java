package hp.dit.hpsssb.aadhaar.com.hpssc;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

import AdaptersList.AdsAdapter;
import DataParse.Ads_JSON;
import Utils.EConstants;
import Model.AdsPOJO;

public class Ads extends Activity {

    private String Date_Service = null;
    LinearLayout LGone;
    Button refresh;
    ProgressBar pb;
    URL url_;
    HttpURLConnection conn_;
    StringBuilder sb = new StringBuilder();
    ListView listv;
    Context context;
    List<GetAds> tasks;
    List<AdsPOJO> ads_Server;
    AdsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Date_Service = bundle.getString("ADS_DATE");
        listv = (ListView) findViewById(R.id.list_ads);
        context = this;
        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setVisibility(View.INVISIBLE);
        tasks = new ArrayList<>();

        if (isOnline()) {
            GetAds asy_Get_Ads = new GetAds();
            asy_Get_Ads.execute(Date_Service);
        } else {
            Toast.makeText(this,EConstants.Error_NoNetwork, Toast.LENGTH_LONG).show();
        }

        listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AdsPOJO Ads_Details = (AdsPOJO) parent.getItemAtPosition(position);
                Intent userSearch = new Intent();
                userSearch.putExtra("ADS_Details", Ads_Details);
                userSearch.setClass(Ads.this, Ads_Details.class);
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

       // LGone.setVisibility(View.VISIBLE);
        adapter = new AdsAdapter(this, R.layout.item_ads, ads_Server);
        listv.setAdapter(adapter);
       // listv.setTextFilterEnabled(true);

    }

    class GetAds extends AsyncTask<String,String,String> {
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
                url_ =new URL("http://hpsssb.hp.gov.in/hpsssbwebAPI/HPSSSB_REST.svc/getInfo_JSON/"+params[0]);
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
            ads_Server = Ads_JSON.parseFeed(result);
            if(ads_Server.isEmpty()){
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

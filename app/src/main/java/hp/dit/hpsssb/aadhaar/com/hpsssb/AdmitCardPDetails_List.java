package hp.dit.hpsssb.aadhaar.com.hpsssb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kuush on 5/5/2016.
 */
public class AdmitCardPDetails_List extends Activity {
    ProgressBar pb;
    URL url_;
    HttpURLConnection conn_;
    StringBuilder sb = new StringBuilder();

    ListView listv;
    Context context;
    List<GetAdmitCard> tasks;
    List<AdmitCardPOJO> Admit_Card_Server;

    String Name_Service ,DOB_Service, ApplicationID_Service = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admit_card__list);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        Name_Service = bundle.getString("Name_Service");
        DOB_Service = bundle.getString("DOB_Service");
        ApplicationID_Service = bundle.getString("ApplicationID_Service");


        listv = (ListView) findViewById(R.id.list);
        context = this;
        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setVisibility(View.INVISIBLE);

        tasks = new ArrayList<>();

        if (isOnline()) {
            GetAdmitCard asy_Get_AdmitCard_Aadhaar = new GetAdmitCard();
            Toast.makeText(getApplicationContext(),Name_Service+DOB_Service+ApplicationID_Service,Toast.LENGTH_LONG).show();
            asy_Get_AdmitCard_Aadhaar.execute(Name_Service,DOB_Service,ApplicationID_Service);
        } else {
            Toast.makeText(this, "Network Error. Please connect to Internet.", Toast.LENGTH_LONG).show();
        }

        listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AdmitCardPOJO AdmitCard_Details = (AdmitCardPOJO) parent.getItemAtPosition(position);
                Intent userSearch = new Intent();
                userSearch.putExtra("Details", AdmitCard_Details);
                userSearch.setClass(AdmitCardPDetails_List.this, AdmitCardDetails.class);
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

        AdmitCard_Adaptar adapter = new AdmitCard_Adaptar(this, R.layout.item_admitcard, Admit_Card_Server);
        listv.setAdapter(adapter);

    }

    /**
     * Async Task Starts Here
     */
    class GetAdmitCard extends AsyncTask<String,String,String> {


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
                url_ =new URL("http://10.241.9.72/HPSSSB_wep/HPSSSB_REST.svc/getAdmitCardPersonalDetails_JSON");
                conn_ = (HttpURLConnection)url_.openConnection();
                conn_.setDoOutput(true);
                conn_.setRequestMethod("POST");
                conn_.setUseCaches(false);
                conn_.setConnectTimeout(10000);
                conn_.setReadTimeout(10000);
                conn_.setRequestProperty("Content-Type", "application/json");
                conn_.connect();

                JSONStringer userJson = new JSONStringer()
                        .object().key("details")
                        .object()
                        .key("Name_Service").value(params[0])
                        .key("DOB_Service").value(params[1])
                        .key("ApplicationID_Service").value(params[2])
                        .endObject()
                        .endObject();



                System.out.println(userJson.toString());
                OutputStreamWriter out = new OutputStreamWriter(conn_.getOutputStream());
                out.write(userJson.toString());
                out.close();


                int HttpResult =conn_.getResponseCode();
                if(HttpResult ==HttpURLConnection.HTTP_OK){
                    System.out.println(HttpResult+ "@@@@@@");
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getInputStream(),"utf-8"));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                        System.out.println(sb.toString()+ "@@@@@@");
                        System.out.println("\n \t" +"@@@@@@"+sb.toString().length());
                    }
                    br.close();


                }else{
                    System.out.println(conn_.getResponseMessage());
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
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
            Admit_Card_Server = AdmitCard_JSON.parseFeed(result);
            if(Admit_Card_Server.isEmpty()){
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

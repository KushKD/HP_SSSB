package hp.dit.hpsssb.aadhaar.com.hpssc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
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

import AdaptersList.AdmitCard_Adaptar;
import DataParse.AdmitCardP_JSON;
import HelperClasses.EConstants;
import Model.AdmitCardPOJO;

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

        Name_Service = bundle.getString(EConstants.Put_Name);
        DOB_Service = bundle.getString(EConstants.Put_DOB);
        ApplicationID_Service = bundle.getString(EConstants.Put_ApplicationID);


        listv = (ListView) findViewById(R.id.list);
        context = this;
        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setVisibility(View.INVISIBLE);

        tasks = new ArrayList<>();

        if (isOnline()) {
            GetAdmitCard asy_Get_AdmitCard_Aadhaar = new GetAdmitCard();
            asy_Get_AdmitCard_Aadhaar.execute(Name_Service,DOB_Service,ApplicationID_Service);
        } else {
            Toast.makeText(this, EConstants.Error_NoNetwork, Toast.LENGTH_LONG).show();
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
                url_ =new URL(EConstants.url_Generic+EConstants.Delemeter+EConstants.function_getAdmitCardPersonalDetails);
                conn_ = (HttpURLConnection)url_.openConnection();
                conn_.setDoOutput(true);
                conn_.setRequestMethod(EConstants.HTTP_Verb_post);
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
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getInputStream(),EConstants.UNICODE));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
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
            Admit_Card_Server = AdmitCardP_JSON.parseFeed(result);
            if(Admit_Card_Server.isEmpty()){
                Toast.makeText(getApplicationContext(),"Details not found.",Toast.LENGTH_LONG).show();
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

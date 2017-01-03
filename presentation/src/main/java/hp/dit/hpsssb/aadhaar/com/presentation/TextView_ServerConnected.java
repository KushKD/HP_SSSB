package hp.dit.hpsssb.aadhaar.com.presentation;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by kuush on 5/24/2016.
 */
public class TextView_ServerConnected extends TextView {

    private GetSmallNotification currentTask = null;

    public TextView_ServerConnected(Context context) {
        super(context);
        SetUP_TextView(context);

        }
    public TextView_ServerConnected(Context context, AttributeSet attrs) {
        super(context, attrs);
        SetUP_TextView(context);

    }

    public TextView_ServerConnected(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        SetUP_TextView(context);

         }


    public void SetUP_TextView(Context context){
        Typeface face= Typeface.createFromAsset(context.getAssets(), "GOTHICB.TTF");
        this.setTypeface(face);
        this.setTextSize(12);
        this.setPadding(13,3,3,3);
        this.setBackgroundColor(Color.parseColor("#FF0000"));
        this.setTextColor(Color.parseColor("#FFFFFF"));
        this.setText("Test for the posts advertised on 7th March 2016 would be conducted during the month of July 2016. The admit card will be made available at least 12 days before the test.  ---HPSSC----");

    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
       // this.setText("I'm Attached");
      //  Log.d("I'm","Attached");
        String date = new SimpleDateFormat("MM-dd-yyyy").format(new Date());
        currentTask = new GetSmallNotification();
        currentTask.execute(date);
    }


    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (currentTask != null) {
            currentTask.cancel(true);
            currentTask = null;
        }
    }

    class GetSmallNotification extends AsyncTask<String,String,String>{

        private ProgressDialog progressDialog;
        private String Server_Value = null;
        String url = null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setText("Loading Notifications.. Please wait!");
        }

        @Override
        protected String doInBackground(String... params) {
            String value = params[0];


            StringBuilder sb = new StringBuilder();
            sb.append("http://hpsssb.hp.gov.in/hpsssbwebAPI/HPSSSB_REST.svc");
            sb.append("/");
            sb.append("getLatestInfo_JSON");
            sb.append("/");
            sb.append(value);

            url = sb.toString();
            JSONParserPresentations jParser = new JSONParserPresentations();
            String result  = jParser.getDataRest(url);

            sb.delete(0, sb.length());
            Object json ;
            try {
                json = new JSONTokener(result).nextValue();
                if (json instanceof JSONObject){
                    JSONObject obj = new JSONObject(result);
                    Server_Value = obj.getString("JSON_LatestNotificationResult");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Server_Value = "Error while fetching the latest notification";
            }
            return Server_Value;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            setText(s);

        }
    }


}

package hp.dit.hpsssb.aadhaar.com.hpsssb;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import DataParse.JSONParser;
import HelperClasses.EConstants;
import HelperClasses.Helper;

public class LogInActivity extends Activity {

    Button register, back;
    EditText et_Mobile;
    Helper helper = null;
    private String IMEI = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        register = (Button) findViewById(R.id.register);
        back = (Button) findViewById(R.id.back);
        et_Mobile = (EditText) findViewById(R.id.etmobile);

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        IMEI = telephonyManager.getDeviceId();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogInActivity.this.finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check weather Phone number is there or not
                String PhoneNumber_Service = et_Mobile.getText().toString().trim();

                if (PhoneNumber_Service.length() == 10) {
                    if (IMEI.length() != 0) {
                         Registration Register_me = new Registration();
                        Register_me.execute(PhoneNumber_Service,IMEI);
                    } else {
                        //Start Async Task with IMEI set to 0
                        Registration Register_mee = new Registration();
                        Register_mee.execute(PhoneNumber_Service,"000000000000");
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Please enter a valid 10 digit Mobile number", Toast.LENGTH_LONG).show();
                }


                //Start Service IMEI and Phone Number

               // Toast.makeText(getBaseContext(), IMEI, Toast.LENGTH_LONG).show();



            }
        });


    }

    class Registration extends AsyncTask<String, String,String>{

        private String Phone_Service = null;
        private String IMEI_Service = null;
        private String Server_Value = null;
        private ProgressDialog dialog;
        String url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(LogInActivity.this);
            this.dialog.setMessage("Please wait ..");
            this.dialog.show();
            this.dialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {

            Phone_Service = params[0];
            IMEI_Service = params[1];
            StringBuilder sb = new StringBuilder();
            sb.append("http://10.241.9.72/HPSSSB_Wep/HPSSSB_REST.svc");
            sb.append(EConstants.Delemeter);
            sb.append(EConstants.function_Register);
            sb.append(EConstants.Delemeter);
            sb.append(Phone_Service);
            sb.append(EConstants.Delemeter);
            sb.append(IMEI_Service);
            url = sb.toString();
            JSONParser jParser = new JSONParser();
            String result  = jParser.getDataRest(url);

            sb.delete(0, sb.length());
            Object json ;
            try {
                json = new JSONTokener(result).nextValue();
                if (json instanceof JSONObject){
                    JSONObject obj = new JSONObject(result);
                    Server_Value = obj.getString("JSON_GetRegistrationResult");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Server_Value = "error";
            }
            return Server_Value;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(Server_Value.length()==22){

                //User has successfully logged in, save this information
                // We need an Editor object to make preference changes.
                SharedPreferences settings = getSharedPreferences(EConstants.PREFS_NAME, 0); // 0 - for private mode
                SharedPreferences.Editor editor = settings.edit();
                //Set "hasLoggedIn" to true
                editor.putBoolean("hasLoggedIn", true);
                // Commit the edits!
                editor.commit();
                Intent intent = new Intent();
                intent.setClass(LogInActivity.this, HPSSSB_MAIN.class);
                startActivity(intent);
                LogInActivity.this.finish();
            }else{

                Toast.makeText(getApplicationContext(),EConstants.Error_NoIdea,Toast.LENGTH_LONG).show();
                dialog.dismiss();

            }
        }
    }
}



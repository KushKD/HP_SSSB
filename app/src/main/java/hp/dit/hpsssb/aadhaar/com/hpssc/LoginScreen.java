package hp.dit.hpsssb.aadhaar.com.hpssc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import JsonManager.JsonParser;
import Utils.EConstants;
import hp.dit.hpsssb.aadhaar.com.presentation.BaseActivity;

public class LoginScreen extends BaseActivity {

    private Button btn_Back, btn_Login, btn_Get_OTP;
    private EditText tv_Phone, tv_OTP;
    private RelativeLayout rl_otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        btn_Back = (Button)findViewById(R.id.quit);
        btn_Login = (Button)findViewById(R.id.login);
        btn_Get_OTP = (Button)findViewById(R.id.get_otp);
        tv_Phone = (EditText)findViewById(R.id.phonenumber);
        tv_OTP = (EditText) findViewById(R.id.otp);
        rl_otp = (RelativeLayout)findViewById(R.id.rl_otp);

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String OTP_Verify = tv_OTP.getText().toString().trim();
                String Phone_Verify = tv_Phone.getText().toString().trim();

                if(OTP_Verify!=null && Phone_Verify.length()==10 ){
                    if(isOnline()) {
                        Async_Verify_OTP async_verify_otp = new Async_Verify_OTP();
                        async_verify_otp.execute(Phone_Verify, OTP_Verify);
                    }else{
                        Toast.makeText(getApplicationContext(), EConstants.Error_NoNetwork,Toast.LENGTH_LONG).show();
                    }
                }else{
                   Toast.makeText(getApplicationContext(), EConstants.Error_NoIdea,Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_Get_OTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String PhoneNumber = tv_Phone.getText().toString().trim();
                if(PhoneNumber.length()==10 && PhoneNumber!=null){
                    if(isOnline()){
                        Async_Get_OTP async_otp = new Async_Get_OTP();
                        async_otp.execute(PhoneNumber);
                    }else{
                        Toast.makeText(getApplicationContext(),EConstants.Error_NoNetwork,Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(),EConstants.ErrorMobile,Toast.LENGTH_SHORT).show();
                }
            }
        });



        btn_Login.setEnabled(false);
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginScreen.this.finish();
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


    class Async_Get_OTP extends AsyncTask<String,String,String >{

        String Server_Phone = null;
        ProgressDialog  progressDialog;
        String url = null;
        String Result = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            progressDialog = new ProgressDialog(LoginScreen.this);
            progressDialog.setMessage(EConstants.Messages_OTP);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            Server_Phone = params[0];

            StringBuilder sb = new StringBuilder();
            sb.append(EConstants.url_Generic);
            sb.append(EConstants.Delemeter);
            sb.append(EConstants.function_GetOTP);
            sb.append(EConstants.Delemeter);
            sb.append(Server_Phone);
            url = sb.toString();
            JsonParser jParser = new JsonParser();
            String result  = jParser.ParseString(url);
            sb.delete(0, sb.length());
            Object json ;
            try {
                json = new JSONTokener(result).nextValue();

                if (json instanceof JSONObject){
                    JSONObject obj = new JSONObject(result);
                    Result = obj.getString(EConstants.OTP_Result);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return Result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


if(s.length()==52){
    progressDialog.dismiss();
    Toast.makeText(getApplicationContext(),s + s.length(),Toast.LENGTH_LONG).show();
    btn_Login.setEnabled(true);
    btn_Get_OTP.setVisibility(View.GONE);
    btn_Login.setVisibility(View.VISIBLE);
    rl_otp.setVisibility(View.VISIBLE);
    tv_Phone.setEnabled(false);

}else{
    progressDialog.dismiss();
    Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
}
        }
    }

    class Async_Verify_OTP extends AsyncTask<String,String,Boolean>{

        String Server_Phone = null;
        String Server_OTP = null;
        ProgressDialog  progressDialog;
        String url = null;
        String Result = null;
        Boolean Server_value= false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            progressDialog = new ProgressDialog(LoginScreen.this);
            progressDialog.setMessage(EConstants.progress_Dialog_Message);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Boolean  doInBackground(String... params) {
            Server_Phone = params[0];
            Server_OTP = params[1];
            StringBuilder sb = new StringBuilder();
            sb.append(EConstants.url_Generic);
            sb.append(EConstants.Delemeter);
            sb.append(EConstants.function_VerifyOTP);
            sb.append(EConstants.Delemeter);
            sb.append(Server_Phone);
            sb.append(EConstants.Delemeter);
            sb.append(Server_OTP);
            url = sb.toString();
            JsonParser jParser = new JsonParser();
            String result  = jParser.ParseString(url);
            sb.delete(0, sb.length());
            Object json ;
            try {
                json = new JSONTokener(result).nextValue();
                if (json instanceof JSONObject){
                    JSONObject obj = new JSONObject(result);
                    Server_value = obj.optBoolean(EConstants.Login_Result);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                  Server_value = false;
            }
            return Server_value;
        };

        @Override
        protected void onPostExecute(Boolean s) {
            super.onPostExecute(s);
            this.progressDialog.dismiss();
            if(s) {
                Intent i_2 = new Intent(LoginScreen.this, AdmitCard.class);
                startActivity(i_2);
                LoginScreen.this.finish();
            }else {
                Toast.makeText(getApplicationContext(),EConstants.Error_NoIdea, Toast.LENGTH_LONG).show();
            }
        }


    }
}

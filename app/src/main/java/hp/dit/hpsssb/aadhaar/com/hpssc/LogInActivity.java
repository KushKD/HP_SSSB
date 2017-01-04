package hp.dit.hpsssb.aadhaar.com.hpssc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import HelperClasses.AppStatus;
import Interfaces.AsyncTaskListener;
import JsonManager.JsonParser;
import Utils.Custom_Dialog;
import Utils.EConstants;
import HelperClasses.Helper;
import Enum.TaskType;
import Utils.Generic_Async_Get;

public class LogInActivity extends Activity implements AsyncTaskListener {

    Button register, back;
    EditText et_Mobile;
    Helper helper = null;
    private String IMEI = null;
    Custom_Dialog CD = new Custom_Dialog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        register = (Button) findViewById(R.id.register);
        back = (Button) findViewById(R.id.back);
        et_Mobile = (EditText) findViewById(R.id.etmobile);


        IMEI = AppStatus.GetIMEI(LogInActivity.this);


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

                if (PhoneNumber_Service.length() == 10 && Integer.parseInt(PhoneNumber_Service.substring(0, 1)) > 6) {
                    if (IMEI.length() != 0) {
                        if (AppStatus.getInstance(LogInActivity.this).isOnline()) {
                            try {
                                String url = null;
                                StringBuilder sb = new StringBuilder();
                                sb.append(EConstants.url_Generic);
                                sb.append(EConstants.Delemeter);
                                sb.append(EConstants.function_Register);
                                sb.append(EConstants.Delemeter);
                                sb.append(PhoneNumber_Service);
                                sb.append(EConstants.Delemeter);
                                sb.append(IMEI);
                                url = sb.toString();
                                new Generic_Async_Get(LogInActivity.this, LogInActivity.this, TaskType.REGISTRATION).execute(url);
                            } catch (Exception ex) {
                                CD.showDialog(LogInActivity.this, ex.getLocalizedMessage().toString());
                            }
                        } else {
                            CD.showDialog(LogInActivity.this, "Please connect to Internet.");
                        }

                    } else {
                        if (AppStatus.getInstance(LogInActivity.this).isOnline()) {
                            try {
                                String url = null;
                                StringBuilder sb = new StringBuilder();
                                sb.append(EConstants.url_Generic);
                                sb.append(EConstants.Delemeter);
                                sb.append(EConstants.function_Register);
                                sb.append(EConstants.Delemeter);
                                sb.append(PhoneNumber_Service);
                                sb.append(EConstants.Delemeter);
                                sb.append("000000000000");
                                url = sb.toString();
                                new Generic_Async_Get(LogInActivity.this, LogInActivity.this, TaskType.REGISTRATION).execute(url);
                            } catch (Exception ex) {
                                CD.showDialog(LogInActivity.this, ex.getLocalizedMessage().toString());
                            }
                        } else {
                            CD.showDialog(LogInActivity.this, "Please connect to Internet.");
                        }
                    }

                } else {
                    CD.showDialog(LogInActivity.this, "Please enter a valid 10 digit Mobile number");
                }


            }
        });


    }

    @Override
    public void onTaskCompleted(String result, TaskType taskType) {

        Log.e("Server Message", result);

        String finalResult = null;

        if(taskType == TaskType.REGISTRATION){

            JsonParser JP = new JsonParser();
            finalResult = JP.ParseString(result);
            if (finalResult.length() == 22) {
                SharedPreferences settings = getSharedPreferences(EConstants.PREFS_NAME, 0); // 0 - for private mode
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("hasLoggedIn", true);
                editor.commit();
                Intent intent = new Intent();
                intent.setClass(LogInActivity.this, HPSSSB_MAIN.class);
                startActivity(intent);
                LogInActivity.this.finish();
            }else{
                CD.showDialog(LogInActivity.this,"Something not good.");
            }
        }

    }


}



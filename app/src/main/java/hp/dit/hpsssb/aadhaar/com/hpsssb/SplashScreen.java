package hp.dit.hpsssb.aadhaar.com.hpsssb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import HelperClasses.EConstants;
import hp.dit.hpsssb.aadhaar.com.presentation.BaseActivity;

public class SplashScreen extends BaseActivity {

    public static final String Folder_name = "/HPSSSB";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                try {
                    check_FileSystem FileCheck = new check_FileSystem();
                    FileCheck.execute(Folder_name);
                }catch(Exception ex){
                    Log.e("Error","While Executing ASYNC Task");
                }

                SharedPreferences settings = getSharedPreferences(EConstants.PREFS_NAME, 0);
                //Get "hasLoggedIn" value. If the value doesn't exist yet false is returned
                boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);

                if(hasLoggedIn)
                {
                    Intent mainIntent = new Intent(SplashScreen.this, HPSSSB_MAIN.class);
                    SplashScreen.this.startActivity(mainIntent);
                    SplashScreen.this.finish();
                }else{
                    Intent loginIntent = new Intent(SplashScreen.this, LogInActivity.class);
                    SplashScreen.this.startActivity(loginIntent);
                    SplashScreen.this.finish();

                }




            }
        }, 3000);
    }


    class check_FileSystem extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);

            if(isSDPresent)
            {
                File folder = new File(Environment.getExternalStorageDirectory() + params[0]);
                boolean success = true;
                if (!folder.exists()) {
                    success = folder.mkdir();
                }
                if (success) {
                    return "Directory Created Successfully";
                } else {
                    return "Something went wrong while creating the Directory. ";
                }

            }
            else
            {
               return "SD Card Not Available";
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
        }
    }

}

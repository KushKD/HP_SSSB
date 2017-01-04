package hp.dit.hpsssb.aadhaar.com.hpssc;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import HelperClasses.AppStatus;
import Enum.TaskType;
import Interfaces.AsyncTaskListener;
import JsonManager.JsonParser;
import Utils.Custom_Dialog;
import Utils.EConstants;
import Utils.Generic_Async_Get;
import hp.dit.hpsssb.aadhaar.com.presentation.CircleImageView;
import hp.dit.hpsssb.aadhaar.com.presentation.CircleLayout;
import hp.dit.hpsssb.aadhaar.com.presentation.BaseActivity;


public class HPSSSB_MAIN extends BaseActivity implements
        CircleLayout.OnItemSelectedListener,
        CircleLayout.OnItemClickListener,
        CircleLayout.OnRotationFinishedListener,
        CircleLayout.OnCenterClickListener,
        AsyncTaskListener{
    private ProgressDialog pDialog;
    public static final int progress_bar_type = 0;
    private TextView selectedTextView;
    private  Button Proceed;
    final Context context = this;
    Custom_Dialog CD = new Custom_Dialog();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hpsssb__main);

        // Set listeners
        CircleLayout circleMenu = (CircleLayout) findViewById(R.id.main_circle_layout);
        circleMenu.setOnItemSelectedListener((CircleLayout.OnItemSelectedListener) this);
        circleMenu.setOnItemClickListener((CircleLayout.OnItemClickListener) this);
        circleMenu.setOnRotationFinishedListener((CircleLayout.OnRotationFinishedListener) this);
        circleMenu.setOnCenterClickListener((CircleLayout.OnCenterClickListener) this);
        selectedTextView = (TextView) findViewById(R.id.main_selected_textView);
        selectedTextView.setText(((CircleImageView) circleMenu.getSelectedItem()).getName());
        Proceed = (Button) findViewById(R.id.proceed);
        Proceed.setText(((CircleImageView) circleMenu.getSelectedItem()).getName());


        Proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ButtonText = (String) ((Button)v).getText();

                switch (ButtonText){
                    case "Results":
                        CD.showDialog(HPSSSB_MAIN.this,EConstants.Messages_Results);
                        break;
                    case "Ads":
                        String dateString_Ads = (String) DateFormat.format("MM-dd-yyyy",new java.util.Date());
                        Intent i_Ads = new Intent(HPSSSB_MAIN.this,Ads.class);
                        i_Ads.putExtra("ADS_DATE",dateString_Ads);
                        startActivity(i_Ads);
                        break;
                    case "Instructions":
                        //Check weather the file is present in the SDCARD
                        File extStorage = Environment.getExternalStorageDirectory();
                        File file = new File(extStorage.getAbsolutePath()+ "/HPSSSB/HPSSSB_Instructions.pdf");

                        if (file.exists()) {
                            Uri path = Uri.fromFile(file);
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(path,EConstants.Intent_Type);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            try {
                                startActivity(intent);
                            }
                            catch (ActivityNotFoundException e) {
                                Toast.makeText(getApplicationContext(),EConstants.Error_NoPDF_Viewer,Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            if(AppStatus.getInstance(HPSSSB_MAIN.this).isOnline()){
                                String URL_Instructions = null;
                                URL_Instructions = EConstants.url_Generic+"/"+EConstants.function_Instructions;
                                new Generic_Async_Get(HPSSSB_MAIN.this, HPSSSB_MAIN.this, TaskType.GET_PDF).execute(URL_Instructions);

                            }else{
                                Toast.makeText(getApplicationContext(),EConstants.Error_NoNetwork,Toast.LENGTH_LONG).show();
                            }
                        }

                        break;
                    case "Vacancies":
                        String dateString = (String) DateFormat.format("dd-MM-yyyy",new java.util.Date());
                        Intent i_vacancy = new Intent(HPSSSB_MAIN.this,Vacancies_List.class);
                        i_vacancy.putExtra(EConstants.PutExtra_Message_Vacancies_Date,dateString);
                        startActivity(i_vacancy);
                        break;
                    case "Dashboard":
                        Intent i_dashboard = new Intent(HPSSSB_MAIN.this, Dashboard.class);
                        startActivity(i_dashboard);
                        break;
                    case "Interview Schedule":
                        CD.showDialog(HPSSSB_MAIN.this,EConstants.Messages_Interview);

                        break;
                    case "Admit Card":
                        Intent i_Login = new Intent(HPSSSB_MAIN.this,LoginScreen.class);
                        startActivity(i_Login);
                        break;
                    default:
                        CD.showDialog(HPSSSB_MAIN.this,EConstants.Error_NoIdea);
                        break;
                }
            }
        });


    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit the application.")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        HPSSSB_MAIN.this.finish();
                        int pid = android.os.Process.myPid();
                        android.os.Process.killProcess(pid);
                        System.exit(0);
                        HPSSSB_MAIN.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }


    @Override
    public void onItemSelected(View view, String name) {
        selectedTextView.setText(name);
        Proceed.setText(name);

        switch (view.getId()) {
            case R.id.main_interviews:
                break;
            case R.id.main_dashboard:
                break;
            case R.id.main_information:
                break;

            case R.id.main_notifications:
                break;
            case R.id.main_admitcard:
                break;

            case R.id.main_vacancies:
                break;
        }
    }

    @Override
    public void onItemClick(View view, String name) {

        switch (view.getId()) {
            case R.id.main_interviews:
               CD.showDialog(HPSSSB_MAIN.this,EConstants.Messages_Interview);
                break;
            case R.id.main_dashboard:
                Intent i_dashboard = new Intent(HPSSSB_MAIN.this, Dashboard.class);
                startActivity(i_dashboard);
                break;
            case R.id.main_information:
                File extStorage = Environment.getExternalStorageDirectory();
                File file = new File(extStorage.getAbsolutePath()+ "/HPSSSB/HPSSSB_Instructions.pdf");

                if (file.exists()) {
                    Uri path = Uri.fromFile(file);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(path,EConstants.Intent_Type);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    try {
                        startActivity(intent);
                    }
                    catch (ActivityNotFoundException e) {
                        Toast.makeText(getApplicationContext(),EConstants.Error_NoPDF_Viewer,Toast.LENGTH_SHORT).show();
                    }
                }else{
                    if(AppStatus.getInstance(HPSSSB_MAIN.this).isOnline()){
                        String URL_Instructions = null;
                        URL_Instructions = EConstants.url_Generic+"/"+EConstants.function_Instructions;
                        new Generic_Async_Get(HPSSSB_MAIN.this, HPSSSB_MAIN.this, TaskType.GET_PDF).execute(URL_Instructions);

                    }else{
                        Toast.makeText(getApplicationContext(),EConstants.Error_NoNetwork,Toast.LENGTH_LONG).show();
                    }
                }
                break;

            case R.id.main_notifications:
                String dateString_Ads = (String) DateFormat.format("MM-dd-yyyy",new java.util.Date());
                Intent i_Ads = new Intent(HPSSSB_MAIN.this,Ads.class);
                i_Ads.putExtra("ADS_DATE",dateString_Ads);
                startActivity(i_Ads);
                break;
            case R.id.main_admitcard:
                 Intent i_Login = new Intent(HPSSSB_MAIN.this,LoginScreen.class);
                 startActivity(i_Login);
                break;

            case R.id.main_vacancies:
                String dateString = (String) DateFormat.format("dd-MM-yyyy",new java.util.Date());
                Intent i_vacancy = new Intent(HPSSSB_MAIN.this,Vacancies_List.class);
                i_vacancy.putExtra(EConstants.PutExtra_Message_Vacancies_Date,dateString);
                startActivity(i_vacancy);
                break;

            case R.id.main_results:
                CD.showDialog(HPSSSB_MAIN.this,EConstants.Messages_Results);
                break;
        }
    }





    @Override
    public void onRotationFinished(View view, String name) {
        Animation animation = new RotateAnimation(0, 360, view.getWidth() / 2,view.getHeight() / 2);
        animation.setDuration(1000);
        view.startAnimation(animation);

    }

    @Override
    public void onCenterClick() {

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type:
                pDialog = new ProgressDialog(this);
                pDialog.setMessage(EConstants.progress_Dialog_Message);
                pDialog.setIndeterminate(false);
                pDialog.setMax(100);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setCancelable(true);
                pDialog.show();
                return pDialog;
            default:
                return null;
        }
    }

    @Override
    public void onTaskCompleted(String result, TaskType taskType) {

        Log.e("Server Message", result);

        String finalResult = null;
        if(taskType == TaskType.GET_PDF){
            JsonParser JP = new JsonParser();
            finalResult = JP.ParsePdfUrl(result);

            // System.out.println(s.length());
            if(finalResult.length()>=57){
                if(AppStatus.getInstance(HPSSSB_MAIN.this).isOnline()) {
                    new DownloadFileFromURL().execute(finalResult);
                }else{
                    CD.showDialog(HPSSSB_MAIN.this,EConstants.Error_NoNetwork);
                }
            }else{
                CD.showDialog(HPSSSB_MAIN.this,EConstants.Error_NoIdea);
            }

        }else{
            CD.showDialog(HPSSSB_MAIN.this,"Something not good.");
        }

    }



    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progress_bar_type);
        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                int lenghtOfFile = conection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                OutputStream output = new FileOutputStream(EConstants.Path_PDF);
                byte data[] = new byte[EConstants.Chuck_Size];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress(""+(int)((total*100)/lenghtOfFile));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String file_url) {
            dismissDialog(progress_bar_type);


            //Open PDF In PDF Viewer
            File file = new File(EConstants.Path_PDF);

            if (file.exists()) {
                Uri path = Uri.fromFile(file);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(path, EConstants.Intent_Type);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                try {
                    startActivity(intent);
                }
                catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(),EConstants.Error_NoPDF_Viewer,Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getApplicationContext(), EConstants.Error_DownloadFile ,Toast.LENGTH_SHORT).show();
            }
        }

    }




}








package hp.dit.hpsssb.aadhaar.com.hpsssb;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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

import hp.dit.hpsssb.aadhaar.com.presentation.CircleImageView;
import hp.dit.hpsssb.aadhaar.com.presentation.CircleLayout;
import hp.dit.hpsssb.aadhaar.com.presentation.BaseActivity;


public class HPSSSB_MAIN extends BaseActivity implements CircleLayout.OnItemSelectedListener, CircleLayout.OnItemClickListener, CircleLayout.OnRotationFinishedListener, CircleLayout.OnCenterClickListener {

    private ProgressDialog pDialog;
    // Progress dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_type = 0;
    private TextView selectedTextView;
    private  Button Proceed;
    final Context context = this;
    public static final String url_Generic = "http://hpsssb.hp.gov.in/Uploads/HPSSSB%20Instructions.pdf" ;
    public static final String url_local = "http://10.0.2.2:8015/HPSSSB_REST.svc/getInstructions_JSON" ;


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
              //  Toast.makeText(getApplicationContext(),ButtonText,Toast.LENGTH_LONG).show();

                switch (ButtonText){
                    case "Results":
                        ShowAlert("No pending results in pipeline.");
                        break;
                    case "Notifications":
                        Toast.makeText(getApplicationContext(),ButtonText + " was clicked",Toast.LENGTH_LONG).show();
                        break;
                    case "Instructions":
                        //Check weather the file is present in the SDCARD
                        File extStorage = Environment.getExternalStorageDirectory();
                        File file = new File(extStorage.getAbsolutePath()+ "/HPSSSB/HPSSSB_Instructions.pdf");

                        if (file.exists()) {
                            Uri path = Uri.fromFile(file);
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(path, "application/pdf");
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            try {
                                startActivity(intent);
                            }
                            catch (ActivityNotFoundException e) {
                                Toast.makeText(getApplicationContext(), "No Application Available to View PDF",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "File Not Found. Please wait file will be downloaded.",Toast.LENGTH_SHORT).show();
                            if(isOnline()){
                                new get_URL_InstructionsPDF().execute(url_local);
                            }else{
                                Toast.makeText(getApplicationContext(),"Network not found.",Toast.LENGTH_LONG).show();
                            }
                        }

                        break;
                    case "Vacancies":
                        String dateString = (String) DateFormat.format("dd.MM.yyyy",new java.util.Date());
                        // Toast.makeText(getApplicationContext(),dateString,Toast.LENGTH_LONG).show();
                        Intent i_vacancy = new Intent(HPSSSB_MAIN.this,Vacancies_List.class);
                        i_vacancy.putExtra("DATE_TO_SEND",dateString);
                        startActivity(i_vacancy);
                        break;
                    case "Dashboard":
                        Toast.makeText(getApplicationContext(),ButtonText + " was clicked",Toast.LENGTH_LONG).show();
                        break;
                    case "Interview Schedule":
                        ShowAlert("Currently there is no Interview Scheduled.");

                        break;
                    case "Admit Card":
                        Intent i_AdmitCard = new Intent(HPSSSB_MAIN.this,AdmitCard.class);
                        startActivity(i_AdmitCard);
                        break;
                    default:
                        Toast.makeText(getApplicationContext(),"Somethings Not Good",Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });


    }


    @Override
    public void onItemSelected(View view, String name) {
        selectedTextView.setText(name);
        Proceed.setText(name);

        switch (view.getId()) {
            case R.id.main_interviews:
                // Handle calendar selection
                break;
            case R.id.main_dashboard:
                // Handle cloud selection
                break;
            case R.id.main_information:
                // Handle facebook selection
                break;
           /* case R.id.main_notification:
                // Handle key selection
                break;*/
            case R.id.main_notifications:
                // Handle profile selection
                break;
            case R.id.main_admitcard:
                // Handle tap selection
                break;

            case R.id.main_vacancies:
                //Handle Tab Selection
                break;
        }
    }

    @Override
    public void onItemClick(View view, String name) {
        // Toast.makeText(getApplicationContext(), getResources().getString(R.string.start_app) + " " + name, Toast.LENGTH_SHORT).show();

        switch (view.getId()) {
            case R.id.main_interviews:
               // Toast.makeText(getApplicationContext(),"Currently there is no Interview Scheduled.",Toast.LENGTH_LONG).show();
                ShowAlert("Currently there is no Interview Scheduled.");
                break;
            case R.id.main_dashboard:
               /* Intent i2 = new Intent(HPSSSB_MAIN.this, Cloud.class);
                startActivity(i2);*/
                break;
            case R.id.main_information:
                //Check weather the file is present in the SDCARD
                File extStorage = Environment.getExternalStorageDirectory();
                File file = new File(extStorage.getAbsolutePath()+ "/HPSSSB/HPSSSB_Instructions.pdf");

                if (file.exists()) {
                    Uri path = Uri.fromFile(file);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(path, "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    try {
                        startActivity(intent);
                    }
                    catch (ActivityNotFoundException e) {
                        Toast.makeText(getApplicationContext(), "No Application Available to View PDF",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "File Not Found. Please wait file will be downloaded.",Toast.LENGTH_SHORT).show();
                    if(isOnline()){
                        new get_URL_InstructionsPDF().execute(url_local);
                    }else{
                        Toast.makeText(getApplicationContext(),"Network not found.",Toast.LENGTH_LONG).show();
                    }
                }
                break;

            case R.id.main_notifications:
                // Handle profile click DFSC Data
                /*Intent i3 = new Intent(HPSSSB_MAIN.this , DFSC.class);
                startActivity(i3);*/

                break;
            case R.id.main_admitcard:
                Intent i_AdmitCard = new Intent(HPSSSB_MAIN.this,AdmitCard.class);
                startActivity(i_AdmitCard);
                break;

            case R.id.main_vacancies:
                String dateString = (String) DateFormat.format("dd.MM.yyyy",new java.util.Date());
               // Toast.makeText(getApplicationContext(),dateString,Toast.LENGTH_LONG).show();
                Intent i_vacancy = new Intent(HPSSSB_MAIN.this,Vacancies_List.class);
                i_vacancy.putExtra("DATE_TO_SEND",dateString);
                startActivity(i_vacancy);
                break;

            case R.id.main_results:
                ShowAlert("No pending results in pipeline.");
                break;
        }
    }

    private void ShowAlert(String s) {
        Log.d("SMS is ==========",s);
        final Dialog dialog = new Dialog(HPSSSB_MAIN.this); // Context, this, etc.
        dialog.setContentView(R.layout.dialog_demo);
        dialog.setTitle("Notification");
        dialog.setCancelable(false);
        dialog.show();

        TextView DialogInfo = (TextView)dialog.findViewById(R.id.dialog_info);
        DialogInfo.setText(s);

        Button agree = (Button)dialog.findViewById(R.id.dialog_ok);
       // Button disagree = (Button)dialog.findViewById(R.id.dialog_cancel);

        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

       /* disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });*/
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

    @Override
    public void onRotationFinished(View view, String name) {
        Animation animation = new RotateAnimation(0, 360, view.getWidth() / 2,view.getHeight() / 2);
        animation.setDuration(1000);  //default is 250
        view.startAnimation(animation);

    }

    @Override
    public void onCenterClick() {

    }

    /**
     * Showing Dialog
     * */
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type:
                pDialog = new ProgressDialog(this);
                pDialog.setMessage("Downloading file. Please wait...");
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


    //ASYNC CLASS Get PDF URL
    class get_URL_InstructionsPDF extends AsyncTask<String,String,String>{

        String url = null;
        private ProgressDialog dialog;
        String Server_value = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(HPSSSB_MAIN.this);
            this.dialog.setMessage("Please wait ....");
            this.dialog.show();
            this.dialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {
            url = params[0];
            JSONParser jParser = new JSONParser();
            String result  = jParser.getPdfURL(url);
            Object json ;
            try {
                json = new JSONTokener(result).nextValue();

                if (json instanceof JSONObject){
                    JSONObject obj = new JSONObject(result);
                     Server_value = obj.optString("JSON_getInstructionsResult");
                }

            } catch (JSONException e) {
                e.printStackTrace();
               return null;
            }
            return Server_value ;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            this.dialog.dismiss();
           System.out.println(s.length());
            if(s.length()>=57){
                if(isOnline()) {
                    new DownloadFileFromURL().execute(s);
                }else{
                    Toast.makeText(getApplicationContext(),"Unable to connect to Internet. Please check your network connection.",Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(getApplicationContext(),"Something went wrong.",Toast.LENGTH_LONG).show();
            }
        }


    }

    //ASYNC CLASS Download File
    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progress_bar_type);
        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // getting file length
                int lenghtOfFile = conection.getContentLength();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                // Output stream to write file
                OutputStream output = new FileOutputStream("/sdcard/HPSSSB/HPSSSB_Instructions.pdf");

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress(""+(int)((total*100)/lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        /**
         * Updating progress bar
         * */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            dismissDialog(progress_bar_type);


            //Open PDF In PDF Viewer
            File file = new File("/sdcard/HPSSSB/HPSSSB_Instructions.pdf");

            if (file.exists()) {
                Uri path = Uri.fromFile(file);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(path, "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                try {
                    startActivity(intent);
                }
                catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "No Application Available to View PDF",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getApplicationContext(), "The downloaded file is not a valid format.",Toast.LENGTH_SHORT).show();
            }
        }

    }




}








package hp.dit.hpsssb.aadhaar.com.hpssc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import AdaptersList.Vacancies_Adapter;
import JsonManager.Vacancy_JSON;
import HelperClasses.AppStatus;
import Interfaces.AsyncTaskListener;
import Utils.Custom_Dialog;
import Utils.EConstants;
import Model.VacancyPOJO;
import Enum.TaskType;
import Utils.Generic_Async_Get;

public class Vacancies_List extends Activity implements AsyncTaskListener {

    private String Date_Service = null;
    LinearLayout LGone;
    Button refresh;
    ProgressBar pb;
    URL url_;
    HttpURLConnection conn_;
    StringBuilder sb = new StringBuilder();
    ListView listv;
    Context context;
    List<VacancyPOJO> vacancies_Server;
    EditText Search_EditText;
    Vacancies_Adapter adapter;
    Custom_Dialog CD = new Custom_Dialog(Vacancies_List.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacancies__list);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Date_Service = bundle.getString(EConstants.PutExtra_Message_Vacancies_Date);
        listv = (ListView) findViewById(R.id.list);
        Search_EditText = (EditText)findViewById(R.id.edit_text_search);
        refresh = (Button)findViewById(R.id.refresh);
        LGone = (LinearLayout)findViewById(R.id.lgone);
        context = this;
        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setVisibility(View.INVISIBLE);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(Vacancies_List.this).isOnline()) {
                    Search_EditText.setText("");
                    String url_ = null;
                    url_ = EConstants.url_Generic+"/getVacancies_JSON/"+Date_Service;
                    new Generic_Async_Get(Vacancies_List.this, Vacancies_List.this, TaskType.GET_VACANCIES).execute(url_);
                } else {
                    CD.showDialog(Vacancies_List.this,EConstants.Error_NoNetwork);
                }
            }
        });



        if (AppStatus.getInstance(Vacancies_List.this).isOnline()) {

            String url_ = null;
            url_ = EConstants.url_Generic+"/getVacancies_JSON/"+Date_Service;
            new Generic_Async_Get(Vacancies_List.this, Vacancies_List.this, TaskType.GET_VACANCIES).execute(url_);

        } else {
            CD.showDialog(Vacancies_List.this,EConstants.Error_NoNetwork);
        }

        listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VacancyPOJO vacancy_Details = (VacancyPOJO) parent.getItemAtPosition(position);
                Intent userSearch = new Intent();
                userSearch.putExtra(EConstants.PutExtra_Message_Vacancies, vacancy_Details);
                userSearch.setClass(Vacancies_List.this, VacancyDetails.class);
                startActivity(userSearch);

            }
        });

        Search_EditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                //MainActivity.this.adapt.getFilter().filter(s);
              //  String searchString=Search_EditText.getText().toString();
              //  adapter.getFilter().filter(searchString);
               // System.out.println("Text ["+s+"] - Start ["+start+"] - Before ["+before+"] - Count ["+count+"]");
               /* if (count < before) {
                    // We're deleting char so we need to reset the adapter data
                    adapter.resetData();
                }*/

                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                Vacancies_List.this.adapter.getFilter().filter(s);


            }
        });
    }





    protected void updateDisplay() {

        LGone.setVisibility(View.VISIBLE);
        adapter = new Vacancies_Adapter(this, R.layout.item_flower, vacancies_Server);
        listv.setAdapter(adapter);
        listv.setTextFilterEnabled(true);

    }

    @Override
    public void onTaskCompleted(String result, TaskType taskType) {

        Log.e("Server Message", result);
        if(taskType == TaskType.GET_VACANCIES){

            vacancies_Server = Vacancy_JSON.parseFeed(result);
            if(!vacancies_Server.isEmpty()){
                updateDisplay();

            }else
            {
                CD.showDialog(Vacancies_List.this,EConstants.Messages_Vacancis);
            }

        }else{
            CD.showDialog(Vacancies_List.this,"Something bad happened");
        }

    }

}



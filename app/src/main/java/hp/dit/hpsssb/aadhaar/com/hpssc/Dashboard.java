package hp.dit.hpsssb.aadhaar.com.hpssc;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import JsonManager.DashboardList_PostWise;
import Utils.Custom_Dialog;
import Utils.EConstants;
import HelperClasses.Helper;
import hp.dit.hpsssb.aadhaar.com.presentation.BaseActivity;

public class Dashboard extends BaseActivity implements View.OnClickListener {

    Button postWise , formsReceived;

    private TextView ext_FromDate , ext_ToDate;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private String GetFromDate = null;
    private String GetToDate = null;

    Custom_Dialog CD = new Custom_Dialog(Dashboard.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);



        postWise = (Button)findViewById(R.id.postwise);
        formsReceived = (Button)findViewById(R.id.formsreceived);
        ext_FromDate = (TextView)findViewById(R.id.fromdate);
        ext_ToDate = (TextView)findViewById(R.id.todate);


        SimpleDateFormat fromdateFormat = new SimpleDateFormat("dd-MM-yyyy");
        ext_FromDate.setText(fromdateFormat.format(new Date())); // it will show 16/07/2013



        SimpleDateFormat todateFormat = new SimpleDateFormat("dd-MM-yyyy");
        ext_ToDate.setText(todateFormat.format(new Date())); // it will show 16/07/2013


        dateFormatter = new SimpleDateFormat(EConstants.Date_Format, Locale.US);
        setDateTimeField();

        postWise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GetFromDate = ext_FromDate.getText().toString().trim();
                Log.e("From Date",GetFromDate);
                GetToDate = ext_ToDate.getText().toString().trim();
                Log.e("To Date",GetToDate);

                if(GetFromDate.length()!=0 && GetToDate.length()!=0){

                    //Compare the Two Dates Here
                    Boolean IsValid ;
                     IsValid = Helper.CheckDates(GetFromDate,GetToDate);
                    if(IsValid){
                    Intent i_postwise= new Intent(Dashboard.this, DashboardList_PostWise.class);
                    i_postwise.putExtra(EConstants.Put_From_Date,GetFromDate);
                    i_postwise.putExtra(EConstants.Put_To_Date,GetToDate);
                    startActivity(i_postwise);
                    }else{
                       CD.showDialog(Dashboard.this,"Please enter valid dates range.");
                    }

                }else{
                    CD.showDialog(Dashboard.this,EConstants.Error_ValidDates);
                }

            }
        });

        formsReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetFromDate = ext_FromDate.getText().toString().trim();
                GetToDate = ext_ToDate.getText().toString().trim();

                if(GetFromDate.length()!=0 && GetToDate.length()!=0){

                    Boolean IsValid = false;
                    IsValid = Helper.CheckDates(GetFromDate,GetToDate);
                    Log.e("Is Valid", Boolean.toString(IsValid));
                    if(IsValid) {

                       // Toast.makeText(getApplicationContext(), "True", Toast.LENGTH_LONG).show();
                    Intent i_formwise= new Intent(Dashboard.this,DashboardList_FormsReceived.class);
                    i_formwise.putExtra(EConstants.Put_From_Date,GetFromDate);
                    i_formwise.putExtra(EConstants.Put_To_Date,GetToDate);
                    startActivity(i_formwise);
                    }else{
                        CD.showDialog(Dashboard.this,"Please enter valid dates range .");
                    }

                }else{
                    CD.showDialog(Dashboard.this,EConstants.Error_ValidDates);
                }
            }
        });


    }

    private void setDateTimeField() {
        ext_FromDate.setOnClickListener(this);
        ext_ToDate.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                ext_FromDate.setText(dateFormatter.format(newDate.getTime()));
                Log.e("From Date",dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                ext_ToDate.setText(dateFormatter.format(newDate.getTime()));
                Log.e("to Date",dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
    @Override
    public void onClick(View v) {
        if(v == ext_FromDate) {
            fromDatePickerDialog.show();
        }else if(v == ext_ToDate) {
            toDatePickerDialog.show();
        }
    }

}

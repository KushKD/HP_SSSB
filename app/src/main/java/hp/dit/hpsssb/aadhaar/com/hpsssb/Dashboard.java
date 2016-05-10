package hp.dit.hpsssb.aadhaar.com.hpsssb;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import hp.dit.hpsssb.aadhaar.com.presentation.BaseActivity;

public class Dashboard extends BaseActivity implements View.OnClickListener {

    Button postWise , formsReceived;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private TextView ext_FromDate , ext_ToDate;
    private String GetFromDate = null;
    private String GetToDate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        postWise = (Button)findViewById(R.id.postwise);
        formsReceived = (Button)findViewById(R.id.formsreceived);

        ext_FromDate = (TextView)findViewById(R.id.fromdate);
        ext_FromDate.setInputType(InputType.TYPE_NULL);

        ext_ToDate = (TextView)findViewById(R.id.todate);
        ext_ToDate.setInputType(InputType.TYPE_NULL);

        dateFormatter = new SimpleDateFormat(EConstants.Date_Format, Locale.US);
        setDateTimeField();

        postWise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GetFromDate = ext_FromDate.getText().toString().trim();
                GetToDate = ext_ToDate.getText().toString().trim();

                if(GetFromDate.length()!=0 && GetToDate.length()!=0){
                    Intent i_postwise= new Intent(Dashboard.this,DashboardList_PostWise.class);
                    i_postwise.putExtra(EConstants.Put_From_Date,GetFromDate);
                    i_postwise.putExtra(EConstants.Put_To_Date,GetToDate);
                    startActivity(i_postwise);

                }else{
                    Toast.makeText(getApplicationContext(),EConstants.Error_ValidDates,Toast.LENGTH_LONG).show();
                }

            }
        });

        formsReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetFromDate = ext_FromDate.getText().toString().trim();
                GetToDate = ext_ToDate.getText().toString().trim();

                if(GetFromDate.length()!=0 && GetToDate.length()!=0){
                    Intent i_formwise= new Intent(Dashboard.this,DashboardList_FormsReceived.class);
                    i_formwise.putExtra(EConstants.Put_From_Date,GetFromDate);
                    i_formwise.putExtra(EConstants.Put_To_Date,GetToDate);
                    startActivity(i_formwise);

                }else{
                    Toast.makeText(getApplicationContext(),EConstants.Error_ValidDates,Toast.LENGTH_LONG).show();
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
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                ext_ToDate.setText(dateFormatter.format(newDate.getTime()));
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

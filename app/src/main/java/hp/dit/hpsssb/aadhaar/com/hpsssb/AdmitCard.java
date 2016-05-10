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

public class AdmitCard extends BaseActivity implements View.OnClickListener {

    private Button back;
    private TextView et_Aadhaar, et_ApplicationID , et_Name , et_DOB;
    private DatePickerDialog dateofBirth_Dialog;
    private SimpleDateFormat dateFormatter;
    private Button bt_GetAdmitCard;
    private Boolean Validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admit_card);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        back = (Button)findViewById(R.id.back);
        bt_GetAdmitCard = (Button)findViewById(R.id.getadmitcard);
        et_Aadhaar = (TextView)findViewById(R.id.etaadhaar);
        et_ApplicationID = (TextView)findViewById(R.id.etappid);
        et_Name = (TextView)findViewById(R.id.etname);
        et_DOB = (TextView)findViewById(R.id.etdateofbirth) ;
        et_DOB.setInputType(InputType.TYPE_NULL);

        setDateTimeField();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdmitCard.this.finish();
            }
        });

        bt_GetAdmitCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String AadhaarNo = et_Aadhaar.getText().toString().trim();
                String ApplicationId = et_ApplicationID.getText().toString().trim();
                String Name = et_Name.getText().toString().trim();
                String DOB = et_DOB.getText().toString().trim();

                if(AadhaarNo.length()==12 && AadhaarNo.length()!= 0 && AadhaarNo!=null  ){

                    Intent i_admit_Aadhaar = new Intent(AdmitCard.this,AdmitCard_List.class);
                    i_admit_Aadhaar.putExtra(EConstants.Put_Aadhaar,AadhaarNo);
                    startActivity(i_admit_Aadhaar);

                }if(ApplicationId.length()!=0 && Name.length()!=0 && DOB.length()!=0 && AadhaarNo.length()!=12 ) {
                  Intent i_admit_PDetals = new Intent(AdmitCard.this,AdmitCardPDetails_List.class);
                    i_admit_PDetals.putExtra(EConstants.Put_Name,Name);
                    i_admit_PDetals.putExtra(EConstants.Put_DOB,DOB);
                    i_admit_PDetals.putExtra(EConstants.Put_ApplicationID,ApplicationId);
                    startActivity(i_admit_PDetals);


                }
                else{
                    Toast.makeText(getApplicationContext(),EConstants.Error_Aadhaar,Toast.LENGTH_LONG).show();

                }




            }
        });


    }



    private void setDateTimeField() {
        et_DOB.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        dateofBirth_Dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                et_DOB.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


    }

    @Override
    public void onClick(View v) {
        if(v == et_DOB) {
            dateofBirth_Dialog.show();
        } else {
            Toast.makeText(getApplicationContext(),EConstants.Error_NoIdea ,Toast.LENGTH_SHORT).show();
        }
    }
}

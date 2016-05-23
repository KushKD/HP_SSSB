package hp.dit.hpsssb.aadhaar.com.hpsssb;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class VacancyDetails extends Activity {

    TextView postid , department, postname, postcode, details, pubdate, lastdate;
    Button back,apply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacancy_details);

        Intent getRoomDetailsIntent = getIntent();
        final VacancyPOJO VacancyDetails =  (VacancyPOJO) getRoomDetailsIntent.getSerializableExtra(EConstants.PutExtra_Message_Vacancies);

        back = (Button)findViewById(R.id.back);
        apply = (Button)findViewById(R.id.apply);

        postid = (TextView)findViewById(R.id.postid);
        department = (TextView)findViewById(R.id.department);
        postname = (TextView)findViewById(R.id.postname);
        postcode = (TextView)findViewById(R.id.postcode);
        details = (TextView)findViewById(R.id.details);
        pubdate = (TextView)findViewById(R.id.pubdate);
        lastdate = (TextView)findViewById(R.id.lastdate);

        postid.setText(VacancyDetails.getPostID());
        department.setText(VacancyDetails.getDepartment());
        postname.setText(VacancyDetails.getPostName());
        postcode.setText(VacancyDetails.getPostCode());
        details.setText(VacancyDetails.getDetails());
        pubdate.setText(VacancyDetails.getPubDate());
        lastdate.setText(VacancyDetails.getLastDate());


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VacancyDetails.this.finish();
            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowAlert( EConstants.ApplyButton_AlertMessage);
            }
        });
    }

    private void ShowAlert(String s) {
        final Dialog dialog = new Dialog(VacancyDetails.this);
        dialog.setContentView(R.layout.dialog_apply);
        dialog.setTitle("Notification");
        dialog.setCancelable(false);
        dialog.show();

        TextView DialogInfo = (TextView)dialog.findViewById(R.id.dialog_info);
        DialogInfo.setText(s);

        Button agree = (Button)dialog.findViewById(R.id.dialog_ok);
         Button disagree = (Button)dialog.findViewById(R.id.dialog_exit);

        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(postid.getText().toString()!=null && postid.getText().toString().length()!=0){
                    dialog.dismiss();
                    EncryptData ED = new EncryptData();
                    //Encrypt String;
                    String Encrypt_PostID = ED.Encryption(postid.getText().toString().trim());
                    //Toast.makeText(getApplicationContext(),Encrypt_PostID,Toast.LENGTH_LONG).show();
                    Log.d(postid.getText().toString().trim(),Encrypt_PostID);
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse("http://hpsssb.hp.gov.in/Login.aspx?Id=0&Apply="+Encrypt_PostID));
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Something went wrong.Please try again later.",Toast.LENGTH_LONG).show();
                }


            }
        });

        disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}

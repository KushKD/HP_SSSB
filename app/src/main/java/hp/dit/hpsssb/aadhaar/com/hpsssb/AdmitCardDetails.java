package hp.dit.hpsssb.aadhaar.com.hpsssb;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AdmitCardDetails extends Activity {

    private TextView name, fhname, postname, address, district, rollno;
    private TextView ExamCenter, CenterAddress, DateofExamination, ReportingTime, Duration;
    ImageView photo, signature;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admit_card_details);

        Intent getRoomDetailsIntent = getIntent();
        final AdmitCardPOJO AdmitCard_Details = (AdmitCardPOJO) getRoomDetailsIntent.getSerializableExtra("Details");

        name = (TextView) findViewById(R.id.name);
        fhname = (TextView) findViewById(R.id.fhname);
        postname = (TextView) findViewById(R.id.postname);
        address = (TextView) findViewById(R.id.address);
        district = (TextView) findViewById(R.id.district);
        rollno = (TextView) findViewById(R.id.rollno);
        ExamCenter = (TextView) findViewById(R.id.examcenter);
        CenterAddress = (TextView) findViewById(R.id.centeraddress);
        DateofExamination = (TextView) findViewById(R.id.dateofexamination);
        ReportingTime = (TextView) findViewById(R.id.reportingtime);
        Duration = (TextView) findViewById(R.id.duration);

        photo = (ImageView) findViewById(R.id.ImageUser);
        signature = (ImageView)findViewById(R.id.signature);

        back = (Button)findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdmitCardDetails.this.finish();
            }
        });

        name.setText(AdmitCard_Details.getName());
        fhname.setText(AdmitCard_Details.getFathersName());
        postname.setText(AdmitCard_Details.getPostName());
        address.setText(AdmitCard_Details.getAddress());
        district.setText(AdmitCard_Details.getDistrict());
        rollno.setText(AdmitCard_Details.getRollNo());
        ExamCenter.setText(AdmitCard_Details.getExamCenter());
        CenterAddress.setText(AdmitCard_Details.getCenterAddress());
        DateofExamination.setText(AdmitCard_Details.getDateofExamination());
        ReportingTime.setText(AdmitCard_Details.getReportingTime());
        Duration.setText(AdmitCard_Details.getDuration());

        byte[] byteArrayphoto = AdmitCard_Details.getPhoto();
        Bitmap bmp1 = BitmapFactory.decodeByteArray(byteArrayphoto, 0, byteArrayphoto.length);
        photo.setImageBitmap(bmp1);

        byte[] byteArraysignature = AdmitCard_Details.getSignature();
        Bitmap bmp_signature = BitmapFactory.decodeByteArray(byteArraysignature, 0, byteArraysignature.length);
        signature.setImageBitmap(bmp_signature);



    }
}
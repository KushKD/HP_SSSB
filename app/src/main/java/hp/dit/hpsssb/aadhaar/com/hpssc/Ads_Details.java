package hp.dit.hpsssb.aadhaar.com.hpssc;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import Model.AdsPOJO;

public class Ads_Details extends Activity {

    TextView shortdisc,longdisc,pubdate;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads__details);



        Intent getRoomDetailsIntent = getIntent();
        final AdsPOJO AdsDetails =  (AdsPOJO)getRoomDetailsIntent.getSerializableExtra("ADS_Details");

        shortdisc = (TextView)findViewById(R.id.shortdisc);
        longdisc = (TextView)findViewById(R.id.details);
        pubdate = (TextView)findViewById(R.id.pubdate);
        back = (Button)findViewById(R.id.back);

        shortdisc.setText(AdsDetails.getDetailed());
        longdisc.setText(AdsDetails.getShortnotification());
        pubdate.setText(AdsDetails.getPublish());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ads_Details.this.finish();
            }
        });


    }

}

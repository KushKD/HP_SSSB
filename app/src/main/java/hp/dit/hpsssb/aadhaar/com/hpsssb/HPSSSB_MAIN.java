package hp.dit.hpsssb.aadhaar.com.hpsssb;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import hp.dit.hpsssb.aadhaar.com.presentation.CircleImageView;
import hp.dit.hpsssb.aadhaar.com.presentation.CircleLayout;
import hp.dit.hpsssb.aadhaar.com.presentation.TestRun;


public class HPSSSB_MAIN extends TestRun implements CircleLayout.OnItemSelectedListener, CircleLayout.OnItemClickListener, CircleLayout.OnRotationFinishedListener, CircleLayout.OnCenterClickListener {

    private TextView selectedTextView;
    final Context context = this;

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
    }


    @Override
    public void onItemSelected(View view, String name) {
        selectedTextView.setText(name);

        switch (view.getId()) {
            case R.id.main_info:
                // Handle calendar selection
                break;
            case R.id.main_cloud_image:
                // Handle cloud selection
                break;
            case R.id.main_facebook_image:
                // Handle facebook selection
                break;
            case R.id.main_key_image:
                // Handle key selection
                break;
            case R.id.main_profile_image:
                // Handle profile selection
                break;
            case R.id.main_tap_phone:
                // Handle tap selection
                break;

            case R.id.main_ration_card_image:
                //Handle Tab Selection
                break;
        }
    }

    @Override
    public void onItemClick(View view, String name) {
        // Toast.makeText(getApplicationContext(), getResources().getString(R.string.start_app) + " " + name, Toast.LENGTH_SHORT).show();

        switch (view.getId()) {
            case R.id.main_info:
                /*Intent i = new Intent(HPSSSB_MAIN.this, Info.class);
                startActivity(i);*/
                break;
            case R.id.main_cloud_image:
               /* Intent i2 = new Intent(HPSSSB_MAIN.this, Cloud.class);
                startActivity(i2);*/
                break;
            case R.id.main_facebook_image:
                /*Intent i1 = new Intent(HPSSSB_MAIN.this, FaceBook_WebView.class);
                startActivity(i1);*/
                break;
            case R.id.main_key_image:
                // Handle key click
               /* Intent i4 = new Intent(HPSSSB_MAIN.this , Inspectors.class);
                startActivity(i4);*/
                break;
            case R.id.main_profile_image:
                // Handle profile click DFSC Data
                /*Intent i3 = new Intent(HPSSSB_MAIN.this , DFSC.class);
                startActivity(i3);*/

                break;
            case R.id.main_tap_phone:
                // Handle tap click  //call epds Toll Free
                // Intent i = new Intent(CircularMenu.this , Phone_Call_Activity.class);
                // startActivity(i);
                /*AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set title
                alertDialogBuilder.setTitle("Give a Call");

                // set dialog message
                alertDialogBuilder
                        .setMessage("You are about to call ePDS Department Himachal Pradesh")
                        .setCancelable(false)
                        .setPositiveButton("Call",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, close
                                // current activity
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:1967"));
                                startActivity(callIntent);

                            }
                        })
                        .setNegativeButton("Don't Call", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();*/



                break;

            case R.id.main_ration_card_image:
                //Handle Tab Selection //Find Your Ration Card
                break;
        }
    }

    @Override
    public void onRotationFinished(View view, String name) {
        Animation animation = new RotateAnimation(0, 360, view.getWidth() / 2,view.getHeight() / 2);
        animation.setDuration(500);  //default is 250
        view.startAnimation(animation);

    }

    @Override
    public void onCenterClick() {

    }





}

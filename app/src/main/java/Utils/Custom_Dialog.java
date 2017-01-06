package Utils;

/**
 * Created by kuush on 1/4/2017.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import Model.ExamScheduleResult;
import hp.dit.hpsssb.aadhaar.com.hpssc.R;
import hp.dit.hpsssb.aadhaar.com.hpssc.Schedule_Exams_Interview;
import hp.dit.hpsssb.aadhaar.com.hpssc.SplashScreen;


/**
 * Created by kuush on 6/16/2016.
 */
public class Custom_Dialog {

    private final Context context;

    public Custom_Dialog(Context context) {
        this.context = context;
    }





    public void showDialog(final Activity activity, String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_custom);

        TextView text = (TextView) dialog.findViewById(R.id.dialog_result);
        text.setText(msg);

        Button dialog_ok = (Button)dialog.findViewById(R.id.dialog_ok);

        dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // activity.finish();
                dialog.dismiss();
            }
        });

        dialog.show();

    }


    public void showDialogExamSchedule(final Activity activity, ExamScheduleResult ExamScheduleResult){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_custom_exam_schedule);

        TextView text = (TextView) dialog.findViewById(R.id.dialog_result);
        TextView startrollnumber = (TextView) dialog.findViewById(R.id.startrollnumber);
        TextView endrollnumber = (TextView) dialog.findViewById(R.id.endrollnumber);
        text.setText(ExamScheduleResult.getExamCentre());
        startrollnumber.setText("Starting Roll Number:- "+ExamScheduleResult.getStartRollNo() );
        endrollnumber.setText("Ending Roll Number:-   "+ExamScheduleResult.getEndRollNo() );

        Button dialog_ok = (Button)dialog.findViewById(R.id.dialog_ok);

        dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // activity.finish();
                dialog.dismiss();
            }
        });

        dialog.show();

    }



   public void showInterview_ExamsSchedule(final Activity activity, String msg){

       final Dialog dialog = new Dialog(activity);
       dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
       dialog.setCancelable(false);
       dialog.setContentView(R.layout.dialog_interview_exams);

       TextView text = (TextView) dialog.findViewById(R.id.dialog_result);
       text.setText(msg);

       Button dialog_dismiss = (Button)dialog.findViewById(R.id.dialog_dismiss);
       Button dialog_interview = (Button)dialog.findViewById(R.id.dialog_interview);
       Button dialog_exams = (Button)dialog.findViewById(R.id.dialog_exams);

       dialog_interview.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               dialog.dismiss();
               Intent i1 = new Intent (context, Schedule_Exams_Interview.class);
               i1.putExtra("SELECTION","interview");
               context.startActivity(i1);
           }
       });

       dialog_exams.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               dialog.dismiss();
               Intent i1 = new Intent (context, Schedule_Exams_Interview.class);
               i1.putExtra("SELECTION","exams");
               context.startActivity(i1);
           }
       });


       dialog_dismiss.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               // activity.finish();
               dialog.dismiss();
           }
       });

       dialog.show();

   }

}
package hp.dit.hpsssb.aadhaar.com.hpsssb;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kuush on 5/2/2016.
 */
public class DashboardForms_Adapter extends ArrayAdapter<DashboardFormsPOJO> {

    private Context context;
    private List<DashboardFormsPOJO> userlist;

    public DashboardForms_Adapter(Context context, int resource, List<DashboardFormsPOJO> objects) {
        super(context, resource, objects);
        this.context = context;
        this.userlist = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_dashboardforms, parent, false);
        DashboardFormsPOJO u = userlist.get(position);
        TextView tv1 = (TextView)view.findViewById(R.id.textView1_aadhaar);
        TextView tv2 = (TextView)view.findViewById(R.id.textView2_name);
        TextView tv3 = (TextView)view.findViewById(R.id.textView3);
        TextView tv4 = (TextView)view.findViewById(R.id.textView4);
        TextView tv5 = (TextView)view.findViewById(R.id.textView5);
        TextView tv6 = (TextView)view.findViewById(R.id.textView6);

        tv1.setText(u.getApplication_Recived());
        tv2.setText(u.getTotal_Payment_Received());
        tv3.setText(u.getHDFC());
        tv4.setText(u.getPNB());
        tv5.setText(u.getLMK());
        tv6.setText(u.getOffline());

        return view;
    }
}

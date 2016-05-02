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
public class DashboardPost_Adapter extends ArrayAdapter<DashboardPostPOJO> {
    private Context context;
    private List<DashboardPostPOJO> userlist;

    public DashboardPost_Adapter(Context context, int resource, List<DashboardPostPOJO> objects) {
        super(context, resource, objects);
        this.context = context;
        this.userlist = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_dashboardpost, parent, false);
        DashboardPostPOJO u = userlist.get(position);
        TextView tv1 = (TextView)view.findViewById(R.id.textView1_aadhaar);
        TextView tv2 = (TextView)view.findViewById(R.id.textView2_name);
        TextView tv3 = (TextView)view.findViewById(R.id.textView3);
        TextView tv4 = (TextView)view.findViewById(R.id.textView4);
        tv1.setText(u.getPost_Name());
        tv2.setText(u.getMale());
        tv3.setText(u.getFemale());
        tv4.setText(u.getOthers());
        return view;
    }
}

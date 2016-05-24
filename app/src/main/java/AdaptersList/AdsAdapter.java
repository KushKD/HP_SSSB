package AdaptersList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import Model.AdmitCardPOJO;
import Model.AdsPOJO;
import hp.dit.hpsssb.aadhaar.com.hpsssb.R;

/**
 * Created by kuush on 5/24/2016.
 */
public class AdsAdapter extends ArrayAdapter<AdsPOJO> {

    private Context context;
    private List<AdsPOJO> userlist;

    public AdsAdapter(Context context, int resource, List<AdsPOJO> objects) {
        super(context, resource, objects);
        this.context = context;
        this.userlist = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_admitcard, parent, false);
        AdsPOJO u = userlist.get(position);
        TextView tv1 = (TextView)view.findViewById(R.id.textView1_aadhaar);
        TextView tv2 = (TextView)view.findViewById(R.id.textView2_name);

        tv1.setText(u.getShortnotification());
        tv2.setText(u.getPublish());

        return view;
    }
}

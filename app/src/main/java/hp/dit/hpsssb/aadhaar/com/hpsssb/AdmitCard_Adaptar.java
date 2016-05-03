package hp.dit.hpsssb.aadhaar.com.hpsssb;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kuush on 5/3/2016.
 */
public class AdmitCard_Adaptar extends ArrayAdapter<AdmitCardPOJO> {
    private Context context;
    private List<AdmitCardPOJO> userlist;

    public AdmitCard_Adaptar(Context context, int resource, List<AdmitCardPOJO> objects) {
        super(context, resource, objects);
        this.context = context;
        this.userlist = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_admitcard, parent, false);
        AdmitCardPOJO u = userlist.get(position);
        TextView tv1 = (TextView)view.findViewById(R.id.textView1_aadhaar);
        TextView tv2 = (TextView)view.findViewById(R.id.textView2_name);

        tv1.setText(u.getPostName());
        tv2.setText(u.getRollNo());

        return view;
    }
}

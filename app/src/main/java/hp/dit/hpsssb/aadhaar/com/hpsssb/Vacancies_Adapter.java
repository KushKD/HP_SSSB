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
 * Created by HPZ231 on 24-07-2015.
 */
public class Vacancies_Adapter extends ArrayAdapter<VacancyPOJO> {

    private Context context;
    private List<VacancyPOJO> userlist;

    public Vacancies_Adapter(Context context, int resource, List<VacancyPOJO> objects) {
        super(context, resource, objects);
        this.context = context;
        this.userlist = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_flower, parent, false);
        VacancyPOJO u = userlist.get(position);
        TextView tv1 = (TextView)view.findViewById(R.id.textView1_aadhaar);
        TextView tv2 = (TextView)view.findViewById(R.id.textView2_name);
        tv1.setText(u.getPostName());
        tv2.setText(u.getPostCode());
        return view;
    }


}

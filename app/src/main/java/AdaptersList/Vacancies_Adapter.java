package AdaptersList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Model.VacancyPOJO;
import hp.dit.hpsssb.aadhaar.com.hpssc.R;

/**
 * Created by HPZ231 on 24-07-2015.
 */
public class Vacancies_Adapter extends ArrayAdapter<VacancyPOJO> implements Filterable {

    private Context context;
    private List<VacancyPOJO> userlist;

    private Filter planetFilter;
    private List<VacancyPOJO> origUserList;

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

    public VacancyPOJO getItem(int position) {
        return userlist.get(position);
    }

    public long getItemId(int position) {
        return userlist.get(position).hashCode();
    }


    @Override
    public int getCount() {
        return userlist.size();
    }

    public void resetData() {
        userlist = origUserList;
    }

    /*
	 * We create our filter
	 */

    @Override
    public Filter getFilter() {
        if (planetFilter == null)
            planetFilter = new PlanetFilter();

        return planetFilter;
    }

    private class PlanetFilter  extends Filter {



        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                results.values = origUserList;
                results.count = origUserList.size();
            }
            else {
                // We perform filtering operation
                List<VacancyPOJO> nPlanetList = new ArrayList<>();

                for (VacancyPOJO p : userlist) {
                    if (p.getPostName().toUpperCase().contains(constraint.toString().toUpperCase()))
                        nPlanetList.add(p);
                    //p.getPostName().toUpperCase().startsWith(constraint.toString().toUpperCase())
                }

                results.values = nPlanetList;
                results.count = nPlanetList.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            // Now we have to inform the adapter about the new list filtered
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                userlist = (List<VacancyPOJO>) results.values;
                notifyDataSetChanged();
            }

        }

    }

}

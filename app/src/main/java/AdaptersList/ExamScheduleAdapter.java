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

import Model.ExamScheduleResult;
import Model.VacancyPOJO;
import hp.dit.hpsssb.aadhaar.com.hpssc.R;

/**
 * Created by kuush on 1/6/2017.
 */

public class ExamScheduleAdapter extends ArrayAdapter<ExamScheduleResult> implements Filterable {

    private Context context;
    private List<ExamScheduleResult> ExamScheduleList;

    private Filter planetFilter;
    private List<ExamScheduleResult> ORG_ExamScheduleList;

    public ExamScheduleAdapter(Context context, int resource, List<ExamScheduleResult> objects) {
        super(context, resource, objects);
        this.context = context;
        this.ExamScheduleList = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_exam_schedule, parent, false);
        ExamScheduleResult u = ExamScheduleList.get(position);
        TextView tv1 = (TextView)view.findViewById(R.id.examcenter);
        TextView tv2 = (TextView)view.findViewById(R.id.examdate);
        tv1.setText(u.getExamCentre());
        tv2.setText(u.getExamDate());
        return view;
    }

    public ExamScheduleResult getItem(int position) {
        return ExamScheduleList.get(position);
    }

    public long getItemId(int position) {
        return ExamScheduleList.get(position).hashCode();
    }


    @Override
    public int getCount() {
        return ExamScheduleList.size();
    }

    public void resetData() {
        ExamScheduleList = ORG_ExamScheduleList;
    }

    /*
	 * We create our filter
	 */

    @Override
    public Filter getFilter() {
        if (planetFilter == null)
            planetFilter = new ExamScheduleAdapter.PlanetFilter();

        return planetFilter;
    }

    private class PlanetFilter  extends Filter {



        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                results.values = ORG_ExamScheduleList;
                results.count = ORG_ExamScheduleList.size();
            }
            else {
                // We perform filtering operation
                List<ExamScheduleResult> nPlanetList = new ArrayList<>();

                for (ExamScheduleResult p : ExamScheduleList) {
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
                ExamScheduleList = (List<ExamScheduleResult>) results.values;
                notifyDataSetChanged();
            }

        }

    }

}

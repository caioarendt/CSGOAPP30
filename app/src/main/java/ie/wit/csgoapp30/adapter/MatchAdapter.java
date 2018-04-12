package ie.wit.csgoapp30.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ie.wit.csgoapp30.R;
import ie.wit.csgoapp30.activities.Edit;
import ie.wit.csgoapp30.activities.Main;
import ie.wit.csgoapp30.activities.Register;
import ie.wit.csgoapp30.models.Match;
import ie.wit.csgoapp30.sqllite.DatabaseHelper;

/**
 * Created by caio_ on 3/13/2018.
 */

public class MatchAdapter extends ArrayAdapter<Match> implements Filterable{
    private Context context;
    public List<Match> matches;

    CustomFilter filter;
    List<Match> filterList;

    public MatchAdapter(Context context, List<Match> matches){
        super(context, R.layout.row_match, matches);
        this.context = context;
        this.matches = matches;
        this.filterList = matches;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View view = inflater.inflate(R.layout.row_match, parent, false);
        Match match = matches.get(position);
        TextView teamsView = (TextView) view.findViewById(R.id.row_teams);
        TextView datetimeView = (TextView) view.findViewById(R.id.row_datetime);

        teamsView.setText(match.getTeam1() + " VS " + match.getTeam2());
        datetimeView.setText(match.getDate() + " - " + match.getTime());

        view.setId(match.getMatchID());
        view.setTag(match.getMatchID());


        ImageView imgDelete = (ImageView) view.findViewById(R.id.imgDelete);

        imgDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder confirmation = new AlertDialog.Builder(context);
                confirmation.setMessage("Are you sure you want to delete this match?");
                confirmation.setCancelable(true);

                confirmation.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                DatabaseHelper db = new DatabaseHelper(context);
                                db.removeMatch(db.getMatch(view.getId()));
                                if (context instanceof Activity) {
                                    ((Activity) context).finish();
                                    ((Activity) context).startActivity(new Intent(((Activity) context), Main.class));
                                }
                            }
                        });

                confirmation.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = confirmation.create();
                alert.show();
            }
        });

        ImageView imgEdit = (ImageView) view.findViewById(R.id.imgEdit);

        imgEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(context);
                int matchid = view.getId();
                Intent intent = new Intent(v.getContext(), Edit.class);
                intent.putExtra("matchid", matchid);
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                    v.getContext().startActivity(intent);
                }
            }
        });

        return view;
    }

    @Override
    public Filter getFilter() {
        // TODO Auto-generated method stub
        if(filter == null)
        {
            filter=new CustomFilter();
        }

        return filter;
    }

    class CustomFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // TODO Auto-generated method stub

            FilterResults results=new FilterResults();

            if(constraint != null && constraint.length()>0)
            {
                //CONSTARINT TO UPPER
                constraint=constraint.toString().toUpperCase();

                ArrayList<Match> filters=new ArrayList<Match>();

                //get specific items
                for(int i=0;i<filterList.size();i++)
                {
                    if(filterList.get(i).getTeam1().toUpperCase().contains(constraint) || filterList.get(i).getTeam2().toUpperCase().contains(constraint) || filterList.get(i).getDate().toUpperCase().contains(constraint) || filterList.get(i).getTime().toUpperCase().contains(constraint))
                    {
                        Match p=new Match(filterList.get(i).getTeam1(), filterList.get(i).getTeam2(), filterList.get(i).getDate(), filterList.get(i).getTime());

                        filters.add(p);
                    }
                }

                results.count=filters.size();
                results.values=filters;

            }else
            {
                results.count=filterList.size();
                results.values=filterList;

            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // TODO Auto-generated method stub

            matches=(ArrayList<Match>) results.values;
            notifyDataSetChanged();
        }

    }

    public int getCount() {
        return matches.size();
    }


}

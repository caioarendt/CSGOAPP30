package ie.wit.csgoapp30.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.content.Context;
import java.util.List;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ie.wit.csgoapp30.R;
import ie.wit.csgoapp30.models.Match;
import ie.wit.csgoapp30.sqllite.DatabaseHelper;

/**
 * Created by caio_ on 3/13/2018.
 */

public class MatchAdapter extends ArrayAdapter<Match> {
    private Context context;
    public List<Match> matches;

    public MatchAdapter(Context context, List<Match> matches){
        super(context, R.layout.row_match, matches);
        this.context = context;
        this.matches = matches;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View view = inflater.inflate(R.layout.row_match, parent, false);
        Match match  = matches.get(position);
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

        return view;
    }

    public int getCount(){
        return matches.size();
    }

}

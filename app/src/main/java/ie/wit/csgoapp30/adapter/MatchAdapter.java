package ie.wit.csgoapp30.adapter;

import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.content.Context;
import java.util.List;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ie.wit.csgoapp30.R;
import ie.wit.csgoapp30.models.Match;

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

        View view = inflater.inflate(R.layout.row_match, parent, false);
        Match match  = matches.get(position);
        TextView teamsView = (TextView) view.findViewById(R.id.row_teams);
        TextView datetimeView = (TextView) view.findViewById(R.id.row_datetime);

        teamsView.setText(match.getTeam1() + " VS " + match.getTeam2());
        datetimeView.setText(match.getDate() + " - " + match.getTime());

        view.setId(match.getMatchID());

        return view;
    }

    public int getCount(){
        return matches.size();
    }

}

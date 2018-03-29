package ie.wit.csgoapp30.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import ie.wit.csgoapp30.R;
import ie.wit.csgoapp30.adapter.MatchAdapter;
import ie.wit.csgoapp30.models.Match;
import ie.wit.csgoapp30.sqllite.DatabaseHelper;

public class Main extends AppCompatActivity {

    private ListView listView;
    private DatabaseHelper db;
    List<Match> matches;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity (new Intent(getApplicationContext(), Create.class));
            }
        });

        listView = (ListView) findViewById(R.id.lstMatches);

        db = new DatabaseHelper(this);
        matches = db.getAllMatches();

        MatchAdapter adapter = new MatchAdapter(this, matches);
        listView.setAdapter(adapter);



    }

}

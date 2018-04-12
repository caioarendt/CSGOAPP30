package ie.wit.csgoapp30.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ie.wit.csgoapp30.R;
import ie.wit.csgoapp30.adapter.MatchAdapter;
import ie.wit.csgoapp30.models.Match;
import ie.wit.csgoapp30.session.Session;
import ie.wit.csgoapp30.sqllite.DatabaseHelper;

public class Main extends AppCompatActivity {

    private ListView listView;
    private DatabaseHelper db;
    List<Match> matches;
    private MatchAdapter adapter;
    private TextView txtGreetings;
    private Session session;
    private String email;
    private EditText edtSearch;


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
        txtGreetings = (TextView) findViewById(R.id.txtGreetings);
        edtSearch = (EditText) findViewById(R.id.edtSearch);

        session = new Session(this);
        txtGreetings.setText("Welcome " + session.getName());

        db = new DatabaseHelper(this);
        matches = db.getAllMatches();

        adapter = new MatchAdapter(this, matches);
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);

        edtSearch.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                Main.this.adapter.getFilter().filter(arg0.toString());
            }

            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

            }

            public void afterTextChanged(Editable arg0) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.action_logout:
                finish();
                startActivity (new Intent(this, Login.class));
                break;
            case R.id.action_reset:
                if(!matches.isEmpty()) {

                    AlertDialog.Builder confirmation = new AlertDialog.Builder(this);
                    confirmation.setMessage("Are you sure you want to delete all matches?");
                    confirmation.setCancelable(true);

                    confirmation.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                                    db.deleteAllMatches();
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), Main.class));
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


                    break;
                }else{
                    Toast t = Toast.makeText(this, "Nothing to delete!", Toast.LENGTH_SHORT);
                    t.show();
                }
        }

        return super.onOptionsItemSelected(item);
    }

}

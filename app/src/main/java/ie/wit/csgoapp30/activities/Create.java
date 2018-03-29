package ie.wit.csgoapp30.activities;

/**
 * Created by caio_ on 2/20/2018.
 * Android tutorials for hassle-free android development and programming. (2018). Android SQLite Database Tutorial - CRUD Operations. [online] Available at: https://www.androidtutorialpoint.com/storage/android-sqlite-database-tutorial/ [Accessed 2 Mar. 2018].
 */

import android.app.Application;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import ie.wit.csgoapp30.R;
import ie.wit.csgoapp30.models.Match;
import ie.wit.csgoapp30.sqllite.DatabaseHelper;

public class Create extends AppCompatActivity {

    private EditText edtTeam1;
    private EditText edtTeam2;
    private EditText edtDate;
    private EditText edtTime;
    private DatabaseHelper db;
    private Match newMatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        edtTeam1 = (EditText) findViewById(R.id.edtTeam1);
        edtTeam2 = (EditText) findViewById(R.id.edtTeam2);
        edtDate = (EditText) findViewById(R.id.edtDate);
        edtTime = (EditText) findViewById(R.id.edtTime);
        newMatch = new Match();
    }

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
            case R.id.action_main:
                startActivity (new Intent(this, Main.class));
                break;
            case R.id.action_create:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void New(View view){

        String team1, team2, date, time;
        team1 = edtTeam1.getText().toString();
        team2 = edtTeam2.getText().toString();
        date = edtDate.getText().toString();
        time = edtTime.getText().toString();

        if(team1.isEmpty() || team2.isEmpty() || date.isEmpty() || time.isEmpty()){
            Toast t = Toast.makeText(this, "Error ! Please fill in all the data", Toast.LENGTH_SHORT);
            t.show();
        }else if(!isValidDate(date)) {
            Toast t = Toast.makeText(this, "Error ! Please enter a valid date", Toast.LENGTH_SHORT);
            t.show();
        }else if(!isValidTime(time)) {
            Toast t = Toast.makeText(this, "Error ! Please enter a valid time", Toast.LENGTH_SHORT);
            t.show();
        }else{
            newMatch.setTeam1(team1);
            newMatch.setTeam2(team2);
            newMatch.setDate(date);
            newMatch.setTime(time);
            db = new DatabaseHelper(this);
            db.addMatch(newMatch);
            db.close();
            Toast t = Toast.makeText(this, "Match : "+ newMatch.getTeam1() + " VS " + newMatch.getTeam2() + ", " + newMatch.getDate() + " - " + newMatch.getTime() + " has been added successfully !", Toast.LENGTH_SHORT);
            t.show();
            startActivity (new Intent(this, Main.class));
        }


    }

    public static boolean isValidTime(String time){
        String[] septime = time.split(":");
        if(septime.length  != 2){
            return false;
        } else if(Integer.parseInt(septime[0]) > 23 || Integer.parseInt(septime[0]) < 0 || Integer.parseInt(septime[1]) < 0 || Integer.parseInt(septime[1]) >59) {
            return false;
        }else{
            return true;
        }

    }


    /**
     *  Java, R. (2018). Regex date format validation on Java. [online] Stackoverflow.com. Available at: https://stackoverflow.com/questions/2149680/regex-date-format-validation-on-java [Accessed 2 Mar. 2018].
     *  */
    public static boolean isValidDate(String text) {
        if (text == null || !text.matches("[0-3]\\d-[01]\\d-\\d{4}"))
            return false;
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        df.setLenient(false);
        try {
            df.parse(text);
            return true;
        } catch (ParseException ex) {
            return false;
        }
    }


    public void Cancel(View view){
        startActivity (new Intent(this, Main.class));
    }
}

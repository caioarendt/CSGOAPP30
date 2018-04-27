package ie.wit.csgoapp30.activities;

/**
 * Created by caio_ on 2/20/2018.
 * Android tutorials for hassle-free android development and programming. (2018). Android SQLite Database Tutorial - CRUD Operations. [online] Available at: https://www.androidtutorialpoint.com/storage/android-sqlite-database-tutorial/ [Accessed 2 Mar. 2018].
 */

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import ie.wit.csgoapp30.R;
import ie.wit.csgoapp30.models.Match;
import ie.wit.csgoapp30.sqllite.DatabaseHelper;

public class Edit extends AppCompatActivity {



    private DatabaseHelper db;
    private EditText edtTeam1;
    private EditText edtTeam2;
    private EditText edtDate;
    private EditText edtTime;
    private Match oldMatch;
    private Match newMatch;
    private int matchid;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        edtTeam1 = (EditText) findViewById(R.id.edtTeam1);
        edtTeam2 = (EditText) findViewById(R.id.edtTeam2);
        edtDate = (EditText) findViewById(R.id.edtDate);
        edtTime = (EditText) findViewById(R.id.edtTime);
        oldMatch = new Match();

        Bundle bundle=getIntent().getExtras();
        matchid = bundle.getInt("matchid");

        db = new DatabaseHelper(this);
        oldMatch = db.getMatch(matchid);
        db.close();
        edtTeam1.setText(oldMatch.getTeam1());
        edtTeam2.setText(oldMatch.getTeam2());
        edtDate.setText(oldMatch.getDate());
        edtTime.setText(oldMatch.getTime());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activities, menu);
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
            case R.id.action_create:
                startActivity (new Intent(this, Create.class));
                break;
            case R.id.action_main:
                startActivity(new Intent(this, Main.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void SaveChanges(View view){

        final String team1 = edtTeam1.getText().toString();
        final String team2 = edtTeam2.getText().toString();
        final String date = edtDate.getText().toString();
        final String time = edtTime.getText().toString();

        if(team1.trim().isEmpty() || team2.trim().isEmpty() || date.trim().isEmpty() || time.trim().isEmpty()){
            Toast t = Toast.makeText(this, "Error ! Please fill in all the data", Toast.LENGTH_SHORT);
            t.show();
        }else{
            AlertDialog.Builder builder1 = new AlertDialog.Builder(Edit.this);
            builder1.setMessage("Are you sure you want to save the changes?");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            newMatch = new Match();
                            newMatch.setMatchID(matchid);
                            newMatch.setTeam1(team1);
                            newMatch.setTeam2(team2);
                            newMatch.setDate(date);
                            newMatch.setTime(time);
                            db = new DatabaseHelper(getApplicationContext());
                            db.updateMatch(newMatch);
                            db.close();
                            Toast t = Toast.makeText(Edit.this, "Match : " + newMatch.getTeam1() + " VS " + newMatch.getTeam2() + ", " + newMatch.getDate() + " - " + newMatch.getTime() + " has been updated successfully !", Toast.LENGTH_SHORT);
                            t.show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), Main.class));
                        }
                    });

            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
    }

    public static boolean isValidTime(String time){
        String[] septime = time.split(":");
        if(septime.length != 2){
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
        Intent intent = new Intent(this, Main.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }

    // https://stackoverflow.com/questions/17901946/timepicker-dialog-from-clicking-edittext?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
    public void Calendar(View view){
        String[] date = edtDate.getText().toString().split("-");
        int mDay = Integer.parseInt(date[0]);
        int mMonth = Integer.parseInt(date[1]) - 1;
        int mYear = Integer.parseInt(date[2]);
        DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                selectedmonth += 1;
                edtDate.setText(selectedday + "-" + selectedmonth + "-" + selectedyear);
            }
        }, mYear, mMonth, mDay);
        mDatePicker.setTitle("Select Date");
        mDatePicker.show();
    }

    public void Clock(View view){
        String[] time = edtTime.getText().toString().split(":");
        int hour = Integer.parseInt(time[0]);
        int minute = Integer.parseInt(time[1]);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                edtTime.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }
}

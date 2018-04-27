package ie.wit.csgoapp30.activities;

/**
 * Created by caio_ on 2/20/2018.
 * Android tutorials for hassle-free android development and programming. (2018). Android SQLite Database Tutorial - CRUD Operations. [online] Available at: https://www.androidtutorialpoint.com/storage/android-sqlite-database-tutorial/ [Accessed 2 Mar. 2018].
 */

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
            case R.id.action_main:
                finish();
                startActivity(new Intent(this, Main.class));
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

        if(team1.trim().isEmpty() || team2.trim().isEmpty() || date.trim().isEmpty() || time.trim().isEmpty()){
            Toast t = Toast.makeText(this, "Error ! Please fill in all the data", Toast.LENGTH_SHORT);
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
            finish();
            startActivity(new Intent(this, Main.class));
        }


    }

    public void Cancel(View view){
        finish();
        startActivity(new Intent(this, Main.class));
    }

    // https://stackoverflow.com/questions/17901946/timepicker-dialog-from-clicking-edittext?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
    public void Calendar(View view){
        Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                edtDate.setText(selectedday + "-" + selectedmonth + "-" + selectedyear);
            }
        }, mYear, mMonth, mDay);
        mDatePicker.setTitle("Select Date");
        mDatePicker.show();
    }

    public void Clock(View view){
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
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

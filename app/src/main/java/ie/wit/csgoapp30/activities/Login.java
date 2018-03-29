package ie.wit.csgoapp30.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ie.wit.csgoapp30.sqllite.DatabaseHelper;

import org.w3c.dom.Text;

import ie.wit.csgoapp30.R;
import ie.wit.csgoapp30.helpers.InputValidation;

public class Login extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin;
    private Button btnRegister;
    private TextView txtEmail;
    private TextView txtPassword;
    private DatabaseHelper databaseHelper;
    private InputValidation inputValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initViews();
        initObjects();


    }

    private void initViews(){

        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtPassword = (TextView) findViewById(R.id.txtPassword);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);

    }

    private void initObjects(){
        databaseHelper = new DatabaseHelper(this);
        inputValidation = new InputValidation(this);
    }

    private void emptyEditText(){
        edtEmail.setText(null);
        edtPassword.setText(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void login(View view){
        if(!inputValidation.isValidEmail(edtEmail.getText().toString()) || inputValidation.isEmpty(edtEmail.getText().toString()) || inputValidation.isEmpty(edtPassword.getText().toString())){
            if(databaseHelper.checkUser(edtEmail.toString().trim(), edtPassword.toString().trim())){
                Intent intent = new Intent(this, Main.class);
                intent.putExtra("email", edtEmail.getText().toString().trim());
                emptyEditText();
                startActivity(intent);
            }else{
                edtPassword.setError("Wrong email or password!");
            }
        }else{
            edtPassword.setError("Please fill in a valid email and password!");
        }
    }

    public void register(View view){

    }
}

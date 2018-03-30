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

import ie.wit.csgoapp30.models.User;
import ie.wit.csgoapp30.session.Session;
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
    private Session session;

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

        session = new Session(this);
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

        switch(id){
            case R.id.action_register:
                finish();
                startActivity (new Intent(this, Register.class));
                break;
        }
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    public void login(View view){
        if(inputValidation.isValidEmail(edtEmail.getText().toString()) || !inputValidation.isStringEmpty(edtEmail.getText().toString()) || !inputValidation.isStringEmpty(edtPassword.getText().toString())){
            if(databaseHelper.checkUser(edtEmail.getText().toString().trim(), edtPassword.getText().toString().trim())){

                User user;
                user = databaseHelper.getUser(edtEmail.getText().toString().trim());
                session.setName(user.getName());
                databaseHelper.close();
                emptyEditText();
                startActivity(new Intent(this, Main.class));
            }else{
                if(!databaseHelper.checkUser(edtEmail.getText().toString().trim())){
                  edtEmail.setError("Wrong email!");
                }else {
                    edtPassword.setError("Wrong password!");
                }
            }
        }else{
            edtPassword.setError("Please fill in a valid email and password!");
        }
    }

    public void register(View view){
        finish();
        startActivity (new Intent(this, Register.class));
    }
}

package ie.wit.csgoapp30.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ie.wit.csgoapp30.R;
import ie.wit.csgoapp30.helpers.InputValidation;
import ie.wit.csgoapp30.models.User;
import ie.wit.csgoapp30.sqllite.DatabaseHelper;

public class Register extends AppCompatActivity {

    private EditText edtName;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtPassword2;
    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        initObjects();

    }

    public void addNewUser(View view){
        if(inputValidation.isStringEmpty(edtEmail.getText().toString()) || inputValidation.isStringEmpty(edtName.getText().toString()) || inputValidation.isStringEmpty(edtPassword.getText().toString()) || inputValidation.isStringEmpty(edtPassword2.getText().toString())){
            Toast t = Toast.makeText(this,"Please fill in all the fields!",Toast.LENGTH_SHORT);
            t.show();
        }else if(!inputValidation.isValidEmail(edtEmail.getText().toString())) {
            Toast t = Toast.makeText(this,"Invalid email!",Toast.LENGTH_SHORT);
            t.show();
        }else if(!inputValidation.isEqual(edtPassword.getText().toString(), edtPassword2.getText().toString())) {
            Toast t = Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT);
            t.show();
        }else if (edtPassword.getText().toString().length()<5){
            Toast t = Toast.makeText(this, "Passwords too short! minimum of 5 characters", Toast.LENGTH_SHORT);
            t.show();
        }else if(databaseHelper.checkUser(edtEmail.getText().toString())){
            Toast t = Toast.makeText(this, "Email already registered!", Toast.LENGTH_SHORT);
            t.show();
        }else{
            user.setName(edtName.getText().toString().trim());
            user.setEmail(edtEmail.getText().toString().trim());
            user.setPassword(edtPassword.getText().toString().trim());

            databaseHelper.addUser(user);
            Toast t = Toast.makeText(this, user.getName().toString() + " registered!", Toast.LENGTH_SHORT);
            t.show();
            finish();
            startActivity(new Intent(this, Login.class));
        }
    }

    public void cancel(View view){
        finish();
        startActivity(new Intent(this, Login.class));
    }

    public void initObjects(){
        inputValidation = new InputValidation(this);
        databaseHelper = new DatabaseHelper(this);
        user = new User();
    }

    public void initViews(){
        edtName = (EditText) findViewById(R.id.edtName);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtPassword2 = (EditText) findViewById(R.id.edtPassword2);

    }

}

package com.example.sf646_a04.fjushv1.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sf646_a04.fjushv1.R;
import com.example.sf646_a04.fjushv1.helpers.MD5Helper;

public class LoginActivity extends AppCompatActivity
    implements View.OnClickListener{

    EditText usern,userp;
    Button butlogin,butregis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usern = (EditText)findViewById(R.id.username);
        userp = (EditText)findViewById(R.id.password);
        butlogin = (Button)findViewById(R.id.login);
        butregis = (Button)findViewById(R.id.logreg);
    }

    public void Login(){
        MD5Helper md5 = new MD5Helper();
        String name = usern.getText().toString();
        String pass = md5.getMD5(userp.getText().toString());

    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login){
            Login();
            //get data from database
        }
        else if (v.getId() == R.id.logreg){
            Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(register);
        }
    }
}

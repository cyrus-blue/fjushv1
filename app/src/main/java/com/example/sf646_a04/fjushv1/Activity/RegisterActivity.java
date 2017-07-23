package com.example.sf646_a04.fjushv1.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sf646_a04.fjushv1.DBconnector;
import com.example.sf646_a04.fjushv1.R;
import com.example.sf646_a04.fjushv1.SendMail;
import com.example.sf646_a04.fjushv1.Userinfo;
import com.example.sf646_a04.fjushv1.helpers.MD5Helper;

import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by SF646_A04 on 2017/5/30.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    //Declaring EditText
    EditText editTextEmail,editPassword,conPassword,editVerify,editName;
    Button buttonSend,buttonRegist;
    String verification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regeister);

        //Initializing the views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        editName = (EditText) findViewById(R.id.editName);
        editVerify = (EditText) findViewById(R.id.editVerify);
        conPassword = (EditText) findViewById(R.id.conPassword);
        buttonSend = (Button) findViewById(R.id.buttonSend);
        buttonRegist = (Button)findViewById(R.id.registerbut);
        //Adding click listener
        buttonSend.setOnClickListener(this);
        buttonRegist.setOnClickListener(this);
    }


    private void sendEmail() {
        //Getting content for email
        String email = editTextEmail.getText().toString().trim();
        String subject = "輔大二手驗證信息";
        String message = "您好：\n您注冊的輔大二手平臺驗證碼為：" + verification + "請及時輸入。謝謝！\n輔大二手團隊";
        SendMail sm = new SendMail(this, email, subject, message);
        //Executing sendmail to send email
        sm.execute();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonSend){
            randomvc();
            sendEmail();
        }
        else if (v.getId() == R.id.registerbut){
            String test = editVerify.getText().toString();
            MD5Helper md5 = new MD5Helper();
            if (test.equals(verification)){
                Userinfo user = new Userinfo();
                user.useremail = editTextEmail.getText().toString().trim();
                user.username = editName.getText().toString();
                user.userpassword = md5.getMD5(editPassword.getText().toString());
                Date date = null;//获取系统时间
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
                    user.createtime = new Timestamp(date.getTime());//Date类型转换为java.sql.Timestamp类型
                    new DBconnector().uploadUser(user);
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
            //send data to database
            //return to login activity
        }
    }

    public void randomvc (){
        Random rgen = new Random();
        int rint = rgen.nextInt(9) + 48;
        for (int i = 0;i < 6;i++){
            verification += (char)rint;
        }
    }
}

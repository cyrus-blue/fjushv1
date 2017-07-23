package com.example.sf646_a04.fjushv1;

import android.util.Log;

import com.example.sf646_a04.fjushv1.Activity.RegisterActivity;
import com.example.sf646_a04.fjushv1.helpers.NotificationHelper;
import com.example.sf646_a04.fjushv1.imgurmodel.UploadtoDB;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by SF646_A04 on 2017/5/9.
 */

public class DBconnector {
    static URL urlforQuery = null;
    static HttpURLConnection urlConnection = null;

    private static Connection con = null;
    private static PreparedStatement pst = null;
    private ResultSet rs = null;
    private static String url = "jdbc:mysql://mysql4.gear.host:3306/";
    private static String user = "secondhands";
    private static String password = "123456!";

    public static String executeQuery(String query_string) {
        String result = "";
        InputStream is =null;
        try {
            switch (query_string){ //php的位置
                case "SELECT * FROM 3c":
                urlforQuery=new URL("http://140.136.150.74/gearhostdb.php");break;
                case "SELECT * FROM category":
                    urlforQuery=new URL("http://140.136.150.74/category.php");break;
            }
            urlConnection=(HttpURLConnection) urlforQuery.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();//接通資料庫
            is=urlConnection.getInputStream();//從database 開啟 stream

            BufferedReader bufReader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
            StringBuilder builder = new StringBuilder();
            String line = null;
            while((line = bufReader.readLine()) != null) {
                builder.append(line + "\n");
            }
            is.close();
            result = builder.toString();
        } catch(Exception e) {
            Log.e("log_tag", e.toString());
        }

        return result;
    }
    public static void uploadData(UploadtoDB udb) throws MalformedURLException {
        try {
            con = DriverManager.getConnection(url, user, password);
            Statement st = (Statement) con.createStatement();

            st.executeUpdate("INSERT INTO incomeCalc " + "VALUES (3, 75, 6, 25, 18.50)");

            con.close();
        }
        catch (SQLException ex) {
            Logger lgr = Logger.getLogger(NotificationHelper.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
        //connect to database
    }
    public static void uploadUser(Userinfo uinfo) throws MalformedURLException {
        try {
            con = DriverManager.getConnection(url, user, password);
            String st = "INSERT INTO user(username,email,password,create_time) VALUES (?, ?, ?, ?, ?)";
            pst = con.prepareStatement(st);
            pst.setString(1,uinfo.username);
            pst.setString(2,uinfo.useremail);
            pst.setString(3,uinfo.userpassword);
            pst.setTimestamp(4,uinfo.createtime);
            pst.executeUpdate();
            con.close();
        }
        catch (SQLException ex) {
            Logger lgr = Logger.getLogger(RegisterActivity.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        //connect to database
    }
}

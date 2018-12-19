package edu.valdosta.finalproject_backend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    static String username;
    static String password;
    public void onClickSignIn(View v) {
        EditText usNm = findViewById(R.id.editText_username);
        EditText pass = findViewById(R.id.editText_password);
        username = usNm.getText().toString();
        password = pass.getText().toString();
        String url="http://srdb1.com/login.php?username="+username+"&password="+password;
        final Downloader d = new Downloader(this, url, username, password, 3);
        d.execute();
    }

    public void onClickSignUp1(View v) {

        EditText usNm = findViewById(R.id.editText_username);
        EditText pass = findViewById(R.id.editText_password);
        username = usNm.getText().toString();
        password = pass.getText().toString();

        String urlAddress = "http://srdb1.com/signup.php";

        //Start async task
        Sender s = new Sender(this,urlAddress,username,password, 3);
        s.execute();
    }

}
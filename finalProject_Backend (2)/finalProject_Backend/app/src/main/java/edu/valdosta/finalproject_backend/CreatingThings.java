package edu.valdosta.finalproject_backend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreatingThings extends AppCompatActivity {

    public SiteManager sM;
    public User currentUser;
    String nameString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SiteManager sM = new SiteManager();
        User u1 = new User(sM, "DanforThing", "Bryce", sM.getUserNum(),"123");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_things);
        Bundle extras = getIntent().getExtras();
        nameString = extras.getString("name");
    }

    public void onClick(View view){

        EditText name = findViewById(R.id.editText_title);

        if (!HomepageActivity.names.contains(name.getText().toString())) {
            String urlAddress = "http://srdb1.com/poster.php";

            //Start async task
            Sender s = new Sender(this,urlAddress,name.getText().toString(),nameString, 0);
            s.execute();


            Intent intent = new Intent(this, HomepageActivity.class);
            intent.putExtra("userName", nameString);
            startActivity(intent);
        } else {
            Toast.makeText(this,"Group already exists",Toast.LENGTH_LONG).show();
        }



    }
}

package edu.valdosta.finalproject_backend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class posts extends AppCompatActivity {
    public SiteManager sM;
    public User currentUser;
    public Question q;
    ListView itemListView;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> out = new ArrayList<>();
    public Question currentQ;
    int postnumber;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Database
        SiteManager sM = new SiteManager();
        User u1 = new User(sM, "DanforThing", "Bryce", sM.getUserNum(),"123");
        String content = getIntent().getStringExtra("post");
        String group = getIntent().getStringExtra("group");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.posts);
        TextView t = findViewById(R.id.textView_QText);
        t.setText(content);


        itemListView = findViewById(R.id.listView_posts);
        int identifier = 2;
        postnumber = getIntent().getIntExtra("postnumber",0);
        username = getIntent().getStringExtra("username");
        String url="http://srdb1.com/getcomments.php?postnumber="+postnumber;
        Log.d("URL",""+url);

        final Downloader d= new Downloader(this,url,itemListView, out, identifier);
        d.execute(); // execute download

        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                out
        );

        itemListView.setAdapter(arrayAdapter);

    }

    public void addItem(View view){
        EditText input = findViewById(R.id.editText_AddQuestion);
        String item = input.getText().toString();
        out.add(item);
        arrayAdapter.notifyDataSetChanged();
        input.setText("");

        String urlAddress = "http://srdb1.com/commentposter.php";

        //Start async task
        Sender s = new Sender(this , postnumber, urlAddress, username, item, 2);
        s.execute();

    }


}


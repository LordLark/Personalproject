package edu.valdosta.finalproject_backend;

import android.content.Intent;
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

public class GroupActivity extends AppCompatActivity {
    public User currentUser;
    public Group currentGroup;
    ListView itemListView;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> out = new ArrayList<>();
    int groupnumber;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);


        SiteManager sM = new SiteManager();
        User u1 = new User(sM, "DanforThing", "Bryce", sM.getUserNum(),"123");

        String s = getIntent().getStringExtra("name");
        username = getIntent().getStringExtra("username");
        TextView t = findViewById(R.id.textView_groupName);
        t.setText(s);

        itemListView = findViewById(R.id.listView3);

        final ArrayList<Integer> commentnumbers = new ArrayList<>();
        int identifier = 1;
        groupnumber = getIntent().getIntExtra("groupnumber",0);
        String url="http://srdb1.com/getposts.php?groupnumber="+groupnumber;
        Log.d("URL",""+url);

        final Downloader d = new Downloader(this,url,itemListView, out, commentnumbers, identifier);
        d.execute(); // execute download

        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                out
        );

        itemListView.setAdapter(arrayAdapter);

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                    String s = itemListView.getItemAtPosition(position).toString();
                                                    int postnumber = commentnumbers.get(position);
                                                    change(s, postnumber);

                                                    arrayAdapter.notifyDataSetChanged();
                                                }
                                            }
        );


    }

    public void onClick(View view){

        String s1 = Integer.toString(currentUser.getPosts().size());
        String s2 = Integer.toString(currentUser.getGroups().size());

        Intent in = new Intent(this, UserProfile.class);
        in.putExtra("name2", username);
        startActivity(in);

    }

    public void change(String s, int postnumber){
        Intent intent = new Intent(this,posts.class);
        intent.putExtra("post",s);
        intent.putExtra("postnumber",postnumber);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    public void addItem(View view){
        EditText input = findViewById(R.id.editText);
        String item = input.getText().toString();
        out.add(item);
        arrayAdapter.notifyDataSetChanged();
        input.setText("");

        String urlAddress = "http://srdb1.com/questionposter.php";

        //Start async task
        Sender s = new Sender(this ,groupnumber, urlAddress, username, item, 1);
        s.execute();

    }
}
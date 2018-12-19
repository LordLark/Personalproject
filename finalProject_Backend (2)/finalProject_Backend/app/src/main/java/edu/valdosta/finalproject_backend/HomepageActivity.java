package edu.valdosta.finalproject_backend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomepageActivity extends AppCompatActivity {

    public User currentUser;
    public Group currentGroup;
    ListView itemListView;
    ArrayAdapter<String> arrayAdapter;
    String name;
    static ArrayList<String> names = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final SiteManager sM = new SiteManager();
        User u1 = new User(sM, "DanforThing", "Bryce", sM.getUserNum(),"123");

        Bundle extras = getIntent().getExtras();
        name = extras.getString("userName");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextView t = findViewById(R.id.textView);
        t.setText("Welcome " + name + "!");

        currentUser = sM.findUser(name);
        names = new ArrayList<>();
        final ArrayList<Integer> groupnumbers = new ArrayList<>();
        int identifier = 0;

        itemListView = findViewById(R.id.listView);

        String url="http://srdb1.com/getallgroups.php?username="+name;
        final Downloader d = new Downloader(this,url,itemListView, names, groupnumbers, identifier);
        d.execute();


        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                names
        );

        itemListView.setAdapter(arrayAdapter);

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                    int groupnumber = groupnumbers.get(position);
                                                    String s = itemListView.getItemAtPosition(position).toString();
                                                    currentGroup = sM.findGroup(s);

                                                    change(s, groupnumber);

                                                    arrayAdapter.notifyDataSetChanged();
                                                }
                                            }
        );


    }

    public void onClick(View view){


        Intent in = new Intent(this, UserProfile.class);
        in.putExtra("name2", name);

        startActivity(in);
    }

    public void change(String s, int groupnumber){
        Intent intent = new Intent(this,GroupActivity.class);
        Log.d("groupnumber",""+groupnumber);
        intent.putExtra("groupnumber",groupnumber);
        intent.putExtra("name",s);
        intent.putExtra("username",name);
        startActivity(intent);

    }

    public void onClickChange(View view){
        Intent intent = new Intent(this, CreatingThings.class);
        intent.putExtra("name",name);
        intent.putExtra("namesarraylist",names);
        startActivity(intent);
    }


}
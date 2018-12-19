package edu.valdosta.finalproject_backend;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class UserProfile extends AppCompatActivity {
    public User currentUser;
    static ArrayList<String> names = new ArrayList<>();
    ListView itemListView;
    ArrayAdapter<String> arrayAdapter;
    String name;
    static TextView t2, t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        name = extras.getString("name2");

        SiteManager sM = new SiteManager();
        User u1 = new User(sM, "DanforThing", "Bryce", sM.getUserNum(),"123");
        currentUser = u1;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        t = findViewById(R.id.textView3);
        t2 = findViewById(R.id.textView4);
        TextView  t3 = findViewById(R.id.textView2);

        t.setText("0");
        t2.setText("0");
        t3.setText(name);

        ArrayList<Question> p = currentUser.getPosts();
        ArrayList<String> out = new ArrayList<>();
        for(Question q:p){
            out.add(q.content + "  -   posted in " + q.getGroup());
        }
        itemListView = findViewById(R.id.listView2);

        final ArrayList<Integer> groupnumbers = new ArrayList<>();
        int identifier = 0;
        String url="http://srdb1.com/getgroups.php?username="+name;
        Downloader d = new Downloader(this,url,itemListView, names, groupnumbers, identifier);
        d.execute();

        url="http://srdb1.com/postscount.php?username="+name;
        d = new Downloader(this,url,itemListView, names, groupnumbers, 4);
        d.execute();
        arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                out
        );

        itemListView.setAdapter(arrayAdapter);

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                    int groupnumber = groupnumbers.get(position);
                                                    String s = itemListView.getItemAtPosition(position).toString();

                                                    change(s, groupnumber);

                                                    arrayAdapter.notifyDataSetChanged();
                                                }
                                            }
        );

    }

    public void change(String s, int groupnumber){
        Intent intent = new Intent(this,GroupActivity.class);
        Log.d("groupnumber",""+groupnumber);
        intent.putExtra("groupnumber",groupnumber);
        intent.putExtra("name",s);
        intent.putExtra("username",name);
        startActivity(intent);

    }

}
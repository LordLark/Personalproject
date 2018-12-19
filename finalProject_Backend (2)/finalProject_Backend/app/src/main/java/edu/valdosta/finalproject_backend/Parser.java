package edu.valdosta.finalproject_backend;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class Parser extends AsyncTask<Void,Integer,Integer> {

    Context c;
    ListView lv;
    String data;
    ArrayList<String> names;
    ArrayList<Integer> groupnumbers;
    int identifier;
    String usernameEntered, passwordEntered, usernameRetrieved, passwordRetrieved;
    ProgressDialog pd;


    public Parser(Context c, String data, ListView lv, ArrayList<String> names, ArrayList<Integer> groupnumbers, int identifier) {
        this.c = c;
        this.data = data;
        this.lv = lv;
        this.names = names;
        this.groupnumbers= groupnumbers;
        this.identifier = identifier;
    }

    public Parser(Context c, String data, String usernameEntered, String passwordEntered, int identifier) {
        this.c = c;
        this.data = data;
        this.usernameEntered = usernameEntered;
        this.passwordEntered = passwordEntered;
        this.identifier = identifier;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd=new ProgressDialog(c);
        pd.setTitle("Parser");
        pd.setMessage("Parsing ....Please wait");
        pd.show();
    }


    @Override
    protected Integer doInBackground(Void... params) {

        return this.parse();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);

        if(integer == 1) {
            if (identifier==0) {
                if (UserProfile.t2!=null) {
                    UserProfile.t2.setText(""+names.size());
                }
            }
            if (identifier==0 || identifier==1 || identifier==2) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(c, android.R.layout.simple_list_item_1, names);
                lv.setAdapter(adapter);
            } else if (identifier==3) {
                Log.d("Parser","usernameEntered: "+usernameEntered);
                Log.d("Parser","usernameRetrieved: "+usernameRetrieved);
                Log.d("Parser","passwordEntered: "+passwordEntered);
                Log.d("Parser","passwordRetrieved: "+passwordRetrieved);
                if (usernameEntered.equals(usernameRetrieved) && passwordEntered.equals(passwordRetrieved)) {
                    Toast.makeText(c, ("Login successfull"),
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(c, HomepageActivity.class);
                    intent.putExtra("userName", usernameRetrieved);
                    c.startActivity(intent);
                } else if (!usernameEntered.equals(usernameRetrieved) && passwordEntered.equals(passwordRetrieved)) {
                    Toast.makeText(c, ("Username or email not found"),
                            Toast.LENGTH_LONG).show();
                } else if (usernameEntered.equals(usernameRetrieved) && !passwordEntered.equals(passwordRetrieved)) {
                    Toast.makeText(c, "Incorrect password",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(c, "Wrong credentials",
                            Toast.LENGTH_LONG).show();
                }

            }

        } else {
            if (identifier==3) {
                Toast.makeText(c, "Wrong credentials",
                        Toast.LENGTH_LONG).show();
            }
        }

        pd.dismiss();
    }

    private int parse()
    {
        try
        {
            JSONArray ja;
            if (data!=null) {
                ja = new JSONArray(data);
            } else {
                return 0;
            }

            JSONObject jo = null;

            if (identifier==0 || identifier==1 || identifier==2) {
                names.clear();
            }

            for(int i=0; i<ja.length(); i++) {
                jo = ja.getJSONObject(i);
                if (identifier==0) {
                    String name = jo.getString("Group_Name");
                    int groupnumber =  Integer.parseInt(jo.getString("G_number"));
                    names.add(name);
                    groupnumbers.add(groupnumber);
                } else if (identifier==1) {
                    String question = jo.getString("Question");
                    Log.d("Question","i: "+question);
                    int postnumber =  Integer.parseInt(jo.getString("post_number"));
                    Log.d("postnumber","i: "+postnumber);
                    String postedby = jo.getString("User_name");
                    names.add(postedby+": "+question);
                    groupnumbers.add(postnumber);
                } else if (identifier==2){
                    String comment = jo.getString("User_Comment");
                    Log.d("comment","i: "+comment);
                    String commentby = jo.getString("User_Name");
                    names.add(commentby+": "+comment);
                }  else if (identifier==3){
                    usernameRetrieved = jo.getString("User_Name");
                    passwordRetrieved = jo.getString("User_Password");
                    Log.d("login","User_Name: "+usernameRetrieved);
                    Log.d("login","User_Password: "+passwordRetrieved);
                }
            }

            return 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }
}


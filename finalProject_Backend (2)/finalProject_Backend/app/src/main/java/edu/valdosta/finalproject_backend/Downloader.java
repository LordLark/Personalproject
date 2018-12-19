package edu.valdosta.finalproject_backend;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Downloader extends AsyncTask<Void,Integer,String> {

    Context c;
    String address;
    ListView lv;
    ArrayList<String> names;
    int identifier;
    ArrayList<Integer> groupnumbers;
    ProgressDialog pd;
    String username, password;

    public Downloader(Context c, String address, ListView lv, ArrayList<String> names, ArrayList<Integer> groupnumbers, int identifier) {
        this.c = c;
        this.address = address;
        this.lv = lv;
        this.names = names;
        this.groupnumbers = groupnumbers;
        this.identifier = identifier;
    }

    public Downloader(Context c, String address, ListView lv, ArrayList<String> names, int identifier) {
        this.c = c;
        this.address = address;
        this.lv = lv;
        this.names = names;
        this.identifier = identifier;
    }

    public Downloader(Context c, String address, String username, String password, int identifier) {
        this.c = c;
        this.address = address;
        this.username = username;
        this.password = password;
        this.identifier = identifier;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd=new ProgressDialog(c);
        pd.setTitle("Fetch Data");
        pd.setMessage("Fetching Data...Please wait");
        pd.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        String data = downloadData();
        return data;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        pd.dismiss();
        if(s != null)
        {
            if (identifier==0 || identifier==1 || identifier==2) {
                Parser p = new Parser(c, s, lv, names, groupnumbers, identifier);
                p.execute();
            } else if (identifier==3) {
                Parser p = new Parser(c, s, username, password, identifier);
                p.execute();

            } else if (identifier==4) {
                Log.d("identifier==4","s: "+s);
                UserProfile.t.setText(s);
            }

        } else
        {
            Toast.makeText(c,"Unable to download data",Toast.LENGTH_SHORT).show();
        }
    }

    private String downloadData()
    {

        InputStream is = null;
        String line = null;

        try {
            URL url=new URL(address);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            is=new BufferedInputStream(con.getInputStream());

            BufferedReader br=new BufferedReader(new InputStreamReader(is));

            StringBuffer sb=new StringBuffer();

            if(br != null) {

                while ((line=br.readLine()) != null) {
                    sb.append(line+"\n");
                }

            }else {
                return null;
            }

            return sb.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(is != null)
            {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}

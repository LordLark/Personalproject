package edu.valdosta.finalproject_backend;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;


public class Sender extends AsyncTask<Void,Void,String> {

    Context c;
    String urlAddress;
    EditText nameTxt,posTxt,teamTxt;

    String name,user;

    ProgressDialog pd;
    int identifier, groupnumber;

    public Sender(Context c, String urlAddress,String nameEditText, String userName, int identifier) {
        this.c = c;
        this.urlAddress = urlAddress;
        this.identifier = identifier;
        name=nameEditText;
        user=userName;

    }

    public Sender(Context c, int groupnumber, String urlAddress, String nameEditText, String userName, int identifier) {
        this.c = c;
        this.urlAddress = urlAddress;
        this.identifier = identifier;
        name=nameEditText;
        user=userName;
        this.groupnumber = groupnumber;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd=new ProgressDialog(c);
        pd.setTitle("Send");
        pd.setMessage("Sending..Please wait");
        pd.show();
    }


    @Override
    protected String doInBackground(Void... params) {
        return this.send();
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);

        pd.dismiss();

        if(response != null)
        {

            Toast.makeText(c,response,Toast.LENGTH_LONG).show();

        }else
        {

            Toast.makeText(c,"Unsuccessful "+response,Toast.LENGTH_LONG).show();
        }
    }

    private String send()
    {
        HttpURLConnection con=Connector.connect(urlAddress);

        if(con==null)
        {
            return null;
        }

        try
        {
            OutputStream os=con.getOutputStream();

            BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            if (identifier==0 || identifier==3) {
                bw.write(new DataPackager(name,user, identifier).packData());
            } else if (identifier==1) {
                Log.d("Sender1","name: "+name);
                Log.d("Sender1","user: "+user);
                bw.write(new DataPackager(groupnumber, name,user, identifier).packData());
            } else if (identifier==2) {
                bw.write(new DataPackager(groupnumber, name,user, identifier).packData());
            }


            bw.flush();
            bw.close();
            os.close();

            int responseCode=con.getResponseCode();

            Log.d("responseCode",""+responseCode);
            if(responseCode==con.HTTP_OK)
            {
                BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuffer response=new StringBuffer();

                String line;

                while ((line=br.readLine()) != null)
                {
                    response.append(line);
                }

                br.close();

                Log.d("response",""+response.toString());
                return response.toString();

            }else
            {

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}

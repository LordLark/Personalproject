package edu.valdosta.finalproject_backend;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;


public class DataPackager {

    String name,user;
    int identifier, commentid;

    public DataPackager(String name, String user, int identifier) {
        this.name = name;
        this.user = user;
        this.identifier = identifier;
    }

    public DataPackager(int commentid, String name, String user, int identifier) {
        this.name = name;
        this.user = user;
        this.identifier = identifier;
        this.commentid = commentid;
    }

    public String packData()
    {
        JSONObject jo=new JSONObject();
        StringBuffer packedData=new StringBuffer();

        try
        {
            String strDate = null;
            if (identifier==1 || identifier==2) {
                Date cal = Calendar.getInstance().getTime();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                strDate = df.format(cal.getTime());
            } else if (identifier==3) {
                Date cal = Calendar.getInstance().getTime();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                strDate = df.format(cal.getTime());
            }

            if (identifier==0) {
                Log.d("jo.put", "Name: "+name);
                Log.d("jo.put", "User: "+user);
                jo.put("Name", name);
                jo.put("User", user);
            } else if (identifier==1) {
                Log.d("jo.put", "Question: "+user);
                Log.d("jo.put", "Username: "+name);
                Log.d("jo.put", "Groupnumber: "+commentid);
                Log.d("jo.put", "date: "+strDate);
                jo.put("Question", user);
                jo.put("Username", name);
                jo.put("Groupnumber", commentid);
                jo.put("date", strDate);
            } else if (identifier==2) {
                Log.d("jo.put", "CommentID: "+commentid);
                Log.d("jo.put", "Username: "+name);
                Log.d("jo.put", "UserComment: "+user);
                Log.d("jo.put", "DateCreated: "+strDate);
                jo.put("CommentID", commentid);
                jo.put("Username", name);
                jo.put("UserComment", user);
                jo.put("DateCreated", strDate);
            } else if (identifier==3) {
                Log.d("jo.put", "User_Name: "+name);
                Log.d("jo.put", "User_Password: "+user);
                Log.d("jo.put", "User_Join_date: "+strDate);
                jo.put("username", name);
                jo.put("password", user);
                jo.put("date", strDate);
            }

            Boolean firstValue=true;

            Iterator it=jo.keys();

            do {
                String key=it.next().toString();
                String value=jo.get(key).toString();

                if(firstValue)
                {
                    firstValue=false;
                }else
                {
                    packedData.append("&");
                }

                packedData.append(URLEncoder.encode(key,"UTF-8"));
                packedData.append("=");
                packedData.append(URLEncoder.encode(value,"UTF-8"));

            } while (it.hasNext());

            Log.d("Packed Data","packedData.toString(): "+packedData.toString());
            return packedData.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

}
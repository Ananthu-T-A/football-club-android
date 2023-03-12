package com.example.footballclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Player_view_practisesection extends AppCompatActivity implements JsonResponse{

    ListView lv1;
    String[] coach,practise,place,date,time,value;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_view_practisesection);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        lv1=(ListView)findViewById(R.id.lv);


        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) Player_view_practisesection.this;
        String q = "/player_view_practise?lid="+sh.getString("log_id","");
        q=q.replace(" ","%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("player_view_practise")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");


                    coach=new String[ja1.length()];
                    practise=new String[ja1.length()];
                    date=new String[ja1.length()];
                    place=new String[ja1.length()];
                    time=new String[ja1.length()];

                    value=new String[ja1.length()];




                    for(int i = 0;i<ja1.length();i++)
                    {



                        coach[i]=ja1.getJSONObject(i).getString("coach");
                        practise[i]=ja1.getJSONObject(i).getString("practice");
                        date[i]=ja1.getJSONObject(i).getString("date");
                        place[i]=ja1.getJSONObject(i).getString("place");
                        time[i]=ja1.getJSONObject(i).getString("time");

                        value[i]="Coach: "+coach[i]+"\nPractise: "+practise[i]+"\nPlace: "+place[i]+"\nDate: "+date[i]+"\nTime: "+time[i];




                    }
//                    Cust_Player_profile clist=new Cust_Player_profile(this,name,email,phone,place,adhar,dob,pic);
//                    lv1.setAdapter(clist);

                    ArrayAdapter<String> ar= new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,value);
                    lv1.setAdapter(ar);


                }

                else {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

                }
            }

        }catch (Exception e)
        {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
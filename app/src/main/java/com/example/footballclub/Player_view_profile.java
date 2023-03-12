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

public class Player_view_profile extends AppCompatActivity implements JsonResponse {

    ListView lv1;
    String[] name,email,phone,place,adhar,pic,dob,value;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_view_profile);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        lv1=(ListView)findViewById(R.id.lv);


        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) Player_view_profile.this;
        String q = "/player_my_profile?lid="+sh.getString("log_id","");
        q=q.replace(" ","%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("player_my_profile")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");


                    name=new String[ja1.length()];
                    email=new String[ja1.length()];
                    phone=new String[ja1.length()];
                    place=new String[ja1.length()];
                    adhar=new String[ja1.length()];
                    pic=new String[ja1.length()];
                    dob=new String[ja1.length()];
                    value=new String[ja1.length()];




                    for(int i = 0;i<ja1.length();i++)
                    {



                        name[i]=ja1.getJSONObject(i).getString("name");
                        email[i]=ja1.getJSONObject(i).getString("email");
                        phone[i]=ja1.getJSONObject(i).getString("phone");
                        place[i]=ja1.getJSONObject(i).getString("place");
                        adhar[i]=ja1.getJSONObject(i).getString("aadhar_no");
                        pic[i]=ja1.getJSONObject(i).getString("photo");
                        dob[i]=ja1.getJSONObject(i).getString("dob");

//                        value[i]="Team: "+name[i]+"\nAbout: "+about[i]+"\nCity: "+city[i]+"\nEmail: "+email[i];




                    }
				Cust_Player_profile clist=new Cust_Player_profile(this,name,email,phone,place,adhar,dob,pic);
				 lv1.setAdapter(clist);

//                    ArrayAdapter<String> ar= new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,value);
//                    lv1.setAdapter(ar);


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
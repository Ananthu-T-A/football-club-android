package com.example.footballclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Player_view_ranks extends AppCompatActivity implements JsonResponse {

    ListView lv1;
    String[] name,rank,value;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full sc
        setContentView(R.layout.activity_player_view_ranks);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        lv1=(ListView)findViewById(R.id.lv);


        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) Player_view_ranks.this;
        String q = "/player_view_rank?lid="+sh.getString("log_id","");
        q=q.replace(" ","%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("player_view_rank")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");


                    name=new String[ja1.length()];
                    rank=new String[ja1.length()];


                    value=new String[ja1.length()];




                    for(int i = 0;i<ja1.length();i++)
                    {



                        name[i]=ja1.getJSONObject(i).getString("name");
                        rank[i]=ja1.getJSONObject(i).getString("rank");


                        value[i]="Player: "+name[i]+"\nRank: "+rank[i];




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
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b = new Intent(getApplicationContext(), PlayerHome.class);
        startActivity(b);
    }
}
package com.example.footballclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Player_view_video extends AppCompatActivity implements JsonResponse,AdapterView.OnItemClickListener {

    ListView lv1;
    String [] photo;
    public static String path;
    String[] video,title,value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full sc
        setContentView(R.layout.activity_player_view_video);


        lv1=(ListView)findViewById(R.id.ltvideo);
        lv1.setOnItemClickListener(this);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) Player_view_video.this;
        String q = "/viewvideos";
        q=q.replace(" ","%20");
        JR.execute(q);

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        path=video[arg2];
        Intent in = new Intent(getApplicationContext(), VideoPlay.class);
        in.putExtra("vdo", title[arg2]);
        startActivity(in);
    }

    @Override
    public void response(JSONObject jo) {

        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("viewvideos")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");


                    video=new String[ja1.length()];
                    title=new String[ja1.length()];
                    value=new String[ja1.length()];



                    for(int i = 0;i<ja1.length();i++)
                    {



                        video[i]=ja1.getJSONObject(i).getString("video");
                        title[i]=ja1.getJSONObject(i).getString("title");
                        value[i]="Title : "+title[i];




                    }
//				Custimage clist=new Custimage(this,photo);
//				 lv1.setAdapter(clist);

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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
package com.example.footballclub;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

public class Coach_view_practicesections extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {

    ListView lv1;
    String[] coach,practise,place,date,time,value,pid;
    SharedPreferences sh;
    public static String prid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full sc
        setContentView(R.layout.activity_coach_view_practicesections);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        lv1=(ListView)findViewById(R.id.lv);

        lv1.setOnItemClickListener(this);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) Coach_view_practicesections.this;
        String q = "/coachviewpractice?lid="+sh.getString("log_id","");
        q=q.replace(" ","%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("coachviewpractice")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");


                    coach=new String[ja1.length()];
                    pid=new String[ja1.length()];
                    practise=new String[ja1.length()];
                    date=new String[ja1.length()];
                    place=new String[ja1.length()];
                    time=new String[ja1.length()];

                    value=new String[ja1.length()];




                    for(int i = 0;i<ja1.length();i++)
                    {



                        coach[i]=ja1.getJSONObject(i).getString("coach");
                        pid[i]=ja1.getJSONObject(i).getString("practice_id");
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        prid=pid[i];

        final CharSequence[] items = {"delate","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Coach_view_practicesections.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals("delate")) {


                    JsonReq JR=new JsonReq();
                    JR.json_response=(JsonResponse) Coach_view_practicesections.this;
                    String q = "/practiceddelete?prid="+prid;
                    q=q.replace(" ","%20");
                    JR.execute(q);

                    Toast.makeText(getApplicationContext(),"Data  Deleted", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),Coach_view_practicesections.class));

                }

                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }

        });
        builder.show();
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b = new Intent(getApplicationContext(), Coachhome.class);
        startActivity(b);
    }
}
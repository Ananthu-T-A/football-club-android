package com.example.footballclub;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Player_view_psysic extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {

    ListView lv1;
    String[] psysician,dob,place,phone,email,value,loginid;
    SharedPreferences sh;
    public static String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_view_psysic);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        lv1=(ListView)findViewById(R.id.lv);
        lv1.setOnItemClickListener(this);


        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) Player_view_psysic.this;
        String q = "/player_view_Physicians?lid="+sh.getString("log_id","");
        q=q.replace(" ","%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("player_view_Physicians")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");


                    psysician=new String[ja1.length()];
                    dob=new String[ja1.length()];
                    phone=new String[ja1.length()];
                    place=new String[ja1.length()];
                    email=new String[ja1.length()];
                    loginid=new String[ja1.length()];

                    value=new String[ja1.length()];




                    for(int i = 0;i<ja1.length();i++)
                    {



                        psysician[i]=ja1.getJSONObject(i).getString("psysician");
                        dob[i]=ja1.getJSONObject(i).getString("dob");
                        phone[i]=ja1.getJSONObject(i).getString("phone");
                        place[i]=ja1.getJSONObject(i).getString("place");
                        email[i]=ja1.getJSONObject(i).getString("email");
                        loginid[i]=ja1.getJSONObject(i).getString("login_id");

                        value[i]="Psysician: "+psysician[i]+"\nDob: "+dob[i]+"\nPlace: "+place[i]+"\nPhone: "+phone[i]+"\nEmail: "+email[i];




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
        number=phone[i];
        SharedPreferences.Editor e=sh.edit();
        e.putString("receiver_id",loginid[i]);
        e.putString("name",psysician[i]);
        e.commit();
        final CharSequence[] items = {"Chat","Call"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Player_view_psysic.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("chat")) {

                    startActivity(new Intent(getApplicationContext(), ChatHere.class));

                } else if (items[item].equals("call")) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+number));//change the number
                    startActivity(callIntent);
//                    startActivity(new Intent(getApplicationContext(), Player_view_nutri.class));

                }

            }

        });
        builder.show();
    }
}
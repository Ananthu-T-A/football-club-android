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
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONObject;

public class PlayerHome extends AppCompatActivity implements JsonResponse {

    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full sc
        setContentView(R.layout.activity_player_home);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        Button b1 = findViewById(R.id.profile);
        Button b2 = findViewById(R.id.news);
        Button b3 = findViewById(R.id.practise);
        Button b4 = findViewById(R.id.videos);
        Button b5 = findViewById(R.id.communication);
        Button b6 = findViewById(R.id.fixture);
        Button b7 = findViewById(R.id.ranks);
        Button b8 = findViewById(R.id.noc);
        Button b9 = findViewById(R.id.complaints);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Player_view_profile.class));
            }
        });

        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),PlayerComplaints.class));
            }
        });

        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JsonReq JR=new JsonReq();
                JR.json_response=(JsonResponse)PlayerHome.this;
                String q="/player_apply_noc?lid=" + sh.getString("log_id","");
                q = q.replace(" ", "%20");
                JR.execute(q);

            }
        });

        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Player_view_ranks.class));
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Player_view_news.class));
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Player_view_practisesection.class));
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Player_view_video.class));
            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Player_view_fixture.class));
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = {"Physicians","Nutrition's","Coach","Cancel"};

                AlertDialog.Builder builder = new AlertDialog.Builder(PlayerHome.this);
                // builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (items[item].equals("Physicians")) {

                            startActivity(new Intent(getApplicationContext(), Player_view_psysic.class));

                        } else if (items[item].equals("Nutrition's")) {
                            startActivity(new Intent(getApplicationContext(), Player_view_nutri.class));
                        }else if (items[item].equals("Coach")) {
                            startActivity(new Intent(getApplicationContext(), Player_view_coach.class));
                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }

                    }

                });
                builder.show();

            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                Toast.makeText(getApplicationContext(), "Request Send Successfully.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), PlayerHome.class));

            } else {

                Toast.makeText(getApplicationContext(), " You Have already Requested for NOC!", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
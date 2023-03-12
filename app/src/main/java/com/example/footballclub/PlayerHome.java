package com.example.footballclub;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PlayerHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_home);

        Button b1 = findViewById(R.id.profile);
        Button b2 = findViewById(R.id.news);
        Button b3 = findViewById(R.id.practise);
        Button b4 = findViewById(R.id.videos);
        Button b5 = findViewById(R.id.communication);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Player_view_profile.class));
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
}
package com.example.footballclub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

public class Coach_add_practice_sections extends AppCompatActivity implements JsonResponse, View.OnClickListener {
    EditText e1, e2, e3, e4;
    Button b1;
    private int mYear, mMonth, mDay, mHour, mMinute;

    public static String practice, place, date, time;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full sc
        setContentView(R.layout.activity_coach_add_practice_sections);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1 = (EditText) findViewById(R.id.practice);
        e2 = (EditText) findViewById(R.id.place);
        e3 = (EditText) findViewById(R.id.date);
        e4 = (EditText) findViewById(R.id.time);
        b1 = (Button) findViewById(R.id.submit);


        e3.setOnClickListener(this);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                practice = e1.getText().toString();
                place = e2.getText().toString();
                date = e3.getText().toString();
                time = e4.getText().toString();
                if (practice.equalsIgnoreCase("")) {
                    e1.setError("Enter practice");
                    e1.setFocusable(true);
                } else if (place.equalsIgnoreCase("")) {
                    e2.setError("Enter place");
                    e2.setFocusable(true);
                } else if (date.equalsIgnoreCase("")) {
                    e3.setError("Enter date");
                    e3.setFocusable(true);
                } else if (time.equalsIgnoreCase("")) {
                    e4.setError("Enter time");
                    e4.setFocusable(true);
                } else {
                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Coach_add_practice_sections.this;
                    String q = "/practice?practice=" + practice + "&place=" + place + "&date=" + date + "&time=" + time+"&lid="+Login.logid;
                    q = q.replace(" ", "%20");
                    JR.execute(q);
                }
            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {

                Toast.makeText(getApplicationContext(), "Practice Section Added Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Coachhome.class));

            } else {
                Toast.makeText(getApplicationContext(), "Somthing Went Worng", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        } catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(b);
    }

    @Override
    public void onClick(View view) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        e3.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

}

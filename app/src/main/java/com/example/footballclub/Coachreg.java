package com.example.footballclub;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Coachreg extends AppCompatActivity implements JsonResponse, View.OnClickListener, AdapterView.OnItemSelectedListener {

    EditText e1, e2, e3, e4, e5, e6, e7,e8,e9;
    Spinner sp1;

    private int mYear, mMonth, mDay, mHour, mMinute;
    String fname, lname, place, phone, email, uname, passw,dob;
    String[] clubname,clubid,value;
    public static String club_id;
    Button b1;
    SharedPreferences sh;
    final int CAMERA_PIC_REQUEST = 0, GALLERY_CODE = 201;
    public static String encodedImage = "", path = "";
    private Uri mImageCaptureUri;
    byte[] byteArray = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coachreg);
        e1 = (EditText) findViewById(R.id.fname);
        e2 = (EditText) findViewById(R.id.lname);
        e3 = (EditText) findViewById(R.id.place);
        e4 = (EditText) findViewById(R.id.phone);
        e5 = (EditText) findViewById(R.id.email);
        e6 = (EditText) findViewById(R.id.uname);
        e7 = (EditText) findViewById(R.id.pass);
        e8 = (EditText) findViewById(R.id.dob);
        e9 = (EditText) findViewById(R.id.ib);

        sp1=(Spinner)findViewById(R.id.sp1);
        b1 = (Button) findViewById(R.id.regbtn);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)Coachreg.this;
        String q="/viewclub";
        q=q.replace(" ","%20");
        JR.execute(q);

        sp1.setOnItemSelectedListener(this);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendAttach();
            }
        });


        e9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImageOption();
            }
        });
        e8.setOnClickListener(this);

    }
        private void sendAttach() {

            try {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//	            String uid = sh.getString("uid", "");


                fname = e1.getText().toString();
                lname = e2.getText().toString();
                place = e3.getText().toString();
                phone = e4.getText().toString();
                email = e5.getText().toString();
                uname = e6.getText().toString();
                passw = e7.getText().toString();
                dob=e8.getText().toString();
                if (fname.equalsIgnoreCase("") || !fname.matches("[a-zA-Z ]+")) {
                    e1.setError("Enter your firstname");
                    e1.setFocusable(true);
                } else if (lname.equalsIgnoreCase("") || !lname.matches("[a-zA-Z ]+")) {
                    e2.setError("Enter your lastname");
                    e2.setFocusable(true);
                } else if (place.equalsIgnoreCase("") || !place.matches("[a-zA-Z ]+")) {
                    e3.setError("Enter your place");
                    e3.setFocusable(true);
                } else if (phone.equalsIgnoreCase("") || phone.length() != 10) {
                    e4.setError("Enter your phone");
                    e4.setFocusable(true);
                } else if (email.equalsIgnoreCase("") || !email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+")) {
                    e5.setError("Enter your email");
                    e5.setFocusable(true);
                } else if (uname.equalsIgnoreCase("")) {
                    e6.setError("Enter your username");
                    e6.setFocusable(true);
                } else if (passw.equalsIgnoreCase("")) {
                    e7.setError("Enter your password");
                    e7.setFocusable(true);
                } else {


                    String q = "http://" + MainActivity.text + "/api/coachreg?clubid="+club_id;
//            String q = "http://" +IpSetting.ip+"/api/user_upload_file";

                    Toast.makeText(getApplicationContext(), "Byte Array:" + byteArray.length, Toast.LENGTH_LONG).show();


                    Map<String, byte[]> aa = new HashMap<>();

                    aa.put("image", byteArray);
                    aa.put("lid", Login.logid.getBytes());
                    aa.put("fname", fname.getBytes());
                    aa.put("lname", lname.getBytes());
                    aa.put("palce", place.getBytes());
                    aa.put("phone", phone.getBytes());
                    aa.put("email", email.getBytes());
                    aa.put("dob", dob.getBytes());

                    FileUploadAsync fua = new FileUploadAsync(q);
                    fua.json_response = (JsonResponse) Coachreg.this;
                    fua.execute(aa);
                }
                } catch(Exception e){
                    Toast.makeText(getApplicationContext(), "Exception upload : " + e, Toast.LENGTH_SHORT).show();
                }
            }

        private void selectImageOption() {
        /*Android 10+ gallery code
        android:requestLegacyExternalStorage="true"*/

            final CharSequence[] items = {"Capture Photo", "Choose from Gallery", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(Coachreg.this);
            builder.setTitle("Take Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("Capture Photo")) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        //intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                        startActivityForResult(intent, CAMERA_PIC_REQUEST);

                    } else if (items[item].equals("Choose from Gallery")) {
                        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, GALLERY_CODE);

                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        }



        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && null != data) {

                mImageCaptureUri = data.getData();
                System.out.println("Gallery Image URI : " + mImageCaptureUri);
                //   CropingIMG();

                Uri uri = data.getData();
                Log.d("File Uri", "File Uri: " + uri.toString());
                // Get the path
                //String path = null;
                try {
//                path = FileUtils.getPath(this, uri);
                    path=FileUtils.getPath(this,uri);
                    Toast.makeText(getApplicationContext(), "path : " + path, Toast.LENGTH_LONG).show();

                    File fl = new File(path);
                    int ln = (int) fl.length();

                    InputStream inputStream = new FileInputStream(fl);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    byte[] b = new byte[ln];
                    int bytesRead = 0;

                    while ((bytesRead = inputStream.read(b)) != -1) {
                        bos.write(b, 0, bytesRead);
                    }
                    inputStream.close();
                    byteArray = bos.toByteArray();

                    Bitmap bit = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//                    Toast.makeText(getApplicationContext(),bit+"",Toast.LENGTH_LONG).show();
//                    e9.setImageBitmap(bit);
                    e9.setText(path);

                    String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    encodedImage = str;
//                sendAttach1();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            } else if (requestCode == CAMERA_PIC_REQUEST && resultCode == Activity.RESULT_OK) {

                try {
                    Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                    e9.setImageBitmap(thumbnail);
                    byteArray = baos.toByteArray();

                    String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    encodedImage = str;
//                sendAttach1();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }



//
//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                fname = e1.getText().toString();
//                lname = e2.getText().toString();
//                place = e3.getText().toString();
//                phone = e4.getText().toString();
//                email = e5.getText().toString();
//                uname = e6.getText().toString();
//                passw = e7.getText().toString();
//
//                if (fname.equalsIgnoreCase("") || !fname.matches("[a-zA-Z ]+")) {
//                    e1.setError("Enter your firstname");
//                    e1.setFocusable(true);
//                } else if (lname.equalsIgnoreCase("") || !lname.matches("[a-zA-Z ]+")) {
//                    e2.setError("Enter your lastname");
//                    e2.setFocusable(true);
//                } else if (place.equalsIgnoreCase("") || !place.matches("[a-zA-Z ]+")) {
//                    e3.setError("Enter your place");
//                    e3.setFocusable(true);
//                } else if (phone.equalsIgnoreCase("") || phone.length() != 10) {
//                    e4.setError("Enter your phone");
//                    e4.setFocusable(true);
//                } else if (email.equalsIgnoreCase("") || !email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+")) {
//                    e5.setError("Enter your email");
//                    e5.setFocusable(true);
//                } else if (uname.equalsIgnoreCase("")) {
//                    e6.setError("Enter your username");
//                    e6.setFocusable(true);
//                } else if (passw.equalsIgnoreCase("")) {
//                    e7.setError("Enter your password");
//                    e7.setFocusable(true);
//                } else {
//                    JsonReq JR = new JsonReq();
//                    JR.json_response = (JsonResponse) Coachreg.this;
//                    String q = "/userregister?fname=" + fname + "&lname=" + lname + "&place=" + place + "&phone=" + phone + "&email=" + email + "&username=" + uname + "&password=" + passw;
//                    q = q.replace(" ", "%20");
//                    JR.execute(q);
//
//
//                }
//            }
//        });
//    }



    @Override
    public void response(JSONObject jo) {
        try {
            String method = jo.getString("method");
            String status;
            if (method.equalsIgnoreCase("reg")) {
                status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "REGISTRATION SUCCESS", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Login.class));

                } else if (status.equalsIgnoreCase("duplicate")) {
                    startActivity(new Intent(getApplicationContext(), Coachreg.class));
                    Toast.makeText(getApplicationContext(), "Username and Password already Exist...", Toast.LENGTH_LONG).show();

                } else {
                    startActivity(new Intent(getApplicationContext(), Coachreg.class));

                    Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
                }
                }
            if (method.equalsIgnoreCase("viewclub")) {
                Toast.makeText(getApplicationContext(),"&&&&&&&&&&&&&&&&&&",Toast.LENGTH_LONG).show();

                status = jo.getString("status");

                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(),"$$$$$$$$$$$$$$$$$$$",Toast.LENGTH_LONG).show();
                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                    Log.d("pearl", status);
                    clubname = new String[ja1.length()];
                    clubid = new String[ja1.length()];
                    value = new String[ja1.length()];

                    Toast.makeText(getApplicationContext(),"kkkkkkkkkkkkkkk",Toast.LENGTH_LONG).show();



                    for (int i = 0; i < ja1.length(); i++) {
                        clubname[i] = ja1.getJSONObject(i).getString("club");
                        clubid[i] = ja1.getJSONObject(i).getString("club_id");
                        Toast.makeText(getApplicationContext(),"$$$$$$$$$$$$$$$$$$$",Toast.LENGTH_LONG).show();


                        value[i] = "Clubnme: " + clubname[i] ;

                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                    sp1.setAdapter(ar);
                }
            }

            } catch(Exception e){
                // TODO: handle exception
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
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

                        e8.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        club_id=clubid[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
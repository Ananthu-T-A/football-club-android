package com.example.footballclub;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Cust_coach_profile extends ArrayAdapter<String>  {

	 private Activity context;       //for to get current activity context
	    SharedPreferences sh;
	private String[] image;
	private String[] name;
	private String[] email;

	private String[] dob;
	private String[] palce;
	private String[] phone;


	 public Cust_coach_profile(Activity context, String[] name, String[] email, String[] phone, String[] place,  String[] dob, String[] images) {
	        //constructor of this class to get the values from main_activity_class

	        super(context, R.layout.cust_coach_profile, images);
	        this.context = context;
	        this.image = images;
		    this.email = email;
		 	this.name = name;

		 	this.dob = dob;
		 	this.palce = place;
			 this.phone=phone;
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	                 //override getView() method

	        LayoutInflater inflater = context.getLayoutInflater();
	        View listViewItem = inflater.inflate(R.layout.cust_coach_profile, null, true);
			//cust_list_view is xml file of layout created in step no.2

	        ImageView im = (ImageView) listViewItem.findViewById(R.id.imageView1);
	        TextView t1=(TextView)listViewItem.findViewById(R.id.textView3);

//			TextView t2=(TextView)listViewItem.findViewById(R.id.textView5);
//			TextView t3=(TextView)listViewItem.findViewById(R.id.textView6);
//			TextView t4=(TextView)listViewItem.findViewById(R.id.textView7);
			t1.setText("Name : "+name[position]+"\nPlace : "+palce[position]+"\nEmail : "+email[position]+"\nNumber : "+phone[position]+"\nDob : "+ dob[position]  );
//			t2.setText(cat[position]);
//			t3.setText(qtys[position]);
//			t4.setText(rate[position]);
	        sh=PreferenceManager.getDefaultSharedPreferences(getContext());
	        
	       String pth = "http://"+sh.getString("ip", "")+"/"+image[position];
	       pth = pth.replace("~", "");
//	       Toast.makeText(context, pth, Toast.LENGTH_LONG).show();
	        
	        Log.d("-------------", pth);
	        Picasso.with(context)
	                .load(pth)
	                .placeholder(R.drawable.ic_launcher_background)
	                .error(R.drawable.ic_launcher_background).into(im);
	        
	        return  listViewItem;
	    }

		private TextView setText(String string) {
			// TODO Auto-generated method stub
			return null;
		}
}
package com.example.frodo;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class Update extends Activity  {
	EditText e1,e2,e3,e4,e5,e6,e7,e8;
	
	
	ImageView i1;
	Button b1;
	JSONParser jsonParser = new JSONParser();
	String url;
	
	SharedPreferences sp;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update);
		e1=(EditText)findViewById(R.id.editText1);
		e2=(EditText)findViewById(R.id.editText2);
		e3=(EditText)findViewById(R.id.editText3);
		e4=(EditText)findViewById(R.id.editText4);
		e5=(EditText)findViewById(R.id.editText5);
		e6=(EditText)findViewById(R.id.editText6);
		e7=(EditText)findViewById(R.id.editText7);
		e8=(EditText)findViewById(R.id.editText8);
		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		
		
		i1=(ImageView)findViewById(R.id.imageView1);
		b1=(Button)findViewById(R.id.button2);
		 try
	        {
	        	if(android.os.Build.VERSION.SDK_INT>9)
	        	{
	        		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        		StrictMode.setThreadPolicy(policy);
	        	}
	        }
	        catch(Exception e)
	        {
	        	
	        }
		
		 List<NameValuePair> params = new ArrayList<NameValuePair>();
		 params.add(new BasicNameValuePair("uid", sp.getString("lid", "")));
		 url="http://"+sp.getString("ip", "")+":8080/Frodo/viewprofile";
         JSONObject json = jsonParser.makeHttpRequest(url, "GET", params);
         try
         {
             //int success = json.getInt(TAG_SUCCESS);
             JSONArray ar=new JSONArray();
             ar=json.getJSONArray("result");
             JSONObject c = ar.getJSONObject(0);
             String name=c.getString("name");
             String dob=c.getString("dob");

             String gender=c.getString("gender");

             String hname=c.getString("hnm");
             String place=c.getString("place");
             String pin=c.getString("pin");
             String cont=c.getString("con");
             String img=c.getString("pict");

             
             String email=c.getString("email");
             e1.setText(name);
             e2.setText(dob);
             e8.setText(gender);
             e3.setText(hname);
             e4.setText(place);
             e5.setText(pin);
             e6.setText(cont);
             e7.setText(email);
             

     		String uri = "http://"+sp.getString("ip", "")+":8080/Frodo/photos/"+img;
     		Picasso.with(getApplicationContext())
             .load(uri)
             .placeholder(R.drawable.ic_launcher)
             .error(R.drawable.ic_launcher).into(i1);
             
         }
             
        catch(Exception e)
        {
        	
        }




             

             
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				String name=e1.getText().toString();
				String dob=e2.getText().toString();
				String gender=e8.getText().toString();
				
				
				String housenm=e3.getText().toString();
				String place=e4.getText().toString();
				String pin=e5.getText().toString();
				String contact=e6.getText().toString();
				
				String email=e7.getText().toString();
				if(name.equals(""))
				{
					e1.setError("Missing");
					e1.requestFocus();
				}
				else if(dob.equals(""))
				{
					e2.setError("Missing");
					e2.requestFocus();
				}
				else if(gender.equals(""))
				{
					e8.setError("Missing");
					e8.requestFocus();
				}
				else if(housenm.equals(""))
				{
					e3.setError("Missing");
					e3.requestFocus();
				}
				else if(place.equals(""))
				{
					e4.setError("Missing");
					e4.requestFocus();
				}
				else if(pin.length()!=6)
				{
					e5.setError("Invalid Pin code");
					e5.requestFocus();
				}
				else if(contact.length()!=10)
				{
					e6.setError("Invalid Phone Number");
					e6.requestFocus();
				}
				else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
				{
					e7.setError("Invalid Email ID");
					e7.requestFocus();
				}
				else
				{
				
				try
				
				{
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				
	             params.add(new BasicNameValuePair("name", name));
	             params.add(new BasicNameValuePair("dob", dob));
	             params.add(new BasicNameValuePair("gender",gender));
	             params.add(new BasicNameValuePair("hname", housenm));
	             params.add(new BasicNameValuePair("place", place));
	             params.add(new BasicNameValuePair("pin", pin));
	             params.add(new BasicNameValuePair("contact", contact));
	             params.add(new BasicNameValuePair("email", email));
	             params.add(new BasicNameValuePair("uid", sp.getString("lid", "")));
	             
	             
	             url="http://"+sp.getString("ip", "")+":8080/Frodo/Update";
	             JSONObject json=jsonParser.makeHttpRequest(url, "GET", params);
	             String s=json.getString("result");
				 if(!s.equalsIgnoreCase("error"))
				 {
					 Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
					 Intent i=new Intent(getApplicationContext(),Home.class);
	                    startActivity(i);
				 }
				 else 
				 {
					 Toast.makeText(getApplicationContext(), " Not updated", Toast.LENGTH_SHORT).show();
				 }
				}
				catch(Exception e)
				{
					
				}
				}
	             
	             
	             
	             
	             
				
				
				//Intent i=new Intent(getApplicationContext(),Home.class);
				//startActivity(i);
				
				
			}
				// TODO Auto-generated method stub
				
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update, menu);
		return true;
	}
}

	
	
			
			
		
		// TODO Auto-generated method stub
		
	



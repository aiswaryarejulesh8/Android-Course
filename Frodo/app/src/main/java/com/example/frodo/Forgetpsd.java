package com.example.frodo;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Forgetpsd extends Activity {
	EditText e1;
	Button b1;
	JSONParser jsonParser = new JSONParser();
	String url="";
	SharedPreferences sp;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgetpsd);
		e1=(EditText)findViewById(R.id.editText1);
		b1=(Button)findViewById(R.id.button1);
		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String email= e1.getText().toString();
				try
				{
				 List<NameValuePair> params = new ArrayList<NameValuePair>();
	             params.add(new BasicNameValuePair("email", email));
	             url="http://"+sp.getString("ip","")+":8080/Frodo/forgetpsd";
	             JSONObject json=jsonParser.makeHttpRequest(url, "GET", params);
	             String s=json.getString("result");
	            // Log.v(s, "result");
	             Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
					
				 if(s.equals("success"))
				 {
					 Toast.makeText(getApplicationContext(), "Submitted", Toast.LENGTH_SHORT).show();
					 Intent i=new Intent(getApplicationContext(),Login.class);
	                    startActivity(i);
				 }
				 else 
				 {
					 Toast.makeText(getApplicationContext(), " Not submitted", Toast.LENGTH_SHORT).show();
				 }

	             
				}
				catch(Exception e)
				{
		
				Toast.makeText(getApplicationContext(), "error"+e,Toast.LENGTH_LONG).show();
				}
				
				
				
				//Intent i=new Intent(getApplicationContext(),Viewdc.class);
				//startActivity(i);
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.forgetpsd, menu);
		return true;
	}

}

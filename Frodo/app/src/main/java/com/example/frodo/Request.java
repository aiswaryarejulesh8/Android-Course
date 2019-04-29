package com.example.frodo;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Request extends Activity {
	EditText e1, e2, e3, e4, e5;
	Button b1;
	JSONParser jsonParser = new JSONParser();
	String url;

	SharedPreferences sp;
	TelephonyManager tm;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request);
		e1 = (EditText) findViewById(R.id.editText1);
		e2 = (EditText) findViewById(R.id.editText2);
		e3 = (EditText) findViewById(R.id.editText3);
		e4 = (EditText) findViewById(R.id.editText4);
		e5 = (EditText) findViewById(R.id.editText5);
		b1 = (Button) findViewById(R.id.button1);
		tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		b1.setOnClickListener(new View.OnClickListener() {


			@Override
			public void onClick(View arg0) {

				String name = e1.getText().toString();
				String branch = e2.getText().toString();
				String cardno = e3.getText().toString();
				String key = e4.getText().toString();
				String amt = e5.getText().toString();
				if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
					// TODO: Consider calling
					//    ActivityCompat#requestPermissions
					// here to request the missing permissions, and then overriding
					//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
					//                                          int[] grantResults)
					// to handle the case where the user grants the permission. See the documentation
					// for ActivityCompat#requestPermissions for more details.
					return;
				}
				String imei = tm.getDeviceId().toString();
				if(name.equals(""))
				{
					e1.setError("Missing");
					e1.requestFocus();
				}
				else if(branch.equals(""))
				{
					e2.setError("Missing");
					e2.requestFocus();
				}
				else if(cardno.equals(""))
				{
					e3.setError("Missing");
					e3.requestFocus();
				}
				else if(key.equals(""))
				{
					e4.setError("Missing");
					e4.requestFocus();
				}
//				else if(amt.length()!=6)
//				{
//					e5.setError("Invalid Pin Code");
//					e5.requestFocus();
//				}
				else
				{
				try
				{
				List<NameValuePair> params = new ArrayList<NameValuePair>();
	             params.add(new BasicNameValuePair("name", name));
	             params.add(new BasicNameValuePair("branch", branch));
	             params.add(new BasicNameValuePair("cardno", cardno));
	             params.add(new BasicNameValuePair("key", key));
	             params.add(new BasicNameValuePair("amount", amt));
	             params.add(new BasicNameValuePair("imei",imei));
	             params.add(new BasicNameValuePair("uid", sp.getString("lid", "")));
	             url="http://"+sp.getString("ip", "")+":8080/Frodo/Request";
	             JSONObject json=jsonParser.makeHttpRequest(url, "GET", params);
	             String s=json.getString("result");
	             Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
					
				 if(s.equals("ok"))
				 {
					 Toast.makeText(getApplicationContext(), "Submitted", Toast.LENGTH_SHORT).show();
					 Intent i=new Intent(getApplicationContext(),Home.class);
	                    startActivity(i);
				 }
				 else 
				 {
					 Toast.makeText(getApplicationContext(), " Not submitted", Toast.LENGTH_SHORT).show();
				 }
	             
				}
				catch(Exception e)
				{
					
				}
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
		getMenuInflater().inflate(R.menu.request, menu);
		return true;
	}

}

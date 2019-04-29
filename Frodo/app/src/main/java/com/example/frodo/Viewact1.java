package com.example.frodo;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.EditText;

public class Viewact1 extends Activity {
	EditText e1,e2,e3,e4,e5;
	JSONParser jsonParser = new JSONParser();
	String url;
	SharedPreferences sp;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewact1);
		e1=(EditText)findViewById(R.id.editText1);
		e2=(EditText)findViewById(R.id.editText2);
		e3=(EditText)findViewById(R.id.editText3);
		e4=(EditText)findViewById(R.id.editText4);
		e5=(EditText)findViewById(R.id.editText5);
		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		 List<NameValuePair> params = new ArrayList<NameValuePair>();
		 params.add(new BasicNameValuePair("uid", sp.getString("lid", "")));
		 url="http://"+sp.getString("ip", "")+":8080/Frodo/Viewactdet";
         JSONObject json = jsonParser.makeHttpRequest(url, "GET", params);
         try
         {
		 String s=json.getString("name");
		 if(!s.equals("error"))
		 {
			 e1.setText(json.getString("name"));
			 e2.setText(json.getString("brh"));
			 
			 e3.setText(json.getString("cardno"));
			 
			 e4.setText(json.getString("key"));
			 
			 e5.setText(json.getString("bal"));
			 
			 
		 }
         }
         catch(Exception e)
         {
        	 
         }
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.viewact1, menu);
		return true;
	}

}

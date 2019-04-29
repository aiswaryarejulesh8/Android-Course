package com.example.frodo;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class Viewdc extends Activity implements OnItemClickListener {
	ListView l1;
	JSONParser jsonParser = new JSONParser();
	String url;
	public static ArrayList<String> date;
    public static ArrayList<String> amount;
    public static ArrayList<String> file;
    public static ArrayList<String> id;
	
	SharedPreferences sp;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewdc);
		l1=(ListView)findViewById(R.id.listView1);
		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		l1.setOnItemClickListener(this);
		 List<NameValuePair> params = new ArrayList<NameValuePair>();
		 params.add(new BasicNameValuePair("uid", sp.getString("lid", "")));
		 url="http://"+sp.getString("ip", "")+":8080/Frodo/Viewdc";
         JSONObject json = jsonParser.makeHttpRequest(url, "GET", params);
         try
         {
        	 JSONArray ar=new JSONArray();
             ar=json.getJSONArray("result");
             date=new ArrayList<String>();
             amount=new ArrayList<String>();
             file=new ArrayList<String>();
             id=new ArrayList<String>();
             for(int i = 0; i < ar.length(); i++)
             {
             JSONObject c = ar.getJSONObject(i);
             date.add(c.getString("date"));
             amount.add(c.getString("amt"));

             file.add(c.getString("file"));

             id.add(c.getString("id"));
             }
             l1.setAdapter(new  Custom(this, date, amount, file));
             
         }
		catch(Exception e)
		{
			
		}
				
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.viewdc, menu);
		return true;
	}

	

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent i=new Intent(getApplicationContext(),Transfer.class);
	    i.putExtra("digit", file.get(arg2));
	    i.putExtra("amount", amount.get(arg2));

		Toast.makeText(getApplicationContext(),amount.get(arg2) , Toast.LENGTH_LONG).show();
		
		startActivity(i);
		// TODO Auto-generated method stub
		
	}

}

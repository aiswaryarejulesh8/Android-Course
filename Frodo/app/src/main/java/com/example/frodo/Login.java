package com.example.frodo;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;


import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener {
	EditText e1,e2;
	Button b1,b2,b3;
	TextView t;
	JSONParser jsonParser = new JSONParser();
    String url="";
	SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        
        e1=(EditText)findViewById(R.id.editText1);
        e2=(EditText)findViewById(R.id.editText2);
        b1=(Button)findViewById(R.id.button1);
        b2=(Button)findViewById(R.id.button2);
        b3=(Button)findViewById(R.id.button3);
        
        t=(TextView)findViewById(R.id.textView3);
        sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        
        t.setOnClickListener(this);
       
		

        

        
    }
   


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }


	@Override
	public void onClick(View arg0) {
		
		if(arg0==b1)
		{
			String name=e1.getText().toString();
			String pass=e2.getText().toString();
			if(name.equals(""))
			{
				e1.setError("Missing");
				e1.requestFocus();
			}
			else if(pass.equals(""))
			{
				e2.setError("Missing");
				e2.requestFocus();
			}
			else
			{
			 List<NameValuePair> params = new ArrayList<NameValuePair>();
             params.add(new BasicNameValuePair("name", name));
             params.add(new BasicNameValuePair("pass", pass));
             url="http://"+sp.getString("ip","")+":8080/Frodo/Login";
            // Log.d("ins===",ur);
             JSONObject json=jsonParser.makeHttpRequest(url, "GET", params);
          String s=null;
           // Log.d("Create Response", json.toString());
				//Toast.makeText(this, json.toString(), Toast.LENGTH_SHORT).show();
             try {
               s=json.getString("status");
                 Log.d("Msg", s);
                 if(s.equalsIgnoreCase("valid"))
                 {
                	 Editor ed=sp.edit();
                	 ed.putString("lid", json.getString("result"));
                	 ed.commit();
                     Intent i=new Intent(getApplicationContext(),Home.class);
                    startActivity(i);
                 }
                 else
                 {
                	 Toast.makeText(getApplicationContext(), "inavlid", Toast.LENGTH_SHORT).show();
                 }
             }
             catch(Exception e)
             {
				 Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
             }
			}



			//Intent i=new Intent(getApplicationContext(),Home.class);
			//startActivity(i);
		}
		else if(arg0==b2)
		{
			Intent i=new Intent(getApplicationContext(),Signup.class);
			startActivity(i);
		}
		else if(arg0==t)
		{
			Intent i=new Intent(getApplicationContext(),Forgetpsd.class);
			startActivity(i);
		}
		else if(arg0==b3)
		{
			Intent i=new Intent(getApplicationContext(),Sendrecieve.class);
			startActivity(i);
		}
		
		
		// TODO Auto-generated method stub
		
	}
    
}

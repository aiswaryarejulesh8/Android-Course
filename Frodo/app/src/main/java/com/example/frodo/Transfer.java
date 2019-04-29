package com.example.frodo;

import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Transfer extends Activity {

	EditText e1;
	Button b1;
	String amt="";
	String dig="";
	String result="";
	SQLiteDatabase db;
	SharedPreferences sp;
	JSONParser jsonParser = new JSONParser();
	
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transfer);
		e1=(EditText)findViewById(R.id.editText1);
		dig=getIntent().getStringExtra("digit");
		e1.setText(dig);
		amt=getIntent().getStringExtra("amount");
		b1=(Button)findViewById(R.id.button1);
		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		 try {
	        	
	        	if(android.os.Build.VERSION.SDK_INT > 9){
	        		
	        		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        		StrictMode.setThreadPolicy(policy);
	        		
	        	}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		 b1.setOnClickListener(new View.OnClickListener() {
			 
			 @Override
			public void onClick(View arg0) {
				 
				 
				 result=e1.getText().toString();
			//	 byte[] newbyte = Base64.decode(file, Base64.NO_WRAP);
					
					try
					{
						
						//Toast.makeText(getApplicationContext(), "insertt"+ss, Toast.LENGTH_LONG).show();
						String arry[]=result.split("//");
						
						String ss="FILE_"+arry[1];
						 String dd[]=dig.split("_");
					        String a=dd[0];
					        Toast.makeText(getApplicationContext(), "aa"+a, Toast.LENGTH_LONG).show();
					        String aa[]=a.split("//");
					       
						db=openOrCreateDatabase("storelist", Context.MODE_PRIVATE, null);
						db.execSQL("CREATE  TABLE IF NOT EXISTS store (fileid int, filename varchar,amount varchar);");
						db.execSQL("INSERT INTO store VALUES('"+aa[1]+"','"+ss+"','"+amt+"')");
						Intent i=new Intent(getApplicationContext(),Home.class);
						startActivity(i);
					}
					catch(Exception e)
					{
						Log.e("Logging", "Received an exception " + e.getMessage() );

					}
				 
				 
				// TODO Auto-generated method stub
				
			}
		});
	        
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.transfer, menu);
		return true;
	}

}

package com.example.frodo;

import java.io.File;
import java.io.FileOutputStream;

import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Coinsplit extends Activity {
     EditText e1;
     TextView t1,t4;
     Button b1,b2;
     SQLiteDatabase db;
     String digit,idd;
     String amt="";
     private File path;
     
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coinsplit);
		e1=(EditText)findViewById(R.id.editText1);
		t1=(TextView)findViewById(R.id.textView1);
		t4=(TextView)findViewById(R.id.textView4);
		b1=(Button)findViewById(R.id.button1);
		b2=(Button)findViewById(R.id.button2);
		 digit=getIntent().getStringExtra("amount");
		  idd=getIntent().getStringExtra("id");
		  
		  
		  String st=digit+" "+"DIGITAL COINS WITH YOU!!";
		    t1.setText(st);
		   
		  b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				amt=e1.getText().toString();
				if(amt.equals(""))
				{
					e1.setError("Missing");
					e1.requestFocus();
				}
				else
				{
				
				if(Float.parseFloat(amt)>Float.parseFloat(digit))
				{
					Toast.makeText(getApplicationContext(), "Amount greater", Toast.LENGTH_LONG).show();
				}
					
				
				
				else 
				{
					java.io.File mediaStorageDir=new java.io.File(Environment.getExternalStorageDirectory(),"/AMOUNT");
					if(!mediaStorageDir.exists()){
						if(!mediaStorageDir.mkdirs()){
							android.util.Log.d("AMOUNT","failed to cerate directory");
							
						}
					}
				
				String filename1=mediaStorageDir.getPath()+java.io.File.separator+amt+".txt";	
				java.io.File mediaFile1 = new java.io.File(filename1);
				
				try{
				FileOutputStream fos=new FileOutputStream(mediaFile1);
				fos.write(amt.getBytes());
				fos.close();
				Toast.makeText(getApplicationContext(), "File Saved At "+filename1, Toast.LENGTH_SHORT).show();
				}
				catch(Exception e)
				{
					
				}
				
				Float baln=Float.parseFloat(digit)-Float.parseFloat(amt);
				String balance=Float.toString(baln);
				Toast.makeText(getApplicationContext(),"BALANCE="+ balance, Toast.LENGTH_SHORT).show();
				db=openOrCreateDatabase("storelist", Context.MODE_PRIVATE, null);
				Cursor c=db.rawQuery("SELECT * FROM store WHERE fileid='"+idd+"' and amount='"+digit+"'", null);
				if(c.moveToFirst())
	    		{
	    			String f=c.getString(0);
	    			

	    		    String g=c.getString(1);
	    		  
	    		    String h=c.getString(2);
	    		  
	    		
	    		
	    			db.execSQL("UPDATE store SET amount='"+balance+"' where fileid='"+idd+"'");
	    			
	    		}
				t4.setText(balance);
				
				}
			}
			}
		});
		
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				path=new File(Environment.getExternalStorageDirectory().getPath()+"/AMOUNT/");
				File datafile = new File(path+"/"+amt+".txt");
				Toast.makeText(getApplicationContext(), datafile+"", Toast.LENGTH_LONG).show();
				 Intent i=new Intent(getApplicationContext(),MainActivity1.class);
				   i.putExtra("digit",datafile+"");
				   startActivity(i);
				
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.coinsplit, menu);
		return true;
	}

		  }

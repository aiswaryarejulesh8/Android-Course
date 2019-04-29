package com.example.frodo;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Sendrecieve extends Activity implements OnClickListener {
	Button b1,b2;
	private File path;
	SQLiteDatabase db;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sendrecieve);
		b1=(Button)findViewById(R.id.button1);
		b2=(Button)findViewById(R.id.button2);
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sendrecieve, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		
		if(arg0==b1)
		{
			Intent i=new Intent(getApplicationContext(),Recieve.class);
			startActivity(i);
		}
		else if(arg0==b2)
		{
			
			
			 path=new File(Environment.getExternalStorageDirectory().getPath()+"/bluetooth/");

			Toast.makeText(this, "path is"+path, Toast.LENGTH_SHORT).show();
			 
				getfile(path);		
			//Intent i=new Intent(getApplicationContext(),Login.class);
			//startActivity(i);
		}
		// TODO Auto-generated method stub
		
	}
	private void getfile(File dir) {
		// TODO Auto-generated method stub
		
		//fileList.clear();
		
		
		File listFile[] = dir.listFiles();
		if (listFile != null && listFile.length > 0) {
			for (int i = 0; i < listFile.length; i++) {

				if (listFile[i].isDirectory()) {
					//fileList.add(listFile[i]);
					getfile(listFile[i]);
				} 
				else {

					double bytes = listFile[i].length();
					double kilobytes = (bytes / 1024);
					double megabytes = (kilobytes / 1024);
					if(megabytes<=1){
					if (listFile[i].getName().endsWith(".txt"))

					{
						String flnm[]=listFile[i].getName().split("\\.");
						Log.d("re",listFile[i].getName());
						
						db=openOrCreateDatabase("storelist", Context.MODE_PRIVATE, null);
						db.execSQL("CREATE  TABLE IF NOT EXISTS store (fileid int, filename varchar,amount varchar);");
						db.execSQL("INSERT INTO store VALUES('"+flnm[0]+"','"+listFile[i].getName()+"','"+flnm[0]+"')");
					
						
					}
					}
				}
				}
		
			
			}
		dir.delete();

		}



}

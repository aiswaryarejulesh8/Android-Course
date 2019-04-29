package com.example.frodo;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Recieve extends Activity implements OnItemClickListener {
	ListView lv;
	
	SQLiteDatabase db;
	String a,b;
	  private ArrayList<String> fileList = new ArrayList<String>();
	  private ArrayList<String> fileList1 = new ArrayList<String>();
	  String[] arry;
	  String[] arry1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recieve);
		lv=(ListView)findViewById(R.id.listView1);
		lv.setOnItemClickListener(this);
		db=openOrCreateDatabase("storelist", Context.MODE_PRIVATE, null);
		db.execSQL("CREATE  TABLE IF NOT EXISTS store (fileid int, filename varchar,amount varchar);");
		Cursor c=db.rawQuery("SELECT * FROM store", null);
		while(c.moveToNext())
		{
			
			a=c.getString(0);
			b=c.getString(2);
			fileList.add(a);
			fileList1.add(b);
			//Toast.makeText(getApplicationContext(), a, Toast.LENGTH_LONG).show();
			
		}
		ArrayAdapter<String>ad=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,fileList1);
		lv.setAdapter(ad);
		
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recieve, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
		Intent i=new Intent(getApplicationContext(),Coinsplit.class);
	    i.putExtra("id", fileList.get(arg2));
	    i.putExtra("amount", fileList1.get(arg2));
	    startActivity(i);

		
		
	}

}

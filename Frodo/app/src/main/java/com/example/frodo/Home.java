package com.example.frodo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Home extends Activity implements OnClickListener {
	Button b1,b2,b3,b4,b5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		b1=(Button)findViewById(R.id.button1);
		b2=(Button)findViewById(R.id.button2);
		b3=(Button)findViewById(R.id.button3);
		b4=(Button)findViewById(R.id.button4);
		b5=(Button)findViewById(R.id.button5);
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
		b3.setOnClickListener(this);
		b4.setOnClickListener(this);
		b5.setOnClickListener(this);
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		if(arg0==b1)
		{
			Intent i=new Intent(getApplicationContext(),Update.class);
			startActivity(i);
		}
		else if(arg0==b2)
		{
			Intent i=new Intent(getApplicationContext(),Viewact1.class);
			startActivity(i);
		}
		else if(arg0==b3)
		{
			Intent i=new Intent(getApplicationContext(),Request.class);
			startActivity(i);
		}
		else if(arg0==b4)
		{
			Intent i=new Intent(getApplicationContext(),Viewdc.class);
			startActivity(i);
		}
		else if(arg0==b5)
		{
			Intent i=new Intent(getApplicationContext(),Login.class);
			startActivity(i);
		}
		
		// TODO Auto-generated method stub
		
	}

}

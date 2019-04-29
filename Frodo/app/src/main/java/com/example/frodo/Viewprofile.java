package com.example.frodo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Viewprofile extends Activity {
	TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12;
	 ImageView i1;
	Button b1;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewprofile);
		t1=(TextView)findViewById(R.id.textView2);
		t2=(TextView)findViewById(R.id.textView4);
		t3=(TextView)findViewById(R.id.textView2);
		t4=(TextView)findViewById(R.id.textView2);
		t5=(TextView)findViewById(R.id.textView2);
		t6=(TextView)findViewById(R.id.textView2);
		t7=(TextView)findViewById(R.id.textView2);
		t8=(TextView)findViewById(R.id.textView2);
		t9=(TextView)findViewById(R.id.textView2);
		t10=(TextView)findViewById(R.id.textView2);
		t11=(TextView)findViewById(R.id.textView2);
		t12=(TextView)findViewById(R.id.textView2);
		
		b1=(Button)findViewById(R.id.button1);
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				
				
				Intent i=new Intent(getApplicationContext(),Update.class);
				startActivity(i);
				// TODO Auto-generated method stub
				
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.viewprofile, menu);
		return true;
	}

}

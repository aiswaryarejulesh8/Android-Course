package com.example.frodo;



import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Custom extends BaseAdapter{

	private Context Context;
	ArrayList<String> b;
	ArrayList<String>c;
	ArrayList<String>e;
	
	
	public Custom(Context applicationContext, ArrayList<String> date, ArrayList<String> amount, ArrayList<String> file) {
		this.Context=applicationContext;
		this.b=date;
		this.c=amount;
		this.e=file;	
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return c.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflator=(LayoutInflater)Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View gridView;
		if(convertView==null)
		{
			gridView=new View(Context);
			gridView=inflator.inflate(R.layout.activity_custom, null);
			
		}
		else
		{
			gridView=(View)convertView;
			
		}
		
		TextView tv1=(TextView)gridView.findViewById(R.id.textView1);
		TextView tv2=(TextView)gridView.findViewById(R.id.textView2);
		TextView tv3=(TextView)gridView.findViewById(R.id.textView3);
		
		
		
		tv1.setTextColor(Color.BLACK);
		tv2.setTextColor(Color.BLACK);
		tv3.setTextColor(Color.BLACK);
		
		
		tv1.setText(b.get(position));
		tv2.setText(c.get(position));
		tv3.setText(e.get(position));
		
		
		return gridView;
		
	}
	
	
	
}

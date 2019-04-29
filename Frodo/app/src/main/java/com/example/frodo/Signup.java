package com.example.frodo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class Signup extends Activity implements OnClickListener, OnItemSelectedListener {
	EditText e1, e2, e3, e4, e5, e6, e7, e8, e9;
	RadioButton r1, r2;
	Spinner s1, s2;
	ImageView i1;
	Button b1, b2;
	String name, dob, gender, housenm, place, pin, district, branch, contact, email, password, pass, imei;
	String[] dist = new String[]{"calicut", "thrissur", "malapuram", "kannur"};
	private static final int FILE_SELECT_CODE1 = 2800;
	ArrayList<String> brh;
	JSONParser jsonParser = new JSONParser();

	TelephonyManager tm;
	String url;

	SharedPreferences sp;
	String path = "";
	String fileName = "";
	String pubkey = "", privatekey = "", id = "";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);

		e1 = (EditText) findViewById(R.id.editText1);
		e2 = (EditText) findViewById(R.id.editText2);
		e3 = (EditText) findViewById(R.id.editText3);
		e4 = (EditText) findViewById(R.id.editText4);
		e5 = (EditText) findViewById(R.id.editText5);
		e6 = (EditText) findViewById(R.id.editText6);
		e7 = (EditText) findViewById(R.id.editText7);
		e8 = (EditText) findViewById(R.id.editText8);
		e9 = (EditText) findViewById(R.id.editText9);

		r1 = (RadioButton) findViewById(R.id.radio0);
		r2 = (RadioButton) findViewById(R.id.radio1);
		s1 = (Spinner) findViewById(R.id.spinner1);
		s2 = (Spinner) findViewById(R.id.spinner2);
		i1 = (ImageView) findViewById(R.id.imageView1);
		b1 = (Button) findViewById(R.id.button1);
		b2 = (Button) findViewById(R.id.button2);
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
		sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

		ArrayAdapter<String> ad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dist);
		s1.setAdapter(ad);
		s1.setOnItemSelectedListener(this);


	}


	@Override
	public void onClick(View arg0) {
		if (arg0 == b1) {
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("*/*");
			intent.addCategory(Intent.CATEGORY_OPENABLE);
			try {
				startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE1);
			} catch (android.content.ActivityNotFoundException ex) { // Potentially direct the user to the Market with a Dialog
				Toast.makeText(getApplicationContext(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();
			}
		} else if (arg0 == b2) {
			name = e1.getText().toString();
			dob = e2.getText().toString();
			gender = "";
			if (r1.isChecked()) {
				gender = r1.getText().toString();
			} else if (r2.isChecked()) {
				gender = r2.getText().toString();
			}
			housenm = e3.getText().toString();
			place = e4.getText().toString();
			pin = e5.getText().toString();
			district = s1.getSelectedItem().toString();
			branch = s2.getSelectedItem().toString();
			contact = e6.getText().toString();
			email = e7.getText().toString();
			password = e8.getText().toString();
			pass = e9.getText().toString();
			if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
				// TODO: Consider calling
				//    ActivityCompat#requestPermissions
				// here to request the missing permissions, and then overriding
				//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
				//                                          int[] grantResults)
				// to handle the case where the user grants the permission. See the documentation
				// for ActivityCompat#requestPermissions for more details.
				return;
			}
			imei = tm.getDeviceId().toString();

			if(name.equals(""))
			{
				e1.setError("Missing");
				e1.requestFocus();
			}
			else if(dob.equals(""))
			{
				e2.setError("Missing");
				e2.requestFocus();
			}
			else if(housenm.equals(""))
			{
				e3.setError("Missing");
				e3.requestFocus();
			}
			else if(place.equals(""))
			{
				e4.setError("Missing");
				e4.requestFocus();
			}
			else if(pin.length()!=6)
			{
				e5.setError("Invalid Pin Code");
				e5.requestFocus();
			}
			else if(contact.length()!=10)
			{
				e6.setError("Invalid Phone Number");
				e6.requestFocus();
			}
			else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
			{
				e7.setError("Invalid Email ID");
				e7.requestFocus();
			}
			else if(password.equals(""))
			{
				e8.setError("Missing");
				e8.requestFocus();
			}
			else if(!pass.equals(password))
			{
				e9.setError("Password Does not Match");
				e9.requestFocus();
			}
			else
			{
			int res=uploadFile(path);
			
			if(res==1)
			{
				Toast.makeText(getApplicationContext(), "Registered Successfully"+path, Toast.LENGTH_LONG).show();
				Intent i=new Intent(getApplicationContext(),Login.class);
				startActivity(i);
			}
			}
			//Intent i=new Intent(getApplicationContext(),Login.class);
			//startActivity(i);
		}
			
		// TODO Auto-generated method stub
		
	}
	public int uploadFile(String sourceFileUri) {
		Log.e("aaaaaaaaaaaaaa", "aaaaaaaaaaaaaa");
		 fileName = sourceFileUri;
		 Toast.makeText(getApplicationContext(), fileName, Toast.LENGTH_LONG).show();
		String upLoadServerUri = "http://"+sp.getString("ip","")+":8080/Frodo/uploadServlet";
        Toast.makeText(getApplicationContext(), upLoadServerUri, Toast.LENGTH_LONG).show();
		FileUpload fp = new FileUpload(fileName);
		Map mp = new HashMap<String,String>();
		mp.put("namek", name);
		mp.put("dob", dob);
		mp.put("gender", gender);
		mp.put("housename", housenm);
		mp.put("place", place);
		mp.put("pin", pin);
		mp.put("district", district);
		mp.put("branch", branch);
		mp.put("contact", contact);
		mp.put("email", email);
		mp.put("password", password);
		mp.put("pass", pass);
		mp.put("imei", imei);
		fp.multipartRequest(upLoadServerUri, mp, fileName, "files", "application/octet-stream");
		return 1;
	}


	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		if(arg0==s1)
		{
		 List<NameValuePair> params = new ArrayList<NameValuePair>();
		 params.add(new BasicNameValuePair("district", dist[arg2]));
		 url="http://"+sp.getString("ip", "")+":8080/Frodo/signup1";
         JSONObject json = jsonParser.makeHttpRequest(url, "GET", params);
         try
         {
        	 JSONArray ar=new JSONArray();
             ar=json.getJSONArray("result");
             brh=new ArrayList<String>();
             for(int i = 0; i < ar.length(); i++)
             {
             JSONObject c = ar.getJSONObject(i);
             brh.add(c.getString("brh"));
             
             }
             ArrayAdapter<String>ad1=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,brh);
     		s2.setAdapter(ad1);
             
             
             
         }
         catch(Exception e)
         {
        	 
         }
		
		}
	
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
	        case FILE_SELECT_CODE1:
		        if (resultCode == RESULT_OK) {
		            // Get the Uri of the selected file 
		            Uri uri = data.getData();
		            Log.d("File Uri", "File Uri: " + uri.toString());
		            // Get the path
		            try {
						path = FileUtils.getPath(this, uri);
						//e4.setText(path);
						//Log.d("path", path);
						File fil = new File(path);
						int fln=(int) fil.length();
				
//						File file = new File(path);
			        
						byte[] byteArray = null;
			        try
			        {
				        InputStream inputStream = new FileInputStream(fil);
				        ByteArrayOutputStream bos = new ByteArrayOutputStream();
				        byte[] b = new byte[fln];
				        int bytesRead =0;
				        
				        while ((bytesRead = inputStream.read(b)) != -1)
				        {
				        	bos.write(b, 0, bytesRead);
				        }
				        
				        byteArray = bos.toByteArray();
				        inputStream.close();
				        Bitmap bmp=BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
				        if(bmp!=null){
				        	
				    
							i1.setVisibility(View.VISIBLE);
				        	 i1.setImageBitmap(bmp);
				        }
			        }
				        catch (Exception e) {
							Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
						}
				        }		
					
					 catch (URISyntaxException e) {
						 Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
					}	     
		        }
				else{
						Toast.makeText(this,"Please select suitable file", Toast.LENGTH_LONG).show();
				}        
		        break;
		}
	
	}
}


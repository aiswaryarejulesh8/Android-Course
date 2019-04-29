package com.example.frodo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.sqlite.SQLiteDatabase;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity1 extends Activity { Button buttonopenDailog, buttonUp, send;
TextView textFolder;
EditText dataPath;
static final int CUSTOM_DIALOG_ID = 0;
ListView dialog_ListView;
File root, fileroot, curFolder;
private List<String> fileList = new ArrayList<String>();
private static final int DISCOVER_DURATION = 300;
private static final int REQUEST_BLU = 1;
BluetoothAdapter btAdatper = BluetoothAdapter.getDefaultAdapter();
File file;



//---------------------------------------------------------------
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_activity1);
    
    dataPath=(EditText)findViewById(R.id.FilePath);
    buttonopenDailog= (Button) findViewById(R.id.opendailog);
    send=(Button)findViewById(R.id.sendBtooth);
    
    String digit=getIntent().getStringExtra("digit");
    dataPath.setText(digit);
    
    buttonopenDailog.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dataPath.setText("");
            showDialog(CUSTOM_DIALOG_ID);
        }
    });

    root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
    curFolder = root;
    send.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            sendViaBluetooth();
            try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        }
    });
}

@Override
protected Dialog onCreateDialog(int id) {
    Dialog dialog = null;
    switch (id) {
        case CUSTOM_DIALOG_ID:
            dialog = new Dialog(MainActivity1.this);
            dialog.setContentView(R.layout.dailoglayout);
            dialog.setTitle("File Selector");
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            textFolder = (TextView) dialog.findViewById(R.id.folder);
            buttonUp = (Button) dialog.findViewById(R.id.up);
            buttonUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ListDir(curFolder.getParentFile());
                }
            });
            dialog_ListView = (ListView) dialog.findViewById(R.id.dialoglist);
            dialog_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    File selected = new File(fileList.get(position));
                    if (selected.isDirectory()) {
                        ListDir(selected);
                    } else if (selected.isFile()) {
                        getselectedFile(selected);
                    } else {
                        dismissDialog(CUSTOM_DIALOG_ID);
                    }
                }
            });
            break;
    }
    return dialog;
}

@Override
protected void onPrepareDialog(int id, Dialog dialog) {
    super.onPrepareDialog(id, dialog);
    switch (id) {
        case CUSTOM_DIALOG_ID:
            ListDir(curFolder);
            break;
    }
}

public void getselectedFile(File f){
    dataPath.setText(f.getAbsolutePath());
    fileList.clear();
    dismissDialog(CUSTOM_DIALOG_ID);
}


public void ListDir(File f) {
    if (f.equals(root)) {
        buttonUp.setEnabled(false);
    } else {
        buttonUp.setEnabled(true);
    }
    curFolder = f;
    textFolder.setText(f.getAbsolutePath());
    dataPath.setText(f.getAbsolutePath());
    File[] files = f.listFiles();
    fileList.clear();

    for (File file : files) {
        fileList.add(file.getPath());
    }
    ArrayAdapter<String> directoryList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fileList);
    dialog_ListView.setAdapter(directoryList);
}

//exit to application---------------------------------------------------------------------------
public void exit(View V) {
    btAdatper.disable();
    Toast.makeText(this,"*** Now Bluetooth is off... Thanks. ***",Toast.LENGTH_LONG).show();
    finish(); }

//Method for send file via bluetooth------------------------------------------------------------
public void sendViaBluetooth() {
    if(!dataPath.equals(null)){
    if (btAdatper == null) {
        Toast.makeText(this, "Device not support bluetooth", Toast.LENGTH_LONG).show();
    } else {
        enableBluetooth();
    }
}else{
        Toast.makeText(this,"Please select a file.",Toast.LENGTH_LONG).show();
    }
}

public void enableBluetooth() {
    Intent discoveryIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
    discoveryIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, DISCOVER_DURATION);
    startActivityForResult(discoveryIntent, REQUEST_BLU);
}

//Override method for sending data via bluetooth availability--------------------------
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == DISCOVER_DURATION && requestCode == REQUEST_BLU) {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_SEND);
        i.setType("*/*");
       file = new File(dataPath.getText().toString());

        i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));

        PackageManager pm = getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(i, 0);
        if (list.size() > 0) {
            String packageName = null;
            String className = null;
            boolean found = false;

            for (ResolveInfo info : list) {
                packageName = info.activityInfo.packageName;
                if (packageName.equals("com.android.bluetooth")) {
                    className = info.activityInfo.name;
                    found = true;
                    break;
                }
            }
            //CHECK BLUETOOTH available or not------------------------------------------------
            if (!found) {
                Toast.makeText(this, "Bluetooth hasn't been found", Toast.LENGTH_LONG).show();
            } else {
            	
            	//new AcceptThread(getApplicationContext());
            	
            	
                i.setClassName(packageName, className);
                startActivity(i);
            }
            
            
        }
       
    } 
    
    else {
        Toast.makeText(this, "Bluetooth is cancelled", Toast.LENGTH_LONG).show();
    }
    
  
  
}

//@Override
//public boolean onCreateOptionsMenu(Menu menu) {
//    // Inflate the menu; this adds items to the action bar if it is present.
//    getMenuInflater().inflate(R.menu.menu_main, menu);
//    return true;
//}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
        Toast.makeText(this, "**********************************\nDeveloper: www.santossingh.com\n**********************************", Toast.LENGTH_LONG).show();
        return true;
    }
    return super.onOptionsItemSelected(item);
}
@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	Intent i=new Intent(getApplicationContext(),Sendrecieve.class);
	startActivity(i);
	}


}

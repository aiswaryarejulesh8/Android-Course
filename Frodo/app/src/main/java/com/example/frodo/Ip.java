package com.example.frodo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Ip extends Activity implements View.OnClickListener {
    EditText ip;
    Button save;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip);

        ip=(EditText)findViewById(R.id.ip);
        save=(Button)findViewById(R.id.save);
        sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
       //
        ip.setText(sh.getString("ip",""));
        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String ip_adrs=ip.getText().toString();
        SharedPreferences.Editor ed=sh.edit();
        ed.putString("ip",ip_adrs);
        ed.commit();
        startActivity(new Intent(Ip.this,Login.class));

    }
}

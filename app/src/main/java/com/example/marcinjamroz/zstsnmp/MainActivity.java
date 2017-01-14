package com.example.marcinjamroz.zstsnmp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.marcinjamroz.zstsnmp.MESSAGE"  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void displayDetail(View view){
        Intent intent = new Intent(this,DetailedViewActivity.class);
        TextView textView = (TextView) view;
        String snmpName = textView.getText().toString();
        intent.putExtra("MIB",snmpName);
        startActivity(intent);
    }
}

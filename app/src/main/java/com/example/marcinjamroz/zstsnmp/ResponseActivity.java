package com.example.marcinjamroz.zstsnmp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResponseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        Intent intent = getIntent();
        String[] response = intent.getStringArrayExtra("response");
        ((TextView)findViewById(R.id.oid)).setText(response[0]);
        ((TextView)findViewById(R.id.value)).setText(response[1]);
        ((TextView)findViewById(R.id.type)).setText(response[2]);
        ((TextView)findViewById(R.id.ip)).setText(response[3]);
    }
}

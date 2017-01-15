package com.example.marcinjamroz.zstsnmp;

import android.content.Intent;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class DetailedViewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);

        Intent intent = getIntent();

        String value = intent.getStringExtra("MIB");

        TextView oid = new TextView(this);
        TextView description = new TextView(this);

        int oidID = this.getResources().getIdentifier(value+"OID","string",this.getPackageName());
        int descriptionID = this.getResources().getIdentifier(value+"Description","string",this.getPackageName());

        oid.setText(oidID);
        oid.setTextSize(25);

        description.setText(descriptionID);
        description.setTextSize(25);

        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.activity_detailed_view);

        viewGroup.addView(oid);
        viewGroup.addView(description);

        try {
            Socket socket = new Socket("10.78.17.22",5555);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(getResources().getString(oidID));
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

package com.example.marcinjamroz.zstsnmp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Space;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class DetailedViewActivity extends AppCompatActivity {

    int oidID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);

        Intent intent = getIntent();

        String value = intent.getStringExtra("MIB");

        setTitle(value);

        TextView oid = new TextView(this);
        TextView description = new TextView(this);

         oidID = this.getResources().getIdentifier(value+"OID","string",this.getPackageName());
        int descriptionID = this.getResources().getIdentifier(value+"Description","string",this.getPackageName());

        oid.setText(oidID);
        oid.setTextSize(25);

        description.setText(descriptionID);
        description.setTextSize(25);

        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.activity_detailed_view);

        viewGroup.addView(oid);
        viewGroup.addView(description);



    }

    protected void getOID(View view){
        new sendAndReceive().execute(oidID);
    }



    public class sendAndReceive extends AsyncTask<Integer,Void,Void>{

        @Override
        protected Void doInBackground(Integer... params) {

            try {
                Socket socket = new Socket("192.168.43.48",8888);
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF(getResources().getString(params[0]));
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


    }


}

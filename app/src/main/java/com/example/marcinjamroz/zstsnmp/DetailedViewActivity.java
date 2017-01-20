package com.example.marcinjamroz.zstsnmp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Space;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.Socket;

public class DetailedViewActivity extends AppCompatActivity {

    int oidID;
    String[] response;
    Socket socket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);

        Intent intent = getIntent();

        String value = intent.getStringExtra("MIB");

        setTitle(value);


        TextView oid = new TextView(this);
        TextView description = new TextView(this);
        description.setMovementMethod(new ScrollingMovementMethod());

         oidID = this.getResources().getIdentifier(value+"OID","string",this.getPackageName());
        int descriptionID = this.getResources().getIdentifier(value+"Description","string",this.getPackageName());

        oid.setText(oidID);
        oid.setTextSize(25);

        description.setText(descriptionID);
        description.setTextSize(25);

        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.activity_detailed_view);

        viewGroup.addView(oid);
        viewGroup.addView(description);

        socket = MainActivity.SocketHandler.getSocket();



    }

    protected void getOID(View view){
        new sendAndReceive().execute(oidID);
    }



    public class sendAndReceive extends AsyncTask<Integer,Void,Void>{

        @Override
        protected Void doInBackground(Integer... params) {

            try {

                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                String oidToSend = getResources().getString(params[0]).split("=")[1];


                dataOutputStream.writeUTF("GET " + oidToSend);

                ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
                response = (String[]) objectInput.readObject();



            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Intent intent = new Intent(getApplicationContext(),ResponseActivity.class);
            intent.putExtra("response",response);
            startActivity(intent);

        }
    }


}

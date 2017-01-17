package com.example.marcinjamroz.zstsnmp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class MainActivity extends AppCompatActivity {

   Socket socket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new connectToServer().execute();
    }


    public void displayDetail(View view){
        Intent intent = new Intent(this,DetailedViewActivity.class);
        TextView textView = (TextView) view;
        String snmpName = textView.getText().toString();
        intent.putExtra("MIB",snmpName);
        startActivity(intent);
    }

    public static class SocketHandler{
        private static Socket socket;

        public static Socket getSocket() {
            return socket;
        }

        public static void setSocket(Socket socket) {
            SocketHandler.socket = socket;
        }
    }

    protected class connectToServer extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Socket socket = new Socket("192.168.0.102",8888);
                SocketHandler.setSocket(socket);
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF("CONNECT");

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

package com.example.marcinjamroz.zstsnmp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class MainActivity extends AppCompatActivity {

   Socket socket;
    ViewFlipper viewFlipper;
    float lastX;
    Button connect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewFlipper = (ViewFlipper)findViewById(R.id.flipper);

        connect = (Button)findViewById(R.id.connectButton);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String IP = ((EditText)findViewById(R.id.serwerIP)).getText().toString();
                String port = ((EditText)findViewById(R.id.serwerPort)).getText().toString();
                new connectToServer().execute(IP,port);
            }
        });


    }



    public void displayDetail(View view){
        Intent intent = new Intent(this,DetailedViewActivity.class);
        TextView textView = (TextView) view;
        String snmpName = textView.getText().toString();
        intent.putExtra("MIB",snmpName);
        startActivity(intent);
    }

    public boolean onTouchEvent(MotionEvent touchEvent){
        switch (touchEvent.getAction()){

            case MotionEvent.ACTION_DOWN:
                lastX = touchEvent.getX();
                break;

            case MotionEvent.ACTION_UP:
                float currentX = touchEvent.getX();
                if(lastX<currentX) {
                    if (viewFlipper.getDisplayedChild() == 0)
                        break;

                    // Next screen comes in from left.
                    viewFlipper.setInAnimation(this, R.anim.slide_in_from_left);
                    // Current screen goes out from right.
                    viewFlipper.setOutAnimation(this, R.anim.slide_out_to_right);
                    // Display next screen.
                    viewFlipper.showNext();

                }

                    if (lastX > currentX) {

                        if (viewFlipper.getDisplayedChild() == 1)
                        break;

                        viewFlipper.setInAnimation(this, R.anim.slide_in_from_right);

                        viewFlipper.setOutAnimation(this, R.anim.slide_out_to_left);

                        viewFlipper.showPrevious();

                    }
                    break;
                }
                return false;

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



    protected class connectToServer extends AsyncTask<String,Void,Void>{
        @Override
        protected Void doInBackground(String... params) {
            try {

                Socket socket = new Socket(params[0],Integer.parseInt(params[1]));
                SocketHandler.setSocket(socket);
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF("CONNECT");

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            viewFlipper.showPrevious();
        }
    }
}

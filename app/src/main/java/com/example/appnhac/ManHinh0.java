package com.example.appnhac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class ManHinh0 extends AppCompatActivity {

    private MyService myService;
    private boolean isBound = false;
    private ServiceConnection connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh0);

        final ImageView imgOn = (ImageView) findViewById(R.id.imgStart);
        final ImageView imgOff = (ImageView) findViewById(R.id.imgStop);
        final ImageView imgFast = (ImageView) findViewById(R.id.imgTua);

        connection = new ServiceConnection() {


            @Override
            public void onServiceConnected(ComponentName componentName, IBinder service) {
                MyService.MyBinder binder = (MyService.MyBinder) service;
                myService = binder.getService();
                isBound = true;
            };

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                isBound = false;
            }
        };
        final Intent intent =
                new Intent(ManHinh0.this,
                        MyService.class);

        imgOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bindService(intent, connection, Context.BIND_AUTO_CREATE);

            }
        });
        imgOff.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(isBound){
                    unbindService(connection);
                    isBound = false;
                }
            }
        });
        imgFast.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(isBound){

                    myService.fastForward();
                }else{
                    Toast.makeText(ManHinh0.this,
                            "Service chưa hoạt động", Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.imgStarttttt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBound){
                    // tua bài hát
                    myService.fastStart();
                }else{
                    Toast.makeText(ManHinh0.this,
                            "Service chưa hoạt động", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
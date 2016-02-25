package com.narolainfotect.demo;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import utility.FontManager;

public class BroadCastServiceService extends Service {
    MyReceiver broadCastReceiver=new MyReceiver();

    public BroadCastServiceService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }



    @Override
    public void onCreate() {
        super.onCreate();

       ///Toast.makeText(getBaseContext(), "onCreate", Toast.LENGTH_LONG).show();
        //initiatePopupWindow();


    }

    @Override
    public void onStart(Intent intent, int startId) {
        Toast.makeText(getBaseContext(),"BroadCastServiceService onStart", Toast.LENGTH_LONG).show();
        startInOtherThread(intent, startId);
    }
    public void startInOtherThread(final Intent intent, final int startId){
        Thread t = new Thread("MyService(" + startId + ")") {
            @Override
            public void run() {
               // _onStart(intent, startId);
                registerBroadcastReceiver();

            }
        };
        t.start();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getBaseContext(),"BroadCastServiceService onStartCommand", Toast.LENGTH_LONG).show();
        startInOtherThread(intent, startId);
        return START_STICKY;
    }
    private void _onStart(final Intent intent, final int startId) {
        //Your Start-Code for the service
        Intent  callActivity=new Intent(this,IncomingCallActivity.class);
        callActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(callActivity);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getBaseContext(),"onDestroy", Toast.LENGTH_LONG).show();

    }
    public void registerBroadcastReceiver() {

        this.registerReceiver(broadCastReceiver, new IntentFilter(
                "android.intent.action.PHONE_STATE"));
        Toast.makeText(this, "Registered broadcast receiver", Toast.LENGTH_SHORT)
                .show();
    }




}

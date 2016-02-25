package com.narolainfotect.demo;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.content.IntentFilter;
import android.os.HandlerThread;
import android.util.Log;


public class BroadCastService extends Service {


    public BroadCastService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {

        Log.d("TAG", "service oncreate");
        HandlerThread thread = new HandlerThread("ServiceStartArguments");
        thread.start();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TAG", "service started");
        MyReceiver reciever = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("restartservice");
        intentFilter.addAction("android.intent.action.PHONE_STATE");
        registerReceiver(reciever, intentFilter);
        // If we get killed, after returning from here, restart
        return START_STICKY;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TAG", "service destroy");
        sendBroadcast(new Intent("restartservice"));
    }

    @SuppressLint("NewApi")
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        // TODO Auto-generated method stub
        super.onTaskRemoved(rootIntent);
        Log.d("TAG", "service onTaskRemoved");
        sendBroadcast(new Intent("restartservice"));

    }


}

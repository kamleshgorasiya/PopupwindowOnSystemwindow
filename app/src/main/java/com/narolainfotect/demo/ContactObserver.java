package com.narolainfotect.demo;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

public class ContactObserver extends Service {
    public ContactObserver() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onStart(Intent intent, int startId) {
        Toast.makeText(getBaseContext(), "ContactObserver onStart", Toast.LENGTH_LONG).show();
        registerContactChangeListener();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getBaseContext(),"ContactObserver onStartCommand", Toast.LENGTH_LONG).show();
        registerContactChangeListener();
        return START_STICKY;
    }
    public void registerContactChangeListener(){
        MyContentObserver contentObserver = new MyContentObserver();
        getContentResolver().registerContentObserver(ContactsContract.RawContacts.CONTENT_URI, true, contentObserver);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TAG", "service destroy");
        sendBroadcast(new Intent("restart_contact_observer"));
    }

    @SuppressLint("NewApi")
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        // TODO Auto-generated method stub
        super.onTaskRemoved(rootIntent);
        Log.d("TAG", "service onTaskRemoved");
        sendBroadcast(new Intent("restart_contact_observer"));

    }


    private class MyContentObserver extends ContentObserver {

        public MyContentObserver() {
            super(null);
        }

        @Override
        public void onChange(boolean selfChange) {
            Log.e("===>onChange","contact change===>"+selfChange);
            super.onChange(selfChange);
        }

    }
}

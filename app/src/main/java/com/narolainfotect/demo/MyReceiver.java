package com.narolainfotect.demo;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

       // String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        //Log.e("onReceive: ", state);
       /* if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)
                || state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {

            Log.d("Ringing", "Phone is ringing");
            context.startService(new Intent(PopUpService.MY_SERVICE));


        }*/

        /*Intent popIntent = new Intent(context, PopUpService.class);
        context.startService(popIntent);*/
        Toast.makeText(context, "onReceive", Toast.LENGTH_LONG).show();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String state = extras.getString(TelephonyManager.EXTRA_STATE);
            Log.e("MY_DEBUG_TAG", state + " <== Daynamic   |  EXTRA_STATE_OFFHOOK==>" + TelephonyManager.EXTRA_STATE_OFFHOOK);

            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                Intent popIntent = new Intent(context, PopUpService.class);
                context.startService(popIntent);
            }
            else if(state.equals(TelephonyManager.EXTRA_STATE_IDLE)){

            }
            if (intent.getAction().equals("restartservice")) {
                context.startService(new Intent(context.getApplicationContext(), BroadCastService.class));
                Log.d("TAG", "restart");
            }
        }
    }
}

package com.narolainfotect.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
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
            Log.e("MY_DEBUG_TAG", state+" <== Daynamic   |  EXTRA_STATE_OFFHOOK==>"+TelephonyManager.EXTRA_STATE_OFFHOOK);

            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                Intent popIntent = new Intent(context, PopUpService.class);
                context.startService(popIntent);
            }
            else if(state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                Intent popIntent = new Intent(context, PopUpService.class);
                context.stopService(popIntent);
            }
        }
    }
}

package com.narolainfotect.demo;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import android.os.Handler;
import java.util.Date;

public class CallReceiver extends PhonecallReceiver
{
    Context context;

    @Override
    protected void onIncomingCallStarted(final Context ctx, String number, Date start)
    {
        Toast.makeText(ctx,"Incoming Call"+ number,Toast.LENGTH_LONG).show();

        context =   ctx;

        /*final Intent intent = new Intent(context, PopupActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("phone_no", number);
        intent.putExtra("date_time",start);*/

        final Intent intent = new Intent(context, PopUpService.class);
        intent.putExtra("phone_no", number);
        intent.putExtra("date_time", start);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
               // ctx.startActivity(intent);
                ctx.startService(intent);
            }
        },2000);

//        MyCus/*tomDialog dialog   =   new MyCustomDialog(context);
//        dialog.*/show();
    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end)
    {
        Toast.makeText(ctx,"Bye Bye"+ number,Toast.LENGTH_LONG).show();
    }
}

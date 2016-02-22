package com.narolainfotect.demo;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import utility.FontManager;

/**
 * Created by c49 on 20/02/16.
 */
public class IncomingCallActivity extends Activity {

    RelativeLayout idDialogParent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            Log.d("IncomingCallActivity", "flag2");



            // TODO Auto-generated method stub
            super.onCreate(savedInstanceState);


            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
            Log.d("IncominActivity: ", "flagy");

            setContentView(R.layout.edit_user_form_dailog);
            idDialogParent=(RelativeLayout)findViewById(R.id.idDialogParent);
            FontManager.markAsIconContainer(idDialogParent,FontManager.getTypeface(getApplicationContext(),FontManager.FONTAWESOME));
            TextView textView=(TextView)findViewById(R.id.idClose);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    finish();
                }
            });

            Log.d("IncomingCaivity: ", "flagz");

           /* String number = getIntent().getStringExtra(
                    TelephonyManager.EXTRA_INCOMING_NUMBER);
            TextView text = (TextView) findViewById(R.id.text);
            text.setText("Incoming call from " + number);*/
        }
        catch (Exception e) {
            Log.d("Exception", e.toString());
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}

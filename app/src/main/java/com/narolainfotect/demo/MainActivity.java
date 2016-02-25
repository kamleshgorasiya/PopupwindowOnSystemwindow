package com.narolainfotect.demo;

import android.Manifest;
import android.app.ActionBar;
import android.content.ContentProviderResult;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
     private View mView;
     int mCurrentX = 20;
     int mCurrentY = 50;

    public static final int MY_PERMISSIONS_REQUEST_SYSTEM_ALERT_WINDOW=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
       // startService(new Intent(getBaseContext(), BroadCastService.class));
    //finish();
        Button button=(Button)findViewById(R.id.idBtnClick);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"You Clicked Me??",Toast.LENGTH_LONG).show();
                //initiatePopupWindow();
               startService(new Intent(getBaseContext(), PopUpService.class));
                // startService(new Intent(PopUpService.MY_SERVICE));
               // startActivity(new Intent(getApplicationContext(), IncomingCallActivity.class));
                finish();
               /* Intent intent = new Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI);
                intent.putExtra(ContactsContract.Intents.Insert.PHONE,"9227350852");

                startActivity(intent);*/
            }
        });

       // initiatePopupWindow();
       // initiatePopupWindow();

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SYSTEM_ALERT_WINDOW: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                  Toast.makeText(getApplicationContext(),permissions.toString(),Toast.LENGTH_LONG).show();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private PopupWindow mPopup;

    private void initiatePopupWindow() {
        try {

            LayoutInflater layoutInflater= (LayoutInflater) MainActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView=layoutInflater.inflate(R.layout.edit_user_form_dailog,(ViewGroup) findViewById(R.id.idDialogParent));
            mPopup = new PopupWindow(mView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            mPopup.setOutsideTouchable(true);
            mPopup.setTouchable(true);
            mPopup.setFocusable(true);
            mPopup.setBackgroundDrawable(new BitmapDrawable());
            View.OnTouchListener otl = new View.OnTouchListener() {
                private float mDx;
                private float mDy;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN) {
                        mDx = mCurrentX - event.getRawX();
                        mDy = mCurrentY - event.getRawY();
                    } else
                    if (action == MotionEvent.ACTION_MOVE) {
                        mCurrentX = (int) (event.getRawX() + mDx);
                        mCurrentY = (int) (event.getRawY() + mDy);
                        mPopup.update(mCurrentX, mCurrentY, -1, -1);
                    }
                    return true;
                }
            };
            mView.setOnTouchListener(otl);


            mView.post(new Runnable() {
                @Override
                public void run() {
                    mPopup.showAtLocation(mView, Gravity.NO_GRAVITY, mCurrentX, mCurrentY);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private PopupWindow nertworkErrorpp;
    private void initNetErrorPopupWindow() {
        try {
// We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) MainActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.passcode_not_match_dialog,(ViewGroup) findViewById(R.id.idDialogParent));
            nertworkErrorpp = new PopupWindow(layout,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,true);
            nertworkErrorpp.showAtLocation(layout, Gravity.CENTER, 0, 0);


            Button btnOk = (Button) layout.findViewById(R.id.btnOk);
            btnOk.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    nertworkErrorpp.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

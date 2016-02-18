package com.narolainfotect.demo;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
     private View mView;
     int mCurrentX = 20;
     int mCurrentY = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
        Button button=(Button)findViewById(R.id.idBtnClick);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"You Clicked Me??",Toast.LENGTH_LONG).show();
                //initiatePopupWindow();
               // startService(new Intent(getBaseContext(), PopUpService.class));
                startService(new Intent(PopUpService.MY_SERVICE));
                finish();
            }
        });

       // initiatePopupWindow();
        initiatePopupWindow();

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



}

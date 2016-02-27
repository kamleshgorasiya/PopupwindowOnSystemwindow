package com.narolainfotect.demo;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Date;

import utility.FontManager;

/**
 * Created by c49 on 20/02/16.
 */
public class PopupActivity extends Activity {


    private View myview;

    /**
     * another popup
     * */
    private WindowManager recButtonWindowManager;
    private Button recButton = null;
    WindowManager.LayoutParams prms;

    PopUpService service;
    Activity activity;
    LayoutInflater li;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            Log.d("IncomingCallActivity", "flag2");

            inittouchWindow();
            // TODO Auto-generated method stub
            super.onCreate(savedInstanceState);


        }
        catch (Exception e) {
            Log.d("Exception", e.toString());
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void inittouchWindow(){

        recButtonWindowManager= (WindowManager) getSystemService(WINDOW_SERVICE);
        recButtonWindowManager.addView(getCustomeView(),getRecbuttonLayout());
        Log.e("Hello","inittouchWindow");
    }
    View.OnTouchListener recButtonOnTouchListener = new View.OnTouchListener() {
        boolean touchconsumedbyMove = false;
        int recButtonLastX;
        int recButtonLastY;
        int recButtonFirstX;
        int recButtonFirstY;
        boolean isLastActionDown=false;
        WindowManager.LayoutParams prm = getRecbuttonLayout();
        int totalDeltaX = recButtonLastX - recButtonFirstX;
        int totalDeltaY = recButtonLastY - recButtonFirstY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {


            switch(event.getActionMasked())
            {
                case MotionEvent.ACTION_DOWN:
                    isLastActionDown=true;
                    recButtonLastX = (int) event.getRawX();
                    recButtonLastY = (int) event.getRawY();
                    recButtonFirstX = recButtonLastX;
                    recButtonFirstY = recButtonLastY;
                    break;
                case MotionEvent.ACTION_UP:
                    if(isLastActionDown) {
                        recButtonWindowManager.removeView(getCustomeView());
                        finish();
                        // service.stopSelf();
                        //stopService(new Intent(coPopUpService.this));
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    isLastActionDown=false;
                    // Toast.makeText(getBaseContext(), "ACTION_MOVE", Toast.LENGTH_LONG).show();
                    int deltaX = (int) event.getRawX() - recButtonLastX;
                    int deltaY = (int) event.getRawY() - recButtonLastY;
                    recButtonLastX = (int) event.getRawX();
                    recButtonLastY = (int) event.getRawY();
                    if (Math.abs(totalDeltaX) >= 5  || Math.abs(totalDeltaY) >= 5) {
                        if (event.getPointerCount() == 1) {
                            prm.x += deltaX;
                            prm.y += deltaY;
                            touchconsumedbyMove = true;
                            recButtonWindowManager.updateViewLayout(getCustomeView(), prm);
                        }
                        else{
                            touchconsumedbyMove = false;
                        }
                    }else{
                        touchconsumedbyMove = false;
                    }
                    break;
                default:
                    break;
            }

            return touchconsumedbyMove;
        }
    };
    private WindowManager.LayoutParams getRecbuttonLayout() {
        if (prms != null) {
            return prms;
        }
        prms = new WindowManager.LayoutParams();
        prms.format = PixelFormat.TRANSLUCENT;
        prms.flags = WindowManager.LayoutParams.FORMAT_CHANGED; // 8
        prms.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        prms.gravity = Gravity.TOP | Gravity.CENTER;
        prms.width = WindowManager.LayoutParams.MATCH_PARENT;
        prms.height = WindowManager.LayoutParams.WRAP_CONTENT;
        // Tools.Log("getRecbuttonLayout", "return getRecbuttonLayout()");
        return prms;


    }


    private View getCustomeView(){
        Log.e("Hello","inittouchWindow");
        if(myview!=null)
            return myview;
        li = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        myview = li.inflate(R.layout.edit_user_form_dailog, null);// ;
        TextView textView=(TextView)myview.findViewById(R.id.idTimeLabel);
        Date date=(Date)getIntent().getSerializableExtra("date_time");
        textView.setText(date.toString());

        TextView idCallerName=(TextView)myview.findViewById(R.id.idCallerName);
        idCallerName.setText(getIntent().getExtras().getString("phone_no")+" is calling you");
        FontManager.markAsIconContainer(myview,FontManager.getTypeface(getBaseContext(),FontManager.FONTAWESOME));
        myview.setOnTouchListener(recButtonOnTouchListener);
        return myview;
    }

    @Override
    public void onBackPressed() {
        recButtonWindowManager.removeView(getCustomeView());
        finish();
    }
}

package com.narolainfotect.demo;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.Toast;

import utility.FontManager;

public class PopUpService extends Service {
    public static final String MY_SERVICE = "com.narolainfotect.demo.service.MY_SERVICE";
    private View myview;

    /**
     * another popup
     * */
    private WindowManager recButtonWindowManager;
    private Button recButton = null;
    WindowManager.LayoutParams prms;
    boolean touchconsumedbyMove = false;
    int recButtonLastX;
    int recButtonLastY;
    int recButtonFirstX;
    int recButtonFirstY;
    boolean isLastActionDown=false;
    PopUpService service;
    Activity activity;
    LayoutInflater li;

    public PopUpService() {
        activity=new MainActivity();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }



    @Override
    public void onCreate() {
        super.onCreate();
        service=this;
       ///Toast.makeText(getBaseContext(), "onCreate", Toast.LENGTH_LONG).show();
        //initiatePopupWindow();
        inittouchWindow();

    }

    @Override
    public void onStart(Intent intent, int startId) {
        Toast.makeText(getBaseContext(),"onStart", Toast.LENGTH_LONG).show();
       // startInOtherThread(intent,startId);
    }
    public void startInOtherThread(final Intent intent, final int startId){
        Thread t = new Thread("MyService(" + startId + ")") {
            @Override
            public void run() {
                _onStart(intent, startId);

            }
        };
        t.start();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getBaseContext(),"onStartCommand", Toast.LENGTH_LONG).show();
      //  startInOtherThread(intent, startId);
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

  public void inittouchWindow(){

      recButtonWindowManager= (WindowManager) getSystemService(WINDOW_SERVICE);
      recButtonWindowManager.addView(getCustomeView(),getRecbuttonLayout());
      Log.e("Hello","inittouchWindow");
  }
    View.OnTouchListener recButtonOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            WindowManager.LayoutParams prm = getRecbuttonLayout();
            int totalDeltaX = recButtonLastX - recButtonFirstX;
            int totalDeltaY = recButtonLastY - recButtonFirstY;

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
        myview = li.inflate(R.layout.edit_user_form_dailog, null);
        FontManager.markAsIconContainer(myview,FontManager.getTypeface(getBaseContext(),FontManager.FONTAWESOME));
        myview.setOnTouchListener(recButtonOnTouchListener);
        return myview;
    }

    private PopupWindow pwindo;
    private void initiatePopupWindow() {
        try {
// We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater)(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.edit_user_form_dailog,(ViewGroup)activity.findViewById(R.id.idDialogParent));
            pwindo = new PopupWindow(layout,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,true);
            pwindo.showAtLocation(layout, Gravity.NO_GRAVITY, 0, 0);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

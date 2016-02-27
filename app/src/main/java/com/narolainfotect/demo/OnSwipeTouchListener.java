package com.narolainfotect.demo;
import android.app.Service;
import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class OnSwipeTouchListener implements OnTouchListener {

    private final GestureDetector gestureDetector;

    boolean touchconsumedbyMove = false;
    int recButtonLastX;
    int recButtonLastY;
    int recButtonFirstX;
    int recButtonFirstY;
    boolean isLastActionDown=false;
    WindowManager.LayoutParams prms;
    Service service;
    View view;
    WindowManager recButtonWindowManager;

    public OnSwipeTouchListener (Service service,WindowManager.LayoutParams prms,View view,WindowManager recButtonWindowManager){
        gestureDetector = new GestureDetector(new GestureListener());
        this.service = service;
        this.prms = prms;
        this.view = view;
        this.recButtonWindowManager = recButtonWindowManager;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
         gestureDetector.onTouchEvent(event);

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
                    recButtonWindowManager.removeView(view);
                    service.stopSelf();
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
                        prms.x += deltaX;
                        prms.y += deltaY;

                        touchconsumedbyMove = true;
                        recButtonWindowManager.updateViewLayout(view, prms);
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

    private final class GestureListener extends SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                    }
                    result = true;
                }
                else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom();
                    } else {
                        onSwipeTop();
                    }
                }
                result = true;

            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    public void onSwipeRight() {
        Log.e("onSwipeRight", "onSwipeRight");
        Animation animation = AnimationUtils.loadAnimation(service.getBaseContext(), R.anim.slide_in_left);
        animation .setStartOffset (2000);
        view.startAnimation(animation);


    }

    public void onSwipeLeft() {
        Log.e("onSwipeLeft","onSwipeLeft");
        Animation animation = AnimationUtils.loadAnimation(service.getBaseContext(), R.anim.slide_in_right);
        animation .setStartOffset(2000);
        view.startAnimation(animation);

    }

    public void onSwipeTop() {
        Log.e("onSwipeTop","onSwipeTop");
    }

    public void onSwipeBottom() {
        Log.e("onSwipeBottom","onSwipeBottom");
    }
}

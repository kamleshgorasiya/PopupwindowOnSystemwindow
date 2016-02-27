package com.narolainfotect.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final GestureDetector  gestureDetector = new GestureDetector(new MyGestureDetector());
        RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.idActivity2);
        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)) return false;
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);

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
    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            try {
                float slope = (e1.getY() - e2.getY()) / (e1.getX() - e2.getX());
                float angle = (float) Math.atan(slope);
                float angleInDegree = (float) Math.toDegrees(angle);
                // left to right
                if (e1.getX() - e2.getX() > 20 && Math.abs(velocityX) > 20) {
                    if ((angleInDegree < 45 && angleInDegree > -45)) {
                        startActivity(new Intent(Main2Activity.this, MainActivity.class));
                        Main2Activity.this.overridePendingTransition(
                                R.anim.slide_in_left, R.anim.slide_out_right);
                        finish();
                    }
                    // right to left fling
                } else if (e2.getX() - e1.getX() > 20
                        && Math.abs(velocityX) > 20) {
                    if ((angleInDegree < 45 && angleInDegree > -45)) {
                        startActivity(new Intent(Main2Activity.this, MainActivity.class));
                        Main2Activity.this.overridePendingTransition(
                                R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();

                    }
                }
                return true;
            } catch (Exception e) {
                // nothing
            }
            return false;
        }
    }
}

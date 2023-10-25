package project.application.myapplication;



import android.content.Intent;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;



public class Activity_2 extends AppCompatActivity {
    float x1, x2, y1, y2;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();
                if(x1 < x2){
                    return false;
                }
                else if (x1 > x2){
                    Intent i = new Intent(Activity_2.this, MainActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.xml.slide_left_start, R.xml.slide_left_end);
                }
        }
        return false;
    }
}

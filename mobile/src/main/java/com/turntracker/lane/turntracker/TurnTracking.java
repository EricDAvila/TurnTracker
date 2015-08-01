package com.turntracker.lane.turntracker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TurnTracking extends Activity {
    SharedPreferences pref = null;
    SharedPreferences.Editor editor = null;
    int currentToken, tokenCount;
    String [] sharedName = {"T1_Name","T2_Name","T3_Name","T4_Name","T5_Name", //used to access Shared Preferences
            "T6_Name","T7_Name","T8_Name","T9_Name"};

    String [] sharedSlider = {"T1_Slider","T2_Slider","T3_Slider","T4_Slider","T5_Slider", //used to access Shared Preferences
            "T6_Slider","T7_Slider","T8_Slider","T9_Slider"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        Intent intent = getIntent();
        tokenCount = (intent.getIntExtra("key", 0))-1;
        currentToken=0;
        final RelativeLayout relativeLayout=new RelativeLayout(this);
        LayoutParams rlParams=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        relativeLayout.setLayoutParams(rlParams);
        setContentView(relativeLayout);
        ImageView iView = getImageView();
        TextView tView = getTextView();
        relativeLayout.addView(iView);
        relativeLayout.addView(tView);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_UP) {
            if (currentToken == tokenCount)
                currentToken = -1;
            currentToken++;
            ImageView iView = getImageView();
            TextView tView = getTextView();
            final RelativeLayout relativeLayout = new RelativeLayout(this);
            LayoutParams rlParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            relativeLayout.setLayoutParams(rlParams);
            relativeLayout.addView(iView);
            relativeLayout.addView(tView);
            setContentView(relativeLayout);
            return true;
        }
        return false;
    }

    public TextView getTextView(){
        TextView tView = new TextView(this);
        tView.setText(pref.getString(sharedName[currentToken], null));
        tView.setTextSize(200);
        tView.setTextColor(Color.BLACK);
        LayoutParams rlParams=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        tView.setLayoutParams(rlParams);
        tView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        return tView;
    }

    public ImageView getImageView(){
        ImageView iView = new ImageView(this);
        LayoutParams rlParams=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        iView.setLayoutParams(rlParams);
        iView.setPadding(10,300,10,300);
        int tokenColor = pref.getInt(sharedSlider[currentToken], 1337);
        if(tokenColor<=0) iView.setImageResource(R.drawable.circle);
        if(tokenColor<=4&&tokenColor!=0) {
            iView.setImageResource(R.drawable.violet_circle);
        }
        if (tokenColor<=8&&tokenColor>4) {
            iView.setImageResource(R.drawable.red_circle);
        }
        if (tokenColor<=12&&tokenColor>8) {
            iView.setImageResource(R.drawable.orange_circle);
        }
        if (tokenColor<=16&&tokenColor>12) {
            iView.setImageResource(R.drawable.lorange_circle);
        }
        if(tokenColor<=20&&tokenColor>16) {
            iView.setImageResource(R.drawable.dyellow_circle);
        }
        if(tokenColor<=24&&tokenColor>20) {
            iView.setImageResource(R.drawable.yellow_circle);
        }
        if(tokenColor<=28&&tokenColor>24) {
            iView.setImageResource(R.drawable.lgreen_circle);
        }
        if(tokenColor<=32&&tokenColor>28) {
            iView.setImageResource(R.drawable.green_circle);
        }
        if(tokenColor<=36&&tokenColor>32) {
            iView.setImageResource(R.drawable.lblue_circle);
        }
        if(tokenColor<=40&&tokenColor>36) {
            iView.setImageResource(R.drawable.blue_circle);
        }
        if(tokenColor<=44&&tokenColor>40) {
            iView.setImageResource(R.drawable.dblue_circle);
        }
        if(tokenColor<=50&&tokenColor>44) {
            iView.setImageResource(R.drawable.purple_circle);
        }
        if(tokenColor>50) {
            iView.setImageResource(R.drawable.purple_circle);
        }
        return iView;
    }
}

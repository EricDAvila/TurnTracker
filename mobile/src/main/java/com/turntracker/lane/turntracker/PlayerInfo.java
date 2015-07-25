package com.turntracker.lane.turntracker;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.gc.materialdesign.views.Slider;

public class PlayerInfo extends Activity {
    boolean paneIsVisible, tokensPaneHasBeenChanged, attributesPaneHasBeenChanged;
    SharedPreferences pref = null;
    Editor editor = null;
    Slider slider = null;
    private ImageView trashCan = null;

    Integer[] imageIDs = new Integer[9]; //stores references to drawables

    String [] sharedName = {"T1_Name","T2_Name","T3_Name","T4_Name","T5_Name", //used to access Shared Preferences
            "T6_Name","T7_Name","T8_Name","T9_Name"}; //for Edit Text box

    String [] sharedSlider = {"T1_Slider","T2_Slider","T3_Slider","T4_Slider","T5_Slider", //used to access Shared Preferences
            "T6_Slider","T7_Slider","T8_Slider","T9_Slider"}; //for slider Value

    int currentToken; //holds which token currently has focus in AttributesPane
                        //from 0-8, and is 10 when no token is in focus
    int plusLocation; //holds location for the plus token from 2-9. When 9, plus isn't visible
                    //plus location is also how drawing is determined

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        paneIsVisible = true;
        tokensPaneHasBeenChanged = true;
        attributesPaneHasBeenChanged = true;
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        slider = (Slider) findViewById(R.id.slider);
        currentToken = 0;
        plusLocation = 2;
        clearTokenValues(0);
        clearTokenValues(1);
        clearTokenValues(2);
        trashCan = (ImageView) findViewById(R.id.trash);
        trashCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentToken==plusLocation-1&&plusLocation>2)
                    paneIsVisible=false;
                if(plusLocation!=0&&plusLocation!=1&&plusLocation!=2) {
                    clearTokenValues(plusLocation-1);
                    plusLocation--;
                }
                if(currentToken==0||currentToken==1){
                    clearTokenValues(currentToken);
                }
                tokensPaneHasBeenChanged=true;
                attributesPaneHasBeenChanged=true;
            }
        });
        slider = (Slider) findViewById(R.id.slider);
        slider.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int sliderValue = slider.getValue();
                if (sliderValue <= 4) {
                    slider.setBackgroundColor(getResources().getColor(R.color.violet));
                    editor.putInt(sharedSlider[currentToken], sliderValue);
                    editor.commit();
                }
                if (sliderValue <= 8 && sliderValue > 4) {
                    slider.setBackgroundColor(getResources().getColor(R.color.red));
                    editor.putInt(sharedSlider[currentToken], sliderValue);
                    editor.commit();
                }
                if (sliderValue <= 12 && sliderValue > 8) {
                    slider.setBackgroundColor(getResources().getColor(R.color.orange));
                    editor.putInt(sharedSlider[currentToken], sliderValue);
                    editor.commit();
                }
                if (sliderValue <= 16 && sliderValue > 12) {
                    slider.setBackgroundColor(getResources().getColor(R.color.lightOrange));
                    editor.putInt(sharedSlider[currentToken], sliderValue);
                    editor.commit();
                }
                if (sliderValue <= 20 && sliderValue > 16) {
                    slider.setBackgroundColor(getResources().getColor(R.color.darkYellow));
                    editor.putInt(sharedSlider[currentToken], sliderValue);
                    editor.commit();
                }
                if (sliderValue <= 24 && sliderValue > 20) {
                    slider.setBackgroundColor(getResources().getColor(R.color.yellow));
                    editor.putInt(sharedSlider[currentToken], sliderValue);
                    editor.commit();
                }
                if (sliderValue <= 28 && sliderValue > 24) {
                    slider.setBackgroundColor(getResources().getColor(R.color.lightGreen));
                    editor.putInt(sharedSlider[currentToken], sliderValue);
                    editor.commit();
                }
                if (sliderValue <= 32 && sliderValue > 28) {
                    slider.setBackgroundColor(getResources().getColor(R.color.green));
                    editor.putInt(sharedSlider[currentToken], sliderValue);
                    editor.commit();
                }
                if (sliderValue <= 36 && sliderValue > 32) {
                    slider.setBackgroundColor(getResources().getColor(R.color.lightBlue));
                    editor.putInt(sharedSlider[currentToken], sliderValue);
                    editor.commit();
                }
                if (sliderValue <= 40 && sliderValue > 36) {
                    slider.setBackgroundColor(getResources().getColor(R.color.blue));
                    editor.putInt(sharedSlider[currentToken], sliderValue);
                    editor.commit();
                }
                if (sliderValue <= 44 && sliderValue > 40) {
                    slider.setBackgroundColor(getResources().getColor(R.color.darkBlue));
                    editor.putInt(sharedSlider[currentToken], sliderValue);
                    editor.commit();
                }
                if (sliderValue <= 50 && sliderValue > 44) {
                    slider.setBackgroundColor(getResources().getColor(R.color.purple));
                    editor.putInt(sharedSlider[currentToken], sliderValue);
                    editor.commit();
                }
                if (event.getAction()==MotionEvent.ACTION_UP) {
                    attributesPaneHasBeenChanged = true;
                    tokensPaneHasBeenChanged = true;
                }
                return false;
            }

        });
        thread.start();
    }

    Thread thread = new Thread() {
        @Override
        public void run() {
            try {
                while (true) {
                    sleep(16);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateTokensPane();
                            updateAttributesPane();
                        }
                    });
                }
            }catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    };

    public void updateTokensPane(){
        if(tokensPaneHasBeenChanged) {
            fillImageIDs();
            GridView gridView = (GridView) findViewById(R.id.gridView1);
            gridView.setAdapter(new GridViewAdapter(this));
            tokensPaneHasBeenChanged=false;
        }
    }

    public void updateAttributesPane(){
        if(attributesPaneHasBeenChanged) {
            if (paneIsVisible) {
                int sliderValue = pref.getInt(sharedSlider[currentToken], 0);
                slider.setValue(sliderValue);
                if (sliderValue <= 4)
                    slider.setBackgroundColor(getResources().getColor(R.color.violet));


                if (sliderValue <= 8 && sliderValue > 4)
                    slider.setBackgroundColor(getResources().getColor(R.color.red));


                if (sliderValue <= 12 && sliderValue > 8)
                    slider.setBackgroundColor(getResources().getColor(R.color.orange));


                if (sliderValue <= 16 && sliderValue > 12)
                    slider.setBackgroundColor(getResources().getColor(R.color.lightOrange));


                if (sliderValue <= 20 && sliderValue > 16)
                    slider.setBackgroundColor(getResources().getColor(R.color.darkYellow));


                if (sliderValue <= 24 && sliderValue > 20)
                    slider.setBackgroundColor(getResources().getColor(R.color.yellow));


                if (sliderValue <= 28 && sliderValue > 24)
                    slider.setBackgroundColor(getResources().getColor(R.color.lightGreen));


                if (sliderValue <= 32 && sliderValue > 28)
                    slider.setBackgroundColor(getResources().getColor(R.color.green));


                if (sliderValue <= 36 && sliderValue > 32)
                    slider.setBackgroundColor(getResources().getColor(R.color.lightBlue));


                if (sliderValue <= 40 && sliderValue > 36)
                    slider.setBackgroundColor(getResources().getColor(R.color.blue));


                if (sliderValue <= 44 && sliderValue > 40)
                    slider.setBackgroundColor(getResources().getColor(R.color.darkBlue));


                if (sliderValue <= 50 && sliderValue > 44)
                    slider.setBackgroundColor(getResources().getColor(R.color.purple));

                //This code segment simulates a touch on the slider in order to change
                //slider's ball element to match the correct color

                long downTime = SystemClock.uptimeMillis();
                long eventTime = SystemClock.uptimeMillis() + 100;
                float x = 0.0f;
                float y = 0.0f;
                int metaState = 0;
                MotionEvent motionEvent = MotionEvent.obtain(
                        downTime,
                        eventTime,
                        MotionEvent.ACTION_DOWN,
                        x,
                        y,
                        metaState
                );
                slider.setValue(sliderValue);
                slider.onTouchEvent(motionEvent);
                motionEvent = MotionEvent.obtain(
                        downTime,
                        eventTime,
                        MotionEvent.ACTION_UP,
                        x,
                        y,
                        metaState
                );
                slider.setValue(sliderValue);
                slider.onTouchEvent(motionEvent);
                //End of simulated touch

            } else {
                EditText edit = (EditText) findViewById(R.id.myText);
                edit.setText("");
                slider.setValue(0);
            }
            EditText edit = (EditText) findViewById(R.id.myText);
            String currentTokenText = pref.getString(sharedName[currentToken], null);
            edit.setText(currentTokenText);
            attributesPaneHasBeenChanged = false;
        }
        EditText edit = (EditText) findViewById(R.id.myText);
        String currentTokenText = edit.getText().toString();
        editor.putString(sharedName[currentToken], currentTokenText);
        editor.commit();
    }

    public void clearTokenValues(int startPoint){
        if(startPoint==0||startPoint==1){
            editor.putString(sharedName[startPoint], "");
            editor.putInt(sharedSlider[startPoint], 0);
            editor.commit();
        }else {
            for (int i = startPoint; i != 9; i++) {
                editor.putString(sharedName[i], "");
                editor.putInt(sharedSlider[i], 0);
                editor.commit();
            }
        }
    }

    public void fillImageIDs(){
        for (int i=0;i<=(plusLocation-1);i++){
            int sliderValue = pref.getInt(sharedSlider[i], 1337);
            if(sliderValue<=0) imageIDs[i] =R.drawable.circle;
            if(sliderValue<=4&&sliderValue!=0) {
                imageIDs[i] =R.drawable.violet_circle;
            }
            if (sliderValue<=8&&sliderValue>4) {
                imageIDs[i] =R.drawable.red_circle;
            }
            if (sliderValue<=12&&sliderValue>8) {
                imageIDs[i] =R.drawable.orange_circle;
            }
            if (sliderValue<=16&&sliderValue>12) {
                imageIDs[i] =R.drawable.lorange_circle;
            }
            if(sliderValue<=20&&sliderValue>16) {
                imageIDs[i] =R.drawable.dyellow_circle;
            }
            if(sliderValue<=24&&sliderValue>20) {
                imageIDs[i] =R.drawable.yellow_circle;
            }
            if(sliderValue<=28&&sliderValue>24) {
                imageIDs[i] =R.drawable.lgreen_circle;
            }
            if(sliderValue<=32&&sliderValue>28) {
                imageIDs[i] =R.drawable.green_circle;
            }
            if(sliderValue<=36&&sliderValue>32) {
                imageIDs[i] =R.drawable.lblue_circle;
            }
            if(sliderValue<=40&&sliderValue>36) {
                imageIDs[i] =R.drawable.blue_circle;
            }
            if(sliderValue<=44&&sliderValue>40) {
                imageIDs[i] =R.drawable.dblue_circle;
            }
            if(sliderValue<=50&&sliderValue>44) {
                imageIDs[i] =R.drawable.purple_circle;
            }
            if(sliderValue>50) {
                imageIDs[i] =R.drawable.purple_circle;
            }
        }
        if(plusLocation<=8)
        imageIDs[plusLocation]=R.drawable.plus;
        for(int i=plusLocation+1;i<=8;i++)
            imageIDs[i] = R.drawable.blank;
    }

    public class GridViewAdapter extends BaseAdapter {
        private Context context;

        public GridViewAdapter(Context c)
        {
            context = c;
        }

        public int getCount() {
            return imageIDs.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(185, 185));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(5, 5, 5, 5);
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(imageIDs[position]);
            if(imageIDs[position]==R.drawable.plus){//adds listener to plus token
                imageView.setOnClickListener(new View.OnClickListener() {//CLICKING ON PLUS
                    @Override
                    public void onClick(View v) {//increment locators on plus touch
                        currentToken = plusLocation;
                        plusLocation++;
                        /*Toast.makeText(PlayerInfo.this,
                                "Current PLUS Location: " + plusLocation,
                                Toast.LENGTH_LONG).show();*/
                        paneIsVisible = true;
                        attributesPaneHasBeenChanged=true;
                        tokensPaneHasBeenChanged=true;
                        }
                });
            } else if(imageIDs[position]!=R.drawable.blank&&imageIDs[position]!=R.drawable.plus){//if not plus, then store location of token in a tag, and listen
                imageView.setTag(Integer.valueOf(position));
                imageView.setOnClickListener(new View.OnClickListener() {//CLICKING ON TOKEN
                    @Override
                    public void onClick(View v) {
                        Integer position = (Integer)v.getTag();
                        currentToken = position;
                        paneIsVisible = true;
                        attributesPaneHasBeenChanged = true;
                        tokensPaneHasBeenChanged=true;
                    }
                });
            }
            return imageView;
        }
    }

}
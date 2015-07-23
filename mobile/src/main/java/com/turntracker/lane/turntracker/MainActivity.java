package com.turntracker.lane.turntracker;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.gc.materialdesign.views.Slider;

public class MainActivity extends Activity{
    Integer[] imageIDs = new Integer[9];//drawble container
    int currentToken = 0;//For currently selected token attribute
    int visibilityTracker;//For tracking last visible token
    boolean oneMoreClickNeeded;//handles last plus sign action
    private ImageView trashCan = null;
    Editor editor;
    String [] sharedName = {"T1_Name","T2_Name","T3_Name","T4_Name","T5_Name",
            "T6_Name","T7_Name","T8_Name","T9_Name"};
    String [] sharedColor = {"T1_Color","T2_Color","T3_Color","T4_Color","T5_Color",
            "T6_Color","T7_Color","T8_Color","T9_Color"};
    SharedPreferences pref = null;
    Slider slider = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        setContentView(R.layout.activity_main);
        visibilityTracker = 3;
        oneMoreClickNeeded = true;
        fillDrawableArray(visibilityTracker);
        redraw();
        trashCan = (ImageView) findViewById(R.id.trash);
        trashCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (plusCheck()) {
                    visibilityTracker--;
                    editor.putString(sharedName[visibilityTracker-1], "");
                    editor.putInt(sharedColor[visibilityTracker-1], 0);
                    editor.commit();
                    //imageIDs[visibilityTracker-1] = R.drawable.circle;
                    //deleteEntry(visibilityTracker-1);
                    imageIDs[visibilityTracker] = R.drawable.blank;
                    imageIDs[(visibilityTracker - 1)] = R.drawable.plus;
                    redraw();
                }
            }
        });
        slider = (Slider) findViewById(R.id.slider);
        slider.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int sliderValue = slider.getValue();
                if(sliderValue<=4) {
                    slider.setBackgroundColor(getResources().getColor(R.color.violet));
                    editor.putInt(sharedColor[currentToken], sliderValue);
                    editor.commit();
                    fillDrawableArray(visibilityTracker);
                }
                if(sliderValue<=8&&sliderValue>4) {
                    slider.setBackgroundColor(getResources().getColor(R.color.red));
                    editor.putInt(sharedColor[currentToken], sliderValue);
                    editor.commit();
                    fillDrawableArray(visibilityTracker);
                }
                if(sliderValue<=12&&sliderValue>8) {
                    slider.setBackgroundColor(getResources().getColor(R.color.orange));
                    editor.putInt(sharedColor[currentToken], sliderValue);
                    editor.commit();
                    fillDrawableArray(visibilityTracker);
                }
                if(sliderValue<=16&&sliderValue > 12) {
                    slider.setBackgroundColor(getResources().getColor(R.color.lightOrange));
                    editor.putInt(sharedColor[currentToken], sliderValue);
                    editor.commit();
                    fillDrawableArray(visibilityTracker);
                }
                if(sliderValue<=20&&sliderValue>16) {
                    slider.setBackgroundColor(getResources().getColor(R.color.darkYellow));
                    editor.putInt(sharedColor[currentToken], sliderValue);
                    editor.commit();
                    fillDrawableArray(visibilityTracker);
                }
                if(sliderValue<=24&&sliderValue>20) {
                    slider.setBackgroundColor(getResources().getColor(R.color.yellow));
                    editor.putInt(sharedColor[currentToken], sliderValue);
                    editor.commit();
                    fillDrawableArray(visibilityTracker);
                }
                if(sliderValue<=28&&sliderValue>24) {
                    slider.setBackgroundColor(getResources().getColor(R.color.lightGreen));
                    editor.putInt(sharedColor[currentToken], sliderValue);
                    editor.commit();
                    fillDrawableArray(visibilityTracker);
                }
                if(sliderValue<=32&&sliderValue>28) {
                    slider.setBackgroundColor(getResources().getColor(R.color.green));
                    editor.putInt(sharedColor[currentToken], sliderValue);
                    editor.commit();
                    fillDrawableArray(visibilityTracker);
                }
                if(sliderValue<=36&&sliderValue>32) {
                    slider.setBackgroundColor(getResources().getColor(R.color.lightBlue));
                    editor.putInt(sharedColor[currentToken], sliderValue);
                    editor.commit();
                    fillDrawableArray(visibilityTracker);
                }
                if(sliderValue<=40&&sliderValue>36) {
                    slider.setBackgroundColor(getResources().getColor(R.color.blue));
                    editor.putInt(sharedColor[currentToken], sliderValue);
                    editor.commit();
                    fillDrawableArray(visibilityTracker);
                }
                if(sliderValue<=44&&sliderValue>40) {
                    slider.setBackgroundColor(getResources().getColor(R.color.darkBlue));
                    editor.putInt(sharedColor[currentToken], sliderValue);
                    editor.commit();
                    fillDrawableArray(visibilityTracker);
                }
                if(sliderValue<=50&&sliderValue>44) {
                    slider.setBackgroundColor(getResources().getColor(R.color.purple));
                    editor.putInt(sharedColor[currentToken], sliderValue);
                    editor.commit();
                    fillDrawableArray(visibilityTracker);
                }
                //if (event.getAction()==MotionEvent.ACTION_UP) redraw();
                return false;
            }
        });
    }
        //slider.onKeyUp()
        /*slider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Slider slider = (Slider) findViewById(R.id.slider);
                int sliderValue = slider.getValue();
                Toast.makeText(MainActivity.this,
                        "SLIDER VALUE: " + sliderValue,
                        Toast.LENGTH_LONG).show();
            }

        });*/


    public void redraw(){//redraws gridView/tokens
        GridView gridView = (GridView) findViewById(R.id.gridView1);
        gridView.setAdapter(new GridViewAdapter(this));
    }

    public void fillDrawableArray(int drawTo) {
        drawTo--;
        for (int i = 0; i != drawTo; i++) {
            imageIDs[i] = R.drawable.circle;
            int sliderValue = pref.getInt(sharedColor[i], 1337);
            Toast.makeText(MainActivity.this,
                    "SliderValue: " + sliderValue,
                    Toast.LENGTH_LONG).show();
            if(sliderValue==0) imageIDs[i] =R.drawable.circle;
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
        }
            imageIDs[drawTo] = R.drawable.plus;
            drawTo++;

            while (drawTo != 9) {
                imageIDs[drawTo] = R.drawable.blank;
                drawTo++;
            }
    }

    public boolean plusCheck(){
        int plusLocation = 0;
        for(int i=0; i!=9; i++)
            if(imageIDs[i]==R.drawable.plus)
                plusLocation = i;
        if(plusLocation==2)
            return false;
        if(plusLocation==0) {
            imageIDs[8] = R.drawable.plus;
            redraw();
            return false;
        }
        return true;
    }

    public void fillTokenAttributes(int tokenNum){
        //pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        String currentTokenText = pref.getString(sharedName[tokenNum],null);
        EditText edit=(EditText)findViewById(R.id.myText);
        edit.setText(currentTokenText);
        slider.setValue(pref.getInt(sharedColor[tokenNum], 0));
        int sliderValue = slider.getValue();
        if(sliderValue<=4) {
            slider.setBackgroundColor(getResources().getColor(R.color.violet));
        }
        if(sliderValue<=8&&sliderValue>4) {
            slider.setBackgroundColor(getResources().getColor(R.color.red));
        }
        if(sliderValue<=12&&sliderValue>8) {
            slider.setBackgroundColor(getResources().getColor(R.color.orange));
        }
        if(sliderValue<=16&&sliderValue > 12) {
            slider.setBackgroundColor(getResources().getColor(R.color.lightOrange));
        }
        if(sliderValue<=20&&sliderValue>16) {
            slider.setBackgroundColor(getResources().getColor(R.color.darkYellow));
        }
        if(sliderValue<=24&&sliderValue>20) {
            slider.setBackgroundColor(getResources().getColor(R.color.yellow));
        }
        if(sliderValue<=28&&sliderValue>24) {
            slider.setBackgroundColor(getResources().getColor(R.color.lightGreen));
        }
        if(sliderValue<=32&&sliderValue>28) {
            slider.setBackgroundColor(getResources().getColor(R.color.green));
        }
        if(sliderValue<=36&&sliderValue>32) {
            slider.setBackgroundColor(getResources().getColor(R.color.lightBlue));
        }
        if(sliderValue<=40&&sliderValue>36) {
            slider.setBackgroundColor(getResources().getColor(R.color.blue));
        }
        if(sliderValue<=44&&sliderValue>40) {
            slider.setBackgroundColor(getResources().getColor(R.color.darkBlue));
        }
        if(sliderValue<=50&&sliderValue>44) {
            slider.setBackgroundColor(getResources().getColor(R.color.purple));
        }
        showTokenAttributes(tokenNum);
        /*
        Need the fill color here
         */

    }

    public void showTokenAttributes(int tokenNum){
        EditText edit=(EditText)findViewById(R.id.myText);
        edit.setVisibility(View.VISIBLE);
        Slider slider2 = slider;
        slider2.setVisibility(View.VISIBLE);
        int sliderValue = slider.getValue();
        /*Toast.makeText(MainActivity.this,
                "SLIDER VALUE: " + sliderValue,
                Toast.LENGTH_LONG).show();*/
        currentToken = tokenNum;
    }

    public void hideTokenAttributes(){
        EditText edit=(EditText)findViewById(R.id.myText);
        edit.setVisibility(View.INVISIBLE);
        Slider slider = (Slider) findViewById(R.id.slider);
        slider.setVisibility(View.INVISIBLE);
    }

    public void saveText(){
        EditText edit=(EditText)findViewById(R.id.myText);
        editor.putString(sharedName[currentToken], edit.getText().toString());
        editor.commit();
    }

    public void deleteEntry(int entryLocation){
        //editor.remove(sharedName[entryLocation]);
        //editor.commit();
        editor.putString(sharedName[entryLocation - 1], "");
        /*Toast.makeText(MainActivity.this,
                "Deleting " + sharedName[visibilityTracker-1],
                Toast.LENGTH_LONG).show();*/
        fillTokenAttributes(currentToken);
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
        if(imageIDs[position]==R.drawable.plus){
            saveText(); //save text on plus sign hit, slider color on release
            hideTokenAttributes();
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!oneMoreClickNeeded) {
                        deleteEntry(9);
                        EditText edit = (EditText) findViewById(R.id.myText);
                        edit.setText("");
                        showTokenAttributes(8);
                        imageIDs[8] = R.drawable.circle;
                        redraw();
                    }
                    if(visibilityTracker!=9) {
                        deleteEntry(visibilityTracker);
                        visibilityTracker++;
                        fillDrawableArray(visibilityTracker);
                        if(imageIDs[8]==R.drawable.plus) oneMoreClickNeeded = false;
                        redraw();

                    }
                }
            });
        } else{
            imageView.setTag(Integer.valueOf(position));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveText(); //save text on token hit, slider color on release
                    Integer position = (Integer)v.getTag();
                    //pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                    String currentTokenText = pref.getString(sharedName[position], null);
                    if(currentTokenText!=null){fillTokenAttributes(position);}
                    else {
                        EditText edit = (EditText) findViewById(R.id.myText);
                        edit.setText("");
                        showTokenAttributes(position);
                    }
                }
            });
        }
        return imageView;
    }
}

}

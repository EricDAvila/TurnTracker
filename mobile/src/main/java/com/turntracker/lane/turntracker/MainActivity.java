package com.turntracker.lane.turntracker;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
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
                if (plusCheck()){
                    visibilityTracker--;
                    //deleteEntry(visibilityTracker-1);
                    imageIDs[visibilityTracker] = R.drawable.blank;
                    imageIDs[(visibilityTracker - 1)] = R.drawable.plus;
                    redraw();
                }
            }
        });
    }

public void redraw(){
        GridView gridView = (GridView) findViewById(R.id.gridView1);
        gridView.setAdapter(new GridViewAdapter(this));
    }

    public void fillDrawableArray(int drawTo) {
        drawTo--;
            for (int i = 0; i != drawTo; i++)
                imageIDs[i] = R.drawable.circle;

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
        showTokenAttributes(tokenNum);
        /*
        Need the fill color here
         */
    }

    public void showTokenAttributes(int tokenNum){
        EditText edit=(EditText)findViewById(R.id.myText);
        edit.setVisibility(View.VISIBLE);
        Slider slider = (Slider) findViewById(R.id.slider);
        slider.setVisibility(View.VISIBLE);
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
        editor.putString(sharedName[entryLocation-1], "");
        Toast.makeText(MainActivity.this,
                "Deleting " + sharedName[visibilityTracker-1],
                Toast.LENGTH_LONG).show();
        fillTokenAttributes(currentToken);
        //editor.remove(sharedColor[visibilityTracker]);
        //editor.commit();
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
                    saveText(); //save text on plus sign hit, slider color on release
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

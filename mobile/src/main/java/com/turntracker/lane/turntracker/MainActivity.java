package com.turntracker.lane.turntracker;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity{
    Integer[] imageIDs = new Integer[9];
    int visibilityTracker;
    boolean oneMoreClickNeeded;
    private ImageView trashCan = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!oneMoreClickNeeded) {
                        imageIDs[8] = R.drawable.circle;
                        redraw();
                    }
                    if(visibilityTracker!=9) {
                        visibilityTracker++;
                        fillDrawableArray(visibilityTracker);
                        if(imageIDs[8]==R.drawable.plus) oneMoreClickNeeded = false;
                        redraw();

                    }
                }
            });
        } else{
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this,
                            "One day when you click me, cool things will happen!",
                            Toast.LENGTH_LONG).show();
                }
            });
        }
        return imageView;
    }
}
}

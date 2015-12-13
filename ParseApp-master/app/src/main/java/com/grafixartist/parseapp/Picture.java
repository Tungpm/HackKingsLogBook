package com.grafixartist.parseapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Picture extends AppCompatActivity {
    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        Intent intent = this.getIntent();
        layout = (LinearLayout) findViewById(R.id.layout);
        Double lat = intent.getDoubleExtra("lat", 1);
        Double lon = intent.getDoubleExtra("lon",1);
        Log.i("tag", "lat" + lat);
        Log.i("tag", "lon" + lon);
        ArrayList<Double> pins;
        try {
            pins = (ArrayList<Double>) InternalStorage.readObject(this, "array");
            for(int i = 0; i < pins.size()/3; i++){
                Log.i("tag", "latitude" + pins.get(i * 3 + 1));
                Log.i("tag", "longitude" + pins.get(i * 3 + 2));
                Double latitude = pins.get(i*3+1);
                Double longitude = pins.get(i*3+2);
                Log.i("tag", latitude + " " + longitude);
                if(latitude.equals(lat) && longitude.equals(lon)) {
                    addPhoto(i);
                }
            }
        } catch (Exception e) {
        }

    }

    private void addPhoto(int i) {
        ArrayList<String> locations = null;
        try {
            locations = (ArrayList<String>) InternalStorage.readObject(this, "location");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ImageView imageView = new ImageView(this);
        File imgFile = new File("/storage/emulated/0/Log/"+locations.get(i));
        Log.i("tag", "finish");
        if(imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageView.setImageBitmap(getResizedBitmap(myBitmap, 1000, 1400));
            imageView.setRotation(90);
            layout.addView(imageView);
        }
        if(!imgFile.exists()) {
            finish();
        }
    }

    public Bitmap getResizedBitmap(Bitmap image, int bitmapWidth,
                                   int bitmapHeight) {
        return Bitmap.createScaledBitmap(image, bitmapWidth, bitmapHeight,
                true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_picture, menu);
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
}

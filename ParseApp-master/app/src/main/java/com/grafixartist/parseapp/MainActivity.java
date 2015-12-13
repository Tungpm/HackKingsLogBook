package com.grafixartist.parseapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.grafixartist.parseapp.ParseObjects.Checkpoint;
import com.grafixartist.parseapp.ParseObjects.Journey;
import com.grafixartist.parseapp.ParseObjects.Photo;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void map(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    //Create the Settings button on click listener
    public void share(View view) {
        LayoutInflater layoutInflater
                = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.description, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                AbsoluteLayout.LayoutParams.WRAP_CONTENT,
                AbsoluteLayout.LayoutParams.WRAP_CONTENT);
        //Creates 4 buttons objects linked to the buttons on the description menu
        final Button btnDismiss = (Button) popupView.findViewById(R.id.connect);
        Button btnMain = (Button) popupView.findViewById(R.id.host);
        final EditText btnConnect = (EditText) popupView.findViewById(R.id.dismiss);
        EditText btnHost = (EditText) popupView.findViewById(R.id.main);
        //Hides the Connect and Host button so the can't be pressed
        popupWindow.setFocusable(true);
        popupWindow.update();
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        //Creates onClickListener that closes the aiGame and returns to the main menu
        btnMain.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Creates onClickListener that closes the description menu
        btnDismiss.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                send(btnConnect.getText()+"",btnDismiss.getText()+"");
            }
        });
        popupWindow.showAsDropDown(findViewById(R.id.history), 50, -30);
    }


    public void send(String title, String description) {

        Journey journey = new Journey();
        ArrayList<String> locations;
        try {
            locations = (ArrayList<String>) InternalStorage.readObject(this, "location");
        } catch (Exception e) {
            locations = new ArrayList<>();
        }
        for (int i = 0; i < locations.size(); i++) {
            File imgFile;
            try {
                imgFile = new File("/storage/emulated/0/Log/" + locations.get(i));
            } catch (Exception e) {
                imgFile = null;
            }
            Checkpoint checkpoint = new Checkpoint();

            Photo photo = new Photo(title, description, imgFile, checkpoint);
        }
    }
    public void history(View view) {

    }

    public Bitmap getResizedBitmap(Bitmap image, int bitmapWidth,
                                   int bitmapHeight) {
        return Bitmap.createScaledBitmap(image, bitmapWidth, bitmapHeight,
                true);
    }

    public void pin(View view){
        ArrayList<Double> pins;
        try {
            pins = (ArrayList<Double>) InternalStorage.readObject(this, "array");
        }catch(Exception e){
            pins = new ArrayList<>();
        }
        GPSTracker gps = new GPSTracker(this);
        if(gps.canGetLocation()) { // gps enabled} // return boolean true/false
            pins.add(0.0);
            pins.add(gps.getLatitude());
            pins.add(gps.getLongitude());
        }
        try {
            InternalStorage.writeObject(this,"array",pins);
        } catch (IOException e) {
            e.printStackTrace();
        }
        gps.stopUsingGPS();
        ArrayList<String> locations;
        try {
            locations = (ArrayList<String>) InternalStorage.readObject(this, "location");
        }catch(Exception e){
            locations = new ArrayList<>();
        }
        locations.add("");
        try {
            InternalStorage.writeObject(this, "location", locations);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clear(View view) throws IOException {
        ArrayList<Double> pins = new ArrayList<>();
        InternalStorage.writeObject(this, "array", pins);
        ArrayList<String> location = new ArrayList<>();
        InternalStorage.writeObject(this, "location", location);
        Toast.makeText(this, "Clear", Toast.LENGTH_LONG).show();
    }

    public void camera(View view) {//camera stuff
        //camera stuff
        Intent imageIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

//folder stuff
        File imagesFolder = new File(Environment.getExternalStorageDirectory(), "Log");
        imagesFolder.mkdirs();

        File image = new File(imagesFolder, "QR_" + timeStamp + ".png");
        Uri uriSavedImage = Uri.fromFile(image);

        imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        startActivityForResult(imageIntent, 1);
        ArrayList<String> locations;
        try {
            locations = (ArrayList<String>) InternalStorage.readObject(this, "location");
        }catch(Exception e){
            locations = new ArrayList<>();
        }
        locations.add("QR_" + timeStamp + ".png");
        ArrayList<Double> pins;
        try {
            pins = (ArrayList<Double>) InternalStorage.readObject(this, "array");
        }catch(Exception e){
            pins = new ArrayList<>();
        }
        GPSTracker gps = new GPSTracker(this);
        if(gps.canGetLocation()) { // gps enabled} // return boolean true/false
            pins.add(1.0);
            pins.add(gps.getLatitude());
            pins.add(gps.getLongitude());
        }
        try {
            InternalStorage.writeObject(this,"array",pins);
            InternalStorage.writeObject(this,"location",locations);
        } catch (IOException e) {
            e.printStackTrace();
        }
        gps.stopUsingGPS();

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
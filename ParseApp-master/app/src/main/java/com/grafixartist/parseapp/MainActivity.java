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
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
                finish();
            }
        });
        popupWindow.showAsDropDown(findViewById(R.id.history), 50, -30);
    }


    public void send(String title, String description) {

        ParseACL acl = new ParseACL();

        // Give public read access
        acl.setPublicReadAccess(true);

        Journey journey = new Journey();
        journey.setTitle(title);
        journey.setDescription(description);
        journey.setUser(ParseUser.getCurrentUser());

        List<Checkpoint> checkpoints = new ArrayList<Checkpoint>();

        ArrayList<String> locations;
        ArrayList<Double> pins;
        try {
            locations = (ArrayList<String>) InternalStorage.readObject(this, "location");
            pins = (ArrayList<Double>) InternalStorage.readObject(this, "array");
        } catch (Exception e) {
            locations = new ArrayList<>();
            pins = new ArrayList<>();
        }

        HashMap<Double, Checkpoint> dict = new HashMap<Double, Checkpoint>();
        for (int i= 0; i<pins.size(); i+= 3) {
            Checkpoint checkpoint;
            if (dict.containsKey(pins.get(i + 1) + pins.get(i + 2)) &&
                    (dict.get(pins.get(i + 1) + pins.get(i + 2)).getLocation().getLatitude() == pins.get(i + 1))) {
                checkpoint = dict.get(pins.get(i + 1) + pins.get(i + 2));
            } else {
                checkpoint = new Checkpoint();
                checkpoint.setTitle((i / 3) + "");
                checkpoint.setDescription(locations.get(i/3));
                checkpoint.setLocation(new ParseGeoPoint(pins.get(i + 1), pins.get(i + 2)));
                dict.put(pins.get(i + 1) + pins.get(i + 2), checkpoint);
            }

            if (pins.get(i) != 0.0) {
                File imgFile;
                try {
                    imgFile = new File("/storage/emulated/0/Log/" + locations.get(i / 3));

                } catch (Exception e) {
                    imgFile = null;
                }
                Photo photo = new Photo();
                List<Photo> l = new ArrayList<Photo>();
                try {
                    Method method = Parse.class.getDeclaredMethod("convertArrayToList", JSONArray.class);
                    method.setAccessible(true);
                    JSONArray jsonArray = checkpoint.getPhotos();
                    l = (List<Photo>) method.invoke(null, jsonArray);
                } catch (Exception e){

                }


                l.add(photo);
                checkpoint.setPhotos(new JSONArray(l));

                photo.setACL(acl);

                photo.saveInBackground();
            }
        }


        for (Checkpoint checkpoint : dict.values()) {
            checkpoint.setACL(acl);
            checkpoint.saveInBackground();
        }

        journey.setCheckpoints(new JSONArray(dict.values()));
        journey.setUser(ParseUser.getCurrentUser());
        journey.setACL(acl);
        journey.saveInBackground();
    }

    public void history(View view) {
        Journey journey = new Journey();
        String title = journey.getTitle();
        String desc = journey.getDescription();

        
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
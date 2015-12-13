package com.grafixartist.parseapp;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.grafixartist.parseapp.ParseObjects.Checkpoint;
import com.grafixartist.parseapp.ParseObjects.Journey;
import com.grafixartist.parseapp.ParseObjects.Photo;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;

/**
 * Created by Suleiman on 24-06-2015.
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(Photo.class);
        ParseObject.registerSubclass(Checkpoint.class);
        ParseObject.registerSubclass(Journey.class);
        Parse.initialize(this, "rJ2wNxcdT3f609TgST6GiauHOybgn5sL9UbFTZA1", "hDHpVPbEbnfUzgFNXEo5QxrgyKAOtJCkaToSyA42");
        FacebookSdk.sdkInitialize(getApplicationContext());
        ParseFacebookUtils.initialize(this);
    }

}

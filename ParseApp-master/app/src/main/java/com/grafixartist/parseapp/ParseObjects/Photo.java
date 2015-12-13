package com.grafixartist.parseapp.ParseObjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.grafixartist.parseapp.ParseObjects.Checkpoint;
import com.parse.ParseObject;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by TungPM on 12/13/2015.
 */
public class Photo extends ParseObject {
    File image;
    String description;
    String title;
    Checkpoint checkpoint;

    public Photo(String tit, String desc, File imag, Checkpoint point) {
        image = imag;
        description = desc;
        title = tit;
        checkpoint = point;
    }

    public Photo() {

    }

}

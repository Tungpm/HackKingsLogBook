package com.grafixartist.parseapp.ParseObjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.grafixartist.parseapp.ParseObjects.Checkpoint;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by TungPM on 12/13/2015.
 */
@ParseClassName("Photo")
public class Photo extends ParseObject {

    public String getTitle(){return getString("title");}
    public String getDescription(){return getString("description");}
    public ParseRelation<Checkpoint> getCheckpoint(){return getRelation("checkpoint");}
    public ParseFile getImage(){return getParseFile("image");}

    public void setTitle(String s){put("title", s);}
    public void setDescription(String s){put("description", s);}
    public void setCheckpoint(ParseRelation<Checkpoint> c){put("checkpoint", c);}
    public void setImage(ParseFile f){put("image", f);}

    public static ParseQuery<Photo> getQuery() {
        return ParseQuery.getQuery(Photo.class);
    }

}

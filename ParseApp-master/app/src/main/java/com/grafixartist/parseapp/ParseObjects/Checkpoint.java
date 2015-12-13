package com.grafixartist.parseapp.ParseObjects;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by TungPM on 12/13/2015.
 */
@ParseClassName("Checkpoint")
public class Checkpoint extends ParseObject {

    public String getTitle(){return getString("title");}
    public String getDescription(){return getString("description");}
    public ParseRelation<Journey> getCheckpoint(){return getRelation("journey");}
    public JSONArray getPhotos(){return getJSONArray("photos");}
    public ParseGeoPoint getLocation(){return getParseGeoPoint("location");}

    public void setTitle(String s){put("title", s);}
    public void setDescription(String s){put("description", s);}
    public void setLocation(ParseGeoPoint g){put("location", g);}
    public void setImage(ParseFile f){put("image", f);}
    public void setJourney(ParseRelation<Journey> j){put("journey", j);}
    public void setPhotos(JSONArray p){put("photos", p);}

    public static ParseQuery<Checkpoint> getQuery() {
        return ParseQuery.getQuery(Checkpoint.class);
    }

}

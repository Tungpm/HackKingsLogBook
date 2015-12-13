package com.grafixartist.parseapp.ParseObjects;

import com.grafixartist.parseapp.ParseObjects.Checkpoint;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import org.json.JSONArray;

/**
 * Created by TungPM on 12/13/2015.
 */
@ParseClassName("Journey")
public class Journey extends ParseObject {
    private String title;
    private String description;
    private Checkpoint[] checkpoints;
    private ParseUser user;


    public String getTitle(){return getString("title");}
    public String getDescription(){return getString("description");}
    public JSONArray getCheckpoints(){return getJSONArray("checkpoints");}
    public ParseUser getUser(){return getParseUser("user");}

    public void setTitle(String s){put("title", s);}
    public void setDescription(String s){put("description", s);}
    public void setCheckpoints(JSONArray c){put("checkpoints", c);}
    public void setUser(ParseUser u){put("user", u);}

    public static ParseQuery<Journey> getQuery() {
        return ParseQuery.getQuery(Journey.class);
    }


}

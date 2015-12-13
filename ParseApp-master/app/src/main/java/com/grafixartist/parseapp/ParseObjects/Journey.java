package com.grafixartist.parseapp.ParseObjects;

import com.grafixartist.parseapp.ParseObjects.Checkpoint;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by TungPM on 12/13/2015.
 */
public class Journey extends ParseObject {
    String name;
    String description;
    Checkpoint[] checkpoints;
    ParseUser user;

    public Journey(){}
}

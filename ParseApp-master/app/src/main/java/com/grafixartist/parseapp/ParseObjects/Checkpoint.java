package com.grafixartist.parseapp.ParseObjects;

import com.parse.ParseGeoPoint;

/**
 * Created by TungPM on 12/13/2015.
 */
public class Checkpoint {
    String name;
    String description;
    Photo[] photos;
    ParseGeoPoint location;
    Journey journey;

    public Checkpoint(){}


}

package com.example.grandzob.StreetSpot;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

/**
 * Created by GrandZob on 21/05/16.
 */
@ParseClassName("Photo")
public class Photo extends ParseObject {


    public void setTitle(String title) {
        put("title", title);
    }


    public void setPhotoFile(ParseFile photoFile)
    {
        put("photo", photoFile);
    }


    public void setLocalisation(ParseGeoPoint localisation) {
        put("localisation", localisation);
    }


}

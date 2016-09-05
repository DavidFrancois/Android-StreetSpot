package com.example.grandzob.testmeal;

import android.util.AndroidException;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by GrandZob on 27/05/16.
 */
public class ParseInit extends android.app.Application {



    @Override
    public void onCreate () {
        super.onCreate();

        ParseObject.registerSubclass(Photo.class);

        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("Walk")
                .server("https://parse-walk.036.ninja/parse/")
                .build());
    }

}

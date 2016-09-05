package com.example.grandzob.StreetSpot;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class GetPhotos extends AppCompatActivity {
    private List<ParseObject> photos;
    private int indice;

    Button prevPic;
    Button nextPic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_photos);
        prevPic = (Button) findViewById(R.id.prevPic);
        nextPic = (Button) findViewById(R.id.nextPic);

        prevPic.setEnabled(false);

        /*
        Geolocation is written because of some issues with emulator, software doesn't seems to be able to read the location written in virtual machine config.
        It works fine on a real device.
        */
        ParseGeoPoint userLocation = new ParseGeoPoint(48.0, 2.0);
        ParseGeoPoint liminf = new ParseGeoPoint(userLocation.getLatitude()- 0.01, userLocation.getLongitude() - 0.01);
        ParseGeoPoint limsup = new ParseGeoPoint(userLocation.getLatitude()+ 0.01, userLocation.getLongitude() + 0.01);


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Photo");
        query.whereWithinGeoBox("localisation", liminf, limsup);
        query.setLimit(3);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null) {
                    photos = objects;
                    ImageView img = (ImageView) findViewById(R.id.imgview);

                    ParseFile image = (ParseFile) objects.get(indice).getParseFile("photo");
                    displayImage(image, img);

                }
                else
                    Log.e("echec : " + e.getMessage(), "done: ");
            }
        });
        //Toast.makeText(GetPhotos.this, photos.get(0).getObjectId() , Toast.LENGTH_LONG).show();
    }
    private void displayImage(ParseFile thumbnail, final ImageView img) {

        if (thumbnail != null) {
            thumbnail.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    if (e == null) {
                        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                        if (bmp != null) {
                            Log.e("parse file ok", " null");
                            img.setImageBitmap(null);
                            img.setImageBitmap(bmp);
                        }
                    } else {
                        Log.e("paser after downloade", " null");
                    }
                }
            });
        } else {
            Log.e("parse file", " null");
            img.setPadding(10, 10, 10, 10);
        }

    }

    public void prevPic(View view) {

    ImageView img = (ImageView) findViewById(R.id.imgview);
        if (indice > 0) {
            --indice;
            ParseFile image = (ParseFile) photos.get(indice).getParseFile("photo");
            displayImage(image, img);
        }
        verif();
    }

    public void nextPic(View view) {

    ImageView img = (ImageView) findViewById(R.id.imgview);
        if (indice +1 < photos.size()) {
            ++indice;
            ParseFile image = (ParseFile) photos.get(indice).getParseFile("photo");
            displayImage(image, img);
        }
        verif();
    }


    public void verif() {
        if (indice == 0) {
            prevPic.setEnabled(false);
        } else if (indice + 1 == photos.size())
            nextPic.setEnabled(false);
        else if (indice > 0 && indice +1 < photos.size()) {
            prevPic.setEnabled(true);
            nextPic.setEnabled(true);
        }
    }

    public void retour(View view) {
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
    }


}

package com.example.grandzob.testmeal;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.SaveCallback;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TakePhoto extends AppCompatActivity  {


    ArrayList<Photo> photos = new ArrayList<Photo>();
    private final int CAMERA_PIC_REQUEST = 1;
    private Photo photo;
    private File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.d("Error", ex.getMessage());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, CAMERA_PIC_REQUEST);
            }
        }


    }

    public File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        final ParseFile pf = new ParseFile(photoFile);
        pf.saveInBackground(new SaveCallback() {

            @Override
            public void done(ParseException e) {
                if (e == null)
                {

                    ParseGeoPoint pointTest = new ParseGeoPoint(48.0, 2.0);
                    photo = new Photo();
                    photo.setTitle("Trololo");
                    photo.setPhotoFile(pf);
                    photo.setLocalisation(pointTest);
                    photo.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(TakePhoto.this, "Photo is saved", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(TakePhoto.this, "Unable to save photo", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                    Toast.makeText(TakePhoto.this, "Unable to upload photo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getPhotos(View view) {
        Intent intent = new Intent(this, GetPhotos.class);
        startActivity(intent);
    }

    public void retour(View view) {
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
    }
}



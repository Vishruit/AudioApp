package com.iitm.vishruit.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity  extends Activity{

    static final int REQUEST_IMAGE_CAPTURE = 1;
    Uri fileUri;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_camera);
        ImageView photoImage = (ImageView) findViewById(R.id.imageView);
//        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Intent i = new Intent("android.media.action.IMAGE_CAPTURE");

        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        File image = null;
        try {
            image = File.createTempFile(
                    "tt331",  /* prefix */
                    ".jpg",         /* suffix */
                        storageDir      /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        fileUri = Uri.fromFile(image);
        i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(i, 1);

//        callCameraButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            // successfully captured the image
            // launching upload activity
            Intent myIntentAudio = new Intent(CameraActivity.this, AudioCapture.class);
            myIntentAudio.putExtra("URL_PIC", fileUri.getPath());
            CameraActivity.this.startActivity(myIntentAudio);

//                        while(urlAudio.isEmpty())
//                        urlAudio = getIntent().getStringExtra("URL_AUDIO");
//
//            startActivity(extras);
            //launchUploadActivity();

        }
    }

//    private void launchUploadActivity() {
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... voids) {
//                //Do things...
//                FileInputStream in = null;
//                try {
//                    in = new FileInputStream(new File(fileUri.getPath()));
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                new HttpFileUpload("http://echoapp.cloudapp.net/").Send_Now(in);
//                return null;
//            }
//        }.execute();

//    }


}

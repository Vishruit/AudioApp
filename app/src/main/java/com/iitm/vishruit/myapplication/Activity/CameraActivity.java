package com.iitm.vishruit.myapplication.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.iitm.vishruit.myapplication.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity  extends Activity{
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    static final int REQUEST_IMAGE_CAPTURE = 1;
    Uri fileUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_camera);

        // Check if we have write permission
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        } else {
            recordPicture();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    recordPicture();
                }
                break;
            }
        }
    }

    private void recordPicture() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        File image = null;
        try {
            image = File.createTempFile(
                    timeStamp,  /* prefix */
                    ".jpg",         /* suffix */
                        storageDir      /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        fileUri = Uri.fromFile(image);
        i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(i, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            // successfully captured the image
            // launching upload activity
            Intent myIntentAudio = new Intent(CameraActivity.this, AudioCaptureActivity.class);
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

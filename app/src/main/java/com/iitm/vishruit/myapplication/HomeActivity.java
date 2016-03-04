package com.iitm.vishruit.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class HomeActivity extends Activity {

    AlertDialog alertDialogStores;
    String urlPic = null, urlAudio = null;
    initialize ini;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custfeed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        View.OnClickListener handler = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {

                    case R.id.button_uploadpic:
//                        showPopUp();
                        Intent myIntentPic = new Intent(HomeActivity.this, CameraActivity.class);
                        //myIntentPic.putExtra("URL_PIC", "url");
                        HomeActivity.this.startActivity(myIntentPic);
                        //while(urlPic.isEmpty())
//                        urlPic = getIntent().getStringExtra("URL_PIC");

                        break;

                    case R.id.button_audio:
                        Intent myIntentAudio = new Intent(HomeActivity.this, AudioCapture.class);
                        //myIntentAudio.putExtra("URL_AUDIO", "url");
                        HomeActivity.this.startActivity(myIntentAudio);

//                        while(urlAudio.isEmpty())
//                        urlAudio = getIntent().getStringExtra("URL_AUDIO");
//                        Log.d("URL_AUDIO", urlAudio);
                        break;
                }

            }
        };

        //urlPic = getIntent().getStringExtra("URL_PIC");
        //urlAudio = getIntent().getStringExtra("URL_AUDIO");

        findViewById(R.id.button_uploadpic).setOnClickListener(handler);
        findViewById(R.id.button_audio).setOnClickListener(handler);

    }

    public void showPopUp(){

        // add your items, this can be done programatically
        // your items can be from a database
        ObjectItem[] ObjectItemData = new ObjectItem[20];


        ObjectItemData[0] = new ObjectItem(91, "Take a Photo");
        ObjectItemData[1] = new ObjectItem(92, "Upload A Photo");

        // our adapter instance
        ArrayAdapterItem adapter = new ArrayAdapterItem(this, R.layout.list_view_row_item, ObjectItemData);

        // create a new ListView, set the adapter and item click listener
        ListView listViewItems = new ListView(this);
        listViewItems.setAdapter(adapter);
        listViewItems.setOnItemClickListener(new OnItemClickListenerListViewItem());

        // put the ListView in the pop up
        alertDialogStores = new AlertDialog.Builder(this)
                .setView(listViewItems)
                .setTitle("Stores")
                .show();
    }
}

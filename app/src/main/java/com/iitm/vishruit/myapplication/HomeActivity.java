package com.iitm.vishruit.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

public class HomeActivity extends Activity {

    AlertDialog alertDialogStores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custfeed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        View.OnClickListener handler = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {

                    case R.id.button_upload:
                        showPopUp();
                        break;
                }
            }
        };

        findViewById(R.id.button_upload).setOnClickListener(handler);
    }

    public void showPopUp(){

        // add your items, this can be done programatically
        // your items can be from a database
        ObjectItem[] ObjectItemData = new ObjectItem[20];

        ObjectItemData[0] = new ObjectItem(91, "Mercury");
        ObjectItemData[1] = new ObjectItem(92, "Watson");
        ObjectItemData[2] = new ObjectItem(93, "Nissan");
        ObjectItemData[3] = new ObjectItem(94, "Puregold");
        ObjectItemData[4] = new ObjectItem(95, "SM");
        ObjectItemData[5] = new ObjectItem(96, "7 Eleven");
        ObjectItemData[6] = new ObjectItem(97, "Ministop");
        ObjectItemData[7] = new ObjectItem(98, "Fat Chicken");
        ObjectItemData[8] = new ObjectItem(99, "Master Siomai");
        ObjectItemData[9] = new ObjectItem(100, "Mang Inasal");
        ObjectItemData[10] = new ObjectItem(101, "Mercury 2");
        ObjectItemData[11] = new ObjectItem(102, "Watson 2");
        ObjectItemData[12] = new ObjectItem(103, "Nissan 2");
        ObjectItemData[13] = new ObjectItem(104, "Puregold 2");
        ObjectItemData[14] = new ObjectItem(105, "SM 2");
        ObjectItemData[15] = new ObjectItem(106, "7 Eleven 2");
        ObjectItemData[16] = new ObjectItem(107, "Ministop 2");
        ObjectItemData[17] = new ObjectItem(108, "Fat Chicken 2");
        ObjectItemData[18] = new ObjectItem(109, "Master Siomai 2");
        ObjectItemData[19] = new ObjectItem(110, "Mang Inasal 2");

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

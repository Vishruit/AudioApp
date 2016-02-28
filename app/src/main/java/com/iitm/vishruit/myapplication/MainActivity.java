package com.iitm.vishruit.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;

import java.util.Arrays;
import com.iitm.vishruit.myapplication.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final SharedPreferences sharedPrefLogin = getSharedPreferences(getString(R.string.sharedPref), Context.MODE_PRIVATE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button loginButton = (Button) findViewById(R.id.button_login);

        if (sharedPrefLogin.contains("LoginStatus")){
            Log.d("Log : ", "LoginStatus True");
            // Share URI Data
            Uri data = getIntent().getData();
            if(data != null){
                // Redirect to Home Page
                Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                homeIntent.putExtra("URIData", data); // Pass DATA URI
                startActivity(homeIntent);
                finish(); // call this to finish the current activity. Preventing Back buttton.
            } else {
//                Log.d("Log : ", "No URI Data Found");
                // Redirect to Home Page
                Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(homeIntent);
                finish(); // call this to finish the current activity. Preventing Back buttton.
            }
        } else {
            Log.d("Log : ", "Does not Contain LoginStatus");
            loginButton.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View view){
                    //Do something when someone clicks on submit button...send a http request and handle it.
                }
            });

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}

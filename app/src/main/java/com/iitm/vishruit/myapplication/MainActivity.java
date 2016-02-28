package com.iitm.vishruit.myapplication;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.iitm.vishruit.myapplication.*;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        final SharedPreferences sharedPrefLogin = getSharedPreferences(getString(R.string.sharedPref), Context.MODE_PRIVATE);

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
                    String url_string = "http://echoapp.cloudapp.net/rest-auth/login/";
                    EditText username = (EditText) findViewById(R.id.et_name);
                    EditText password = (EditText) findViewById(R.id.et_password);
                    try {
                        String query = "username="+username+"&password="+password;

                        Log.v("Query",query);
                        URL url = new URL(url_string);
                        HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                        //Set to POST
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setReadTimeout(10000);
                        Writer writer = new OutputStreamWriter(httpURLConnection.getOutputStream());
                        writer.write(query);
                        writer.flush();
                        writer.close();

                        StringBuilder builder = new StringBuilder();
                        builder.append(httpURLConnection.getResponseCode())
                                .append(" ")
                                .append(httpURLConnection.getResponseMessage())
                                .append("\n");

                        Map<String, List<String>> map = httpURLConnection.getHeaderFields();
                        for (Map.Entry<String, List<String>> entry : map.entrySet())
                        {
                            if (entry.getKey() == null)
                                continue;
                            builder.append( entry.getKey())
                                    .append(": ");

                            List<String> headerValues = entry.getValue();
                            Iterator<String> it = headerValues.iterator();
                            if (it.hasNext()) {
                                String itnext = it.next();

                                if (entry.getKey() == "Key"){
                                    SharedPreferences.Editor e = sharedPrefLogin.edit();
                                    e.putBoolean("loggedIn",true);
                                    e.putString("Key", itnext);
                                }

                                builder.append(itnext);

                                while (it.hasNext()) {
                                    builder.append(", ")
                                            .append(it.next());
                                }
                            }

                            builder.append("\n");
                        }
                        System.out.print(builder);
                        Log.v("Builder", builder.toString());
                        if(sharedPrefLogin.contains("Key")){
                            Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(homeIntent);
                            finish();
                        } else {
                            Context context = getApplicationContext();
                            Toast toast = Toast.makeText(context, "There was an error with your login", Toast.LENGTH_SHORT);
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                    }
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

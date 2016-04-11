package com.iitm.vishruit.myapplication.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.iitm.vishruit.myapplication.R;

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
                    EditText et_username = (EditText) findViewById(R.id.et_name);
                    EditText et_password = (EditText) findViewById(R.id.et_password);
                    EditText et_email = (EditText) findViewById(R.id.et_email);
                    final String username = et_username.getText().toString();
                    final String password = et_password.getText().toString();
                    final String email = et_email.getText().toString();

                    String url_string = "http://kunalgrover05.pythonanywhere.com/rest-auth/login/";

                    Log.v("Query: ",url_string);

                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url_string,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // Display the first 500 characters of the response string.

                                    try {
                                        JSONObject loginResponse = new JSONObject(response);
                                        Log.d("Server Response : ", loginResponse.toString());

                                        // On Successful Response from API.
                                        if (loginResponse.has("key")){
                                            SharedPreferences.Editor editor = sharedPrefLogin.edit();
                                            editor.putString("LoginStatus", "True");
                                            editor.putString("username", username);
                                            editor.putString("email", email);
                                            editor.putString("key", loginResponse.getString("key"));
                                            editor.apply();

                                            // Redirect to Home Page
                                            Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                                            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(homeIntent);
                                            finish(); // call this to finish the current activity. Preventing Back buttton.

                                        } else {
                                            Toast toast = new Toast(MainActivity.this);
                                            toast.setText("Sorry, wrong credentials provided.");
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Log.e("Error", "Could not parse malformed JSON String: " + response );
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Login Error: ", "The request failed with error: "+error.toString());
                        }
                    }){
                        @Override
                        protected Map<String,String> getParams(){
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("username",username);
                            params.put("password",password);
                            params.put("email", email);
                            return params;
                        }
                    };
                    queue.add(stringRequest);
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

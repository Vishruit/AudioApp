package com.iitm.vishruit.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AudioCapture extends Activity {

        Button play,stop,record, finish;
        private MediaRecorder myAudioRecorder;
        public String outputFile = null;
        Intent extras;
        public String imageFile = null;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.content_audio_capture);

            play=(Button)findViewById(R.id.button3);
            stop=(Button)findViewById(R.id.button2);
            record=(Button)findViewById(R.id.button);
            finish  = (Button)findViewById(R.id.button_finish);
            imageFile = getIntent().getStringExtra("URL_PIC");
            Log.d("Test", imageFile);
            stop.setEnabled(false);
            play.setEnabled(false);
            outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";;

            myAudioRecorder=new MediaRecorder();
            myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            myAudioRecorder.setOutputFile(outputFile);

            record.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        myAudioRecorder.prepare();
                        myAudioRecorder.start();
                    }

                    catch (IllegalStateException | IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    record.setEnabled(false);
                    stop.setEnabled(true);

                    Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
                }
            });

            stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myAudioRecorder.stop();
                    myAudioRecorder.release();
                    myAudioRecorder  = null;

                    stop.setEnabled(false);
                    play.setEnabled(true);

                    Toast.makeText(getApplicationContext(), "Audio recorded successfully",Toast.LENGTH_LONG).show();
                }
            });

            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) throws IllegalArgumentException,SecurityException,IllegalStateException {
                    MediaPlayer m = new MediaPlayer();

                    try {
                        m.setDataSource(outputFile);
                    }

                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        m.prepare();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    m.start();
                    Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();
                }
            });

            finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) throws IllegalArgumentException, SecurityException, IllegalStateException {
//                    extras = new Intent(AudioCapture.this, HomeActivity.class);
//                    extras.putExtra("URL_AUDIO", outputFile);
//                    startActivity(extras);s
                    Log.d("LOG", imageFile);
                    Log.d("LOG2", outputFile);

                    launchUploadActivity(imageFile, outputFile);
                }
            });
        }

        private void launchUploadActivity(String i, String a) {
            final String i_u = i;
            final String a_u = a;
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    //Do things...
                    FileInputStream is = null;
//                    FileInputStream as = null;

//                    Uri audio = Uri.fromFile(new File(a_u));
                    Log.d("LOG", i_u);
                    Log.d("LOG2", a_u);
                    Uri image = Uri.fromFile(new File(i_u));

                    try {
                        is = new FileInputStream(new File(i_u));
                        int p = is.available();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        return null;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
//                    try {
//                        as = new FileInputStream(new File(a_u));
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
                    new HttpFileUpload("http://echoapp.cloudapp.net/").Send_Now(is);//, ass);
                    return null;
                }
            }.execute();

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
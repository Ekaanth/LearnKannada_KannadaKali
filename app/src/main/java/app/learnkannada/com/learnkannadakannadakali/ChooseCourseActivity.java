package app.learnkannada.com.learnkannadakannadakali;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class ChooseCourseActivity extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT = 100;

    private Button dayCourse, flexiCourse;
    private ImageView mic;

    private MediaPlayer mediaPlayer;
    private AlertDialog.Builder builder, infoBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_course);

        dayCourse = (Button) findViewById(R.id.dayCourseID);
        flexiCourse = (Button) findViewById(R.id.comfortCourseID);
        mic = (ImageView) findViewById(R.id.micID);

        builder = new AlertDialog.Builder(this);
        infoBuilder = new AlertDialog.Builder(this);

        dayCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(ChooseCourseActivity.this,RecyclerViewActivity.class);
                /*intent.putExtra("category","dayCours");
                intent.putExtra("from","dayCourse");*/
                startActivity(new Intent(ChooseCourseActivity.this,CourseHomeActivity.class));
            }
        });

        flexiCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Coming soon...",Toast.LENGTH_LONG).show();
                //startActivity(new Intent(ChooseCourseActivity.this, Homepage.class));
            }
        });

        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Mic",Toast.LENGTH_SHORT).show();
                promptSpeechInput();
            }
        });

    }

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        }catch(ActivityNotFoundException e)
        {
            Toast.makeText(getApplicationContext(),"Missing in my Vocabulary!",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //Toast.makeText(getApplicationContext(),result.get(0),Toast.LENGTH_LONG).show();
                    handleSpokenWords(result.get(0));
                }
                break;
            }

        }
    }

    private void handleSpokenWords(String s) {
        final String spokenString = s.replaceAll(" ","_")
                .toLowerCase();
        String spokenStringEx = spokenString + "_ex";

        //checking number of words spoken
        int lengthOfWords = s.split(" ").length;

        //throwing message if it has more than 2 words
        if(lengthOfWords>2)
        {
            Toast.makeText(getApplicationContext(),"Stay tuned! \nSentences Translations \"coming soon!\"",Toast.LENGTH_LONG).show();
            return;
        }

        //playing found music file
        try {
            playOffline(spokenString);
            if(checkResource(spokenStringEx))
            {
                builder.setTitle("Yay! Example found for \"" + spokenString.replaceAll("_"," ") + "\"")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent exIntent = new Intent(ChooseCourseActivity.this, ExampleActivity.class);
                        exIntent.putExtra("name",spokenString.replaceAll("_"," "));
                        startActivity(exIntent);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(getApplicationContext(),"No Problem!",Toast.LENGTH_LONG).show();
                            }
                }).setMessage("Would you like to check it?")
                        .create().show();

                //Toast.makeText(getApplicationContext(),"Found",Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            //Toast.makeText(getApplicationContext(),"Resouce Not Found",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    //method checks if the resource with string supplied is present in raw directory
    private boolean checkResource(String spokenStringEx) {
        Integer id = getResources().getIdentifier(spokenStringEx.toLowerCase(),"raw",getPackageName());
        if(id>0)
            return true;
        else
            return false;
    }

    private void playOffline(String name) throws IOException {
        // Toast.makeText(context,"playing...",Toast.LENGTH_SHORT).show();
        mediaPlayer = new MediaPlayer();
        Integer id=getResources().getIdentifier(name.toLowerCase(),"raw",getPackageName());
        //Toast.makeText(getApplicationContext(),"Playing " + name + ".mp3 file...", Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();
        try{
            mediaPlayer = MediaPlayer.create(getApplicationContext(),id);
        }
        catch(Exception e) {
            Toast.makeText(getApplicationContext(), "Missing in my Vocabulary!", Toast.LENGTH_LONG).show();
        }

        mediaPlayer.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_feedback, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.feedbackID) {
            //Toast.makeText(getApplicationContext(), "True", Toast.LENGTH_LONG).show();
            StringBuilder body = new StringBuilder();
            body.append("Hello Team HithAM, \n \n");
            body.append("I have used your app \"Kannada Kali\"\n\n");
            body.append("********Please fill in your feedback/grievances here********\n");
            body.append("\n Regards, \n");
            body.append("Kannada Kali User");
            String company[] = {"varunvgnc@gmail.com","aggithaya@gmail.com"};
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "", null));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Kannada Kali user wants to cantact you");
            intent.putExtra(Intent.EXTRA_EMAIL, company);
            intent.putExtra(Intent.EXTRA_TEXT, body.toString());
            startActivity(intent);
            return true;
        }
        else if (item.getItemId() == R.id.settingsID)
        {
            infoBuilder.setTitle("Settings");
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.settings_dialog, null);
            infoBuilder.setView(dialogView);
            infoBuilder.create().show();
        }
        return super.onOptionsItemSelected(item);
    }
}

package app.learnkannada.com.learnkannadakannadakali;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.onesignal.OneSignal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class ChooseCourseActivity extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT = 100;

    private Button dayCourse, flexiCourse;
    private ImageView mic;

    private MediaPlayer mediaPlayer;
    private AlertDialog.Builder builder, infoBuilder, infoProvider;
    private AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_course);

        getSupportActionBar().setTitle("Kannada Kali");

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        dayCourse = (Button) findViewById(R.id.dayCourseID);
        flexiCourse = (Button) findViewById(R.id.comfortCourseID);
        mic = (ImageView) findViewById(R.id.micID);

        builder = new AlertDialog.Builder(this);
        infoBuilder = new AlertDialog.Builder(this);
        infoProvider = new AlertDialog.Builder(this);

        dialog = new AlertDialog.Builder(this);

        dayCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(ChooseCourseActivity.this,CategoryContentActivity.class);
                /*intent.putExtra("category","dayCours");
                intent.putExtra("from","dayCourse");*/
                startActivity(new Intent(ChooseCourseActivity.this, DaysCourseHomeActivity.class));
                finish();
                animateInvisible(flexiCourse);
            }
        });

        flexiCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Coming soon...",Toast.LENGTH_LONG).show();
                startActivity(new Intent(ChooseCourseActivity.this, FlexiCourseHomeActivity.class));
                finish();
                animateInvisible(dayCourse);
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

    private void animateInvisible(final View myView) {
        // previously visible view

// get the center for the clipping circle
        int cx = myView.getWidth() / 2;
        int cy = myView.getHeight() / 2;

// get the initial radius for the clipping circle
        float initialRadius = (float) Math.hypot(cx, cy);

// create the animation (the final radius is zero)
        Animator anim =
                null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);
        }

// make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                myView.setVisibility(View.INVISIBLE);
            }
        });

// start the animation
        anim.start();
    }

    /**
     * Showing google speech input dialog
     */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Missing in my Vocabulary!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     */
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
        final String spokenString = s.replaceAll(" ", "_")
                .toLowerCase();
        String spokenStringEx = spokenString + "_ex";

        //playing found music file
        try {
            if (resourceAvailable(spokenString))
                playOffline(spokenString);
            else {
                final String[] splitWords = s.split(" ");
                for (int j = 0; j < splitWords.length; j++) {
                    if (resourceAvailable(splitWords[j] + "_ex")) {
                        //Toast.makeText(getApplicationContext(),"Found " + splitWords[j] + " at " + j, Toast.LENGTH_LONG).show();
                        final int finalJ = j;
                        infoBuilder.setTitle("Oops! I am too young for sentences. But wait!")
                                .setMessage("\nI can give you different example for \n\"" + splitWords[j].toUpperCase()
                                        + "\"\n")
                                .setPositiveButton("Show me", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent exIntent = new Intent(ChooseCourseActivity.this, ExampleActivity.class);
                                        exIntent.putExtra("name", splitWords[finalJ]);
                                        startActivity(exIntent);
                                    }
                                })
                                .setIcon(R.drawable.idea)
                                .setCancelable(false)
                                .setNegativeButton("No", null)
                                .create().show();
                        return;
                    }
                }
                if (splitWords.length > 1)
                    Toast.makeText(getApplicationContext(), "Oops! I am too young for sentences.", Toast.LENGTH_LONG).show();
                else {
                    //Toast.makeText(getApplicationContext(),"Sorry! I don't know that yet.", Toast.LENGTH_LONG).show();
                    infoProvider.setTitle("Sorry! I don't know that yet.")
                            .setMessage(" Would you like to add \"" + spokenString.toUpperCase() + "\" to my vocabulary?")
                            .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    StringBuilder body = new StringBuilder();
                                    body.append("Hello Team HithAM, \n \n");
                                    body.append("I found that the word \"" + spokenString.toUpperCase() + "\" is missing in your app vocabulary and it would be helpful to all if this word is added to it.\n\n");
                                    body.append("\n Regards, \n");
                                    body.append("Kannada Kali User");
                                    String company[] = {"hithamcreations@gmail.com"};
                                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "", null));
                                    intent.putExtra(Intent.EXTRA_SUBJECT, "Kannada Kali user wants to cantact you");
                                    intent.putExtra(Intent.EXTRA_EMAIL, company);
                                    intent.putExtra(Intent.EXTRA_TEXT, body.toString());
                                    startActivity(intent);
                                }
                            })
                            .setIcon(R.drawable.idea).setCancelable(true).create().show();
                }
            }
            if (resourceAvailable(spokenStringEx)) {
                builder.setTitle("Yay! Example found for \"" + spokenString.replaceAll("_", " ") + "\"")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent exIntent = new Intent(ChooseCourseActivity.this, ExampleActivity.class);
                                exIntent.putExtra("name", spokenString.replaceAll("_", " "));
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
    private boolean resourceAvailable(String spokenStringEx) {
        Integer id = getResources().getIdentifier(spokenStringEx.toLowerCase(), "raw", getPackageName());
        if (id > 0)
            return true;
        else
            return false;
    }

    private void playOffline(String name) throws IOException {
        // Toast.makeText(context,"playing...",Toast.LENGTH_SHORT).show();
        mediaPlayer = new MediaPlayer();
        Integer id = getResources().getIdentifier(name.toLowerCase(), "raw", getPackageName());
        //Toast.makeText(getApplicationContext(),"Playing " + name + ".mp3 file...", Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();
        try {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), id);
        } catch (Exception e) {
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

        /*if (item.getItemId() == R.id.feedbackID) {
            //Toast.makeText(getApplicationContext(), "True", Toast.LENGTH_LONG).show();
            StringBuilder body = new StringBuilder();
            body.append("Hello Team HithAM, \n \n");
            body.append("I have used your app \"Kannada Kali\"\n\n");
            body.append("********Please fill in your feedback/grievances here********\n");
            body.append("\n Regards, \n");
            body.append("Kannada Kali User");
            String company[] = {"hithamcreations@gmail.com"};
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "", null));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Kannada Kali user wants to cantact you");
            intent.putExtra(Intent.EXTRA_EMAIL, company);
            intent.putExtra(Intent.EXTRA_TEXT, body.toString());
            startActivity(intent);
            return true;
        }*/
       /* else if (item.getItemId() == R.id.settingsID)
        {
            infoBuilder.setTitle("Settings");
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.settings_dialog, null);
            infoBuilder.setView(dialogView);
            infoBuilder.create().show();
        }*/
        if (item.getItemId() == R.id.rateID) {
            Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            // To count with Play market backstack, After pressing back button,
            // to taken back to our application, we need to add following flags to intent.
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
            }
        } else if (item.getItemId() == R.id.shareAppID) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, ("Learn \"Spoken Kannada\" in just 10 days!\n" +
                    "Use helpful and handy feature Instant Words Translation\n" +
                    "\n\nDownload Kannada Kali app now!\n" +
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())
                    + "\n\nTrailer here:\n" + Uri.parse("https://www.youtube.com/watch?v=dTOBnFx4Kvc")));
            sendIntent.setType("text/plain");
            sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            startActivity(sendIntent);
        } else if (item.getItemId() == R.id.moreID) {
            item.getSubMenu().clear();
            getMenuInflater().inflate(R.menu.menu_more, item.getSubMenu());
        } /*else if (item.getItemId() == R.id.aboutUsID) {
            Toast.makeText(getApplicationContext(), "About us selected", Toast.LENGTH_SHORT).show();
        }*/
        else if (item.getItemId() == R.id.aboutAppID)
        {
          dialog.setTitle("About the app")
                  .setMessage("-- Kannada Kali app is being developed by \"HithAM Creations\", a team of two passionate developers." +
                          "\n-- The app is exclusively developed for people who want to learn \"Spoken Kannada\"" +
                          " on the go.\n" +
                          "-- We are working really hard to add more and more exciting features to the app." +
                          "\n-- We request all the users to spend some time on the app and let us know your expectations." +
                          "\n-- Please help us improve this app by providing your valuable suggestions.")
                  .setPositiveButton("Feedback", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialogInterface, int i) {
                          StringBuilder body = new StringBuilder();
                          body.append("Hello Team HithAM, \n \n");
                          body.append("I have used your app \"Kannada Kali\"\n\n");
                          body.append("********Please fill in your feedback/grievances here********\n");
                          body.append("\n Regards, \n");
                          body.append("Kannada Kali User");
                          String company[] = {"hithamcreations@gmail.com"};
                          Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "", null));
                          intent.putExtra(Intent.EXTRA_SUBJECT, "Kannada Kali user wants to cantact you");
                          intent.putExtra(Intent.EXTRA_EMAIL, company);
                          intent.putExtra(Intent.EXTRA_TEXT, body.toString());
                          startActivity(intent);
                      }
                  })
                  .setNegativeButton("Rate app", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialogInterface, int i) {
                          Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
                          Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                          // To count with Play market backstack, After pressing back button,
                          // to taken back to our application, we need to add following flags to intent.
                          goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                  Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                                  Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                          try {
                              startActivity(goToMarket);
                          } catch (ActivityNotFoundException e) {
                              startActivity(new Intent(Intent.ACTION_VIEW,
                                      Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
                          }
                      }
                  })
                  .setIcon(R.drawable.alphabets)
                  .setCancelable(true)
                  .create().show();
        } else if (item.getItemId() == R.id.disclaimerID) {
            dialog.setTitle("Disclaimer")
                    .setMessage("-- This is a FREE app."
                            +"\n-- \"10 Days Course\" has been designed to help people learn \"Basic Spoken " +
                            "Kannada\". This does not guarantee the complete learning of Kannada language.\n"
                         +   "-- Translations given at some places in the app are not in pure Kannada."
                    + "\n-- We have used English words at many places to keep the learning simple and appealing.\n" +
                            "This attempt should not be mistaken as disrespect to Kannada language." +
                            "\n-- HithAM Creations is not responsible for any damage caused due to misuse of this " +
                            "application. The app is published for the sole purpose of helping people learn " +
                            "spoken Kannada only.")
                    .setIcon(R.drawable.ic_error_black_24dp)
                    .setCancelable(true)
                    .setPositiveButton(null,null)
                    .setNegativeButton(null,null)
                    .create().show();
        } /*else if (item.getItemId() == R.id.aboutKannadaID) {
            Toast.makeText(getApplicationContext(), "About Kannada selected", Toast.LENGTH_SHORT).show();
        }*/
        else if(item.getItemId() == R.id.thumbRuleID)
        {
            dialog.setTitle("Thumb Rule")
                    .setMessage("-- Always try to translate given English sentences to your mother tongue first and " +
                            "then to Kannada. \n--This helps you understand and learn kannada faster!" )
                    .setCancelable(true)
                    .setPositiveButton(null,null)
                    .setNegativeButton(null,null)
                    .setIcon(R.drawable.ic_thumb_up_black_24dp)
                    .create().show();
        }
        return super.onOptionsItemSelected(item);
    }
}

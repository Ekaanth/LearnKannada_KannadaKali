package app.learnkannada.com.learnkannadakannadakali;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.onesignal.OneSignal;

import java.util.ArrayList;
import java.util.Locale;

import animation.AnimateVisibility;
import constants.Constants;
import utils.AudioPlayer;
import utils.FindResource;

public class ChooseCourseActivity extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT = 100;

    private Button dayCourse, flexiCourse;
    private ImageView mic;

    private MediaPlayer mediaPlayer;
    private AlertDialog.Builder builder, infoBuilder, infoProvider;
    private AlertDialog.Builder dialog;

    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_drawer);

        //getSupportActionBar().setTitle(Constants.KANNADA_KALI);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBarID);
        toolbar.setTitle("Kannada Kali");
        setSupportActionBar(toolbar);

        OneSignal.startInit(this)
                /*.setNotificationOpenedHandler(new NotificationOpenedHandler())*/
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                /*.setNotificationReceivedHandler(new NotificationReceivedHandler())*/
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayoutID);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationID);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
                    Toast.makeText(getApplicationContext(),"Please review us on Play-Store",Toast.LENGTH_SHORT).show();
                    return true;
                }
                else if (item.getItemId() == R.id.shareAppID) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, ("Hey there, \n\n Check out this nifty app which can help you ace spoken " +
                            "Kannada in just 10 days. Get Instant translation to any english word using \"Instant " +
                            "Word Translation\" feature. \nYou can even have a chat with the developers and discuss on your ideas " +
                            "in making this app better and better. Why don\'t you give it a try?" +
                            "\n\nDownload Kannada Kali app now!\n" +
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())
                            + "\n\nTrailer here:\n" + Uri.parse("https://www.youtube.com/watch?v=dTOBnFx4Kvc")));
                    sendIntent.setType("text/plain");
                    sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    startActivity(sendIntent);
                    return true;
                }
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
                                    body.append(R.string.KANNADA_KALI_USER);
                                    String company[] = {Constants.HITHAM_EMAIL};
                                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "", null));
                                    intent.putExtra(Intent.EXTRA_SUBJECT, R.string.WANTS_TO_CONTACT);
                                    intent.putExtra(Intent.EXTRA_EMAIL, company);
                                    intent.putExtra(Intent.EXTRA_TEXT, body.toString());
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton(Constants.RATE_APP, new DialogInterface.OnClickListener() {
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
                                    Toast.makeText(getApplicationContext(),"Please review us on Play-Store",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setIcon(R.drawable.alphabets)
                            .setCancelable(true)
                            .create().show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
                else if (item.getItemId() == R.id.aboutUsID)
                {
                    startActivity(new Intent(ChooseCourseActivity.this,AboutusActivity.class));
                    finish();
                    return true;
                }
                else if (item.getItemId() == R.id.disclaimerID) {
                    dialog.setTitle("Disclaimer")
                            .setMessage("-- This is a FREE app."
                                    +"\n-- \"10 Days Course\" has been designed to help people learn \"Basic Spoken " +
                                    "Kannada\". This does not guarantee the complete learning of Kannada language.\n"
                                    +   "-- Translations given at some places in the app are not in pure Kannada."
                                    + "\n-- We have used English words at many places to keep the learning simple and appealing.\n" +
                                    "This attempt should not be mistaken as disrespect to Kannada language." +
                                    "\n-- Individual names used in the app are used " +
                                    "for illustration purposes ONLY. " +
                                    "If anybody's name is matching with the names used in the app, it's " +
                                    "purely coincidental." +
                                    "\n-- HithAM Creations is not responsible for any damage caused due to misuse of this " +
                                    "application. The app is published for the sole purpose of helping people learn " +
                                    "spoken Kannada only.")
                            .setIcon(R.drawable.ic_error_black_24dp)
                            .setCancelable(true)
                            .setPositiveButton(null,null)
                            .setNegativeButton(null,null)
                            .create().show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
                else if(item.getItemId() == R.id.thumbRuleID)
                {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    dialog.setTitle(Constants.THUMB_RULE)
                            .setMessage("-- Always try to translate given English sentences to your mother tongue first and " +
                                    "then to Kannada. \n--This helps you understand and learn kannada faster!" )
                            .setCancelable(true)
                            .setPositiveButton(null,null)
                            .setNegativeButton(null,null)
                            .setIcon(R.drawable.ic_thumb_up_black_24dp)
                            .create().show();
                    return true;
                }
                else if (item.getItemId() == R.id.facebookID)
                {
                    Intent facebookAppIntent;
                    try {
                        facebookAppIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/918533634976014"));
                        startActivity(facebookAppIntent);
                    } catch (ActivityNotFoundException e) {
                        facebookAppIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://facebook.com/learnkannada.kannadakali"));
                        startActivity(facebookAppIntent);
                    }
                    Toast.makeText(getApplicationContext(),"Please follow and share our page",Toast.LENGTH_LONG).show();
                    return true;
                }
                else
                    return onOptionsItemSelected(item);
            }
        });

        View headerview = navigationView.getHeaderView(0);
        TextView version = (TextView) headerview.findViewById(R.id.versionID);
        try {
            PackageInfo packageInfo = this.getPackageManager().getPackageInfo(getPackageName(),0);
            String currentVersion = "v"+packageInfo.versionName;
            version.setText(currentVersion);
        } catch (PackageManager.NameNotFoundException e) {
            version.setText("Smartapp");
            e.printStackTrace();
        }

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
                AnimateVisibility.animateInvisible(flexiCourse);
            }
        });

        flexiCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Coming soon...",Toast.LENGTH_LONG).show();
                startActivity(new Intent(ChooseCourseActivity.this, FlexiCourseHomeActivity.class));
                finish();
                AnimateVisibility.animateInvisible(dayCourse);
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayoutID);
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
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
            Toast.makeText(getApplicationContext(), R.string.missing_in_voc, Toast.LENGTH_SHORT).show();
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
        /*if( s.equals(Constants.DO)||s.equals(Constants.THIS))
            s=s+"_";*/
        final String spokenString = FindResource.processStringName(s).replaceAll(" ", "_")
                .toLowerCase();
        String spokenStringEx = spokenString + Constants._EX;

        //playing found music file
        if (FindResource.rawResourceAvailable(getApplicationContext(), spokenString))
            AudioPlayer.playAudio(getApplicationContext(),spokenString);
        else {
            final String[] splitWords = s.split(" ");
            for (int j = 0; j < splitWords.length; j++) {
                if (FindResource.rawResourceAvailable(getApplicationContext(), splitWords[j] + "_ex")) {
                    //Toast.makeText(getApplicationContext(),"Found " + splitWords[j] + " at " + j, Toast.LENGTH_LONG).show();
                    final int finalJ = j;
                    infoBuilder.setTitle(R.string.too_young)
                            .setMessage("\n"+"I can give you different example for "+"\n\"" + splitWords[j].toUpperCase()
                                    + "\"\n")
                            .setPositiveButton(R.string.SHOW_ME, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent exIntent = new Intent(ChooseCourseActivity.this, ExampleActivity.class);
                                    exIntent.putExtra(Constants.NAME, splitWords[finalJ]);
                                    startActivity(exIntent);
                                }
                            })
                            .setIcon(R.drawable.idea)
                            .setCancelable(false)
                            .setNegativeButton(Constants.NO, null)
                            .create().show();
                    return;
                }
            }
            if (splitWords.length > 1)
                Toast.makeText(getApplicationContext(), R.string.too_young, Toast.LENGTH_LONG).show();
            else {
                //Toast.makeText(getApplicationContext(),"Sorry! I don't know that yet.", Toast.LENGTH_LONG).show();
                infoProvider.setTitle(R.string.sorry_idk_that_yet)
                        .setMessage(" Would you like to add \"" + spokenString.toUpperCase() + "\" to my vocabulary?")
                        .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                StringBuilder body = new StringBuilder();
                                body.append("Hello Team HithAM, \n \n");
                                body.append("I found that the word \"" + spokenString.toUpperCase() + "\" is missing in your app vocabulary and it would be helpful to all if this word is added to it.\n\n");
                                body.append("\n Regards, \n");
                                body.append(getResources().getString(R.string.KANNADA_KALI_USER));
                                String company[] = {Constants.HITHAM_EMAIL};
                                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "", null));
                                intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.WANTS_TO_CONTACT));
                                intent.putExtra(Intent.EXTRA_EMAIL, company);
                                intent.putExtra(Intent.EXTRA_TEXT, body.toString());
                                startActivity(intent);
                            }
                        })
                        .setIcon(R.drawable.idea).setCancelable(true).create().show();
            }
        }
        if (FindResource.rawResourceAvailable(getApplicationContext(), spokenStringEx)) {
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
            }).setMessage(R.string.WOULD_YOU_LIKE_TO_CHECK)
                    .create().show();

            //Toast.makeText(getApplicationContext(),"Found",Toast.LENGTH_LONG).show();
        }
    }

    /*private void playOffline(String name) throws IOException {
        // Toast.makeText(context,"playing...",Toast.LENGTH_SHORT).show();
        mediaPlayer = new MediaPlayer();
        Integer id = getResources().getIdentifier(name.toLowerCase(), "raw", getPackageName());
        //Toast.makeText(getApplicationContext(),"Playing " + name + ".mp3 file...", Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();
        try {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), id);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), R.string.missing_in_voc, Toast.LENGTH_LONG).show();
        }

        mediaPlayer.start();
    }*/

}

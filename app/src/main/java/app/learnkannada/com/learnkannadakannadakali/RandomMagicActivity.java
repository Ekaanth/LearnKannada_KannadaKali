package app.learnkannada.com.learnkannadakannadakali;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import Animation.AnimateVisibility;
import Utils.AudioPlayer;
import Utils.FindResource;

public class RandomMagicActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private CardView rootVerbCard, presentCard, pastCard, futureCard, pastContcard, presentContCard, futureContCard;
    private Button randomVerb;
    private TextView presentWord, pastWord, futureWord, pastContWord, presentContWord, futureContWord, rootVerbEng, rootVerbKan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_magic);

        getSupportActionBar().setTitle("Random Magic");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        builder = new AlertDialog.Builder(this);
        randomVerb = (Button) findViewById(R.id.randomVerbID);
        presentContWord = (TextView) findViewById(R.id.presentContWordID);
        pastContWord = (TextView) findViewById(R.id.pastContWordID);
        futureContWord = (TextView) findViewById(R.id.futureContWordID);
        pastWord = (TextView) findViewById(R.id.pastWordID);
        futureWord = (TextView) findViewById(R.id.futureWordID);
        rootVerbEng = (TextView) findViewById(R.id.rootVerbEngID);
        rootVerbKan = (TextView) findViewById(R.id.rootVerbKanID);
        presentWord = (TextView) findViewById(R.id.presentWordID);

        rootVerbCard = (CardView) findViewById(R.id.rootVerbID);
        presentCard = (CardView) findViewById(R.id.presentID);
        pastCard = (CardView) findViewById(R.id.pastID);
        presentContCard = (CardView) findViewById(R.id.presentContinuousID);
        pastContcard = (CardView) findViewById(R.id.pastContinuousID);
        futureCard = (CardView) findViewById(R.id.futureID);
        futureContCard = (CardView) findViewById(R.id.futureContinuousID);

        String[] randomVerbs = getResources().getStringArray(R.array.randomVerbs);
        String[] randomVerbsKan = getResources().getStringArray(R.array.randomVerbs_kan);
        String[] pastVerbs = getResources().getStringArray(R.array.past_kan);
        String[] presentVerbs = getResources().getStringArray(R.array.present_kan);
        String[] futureVerbs = getResources().getStringArray(R.array.future_kan);
        String[] pastContVerbs = getResources().getStringArray(R.array.pastCont_kan);
        final String[] presentContVerbs = getResources().getStringArray(R.array.presentCont_kan);
        final String[] futureContVerbs = getResources().getStringArray(R.array.futureCont_kan);

        final List<String> randomVerbsList = new ArrayList<>(Arrays.asList(randomVerbs));
        final List<String> randomVerbsList_kan = new ArrayList<>(Arrays.asList(randomVerbsKan));
        final List<String> pastVerbsList = new ArrayList<>(Arrays.asList(pastVerbs));
        final List<String> presentVerbsList = new ArrayList<>(Arrays.asList(presentVerbs));
        final List<String> futureVerbsList = new ArrayList<>(Arrays.asList(futureVerbs));
        final List<String> pastContVerbsList = new ArrayList<>(Arrays.asList(pastContVerbs));
        final List<String> presentContVerbsList = new ArrayList<>(Arrays.asList(presentContVerbs));
        final List<String> futureContVerbsList = new ArrayList<>(Arrays.asList(futureContVerbs));

        final Random random = new Random();

        randomVerb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (randomVerbsList.size() != 0) {

                    //below structure is to help in future if animation needs to be done in order.
                    //if we need things to get animated one after the other, then duration sent may have
                    //to be modified as below.
                /*animateAfterDelay(200+100, pastCard);
                animateAfterDelay(200+200, presentCard);
                animateAfterDelay(200+300, futureCard);
                animateAfterDelay(200+400, pastContcard);
                animateAfterDelay(200+500, presentContCard);
                animateAfterDelay(200+600, futureContCard);*/

                    //adding 200 to separate root verb and other animations.
                    /*animateAfterDelay(200 + 100, pastCard);
                    animateAfterDelay(200 + 100, presentCard);
                    animateAfterDelay(200 + 100, futureCard);
                    animateAfterDelay(200 + 100, pastContcard);
                    animateAfterDelay(200 + 100, presentContCard);
                    animateAfterDelay(200 + 100, futureContCard);*/

                    int randomInt = random.nextInt(randomVerbsList.size());
                    AnimateVisibility.animateVisible(rootVerbCard);
                    //Toast.makeText(getApplicationContext(),randomVerbs[randomInt],Toast.LENGTH_SHORT).show();
                    rootVerbEng.setText(randomVerbsList.get(randomInt));
                    rootVerbKan.setText(randomVerbsList_kan.get(randomInt));
                    pastWord.setText(pastVerbsList.get(randomInt));
                    presentWord.setText(presentVerbsList.get(randomInt));
                    futureWord.setText(futureVerbsList.get(randomInt));
                    pastContWord.setText(pastContVerbsList.get(randomInt));
                    presentContWord.setText(presentContVerbsList.get(randomInt));
                    futureContWord.setText(futureContVerbsList.get(randomInt));

                    randomVerbsList.remove(randomInt);
                    randomVerbsList_kan.remove(randomInt);
                    pastVerbsList.remove(randomInt);
                    presentVerbsList.remove(randomInt);
                    futureVerbsList.remove(randomInt);
                    pastContVerbsList.remove(randomInt);
                    presentContVerbsList.remove(randomInt);
                    futureContVerbsList.remove(randomInt);

                } else {
                    builder.setTitle("Congratulations!")
                            .setMessage("You have successfully learned all the available " +
                                    "verbs. \n\nStay tuned for more!")
                            .setPositiveButton("Start over", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(RandomMagicActivity.this, RandomMagicActivity.class));
                                    finish();
                                }
                            })
                            .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(RandomMagicActivity.this, FlexiCourseHomeActivity.class));
                                    finish();
                                }
                            })
                            .setIcon(R.drawable.random)
                            .create().show();
                }

                presentCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String resourceName = presentWord.getText().toString().replaceAll(" ","_").toLowerCase();
                        if(FindResource.resourceAvailable(getApplicationContext(),resourceName))
                            AudioPlayer.playAudio(getApplicationContext(),resourceName);
                        else
                            Toast.makeText(getApplicationContext(),resourceName,Toast.LENGTH_SHORT).show();
                    }
                });

                pastCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String resourceName = pastWord.getText().toString().replaceAll(" ","_").toLowerCase();
                        if(FindResource.resourceAvailable(getApplicationContext(),resourceName))
                            AudioPlayer.playAudio(getApplicationContext(),resourceName);
                        else
                            Toast.makeText(getApplicationContext(),resourceName,Toast.LENGTH_SHORT).show();
                    }
                });

                futureCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String resourceName = futureWord.getText().toString().replaceAll(" ","_").toLowerCase();
                        if(FindResource.resourceAvailable(getApplicationContext(),resourceName))
                            AudioPlayer.playAudio(getApplicationContext(),resourceName);
                        else
                            Toast.makeText(getApplicationContext(),resourceName,Toast.LENGTH_SHORT).show();
                    }
                });

                pastContcard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String resourceName = pastContWord.getText().toString().replaceAll(" ","_").toLowerCase();
                        if(FindResource.resourceAvailable(getApplicationContext(),resourceName))
                            AudioPlayer.playAudio(getApplicationContext(),resourceName);
                        else
                            Toast.makeText(getApplicationContext(),resourceName,Toast.LENGTH_SHORT).show();
                    }
                });

                presentContCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String resourceName = presentContWord.getText().toString().replaceAll(" ","_").toLowerCase();
                        if(FindResource.resourceAvailable(getApplicationContext(),resourceName))
                            AudioPlayer.playAudio(getApplicationContext(),resourceName);
                        else
                            Toast.makeText(getApplicationContext(),resourceName,Toast.LENGTH_SHORT).show();
                    }
                });

                futureContCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String resourceName = futureContWord.getText().toString().replaceAll(" ","_").toLowerCase();
                        if(FindResource.resourceAvailable(getApplicationContext(),resourceName))
                            AudioPlayer.playAudio(getApplicationContext(),resourceName);
                        else
                            Toast.makeText(getApplicationContext(),resourceName,Toast.LENGTH_SHORT).show();
                    }
                });

                rootVerbCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String resourceName = rootVerbEng.getText().toString().replaceAll(" ","_").toLowerCase();
                        if(resourceName.equals("do"))
                            resourceName+="_";
                        if(FindResource.resourceAvailable(getApplicationContext(),resourceName))
                            AudioPlayer.playAudio(getApplicationContext(),resourceName);
                        else
                            Toast.makeText(getApplicationContext(),resourceName,Toast.LENGTH_SHORT).show();
                    }
                });
            }
            /*private void animateAfterDelay(int i, final View myView) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AnimateVisibility.animateVisible(myView);
                    }
                }, i);
            }*/
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_random, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.whatsthisID) {
            builder.setTitle("Random magic")
                    .setIcon(R.drawable.random)
                    .setMessage(R.string.randomMagicDialogMessage)
                    .setPositiveButton("Cool", null)
                    .create().show();
        }
        else if(item.getItemId() == R.id.randomMagicDisclaimer)
        {
            builder.setTitle("Disclaimer")
                    .setMessage("All the verbs written here are with respect to \"First Person\" only." +
                            "\nThey get changed a little for Second and Third person (coming soon).")
                    .setIcon(R.drawable.ic_error_black_24dp)
                    .setPositiveButton("Ok",null)
                    .create().show();
        }
        else if (item.getItemId() == R.id.randomMagicSuggestVerbID)
        {
            StringBuilder body = new StringBuilder();
            body.append("Hello Team HithAM, \n \n");
            body.append("I think it would be helpful if you can add the below verb(s) and it\'s tenses to this section\n\n");
            body.append("/* verbs here */ \n");
            body.append("\n Regards, \n");
            body.append("Kannada Kali User");
            String company[] = {"hithamcreations@gmail.com"};
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "", null));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Kannada Kali user wants to cantact you");
            intent.putExtra(Intent.EXTRA_EMAIL, company);
            intent.putExtra(Intent.EXTRA_TEXT, body.toString());
            startActivity(intent);
        }
        else if(item.getItemId() == android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RandomMagicActivity.this, FlexiCourseHomeActivity.class));
        finish();
    }
}

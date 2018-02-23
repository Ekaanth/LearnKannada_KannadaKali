package app.learnkannada.com.learnkannadakannadakali;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import utils.AudioPlayer;

public class ExampleActivity extends AppCompatActivity {

    private TextView example, wordInEng, wordInKan;
    private ImageView speaker;

    private String spokenWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        example = (TextView) findViewById(R.id.exampleText);
        wordInEng = (TextView) findViewById(R.id.wordInEngID);
        wordInKan = (TextView) findViewById(R.id.wordInKanID);

        speaker = (ImageView) findViewById(R.id.speakerID);

        Intent i = getIntent();

        //receiving content in english as name
        String name = i.getStringExtra("name");
        String kName = i.getStringExtra("kName");


        wordInEng.setText(name);
        wordInKan.setText(kName);

        //setting day in actionbar
        getSupportActionBar().setTitle("\"" + name + "\"" + " example");

        //replacing whitespaces with "_" and saving it
        //RULE: STRINGS.XML SHOULD HAVE THE VALUES IN THE SAME FORMAT
        //Ex: Value = "For Me" --> String.xml = "For_Me_ex" which should contain both kan and english words.
        String exampleText = name.replaceAll(" ", "_").replaceAll("\\?","")
                .replaceAll("\\(","_").replaceAll("\\)","")+ "_ex";

        try {
            example.setText(getResources().getIdentifier(exampleText.toLowerCase(), "string", getPackageName()));
        }catch(Exception e)
        {
            throw new IllegalStateException("\nERROR! Example Text NOT FOUND _for the word " + name.toUpperCase(),e);
        }
        //exampleKan.setText(getResources().getIdentifier(nameInKan,"string",getPackageName()));

        //spokenWord to keep the name of relevant mp3 file
        //RULE: mp3 file must be saved in the same format
        //Ex: Value = "For Us" --> mp3 file = for_us_ex.mp3
        //spokenWord = nameInKan;
        spokenWord = name.replaceAll(" ", "_").replaceAll("\\?","").toLowerCase()
                .replaceAll("\\(","_")
                .replaceAll("\\)","")+ "_ex";

        speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    AudioPlayer.playAudio(getApplicationContext(),spokenWord);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(AudioPlayer.mediaPlayer!=null) {
            if(AudioPlayer.mediaPlayer.isPlaying())
            {
                AudioPlayer.mediaPlayer.stop();
            AudioPlayer.mediaPlayer.release();
            AudioPlayer.mediaPlayer = null;
            }
        }
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            onBackPressed();
            return  true;
        }
        else
            return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        if(AudioPlayer.mediaPlayer!=null) {
            if(AudioPlayer.mediaPlayer.isPlaying())
            {
                AudioPlayer.mediaPlayer.stop();
                AudioPlayer.mediaPlayer.release();
                AudioPlayer.mediaPlayer = null;
            }
        }
        super.onPause();
    }
}

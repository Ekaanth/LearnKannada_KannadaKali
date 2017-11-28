package app.learnkannada.com.learnkannadakannadakali;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class ExampleActivity extends AppCompatActivity {

    private TextView example, wordInEng, wordInKan;
    private ImageView speaker;

    private MediaPlayer mediaPlayer;
    private String spokenWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        example = (TextView) findViewById(R.id.exampleText);
        //exampleKan = (TextView) findViewById(R.id.exampleInKanID);
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

        //replacing whitespaces with "_" and saving it as nameInEng and nameInKan
        //RULE: STRINGS.XML SHOULD HAVE THE VALUES IN THE SAME FORMAT
        //Ex: Value = "For Me" --> String.xml = "For_Me_ex_inEng/inKan
        String exampleText = name.replaceAll(" ", "_").replaceAll("\\?","")
                .replaceAll("\\(","_").replaceAll("\\)","")+ "_ex";
                //.replaceAll("\\)","")+ "_ex_inEng";
        /*final String nameInKan = name.replaceAll(" ","_")
                .replaceAll("\\(","_")
                .replaceAll("\\)","")+ "_ex_inKan";*/

        /*Toast.makeText(getApplicationContext(),nameInEng + nameInKan, Toast.LENGTH_LONG).show();*/

        example.setText(getResources().getIdentifier(exampleText.toLowerCase(),"string",getPackageName()));
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
                //Toast.makeText(getApplicationContext(),spokenWord,Toast.LENGTH_LONG).show();
                try {
                    playOffline(spokenWord);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void playOffline(String name) throws IOException {
        // Toast.makeText(context,"playing...",Toast.LENGTH_SHORT).show();
        mediaPlayer = new MediaPlayer();
        Integer id=getResources().getIdentifier(name.toLowerCase(),"raw",getPackageName());
        //Toast.makeText(getApplicationContext(),"Playing " + name + ".mp3 file...", Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();
        mediaPlayer = MediaPlayer.create(getApplicationContext(),id);
        mediaPlayer.start();
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
}

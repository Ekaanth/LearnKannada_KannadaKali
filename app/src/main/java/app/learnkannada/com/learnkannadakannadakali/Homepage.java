package app.learnkannada.com.learnkannadakannadakali;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Locale;

public class Homepage extends AppCompatActivity {

    private Button words, conversations;
    private ImageView mic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        words = (Button) findViewById(R.id.wordsID);
        conversations = (Button) findViewById(R.id.conversationsID);
        mic = (ImageView) findViewById(R.id.micID);

        words.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Homepage.this, MainActivity.class));

            }
        });

        conversations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Coming soon...",Toast.LENGTH_LONG).show();
            }
        });

    }
}

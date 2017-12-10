package app.learnkannada.com.learnkannadakannadakali;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Homepage extends AppCompatActivity {

    private Button words, conversations, randomMagic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        AppRater.app_launched(this);

        words = (Button) findViewById(R.id.wordsID);
        conversations = (Button) findViewById(R.id.conversationsID);
        randomMagic = (Button) findViewById(R.id.randomVerbsID);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        words.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Homepage.this, MainActivity.class);
                i.putExtra("from","words");
                startActivity(i);

            }
        });

        conversations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Homepage.this, MainActivity.class);
                i.putExtra("from","conversations");
                startActivity(i);
            }
        });

        randomMagic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Coming soon...", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            onBackPressed();
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }
}

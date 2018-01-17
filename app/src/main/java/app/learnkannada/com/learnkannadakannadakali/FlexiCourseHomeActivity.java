package app.learnkannada.com.learnkannadakannadakali;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;

import constants.Constants;
import sharedPreferences.AppRater;
import sharedPreferences.ReferFriends;

public class FlexiCourseHomeActivity extends AppCompatActivity {

    private CardView words, conversations, randomMagic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexi_course_home);

        AppRater.app_launched(this);

        words = (CardView) findViewById(R.id.wordsID);
        conversations = (CardView) findViewById(R.id.conversationsID);
        randomMagic = (CardView) findViewById(R.id.randomMagicID);

        AppRater.app_launched(this);
        ReferFriends.app_launched(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(Constants.FLEXI_COURSE);


        words.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FlexiCourseHomeActivity.this, CategoriesActivity.class);
                i.putExtra(Constants.FROM,Constants.WORDS);
                startActivity(i);
                finish();

            }
        });

        conversations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FlexiCourseHomeActivity.this, CategoriesActivity.class);
                i.putExtra(Constants.FROM,Constants.CONVERSATIONS);
                startActivity(i);
                finish();
            }
        });

        randomMagic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Coming soon...", Toast.LENGTH_LONG).show();
                startActivity(new Intent(FlexiCourseHomeActivity.this, RandomMagicActivity.class));
                finish();
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(FlexiCourseHomeActivity.this, ChooseCourseActivity.class));
        finish();
    }
}

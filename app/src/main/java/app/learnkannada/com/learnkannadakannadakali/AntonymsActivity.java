package app.learnkannada.com.learnkannadakannadakali;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import adapter.ListViewAdapter;
import constants.Constants;

/**
 * Created by raggitha on 06-Feb-18.
 */

public class AntonymsActivity extends AppCompatActivity {

    private List<String> word = new ArrayList<>();
    private List<String> antonym = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_antonym);
        getSupportActionBar().setTitle(Constants.ANTONYMS);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.antonymRecyclerViewID);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String[] wordList = getResources().getStringArray(R.array.pWords_array_Eng);
        String[] antonymsList = getResources().getStringArray(R.array.nWords_array_Eng);

        for (int i = 0; i < wordList.length; i++) {
            word.add(wordList[i]);
            antonym.add(antonymsList[i]);
        }

        RecyclerView.Adapter adapter = new ListViewAdapter(getApplicationContext(), word, Constants.ANTONYMS, antonym);
        recyclerView.setAdapter(adapter);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter(Constants.FROM_ADAPTER));

    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String wordEng = intent.getStringExtra(Constants.WORDS);
            String antonymEng = intent.getStringExtra(Constants.ANTONYMS);
            String wordKan ,antonymKan;

            String[] wordKanArray = getResources().getStringArray(R.array.pWords_array_kan);
            String[] antonymKanArray = getResources().getStringArray(R.array.nWords_array_kan);
            wordKan = wordKanArray[word.indexOf(wordEng)];
            antonymKan = antonymKanArray[antonym.indexOf(antonymEng)];

            AntonymTopFragment fr = (AntonymTopFragment) getFragmentManager().findFragmentById(R.id.fr_id);
            fr.updateValues(wordEng,antonymEng,wordKan,antonymKan);
        }
    };

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
        startActivity(new Intent(AntonymsActivity.this,FlexiCourseHomeActivity.class));
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        finish();
    }
}

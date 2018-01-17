package app.learnkannada.com.learnkannadakannadakali;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import Adapter.ListViewAdapter;
import SharedPreferences.AppRater;
import SharedPreferences.ReferFriends;
import Utils.AudioPlayer;

public class DayActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ListViewAdapter adapter;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewID);

        AppRater.app_launched(this);
        ReferFriends.app_launched(this);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        String day = intent.getStringExtra("position");

        //setting day in actionbar
        if(day.contains("cab"))
            getSupportActionBar().setTitle("Cab/Auto Driver");
        else if(day.contains("newmaid"))
            getSupportActionBar().setTitle("New maid");
        else if(day.contains("regular"))
            getSupportActionBar().setTitle("Regular Maid");
        else if(day.contains("friend"))
            getSupportActionBar().setTitle("Friend");
        else if(day.contains("conductor"))
            getSupportActionBar().setTitle("Bus-conductor");
        else if(day.contains("vendor"))
            getSupportActionBar().setTitle("Vendor");
        else if(day.contains("delivery"))
            getSupportActionBar().setTitle("Delivery boy");
        else if(day.contains("directions"))
            getSupportActionBar().setTitle("Asking directions");
        else
            getSupportActionBar().setTitle(day);

        List<String> input = new ArrayList<>();
        List<String> kanInput = new ArrayList<>();

        String[] listValues = null, listValuesInKan = null;

        Resources res = getResources();

        String englishArray = day.replaceAll(" ","")+"_content";
        String kannadaArray = day.replaceAll(" ","")+"_content_kannada";
        listValues = getResources().getStringArray(getResources().getIdentifier(englishArray,"array",getPackageName()));
        listValuesInKan = getResources().getStringArray(getResources().getIdentifier(kannadaArray,"array",getPackageName()));

        for (int i = 0; i < listValues.length; i++) {
            input.add(listValues[i]);
            kanInput.add(listValuesInKan[i]);
        }

        //below condition is to disable example button for particular cases
        if(day.equals("Day 9") || day.equals("Day 10") || day.equals("Day 8") || !day.contains("Day"))
            adapter = new ListViewAdapter(getApplicationContext(),input, "day89_10Course", kanInput);
        else
            adapter = new ListViewAdapter(getApplicationContext(),input, "dayCourse", kanInput);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recycler, menu);
        MenuItem item = menu.findItem(R.id.searchID);

        searchView = (SearchView) MenuItemCompat.getActionView(item);
        search(searchView);
        return true;
    }
    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
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
        else
            return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(AudioPlayer.mediaPlayer!=null) {
            if(AudioPlayer.mediaPlayer.isPlaying())
            {
                AudioPlayer.mediaPlayer.stop();
                AudioPlayer.mediaPlayer.release();
                AudioPlayer.mediaPlayer = null;
                //Toast.makeText(getApplicationContext(),"killed on back pressed",Toast.LENGTH_SHORT).show();
            }
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        if(AudioPlayer.mediaPlayer!=null) {
            if(AudioPlayer.mediaPlayer.isPlaying())
            {
                AudioPlayer.mediaPlayer.stop();
                AudioPlayer.mediaPlayer.release();
                AudioPlayer.mediaPlayer = null;
                //Toast.makeText(getApplicationContext(),"killed on back pressed",Toast.LENGTH_SHORT).show();
            }
        }
        super.onPause();
    }
}

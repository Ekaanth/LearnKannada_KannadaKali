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

public class DayActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ListViewAdapter adapter;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_home);

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
        getSupportActionBar().setTitle(day);

        List<String> input = new ArrayList<>();
        List<String> kanInput = new ArrayList<>();

        String[] listValues = null, listValuesInKan = null;

        Resources res = getResources();

        String englishArray = day.replaceAll(" ","")+"_content";
        String kannadaArray = day.replaceAll(" ","")+"_content_kannada";
        listValues = getResources().getStringArray(getResources().getIdentifier(englishArray,"array",getPackageName()));
        listValuesInKan = getResources().getStringArray(getResources().getIdentifier(kannadaArray,"array",getPackageName()));


        /*switch(day)
        {
            case "Day 1": {
                listValues = res.getStringArray(R.array.Day1_content);
                listValuesInKan = res.getStringArray(R.array.Day1_content_kannada);
                break;
            }
            case "Day 2": {
                listValues = res.getStringArray(R.array.Day2_content);
                listValuesInKan = res.getStringArray(R.array.Day2_content_kannada);
                break;
            }
            case "Day 3": {
                listValues = res.getStringArray(R.array.Day3_content);
                listValuesInKan = res.getStringArray(R.array.Day3_content_kannada);
                break;
            }
            case "Day 4": {
                listValues = res.getStringArray(R.array.Day4_content);
                listValuesInKan = res.getStringArray(R.array.Day4_content_kannada);
                break;
            }
            case "Day 5": {
                listValues = res.getStringArray(R.array.Day5_content);
                listValuesInKan = res.getStringArray(R.array.Day5_content_kannada);
                break;
            }
            case "Day 6": {
                listValues = res.getStringArray(R.array.Day6_content);
                listValuesInKan = res.getStringArray(R.array.Day6_content_kannada);
                break;
            }
            case "Day 7": {
                listValues = res.getStringArray(R.array.Day7_content);
                listValuesInKan = res.getStringArray(R.array.Day7_content_kannada);
                break;
            }
            case "Day 8": {
                listValues = res.getStringArray(R.array.Day8_content);
                listValuesInKan = res.getStringArray(R.array.Day8_content_kannada);
                break;
            }
            case "Day 9": {
                listValues = res.getStringArray(R.array.Day9_content);
                listValuesInKan = res.getStringArray(R.array.Day9_content_kannada);
                break;
            }
            case "Day 10": {
                listValues = res.getStringArray(R.array.Day10_content);
                listValuesInKan = res.getStringArray(R.array.Day10_content_kannada);
                break;
            }
            default:
                break;
        }
*/

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

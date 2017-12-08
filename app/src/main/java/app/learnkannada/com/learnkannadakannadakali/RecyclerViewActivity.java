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

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ListViewAdapter adapter;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewID);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List<String> input = new ArrayList<>();
        List<String> kanInput = new ArrayList<>();

        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        Resources res = getResources();
        String[] listValues = null, listValuesInKan = null;
        switch (category) {
            case "enquiry":
                break;
            case "numbers":
                listValues = res.getStringArray(R.array.eNumbers_array);
                listValuesInKan = res.getStringArray(R.array.kNumbers_array);
                break;
            case "beginner":
                listValues = res.getStringArray(R.array.beginnerEng_array);
                listValuesInKan = res.getStringArray(R.array.beginnerKan_array);
                break;
            case "greetings":
                listValues = res.getStringArray(R.array.greetingsEng_array);
                listValuesInKan = res.getStringArray(R.array.greetingsKan_array);
                break;
            case "days":
                listValues = res.getStringArray(R.array.daysEng_array);
                listValuesInKan = res.getStringArray(R.array.daysKan_array);
                break;
            case "time":
                listValues = res.getStringArray(R.array.timeEng_array);
                listValuesInKan = res.getStringArray(R.array.timeKan_array);
                break;
            case "relationships":
                listValues = res.getStringArray(R.array.relationshipsEng_array);
                listValuesInKan = res.getStringArray(R.array.relationshipsKan_array);
                break;
            case "vegetables":
                listValues = res.getStringArray(R.array.vegetablesEng_array);
                listValuesInKan = res.getStringArray(R.array.vegetablesKan_array);
                break;
            case "colors":
                listValues = res.getStringArray(R.array.colorsEng_array);
                listValuesInKan = res.getStringArray(R.array.colorsKan_array);
                break;
            case "directions":
                listValues = res.getStringArray(R.array.directionsEng_array);
                listValuesInKan = res.getStringArray(R.array.directionsKan_array);
                break;
            default:
                listValues = res.getStringArray(R.array.vegetablesKan_array);
                listValuesInKan = res.getStringArray(R.array.vegetablesKan_array);
                break;
        }

        for (int i = 0; i < listValues.length; i++) {
            input.add(listValues[i]);
            kanInput.add(listValuesInKan[i]);
        }

        adapter = new ListViewAdapter(getApplicationContext(), input, category, kanInput);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
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
}

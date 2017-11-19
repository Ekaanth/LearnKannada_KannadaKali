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
import android.widget.Toast;

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

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        String day = intent.getStringExtra("position");

        List<String> input = new ArrayList<>();
        List<String> kanInput = new ArrayList<>();

        String[] listValues = null, listValuesInKan = null;

        Resources res = getResources();

        switch(day)
        {
            case "Day 1": {
                listValues = res.getStringArray(R.array.day1_content);
                listValuesInKan = res.getStringArray(R.array.day1_content_kannada);
                break;
            }
            default:
                break;
        }


        for (int i = 0; i < listValues.length; i++) {
            input.add(listValues[i]);
            kanInput.add(listValuesInKan[i]);
        }

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

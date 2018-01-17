package app.learnkannada.com.learnkannadakannadakali;

import android.content.Intent;
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

import adapter.ListViewAdapter;
import constants.Constants;

public class CategoriesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ListViewAdapter adapter;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewID);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(Constants.CATEGORIES);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List<String> input = new ArrayList<>();
        List<String> kanInput = new ArrayList<>();
        String listValues[] = null;
        String listValueKan[] = null;

        String from = getIntent().getStringExtra(Constants.FROM);

        if (from.equals(Constants.WORDS)) {
            listValues = getResources().getStringArray(R.array.flexi_words_array);
            listValueKan = getResources().getStringArray(R.array.flexi_words_kanArray);
            adapter = new ListViewAdapter(getApplicationContext(), input, Constants.FLEXI_WORDS, kanInput);
        } else {
            listValues = getResources().getStringArray(R.array.flexi_conversations_array);
            listValueKan = getResources().getStringArray(R.array.flexi_conversations_kanArray);
            adapter = new ListViewAdapter(getApplicationContext(), input, Constants.FLEXI_CONVERSATIONS, kanInput);
        }

        if (listValueKan.length == listValues.length)
            for (int i = 0; i < listValues.length; i++) {
                input.add(listValues[i]);
                kanInput.add(listValueKan[i]);
            }

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
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(CategoriesActivity.this, FlexiCourseHomeActivity.class));
        finish();
    }
}

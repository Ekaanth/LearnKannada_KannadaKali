package app.learnkannada.com.learnkannadakannadakali;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import adapter.ListViewAdapter;
import constants.Constants;
import utils.AudioPlayer;

public class CategoryContentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ListViewAdapter adapter;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_content);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewID);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List<String> input = new ArrayList<>();
        List<String> kanInput = new ArrayList<>();
        List<String> kanScriptsList = new ArrayList<>();

        Intent intent = getIntent();
        String category = intent.getStringExtra(Constants.CATEGORY);

        //setting Action bar title
        String actionBarTitle = category.substring(0,1).toUpperCase()+category.substring(1,category.length());
        getSupportActionBar().setTitle(actionBarTitle.replace("_"," "));

        String[] listValues = null , listValuesInKan = null , listValuesInKanScript = null;
        String englishArray = category.replaceAll(" ","")+Constants._CONTENT;
        String kannadaArray = category.replaceAll(" ","")+Constants._CONTENT_KANNADA;
        String kannadaScriptsArray = category.replaceAll(" ","") + Constants._CONTENT_INKANNADA;
        listValues = getResources().getStringArray(getResources().getIdentifier(englishArray,Constants.ARRAY,getPackageName()));
        listValuesInKan = getResources().getStringArray(getResources().getIdentifier(kannadaArray,Constants.ARRAY,getPackageName()));
        if(!category.equals(Constants.ALPHABETS))
        {
            listValuesInKanScript = getResources().getStringArray(getResources().getIdentifier(kannadaScriptsArray,Constants.ARRAY,getPackageName()));
        }

        for (int i = 0; i < listValues.length; i++) {
            input.add(listValues[i]);
            kanInput.add(listValuesInKan[i]);
            if(!category.equals(Constants.ALPHABETS))
                kanScriptsList.add(listValuesInKanScript[i]);
        }

        if(!category.equals(Constants.ALPHABETS))
            adapter = new ListViewAdapter(this, input, category, kanInput, kanScriptsList);
        else
            adapter = new ListViewAdapter(this, input, category, kanInput);
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
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
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
}
